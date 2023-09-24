package com.bootcamp.posapi.service.impl;

import com.bootcamp.posapi.config.CommonConstant;
import com.bootcamp.posapi.config.JwtService;
import com.bootcamp.posapi.entity.RoleEntity;
import com.bootcamp.posapi.entity.TokenEntity;
import com.bootcamp.posapi.entity.UserEntity;
import com.bootcamp.posapi.model.AuthRequest;
import com.bootcamp.posapi.model.AuthResponse;
import com.bootcamp.posapi.model.ProfileResponse;
import com.bootcamp.posapi.model.RegisterRequest;
import com.bootcamp.posapi.repository.RoleRepo;
import com.bootcamp.posapi.repository.TokenRepo;
import com.bootcamp.posapi.repository.UserRepo;
import com.bootcamp.posapi.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    @Override
     public Optional<AuthResponse> authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserEntity user = userRepo.findByEmail(request.getEmail()).orElse(null);
        if(user == null){
            log.info("User not found");
            return Optional.empty();
        }
        
        // return dengan panggil method getResponse
        return getResponse(user, false);
    }

    @Override
    public Optional<AuthResponse> register(RegisterRequest request) {
        if(request == null){
            return Optional.empty();
        }
        // check email, apakah sudah digunakan apa belum
        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            log.info("Email already in used");
            return Optional.empty();
        }
        UserEntity user = new UserEntity();
        //di copy semua data dari request ke object user
        BeanUtils.copyProperties(request, user);

        // set password diganti dengan enkripsi
        user.setPassword(encoder.encode(request.getPassword()));

        // get role
        RoleEntity role = getRole(request.getRole());
        if(role != null){
            //set value ke roles
            user.setRoles(List.of(role));
        }
        try{
            userRepo.save(user);
            log.info("Create user success");

            //jika berhail save, maka kembalikan nilai dengan panggilan method getResponse
            return getResponse(user, true);
        }catch (Exception e){
            log.error("Create user failed, error: {}",e.getMessage());
            //jika gagal   save, maka kemabalikan data kosong
            return Optional.empty();
        }

    }

    private RoleEntity getRole(String roleName) {
        RoleEntity role = roleRepo.findByName(roleName).orElse(null);
        if(role != null){
            return role;
        }
        role = roleRepo.findByName(CommonConstant.DEFAULT_ROLE).orElse(null);
        return role;
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith(CommonConstant.BEARER_VALUE)){
            return;
        }
        // di ambil String dari index 7
        refreshToken = authHeader.substring(7);
        // extract email dari token lewat method jwtService.extracUsername
        userEmail = jwtService.extractUsername(refreshToken);

        //jika userEmail nya tidak null, jika null maka lewat
        if(userEmail != null){
            // check use entity lewat userRepo.findByEmail
            // jika user tidak di temukan, maka berikan pesan error
            UserEntity user = userRepo.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User not found"));

            // jika tokennya valid
            if(jwtService.isTokenValid(refreshToken, user)){
                // call method get response
                Optional<AuthResponse> authResponse = this.getResponse(user, false);
                // set Response
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

    }

    @Override
    public Optional<ProfileResponse> profile(HttpServletRequest request, HttpServletResponse response) {
       final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
       final String refreshToken;
       final String userEmail;

       if(authHeader == null || !authHeader.startsWith(CommonConstant.BEARER_VALUE)){
           return Optional.empty();

       }
       // di ambil string dari index ke 7
        refreshToken = authHeader.substring(7);
       // extract email dari token lewat mthod jwtService.extractUsername
        userEmail = jwtService.extractUsername(refreshToken);

        //jika userEmail nya null maka return empty
        if(userEmail == null){
            return Optional.empty();
        }
        // jika user tidak ditemukan, maka berikan pesan error
        UserEntity user = userRepo.findByEmail(userEmail).orElse(null);//.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(user == null){
            return Optional.empty();
        }
        ProfileResponse result = new ProfileResponse();
        BeanUtils.copyProperties(user, result);
        // get role dari user ..
        List<String> roles = user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList());
        result.setRoles(roles);
        // return
        return Optional.of(result);

        }

    private Optional<AuthResponse> getResponse(UserEntity user, boolean isRegister) {
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        // save token
        this.saveUserToken(user.getId(), jwtToken);
        
        // when not register
        if(!isRegister)
            this.revokeAllUserTokens(user);

        //generate response
        var result = new AuthResponse(jwtToken, refreshToken);

        return Optional.of(result);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
        if( validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    private void saveUserToken(Long userId, String jwtToken) {
        var token = new TokenEntity();
        token.setUserId(userId);
        token.setToken(jwtToken);
        token.setTokenType(CommonConstant.BEARER_TYPE);
        token.setExpired(false);
        token.setRevoked(false);
        try{
            tokenRepo.save(token);
            log.info("Save token success");
        }catch (Exception e){
            log.error("Save token failed, error: {}", e.getMessage());
        }

    }


}

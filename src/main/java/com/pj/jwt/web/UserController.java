package com.pj.jwt.web;

import com.pj.jwt.dto.AuthorityDTO;
import com.pj.jwt.dto.UserDTO;
import com.pj.jwt.security.CustomUserDetails;
import com.pj.jwt.security.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping(value = {"/get_logged_in_user", "/home"})
    public UserDTO getLoggedInUser() {
        return mapUserAndReturnJwtToken(SecurityContextHolder.getContext().getAuthentication(), false);
    }

    private UserDTO mapUserAndReturnJwtToken(Authentication authentication, boolean generateToken) {
        var customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(customUserDetails.getUsername());
        userDTO.setEnabled(customUserDetails.isEnabled());
        userDTO.setAccountNonExpired(customUserDetails.isAccountNonExpired());
        userDTO.setAccountNonLocked(customUserDetails.isAccountNonLocked());
        userDTO.setCredentialsNonExpired(customUserDetails.isCredentialsNonExpired());
        userDTO.setAuthorities(mapAuthorities(customUserDetails.getAuthorities()));
        if (generateToken) {
            var jwtToken = jwtUtil.generateToken(customUserDetails);
            userDTO.setToken(jwtToken);
            userDTO.setTimeBeforeExpiration(jwtUtil.extractExpiration(jwtToken));
        }
        return userDTO;
    }

    private Set<AuthorityDTO> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<AuthorityDTO> authorityDTOList = new HashSet<>();
        authorities.forEach(grantedAuthority -> authorityDTOList.add(new AuthorityDTO(grantedAuthority.getAuthority())));
        return authorityDTOList;
    }

    @PostMapping(value = {"/authenticate", "/login"})
    public UserDTO loginUser(@RequestParam String username, @RequestParam String password) {
        //log.info("Login attempted at {} from User:{} ", Instant.now(), username);
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return mapUserAndReturnJwtToken(authentication, true);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(null);
        try {
            request.logout();
        } catch (ServletException e) {
            log.error("Error logging out user", e);
        }
    }
}
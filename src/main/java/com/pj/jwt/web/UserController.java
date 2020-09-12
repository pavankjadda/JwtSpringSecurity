package com.pj.jwt.web;

import com.pj.jwt.domain.LoginRequest;
import com.pj.jwt.dto.AuthorityDTO;
import com.pj.jwt.dto.UserDTO;
import com.pj.jwt.security.CustomUserDetails;
import com.pj.jwt.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
public class UserController
{
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public UserController( AuthenticationManager authenticationManager, JwtUtil jwtUtil)
	{
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@GetMapping(value = {"/get_logged_in_user", "/home"})
	public Object getLoggedInUser(HttpServletRequest request)
	{
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@PostMapping(value = {"/authenticate","/login"})
	public Object loginUser(@RequestBody LoginRequest loginRequest)
	{
		Authentication authentication=authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		return mapUserAndReturnJwtToken(authentication,true);
	}

	private UserDTO mapUserAndReturnJwtToken(Authentication authentication, boolean generateToken)
	{
		CustomUserDetails customUserDetails= (CustomUserDetails) authentication.getPrincipal();

		UserDTO userDTO=new UserDTO();
		userDTO.setUsername(customUserDetails.getUsername());
		userDTO.setEnabled(customUserDetails.isEnabled());
		userDTO.setAccountNonExpired(customUserDetails.isAccountNonExpired());
		userDTO.setAccountNonLocked(customUserDetails.isAccountNonLocked());
		userDTO.setCredentialsNonExpired(customUserDetails.isCredentialsNonExpired());
		userDTO.setAuthorities(mapAuthorities((Collection<GrantedAuthority>) customUserDetails.getAuthorities()));
		if(generateToken)
			userDTO.setToken(jwtUtil.generateToken(customUserDetails));
		return userDTO;
	}

	private Set<AuthorityDTO> mapAuthorities(Collection<GrantedAuthority> authorities)
	{
		Set<AuthorityDTO> authorityDTOList=new HashSet<>();
		authorities.forEach(grantedAuthority -> authorityDTOList.add(new AuthorityDTO(grantedAuthority.getAuthority())));
		return authorityDTOList;
	}

	@GetMapping("/logout")
	public void logout(HttpServletRequest request)
	{
		SecurityContextHolder.getContext().setAuthentication(null);
		try
		{
			request.logout();
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
	}
}
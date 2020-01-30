package com.pj.jwt.web;

import com.pj.jwt.domain.LoginRequest;
import com.pj.jwt.dto.AuthorityDTO;
import com.pj.jwt.dto.UserDTO;
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
		org.springframework.security.core.userdetails.User user= (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		UserDTO userDTO=new UserDTO();
		userDTO.setUsername(user.getUsername());
		userDTO.setEnabled(user.isEnabled());
		userDTO.setAccountNonExpired(user.isAccountNonExpired());
		userDTO.setAccountNonLocked(user.isAccountNonLocked());
		userDTO.setCredentialsNonExpired(user.isCredentialsNonExpired());
		userDTO.setAuthorities(mapAuthorities(user.getAuthorities()));
		if(generateToken)
			userDTO.setToken(jwtUtil.generateToken(user));
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

package com.pj.jwt.security;

import com.pj.jwt.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails
{
	private User user;

	private Collection<? extends GrantedAuthority> roles;

	public CustomUserDetails(User user, Collection<? extends GrantedAuthority> roles)
	{
		this.user = user;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return roles;
	}

	@Override
	public String getPassword()
	{
		return user.getPassword();
	}

	@Override
	public String getUsername()
	{
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return user.getAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return user.getAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return user.getCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled()
	{
		return user.getActive();
	}
}

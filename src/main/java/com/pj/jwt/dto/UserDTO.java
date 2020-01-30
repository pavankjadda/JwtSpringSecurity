package com.pj.jwt.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserDTO
{
	private String username;
	private String token;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private Date timeBeforeExpiration;
	private Date graceLoginsRemaining;
	private Set<AuthorityDTO> authorities;
}

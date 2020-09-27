package com.pj.jwt.dto;

import lombok.Data;

@Data
public class AuthorityDTO
{
	private String authority;

	public AuthorityDTO(String authority)
	{
		this.authority = authority;
	}
}

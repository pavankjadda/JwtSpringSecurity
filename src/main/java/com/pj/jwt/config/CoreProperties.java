package com.pj.jwt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(value = "core")
@Configuration
@Data
public class CoreProperties
{
	private String jwtSecret;
}

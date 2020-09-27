package com.pj.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(value = "core")
@Configuration
public class CoreProperties
{
	private String jwtSecret;

	public CoreProperties()
	{
	}

	public String getJwtSecret()
	{
		return this.jwtSecret;
	}

	public void setJwtSecret(String jwtSecret)
	{
		this.jwtSecret = jwtSecret;
	}

	public boolean equals(final Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof CoreProperties))
			return false;
		final CoreProperties other = (CoreProperties) o;
		if (!other.canEqual((Object) this))
			return false;
		final Object this$jwtSecret = this.getJwtSecret();
		final Object other$jwtSecret = other.getJwtSecret();
		if (this$jwtSecret == null ? other$jwtSecret != null : !this$jwtSecret.equals(other$jwtSecret))
			return false;
		return true;
	}

	protected boolean canEqual(final Object other)
	{
		return other instanceof CoreProperties;
	}

	public int hashCode()
	{
		final int PRIME = 59;
		int result = 1;
		final Object $jwtSecret = this.getJwtSecret();
		result = result * PRIME + ($jwtSecret == null ? 43 : $jwtSecret.hashCode());
		return result;
	}

	public String toString()
	{
		return "CoreProperties(jwtSecret=" + this.getJwtSecret() + ")";
	}
}

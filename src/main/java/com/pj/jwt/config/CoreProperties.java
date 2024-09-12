package com.pj.jwt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Setter
@Getter
@ConfigurationProperties(value = "core")
@Configuration
public class CoreProperties {
    private String jwtSecret;

    public CoreProperties() {
        // empty constructor
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CoreProperties;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $jwtSecret = this.getJwtSecret();
        result = result * PRIME + ($jwtSecret == null ? 43 : $jwtSecret.hashCode());
        return result;
    }

    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CoreProperties other))
            return false;
        if (!other.canEqual(this))
            return false;
        final Object this$jwtSecret = this.getJwtSecret();
        final Object other$jwtSecret = other.getJwtSecret();
        return Objects.equals(this$jwtSecret, other$jwtSecret);
    }

    public String toString() {
        return "CoreProperties(jwtSecret=" + this.getJwtSecret() + ")";
    }
}
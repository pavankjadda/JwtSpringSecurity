package com.pj.jwt.dto;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class UserDTO {
    private String username;
    private String token;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Date timeBeforeExpiration;
    private Date graceLoginsRemaining;
    private Set<AuthorityDTO> authorities;

    public UserDTO() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getTimeBeforeExpiration() {
        return this.timeBeforeExpiration;
    }

    public void setTimeBeforeExpiration(Date timeBeforeExpiration) {
        this.timeBeforeExpiration = timeBeforeExpiration;
    }

    public Date getGraceLoginsRemaining() {
        return this.graceLoginsRemaining;
    }

    public void setGraceLoginsRemaining(Date graceLoginsRemaining) {
        this.graceLoginsRemaining = graceLoginsRemaining;
    }

    public Set<AuthorityDTO> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<AuthorityDTO> authorities) {
        this.authorities = authorities;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $token = this.getToken();
        result = result * PRIME + ($token == null ? 43 : $token.hashCode());
        result = result * PRIME + (this.isAccountNonExpired() ? 79 : 97);
        result = result * PRIME + (this.isAccountNonLocked() ? 79 : 97);
        result = result * PRIME + (this.isCredentialsNonExpired() ? 79 : 97);
        result = result * PRIME + (this.isEnabled() ? 79 : 97);
        final Object $timeBeforeExpiration = this.getTimeBeforeExpiration();
        result = result * PRIME + ($timeBeforeExpiration == null ? 43 : $timeBeforeExpiration.hashCode());
        final Object $graceLoginsRemaining = this.getGraceLoginsRemaining();
        result = result * PRIME + ($graceLoginsRemaining == null ? 43 : $graceLoginsRemaining.hashCode());
        final Object $authorities = this.getAuthorities();
        result = result * PRIME + ($authorities == null ? 43 : $authorities.hashCode());
        return result;
    }

    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserDTO other))
            return false;
        if (!other.canEqual(this))
            return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (!Objects.equals(this$username, other$username))
            return false;
        final Object this$token = this.getToken();
        final Object other$token = other.getToken();
        if (!Objects.equals(this$token, other$token))
            return false;
        if (this.isAccountNonExpired() != other.isAccountNonExpired())
            return false;
        if (this.isAccountNonLocked() != other.isAccountNonLocked())
            return false;
        if (this.isCredentialsNonExpired() != other.isCredentialsNonExpired())
            return false;
        if (this.isEnabled() != other.isEnabled())
            return false;
        final Object this$timeBeforeExpiration = this.getTimeBeforeExpiration();
        final Object other$timeBeforeExpiration = other.getTimeBeforeExpiration();
        if (!Objects.equals(this$timeBeforeExpiration, other$timeBeforeExpiration))
            return false;
        final Object this$graceLoginsRemaining = this.getGraceLoginsRemaining();
        final Object other$graceLoginsRemaining = other.getGraceLoginsRemaining();
        if (!Objects.equals(this$graceLoginsRemaining, other$graceLoginsRemaining))
            return false;
        final Object this$authorities = this.getAuthorities();
        final Object other$authorities = other.getAuthorities();
        return Objects.equals(this$authorities, other$authorities);
    }

    public String toString() {
        return "UserDTO(username=" + this.getUsername() + ", token=" + this.getToken() + ", accountNonExpired=" + this.isAccountNonExpired() +
                ", accountNonLocked=" + this.isAccountNonLocked() + ", credentialsNonExpired=" + this.isCredentialsNonExpired() + ", enabled=" +
                this.isEnabled() + ", timeBeforeExpiration=" + this.getTimeBeforeExpiration() + ", graceLoginsRemaining=" + this.getGraceLoginsRemaining() +
                ", authorities=" + this.getAuthorities() + ")";
    }
}
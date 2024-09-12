package com.pj.jwt.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AuthorityDTO {
    private String authority;

    public AuthorityDTO(String authority) {
        this.authority = authority;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AuthorityDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $authority = this.getAuthority();
        result = result * PRIME + ($authority == null ? 43 : $authority.hashCode());
        return result;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AuthorityDTO other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$authority = this.getAuthority();
        final Object other$authority = other.getAuthority();
        return Objects.equals(this$authority, other$authority);
    }

    public String toString() {
        return "AuthorityDTO(authority=" + this.getAuthority() + ")";
    }
}
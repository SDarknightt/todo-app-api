package com.samu.todoapi.security;

import java.util.Collection;
import java.util.List;

import com.samu.todoapi.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
public class UserPrincipal implements UserDetails {
    private final User user;

	public UserPrincipal(@NotNull User user) {
		this.user = user;
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.user.getAuthority());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
	public String getUsername() {
        return this.user.getEmail();
    }

    @Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

    @Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User getUser() {
		return this.user;
	}
}

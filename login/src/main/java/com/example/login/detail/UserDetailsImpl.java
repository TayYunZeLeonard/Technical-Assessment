package com.example.login.detail;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.login.entity.User;

public class UserDetailsImpl implements UserDetails {
	
	private User user;
    
    public UserDetailsImpl(User user) {
        this.user = user;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
    	List<GrantedAuthority> authorities = new LinkedList<>();
    	authorities.add(authority);
        return authorities;
    }
 
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
	@Override
	public String getUsername() {
		return user.getUsername();
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
     
    public String getFullName() {
        return user.getName();
    }
    
    public String getRole() {
    	return user.getRole();
    }
}

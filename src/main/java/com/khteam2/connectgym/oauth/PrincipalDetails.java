package com.khteam2.connectgym.oauth;

import com.khteam2.connectgym.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class   PrincipalDetails implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 1L;
	private Member user;
	private Map<String, Object> attributes;

	public PrincipalDetails(Member user) {
		this.user = user;
	}

	public PrincipalDetails(Member user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	public Member getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return user.getUserPw();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
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
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		collet.add(()->{ return user.getUserEmail();});
		return collet;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return user.getUserId()+"";
	}
	
}

package com.khteam2.connectgym.oauth;

import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private MemberRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = userRepository.findByUserName(username);
		if(user == null) {
			return null;
		}else {
			return new PrincipalDetails(user);
		}
		
	}

}

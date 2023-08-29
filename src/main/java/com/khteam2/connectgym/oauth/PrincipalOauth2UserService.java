package com.khteam2.connectgym.oauth;


import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.member.MemberRepository;
import com.khteam2.connectgym.oauth.provider.GoogleUserInfo;
import com.khteam2.connectgym.oauth.provider.NaverUserInfo;
import com.khteam2.connectgym.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		return processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인");
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		}  else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
			System.out.println("네이버 로그인");
			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
		} else {
			System.out.println("구글 네이버 지원");
		}

		Optional<Member> userOptional =
				userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
		
		Member user;

		if (userOptional.isPresent()) {
			user = userOptional.get();
            user.setUserEmail(oAuth2UserInfo.getEmail());
			userRepository.save(user);
		} else {
            log.info("member 무결성 제약 = {}", oAuth2UserInfo.getProvider());
			user = Member.builder()
					.userId(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .userName(oAuth2UserInfo.getName())
					.userEmail(oAuth2UserInfo.getEmail())
					.provider(oAuth2UserInfo.getProvider())
					.providerId(oAuth2UserInfo.getProviderId())
					.build();
			userRepository.save(user);
		}

		return new PrincipalDetails(user, oAuth2User.getAttributes());
	}
}

package com.widget.ledger.web.authentication.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.widget.ledger.web.domain.State;
import com.widget.ledger.web.domain.User;
import com.widget.ledger.web.domain.UserProfile;
import com.widget.ledger.web.service.IUserService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	
	private static Logger LOG = LoggerFactory
			.getLogger(CustomUserDetailsService.class);

	/*@Autowired
	@Qualifier("userServiceImpl")
	private IUserService userServiceImpl;*/

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String ssoId)
			throws UsernameNotFoundException {
		return null;
		/*final User user = userServiceImpl.findBySso(ssoId);
		System.out.println("User : " + user);
		LOG.debug("User : "+user);
		if (user == null) {
			LOG.debug("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		return new org.springframework.security.core.userdetails.User(
				user.getSsoId(), user.getPassword(), user.getState().equalsIgnoreCase(
						State.ACTIVE.getState()), true, true, true,
				getGrantedAuthorities(user));*/
	}

	/**
	 * @param user
	 * @return
	 */
	private List<GrantedAuthority> getGrantedAuthorities(final User user) {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (final UserProfile userProfile : user.getUserProfiles()) {
			System.out.println("UserProfile : " + userProfile);
			authorities.add(new SimpleGrantedAuthority("ROLE_"
					+ userProfile.getType()));
		}
		System.out.print("authorities :" + authorities);
		return authorities;
	}

}

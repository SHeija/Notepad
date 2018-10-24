package com.haagahelia.notepad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.haagahelia.notepad.domain.User;
import com.haagahelia.notepad.domain.UserRepository;


@Service
public class UserDetailService implements UserDetailsService {
	
	private final UserRepository userRepo;

	@Autowired
	public UserDetailService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {   
    	User currentUser = userRepo.findByUsername(username);
        UserDetails user = new org.springframework.security.core.userdetails.User(username, currentUser.getPasswordHash(), 
        		AuthorityUtils.createAuthorityList(currentUser.getRole()));
        return user;
    }   

}
package com.haagahelia.notepad;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.haagahelia.notepad.domain.User;
import com.haagahelia.notepad.domain.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepo;
	
	@Before
	public void dropAll() {
		//Creating a controlled test environment = blank slate
		userRepo.deleteAll();
	}
	@Test
	public void saveTest() {
		User user = new User ("user","$2a$10$XI.iPh1Zig5XAj.mCwNFEeX2pJOSWOC9PBJrqsTB09t/ljQ1QThyS","email@email.com","USER");
		assertNotNull(user);
		userRepo.save(user);
		assertNotNull(userRepo.findById(user.getId()));
	}
	
	@Test
	public void deleteTest() {
		User user = new User ("user","$2a$10$XI.iPh1Zig5XAj.mCwNFEeX2pJOSWOC9PBJrqsTB09t/ljQ1QThyS","email@email.com","USER");
		userRepo.save(user);
		userRepo.delete(user);
		assertNull(userRepo.findByUsername(user.getUsername())); 
	}
	
	@Test
	public void searchTest() {
		User user = new User ("user","$2a$10$XI.iPh1Zig5XAj.mCwNFEeX2pJOSWOC9PBJrqsTB09t/ljQ1QThyS","email@email.com","USER");
		userRepo.save(user);
		assertNotNull(userRepo.findById(user.getId()));
		assertNotEquals(userRepo.findByRole("USER").size(), 0);
		assertNotNull(userRepo.findByUsername("user"));
	}
	

}

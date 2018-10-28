package com.haagahelia.notepad;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.haagahelia.notepad.web.NoteController;
import com.haagahelia.notepad.web.RestController;
import com.haagahelia.notepad.web.UserDetailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotepadApplicationTests {

	@Autowired
	private NoteController noteController;

	@Autowired
	private RestController restController;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Test
	public void contextLoads() throws Exception {
		assertNotNull(noteController);
		assertNotNull(restController);
		assertNotNull(userDetailService);
	}

}

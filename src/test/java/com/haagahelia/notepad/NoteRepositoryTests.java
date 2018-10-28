package com.haagahelia.notepad;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.haagahelia.notepad.domain.Note;
import com.haagahelia.notepad.domain.NoteRepository;
import com.haagahelia.notepad.domain.User;
import com.haagahelia.notepad.domain.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteRepositoryTests {

	@Autowired
	private NoteRepository noteRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Before
	public void dropAll() {
		//Creating a controlled test environment = blank slate
		noteRepo.deleteAll(); 
		userRepo.deleteAll();
	}
	
	@Test
	public void saveTest() {
		Note note = new Note("x","y");
		noteRepo.save(note);
		assertNotNull(note.getId());
		assertNotNull(note.getCreatedDate());
		assertNotNull(note.getUpdatedAt());
		assertNotNull(noteRepo.findById(note.getId()));
	}
	
	@Test
	public void deleteTest() {
		Note note = new Note("x","y");
		noteRepo.save(note);
		noteRepo.delete(note);
		assertEquals(noteRepo.findByTitle("x").size(), 0); 
	}
	
	@Test
	public void findAllTest() {
		assertNotNull(noteRepo.findAll());
	}
	
	@Test
	public void findByIdTest() {
		Note note = new Note("x","y");
		noteRepo.save(note);
		assertNotNull(noteRepo.findById(note.getId()));
	}

	@Test
	public void findByTitleTest() {
		String title = "x";
		Note note = new Note(title,"y");
		noteRepo.save(note);
		assertNotNull(noteRepo.findByTitle(title));
	}
	
	@Test
	public void findByOwnerTest() {
		User user = new User("user", "$2a$10$VV9CqMVjVtZsQEsc4LaERO2C0Lu1BdmbFAbTCAgBrgHu1GGkLmIim", "user@email.com", "USER");
		userRepo.save(user);
		Note note = new Note("x","y",user);
		noteRepo.save(note);
		assertEquals(noteRepo.findByOwner(user).get(0).getId(), note.getId());
	}
	
}

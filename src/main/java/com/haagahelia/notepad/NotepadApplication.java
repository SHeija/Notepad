package com.haagahelia.notepad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.haagahelia.notepad.domain.Note;
import com.haagahelia.notepad.domain.NoteRepository;
import com.haagahelia.notepad.domain.User;
import com.haagahelia.notepad.domain.UserRepository;

@SpringBootApplication
@EnableJpaAuditing
public class NotepadApplication {
	
	//Logger
	private static final Logger logger = LoggerFactory.getLogger(NotepadApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NotepadApplication.class, args);
	}
	
	//CommandLineRunner
		@Bean
		public CommandLineRunner demo (NoteRepository noteRepo, UserRepository userRepo) {
			return (args) -> {
				logger.info("Creating test users");
				//admin, pass: admin
				User admin = new User("admin", "$2a$10$PFGMaYCLb2A2zxdxUtJBHOuXD5EO0gA/JIRp7KgiHjFe40WVVzQ5a", "admin@email.com", "ADMIN");
				//user, pass: user
				User user = new User("user", "$2a$10$VV9CqMVjVtZsQEsc4LaERO2C0Lu1BdmbFAbTCAgBrgHu1GGkLmIim", "user@email.com", "USER");
				
				logger.info("Saving demo users");
				userRepo.save(admin);
				userRepo.save(user);
				
				logger.info("Creating demo notes");
				Note note1 = new Note("Test1","Lorem ipsum -admin", admin);
				Note note2 = new Note("Testi2", "Lorem ipsum -user", user);
				
				logger.info("Saving demo notes");
				noteRepo.save(note1);
				noteRepo.save(note2);
				
				
			};
		}
	
}

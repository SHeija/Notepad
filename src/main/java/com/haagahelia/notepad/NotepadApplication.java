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
			logger.info("Creating demo notes");
			Note note1 = new Note("Test1","Lorem ipsum");
			Note note2 = new Note("Testi2", "Lorem ipsum");
			
			logger.info("Saving demo notes");
			noteRepo.save(note1);
			noteRepo.save(note2);
			
			logger.info("Creating test users");
			//admin, pass: admin
			User admin = new User("admin", "$2a$04$6kI461rTmPYiTsVdqfPyLu6WApsx0DJzjVPi2J1cbR8YyH9nof7vC", "admin@email.com", "ADMIN");
			//user, pass: user
			User user = new User("user", "$2a$04$IOru3M7tt5HxRdXTkRLOte.fjB/N/4aHy1CDdXeaj3.6lQFrqtq5y", "user@email.com", "USER");
			
			logger.info("Saving demo users");
			userRepo.save(admin);
			userRepo.save(user);
		};
	}
	
}

package com.haagahelia.notepad.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haagahelia.notepad.domain.Note;
import com.haagahelia.notepad.domain.NoteRepository;
import com.haagahelia.notepad.domain.User;
import com.haagahelia.notepad.domain.UserRepository;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private NoteRepository noteRepo;

	@Autowired
	private UserRepository userRepo;
	
	//REST API
	
	//TODO: stuff should probs be authenticated but I can't get it to work
	
	//get ALL notes
	@RequestMapping(value = "/api/notes", method=RequestMethod.GET)
	public List<Note> noteListRest(){
		return (List<Note>) noteRepo.findAll();
	}
	
    //get one user's notes
    @RequestMapping(value = "/api/{username}/notes", method=RequestMethod.GET)
    public List<Note> userNoteListRest(@PathVariable("username") String username){
    	User owner = userRepo.findByUsername(username);
    	return (List<Note>) noteRepo.findByOwner(owner);
    }
    
	//get one note
	@RequestMapping(value = "/api/note/{id}", method=RequestMethod.GET)
	public Optional<Note> noteGetRest(@PathVariable("id") Long id) {
		return noteRepo.findById(id);
	}
	
	//save new note to a user
	@PostMapping("/api/{username}/notes/save")
	public void noteSaveRest(@RequestBody Note note, @PathVariable("username") String username) {
		note.setOwner(userRepo.findByUsername(username));
		noteRepo.save(note);
	}
	
	//update note
	@RequestMapping(value="/api/note/{id}", method=RequestMethod.PUT)
	public void noteUpdateRest(@RequestBody Note note) {
		noteRepo.save(note); //if the id in the note object already exists, .save updates the entry instead.
	}

	//delete via REST
	@RequestMapping(value = "/api/note/{id}", method=RequestMethod.DELETE)
	public void noteDeleteRest(@PathVariable("id") Long id) {
		noteRepo.deleteById(id);
	}
}

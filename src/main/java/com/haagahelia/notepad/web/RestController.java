package com.haagahelia.notepad.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haagahelia.notepad.domain.Note;
import com.haagahelia.notepad.domain.NoteRepository;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private NoteRepository noteRepo;

	//REST API
	
	//get all notes 
	@RequestMapping(value = "/api/notes", method=RequestMethod.GET)
	public List<Note> noteListRest(){
		return (List<Note>) noteRepo.findAll();
	}
	
	//delete via REST
	@RequestMapping(value = "/api/note/{id}", method=RequestMethod.DELETE)
	public void noteDeleteRest(@PathVariable("id") Long id) {
		noteRepo.deleteById(id);
	}
	
	//get one note
	@RequestMapping(value = "/api/note/{id}", method=RequestMethod.GET)
	public Optional<Note> noteGetRest(@PathVariable("id") Long id) {
		return noteRepo.findById(id);
	}
	

}

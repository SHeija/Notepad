package com.haagahelia.notepad.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haagahelia.notepad.domain.Note;
import com.haagahelia.notepad.domain.NoteRepository;

@Controller
public class RestController {
	
	@Autowired
	private NoteRepository noteRepo;

	//REST API
	
	//get all notes 
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/api/notes", method=RequestMethod.GET)
	public @ResponseBody List<Note> noteListRest(){
		return (List<Note>) noteRepo.findAll();
	}
	
	//delete via REST
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/api/note/{id}", method=RequestMethod.DELETE)
	public @ResponseBody void noteDeleteRest(@PathVariable("id") Long id) {
		noteRepo.deleteById(id);
	}
	
	//get one note
	@RequestMapping(value = "/api/note/{id}", method=RequestMethod.GET)
	public @ResponseBody Optional<Note> noteGetRest(@PathVariable("id") Long id) {
		return noteRepo.findById(id);
	}
	

}

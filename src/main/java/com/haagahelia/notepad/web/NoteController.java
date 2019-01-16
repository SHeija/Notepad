package com.haagahelia.notepad.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haagahelia.notepad.domain.Note;
import com.haagahelia.notepad.domain.NoteRepository;
import com.haagahelia.notepad.domain.User;
import com.haagahelia.notepad.domain.UserRepository;

@Controller
public class NoteController {
	
	@Autowired
	private NoteRepository noteRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	//Login
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}
	
	//List own notes
	@RequestMapping({"/", "/notelist"})
	public String noteList(Model model, Principal principal) {
		boolean adminViewOn = false; //toggles "admin superview" button
    	model.addAttribute("adminView", adminViewOn);
		
	    String name = principal.getName(); //get logged in username
	    User user = userRepo.findByUsername(name); //get logged in user object based on username
		model.addAttribute("notes", noteRepo.findByOwner(user));
		return "notelist";
	}
	
	//List all notes AKA admin superview
    @PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping({"/admin"})
	public String noteList(Model model) {
    	boolean adminViewOn = true; //toggles "admin superview" button
    	model.addAttribute("adminView", adminViewOn);
    	
		model.addAttribute("notes", noteRepo.findAll());
		return "notelist";
	}
	
	
	//add note via html
	@RequestMapping("/add")
	public String noteAdd(Model model) {
		model.addAttribute("note", new Note());
		return "addnote";
	}
	
	//save note via html
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String noteSave(Note note, Principal principal) {
		String name = principal.getName(); //get logged in username
	    User user = userRepo.findByUsername(name); //get logged in user object based on username
	    note.setOwner(user); //Set current user as owner
		noteRepo.save(note);
		return "redirect:notelist";
	}
	
	//delete via html
    @PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String noteDelete(@PathVariable("id") Long id) {
		noteRepo.deleteById(id);
		return "redirect:../notelist";
	}
	
	//edit via html
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String noteEdit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("note", noteRepo.findById(id));
		return "editnote";
	}
	
	

}

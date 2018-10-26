package com.haagahelia.notepad.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	    String name = principal.getName(); //get logged in username
	    User user = userRepo.findByUsername(name); //get logged in user based on username
		model.addAttribute("notes", noteRepo.findByOwner(user));
		return "notelist";
	}
	
	
	//List all notes AKA admin superview
    @PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping({"/admin"})
	public String noteList(Model model) {
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
	    User user = userRepo.findByUsername(name); //get logged in user based on username
	    note.setOwner(user); //Set current user as owner
		noteRepo.save(note);
		return "redirect:notelist";
	}
	
	//delete via html
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
	
	
	//REST API
	
	//get all notes 
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/notes", method=RequestMethod.GET)
	public @ResponseBody List<Note> noteListRest(){
		return (List<Note>) noteRepo.findAll();
	}
	
	//delete via REST
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/note/{id}", method=RequestMethod.DELETE)
	public String noteDeleteRest(@PathVariable("id") Long id) {
		noteRepo.deleteById(id);
		return "redirect:../notelist";
	}

}

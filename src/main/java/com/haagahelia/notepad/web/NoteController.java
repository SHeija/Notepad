package com.haagahelia.notepad.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haagahelia.notepad.domain.Note;
import com.haagahelia.notepad.domain.NoteRepository;

@Controller
public class NoteController {
	
	@Autowired
	private NoteRepository noteRepo;
	
	//Login
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}
	
	//List all
	@RequestMapping("/notelist")
	public String noteList(Model model) {
		model.addAttribute("notes", noteRepo.findAll());
		return "notelist";
	}
	
	//REST API
	
	//get all notes TEST!
	@RequestMapping(value = "/notes", method=RequestMethod.GET)
	public @ResponseBody List<Note> noteListRest(){
		return (List<Note>) noteRepo.findAll();
	}

}

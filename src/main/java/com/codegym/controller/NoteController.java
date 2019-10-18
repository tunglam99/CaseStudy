package com.codegym.controller;

import com.codegym.model.Note;
import com.codegym.model.NoteType;
import com.codegym.service.NoteService;
import com.codegym.service.NoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private NoteTypeService noteTypeService;

    @ModelAttribute("noteTypes")
    public Iterable<NoteType> noteTypes(){
        return noteTypeService.findAll();
    }

    @GetMapping("/notes")
    public ModelAndView listNote(@RequestParam("s") Optional<String> s,@PageableDefault(value =6) Pageable pageable){
        Page<Note> notes ;
        if (s.isPresent()){
            notes = noteService.findAllByTitleContaining(s.get(),pageable);
        } else{
            notes = noteService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/note/list");
        modelAndView.addObject("notes",notes);
        return modelAndView;
    }

    @GetMapping("/create-note")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/note/create");
        modelAndView.addObject("note",new Note());
        return modelAndView;
    }

    @PostMapping("/save-note")
    public ModelAndView saveNote(@ModelAttribute Note note, BindingResult result){
        if (result.hasFieldErrors()){
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        noteService.save(note);

        ModelAndView modelAndView = new ModelAndView("/note/create");
        modelAndView.addObject("note",new Note());
        modelAndView.addObject("message","New Note created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-note/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Note note = noteService.findById(id);
        if (note != null){
            ModelAndView modelAndView = new ModelAndView("/note/edit");
            modelAndView.addObject("note",note);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error:404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-note")
    public String updateNote(@ModelAttribute("note") Note note, BindingResult result){
        if (result.hasFieldErrors()){
            System.out.println("Result Error Occured" + result.getAllErrors());
        }
        noteService.save(note);
        return "redirect:/notes";
    }

    @GetMapping("/delete-note/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Note note = noteService.findById(id);
        if (note != null){
            ModelAndView modelAndView = new ModelAndView("/note/delete");
            modelAndView.addObject("note", note);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error:404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-note")
    public String deleteNote(@ModelAttribute("note") Note note){
        noteService.remove(note.getId());
        return "redirect:/notes";
    }

    @GetMapping("/view-note/{id}")
    public ModelAndView viewNote(@PathVariable Long id, Model model){
        Note notes = noteService.findById(id);
        if(notes!=null){
            ModelAndView modelAndView = new ModelAndView("/note/view");
            modelAndView.addObject("notes",notes);
            return modelAndView;
        }else{
            ModelAndView modelAndView = new ModelAndView("error-404");
            return modelAndView;
        }
    }

    /*@PostMapping("/update-note")
    public String updateView(@ModelAttribute("note") Note note, BindingResult result){
        if (result.hasFieldErrors()){
            System.out.println("Result Error Occured" + result.getAllErrors());
        }
        noteService.save(note);
        return "redirect:/notes";
    }*/


    @GetMapping("note/excel")
    public ModelAndView exportNoteExcel() throws IOException {
        File file = noteService.exportExcel();
        ModelAndView modelAndView = new ModelAndView("/note/export", "message","Export done. File: "+  file.getAbsolutePath() );
        return modelAndView;
    }


    @GetMapping("/note/importExcel")
    public ModelAndView importExcel() throws IOException {
        noteService.importExcel();

        ModelAndView modelAndView = new ModelAndView("redirect:/notes");
        modelAndView.addObject("message","Import successful");
        return modelAndView;
    }

}

package com.codegym.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "noteTypes")
public class NoteType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @OneToMany(targetEntity = Note.class)
    private List<Note> notes;

    public NoteType() {
    }

    public NoteType(String name, String description, List<Note> notes) {
        this.name = name;
        this.description = description;
        this.notes = notes;
    }

    public NoteType(Long id, String name, String description, List<Note> notes) {
        this.name = name;
        this.description = description;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}

package com.example.sqldz41.controller;

import com.example.sqldz41.record.FacultyRecord;
import com.example.sqldz41.record.StudentRecord;
import com.example.sqldz41.service.FacultyService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public FacultyRecord create(@RequestBody FacultyRecord facultyRecord) {
        return facultyService.create(facultyRecord);
    }

    @GetMapping("/{id}")
    public FacultyRecord read(@PathVariable long id) {
        return facultyService.read(id);
    }

    @PutMapping("/{id}")
    public FacultyRecord update(@PathVariable long id, @RequestBody FacultyRecord facultyRecord) {
        return facultyService.update(id, facultyRecord);
    }

    @DeleteMapping("/{id}")
    public FacultyRecord delete(@PathVariable long id) {
        return facultyService.delete(id);
    }

    @GetMapping(params = "color")
    public Collection<FacultyRecord> findByColor(@RequestParam String color) {
        return facultyService.findByColor(color);
    }

    @GetMapping(params = "colorOnName")
    public Collection<FacultyRecord> findByColorOnName(@RequestParam String colorOnName) {
        return facultyService.findByColorOnName(colorOnName);
    }

    @GetMapping("/{id}/students")
    public Collection<StudentRecord> getStudentsByFaculty(@PathVariable long id) {
        return facultyService.getStudentsByFaculty(id);
    }
}

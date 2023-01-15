package com.example.sqldz41.controller;


import com.example.sqldz41.record.FacultyRecord;
import com.example.sqldz41.record.StudentRecord;
import com.example.sqldz41.service.StudentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;


@RestController
@RequestMapping("/students")
@Validated
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public StudentRecord create(@RequestBody StudentRecord studentRecord) {
        return studentService.create(studentRecord);
    }

    @GetMapping("/{id}")
    public StudentRecord read(@PathVariable long id) {
        return studentService.read(id);
    }

    @PutMapping("/{id}")
    public StudentRecord update(@PathVariable long id,
                          @RequestBody StudentRecord studentRecord) {
        return studentService.update(id,studentRecord);
    }

    @DeleteMapping ("/{id}")
    public StudentRecord delete(@PathVariable long id) {
        return studentService.delete(id);
    }

    @GetMapping(params="age")
    public Collection<StudentRecord> findByAge(@RequestParam int age) {
        return studentService.findByAge(age);
    }

    @GetMapping(params={"MinAge","MaxAge"})
    public Collection<StudentRecord> findByAgeBetween(@RequestParam int MinAge,
                                                      @RequestParam int MaxAge) {
        return studentService.findByAgeBetween(MinAge,MaxAge);
    }
    @GetMapping("/{id}/faculty")
    public FacultyRecord getFacultyByStudent(@PathVariable long id) {
        return studentService.getFacultyByStudent(id);
    }

    @PatchMapping("/{id}/avatar")
    public StudentRecord patchStudentAvatar(@PathVariable long id,
                             @RequestParam("avatarId") long avatarId) {
       return studentService.patchStudentAvatar(id, avatarId);

    }

    @GetMapping("/totalCount")
    public int totalCountOfStudents() {
        return studentService.totalCountOfStudents();
    }

    @GetMapping("/averageAge")
    public double averageAgeOfStudents () {
        return studentService.averageAgeOfStudents();
    }

    @GetMapping("/lastStudents")
    public List<StudentRecord> lastStudents(@RequestParam @Min(1) @Max(10) int count) {
        return studentService.lastStudents(count);
    }

    @GetMapping("/findStudentNamesWhichStartedWithA")
    public Stream<String> findStudentNamesWhichStartedWithA() {
        return studentService.findStudentNamesWhichStartedWithA();
    }

    @GetMapping("/findStudentAverageAge")
    public Stream<String> findStudentAverageAge() {
        return studentService.findStudentAverageAge();
    }







}

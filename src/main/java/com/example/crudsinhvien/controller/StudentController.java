package com.example.crudsinhvien.controller;

import com.example.crudsinhvien.entity.Students;
import com.example.crudsinhvien.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Students createStudent(@RequestBody Students student) {
        return studentService.createStudent(student);
    }

    @GetMapping
    public List<Students> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Students getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public Students updateStudent(@PathVariable Long id, @RequestBody Students student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}

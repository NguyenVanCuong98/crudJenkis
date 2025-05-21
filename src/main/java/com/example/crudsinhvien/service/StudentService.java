package com.example.crudsinhvien.service;

import com.example.crudsinhvien.entity.Students;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Students createStudent(Students student);
    List<Students> getAllStudents();
    Students getStudentById(Long id);
    Students updateStudent(Long id, Students student);
    void deleteStudent(Long id);
}

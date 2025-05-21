package com.example.crudsinhvien.service.impl;

import com.example.crudsinhvien.entity.Students;
import com.example.crudsinhvien.repository.StudentRepository;
import com.example.crudsinhvien.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Students createStudent(Students student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Students> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Students getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Override
    public Students updateStudent(Long id, Students student) {
        Students existingStudent = getStudentById(id);
        existingStudent.setName(student.getName());
        existingStudent.setDiem(student.getDiem());
        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}

package com.example.crudsinhvien.service.impl;

import com.example.crudsinhvien.entity.Students;
import com.example.crudsinhvien.repository.StudentRepository;
import com.example.crudsinhvien.service.StudentService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RedissonClient redissonClient;


    @Override
    public Students createStudent(Students student) {
        String lockKey = "student:create:" + student.getName();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if(lock.tryLock(5,10, TimeUnit.SECONDS)) {
                Optional<Students> existing = studentRepository.findByName(student.getName());
                if (existing.isPresent()) {
                    throw new RuntimeException("Student already exists with this email");
                }

                return studentRepository.save(student);
            }else{
                throw new RuntimeException("Another creation process is ongoing. Please try again.");
            }

        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted", e);
        }finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
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

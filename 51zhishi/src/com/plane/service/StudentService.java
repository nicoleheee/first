package com.plane.service;
import com.plane.entity.Student;

import java.util.List;

public interface StudentService {
    void addStudent(Student student);

    Student selectStudentById(int id);

    void updateStudent(Student student);

    void deleteStudentById(Integer id);

    List<Student> selectStudent();

    List<Student> queryStudent(int begin, int pageSize);

    int getTotStudentCount();
}

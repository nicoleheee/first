package com.plane.service.impl;

import com.plane.entity.Student;
import com.plane.mapper.StudentMapper;
import com.plane.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void addStudent(Student student){

        studentMapper.addStudent(student);
    }

    @Override
    public Student selectStudentById(int id){
        return studentMapper.selectStudentById(id);

    }

    @Override
    public void updateStudent(Student student){

        studentMapper.updateStudent(student);
    }

    @Override
    public void deleteStudentById(Integer id){
         studentMapper.deleteStudentById(id);

    }
    @Override
    public List<Student> selectStudent(){
        return studentMapper.selectStudent();
    }
    @Override
    public List<Student> queryStudent(int begin, int pageSize){
        return studentMapper.queryStudent(begin,pageSize);
    }
    @Override
    public int getTotStudentCount(){
        return studentMapper.getTotStudentCount();
    }



}

package com.plane.mapper;

import com.plane.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {
    void addStudent(Student student);

    Student selectStudentById(@Param("id") int id);

    void updateStudent(Student student);

    void deleteStudentById(int id);

    List<Student> selectStudent();

    List<Student> queryStudent(@Param("begin") int begin,
                               @Param("pageSize") int pageSize);
    //声明

    int getTotStudentCount();

}

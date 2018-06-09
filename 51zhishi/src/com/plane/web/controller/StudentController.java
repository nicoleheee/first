package com.plane.web.controller;

import com.plane.entity.Student;
import com.plane.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/add")
    public String add(){
        Student student=new Student();
        student.setName("Susan");
        student.setSex("female");
        student.setAge("18yrs");
        studentService.addStudent(student);
        return "/tpls/add";
    }
    @RequestMapping("/detail")
    public String detail(Integer id, Model model){
        Student stu=studentService.selectStudentById(id);
        model.addAttribute("student",stu);
        return "/tpls/student/detail";
    }
    @RequestMapping("/update")
    public String update(Student student,Model model){
        studentService.updateStudent(student);
        return "redirect:/detail?id="+student.getId();
    }
    @RequestMapping("/delete")
    public String delete(Integer id){
        studentService.deleteStudentById(id);
        return "redirect:/detail?id=1";
    }
    @RequestMapping("/list")
    public String list(Model model){
        List<Student> studentList=studentService.selectStudent();
        model.addAttribute("studentList", studentList);
        return "/tpls/student/list";
    }


}

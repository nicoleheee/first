package com.plane.web.controller;

import com.plane.entity.Student;
import com.plane.service.StudentService;
import com.plane.web.util.PageUtil;
import com.plane.web.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/add_ajax")
    @ResponseBody
    public JsonResult add_ajax(Student student){
        JsonResult result=new JsonResult(false);
        studentService.addStudent(student);
        result.setSuccess(true);
        result.setExtendData(student);
        return result;
    }

    @RequestMapping("/detail")
    public String detail(Integer id, Model model){
        Student stu=studentService.selectStudentById(id);
        model.addAttribute("student",stu);
        return "/tpls/student/detail";
    }
    @RequestMapping("/find_ajax")
    @ResponseBody
    public JsonResult find_ajax(Integer id){
        JsonResult result=new JsonResult(false);
        Student stu=studentService.selectStudentById(id);
        result.setSuccess(true);
        result.setExtendData(stu);
        return result;
    }

    @RequestMapping("/update")
    public String update(Student student,Model model){
        studentService.updateStudent(student);
        return "redirect:/detail?id="+student.getId();
    }
    @RequestMapping("/update_ajax")
    @ResponseBody
    public JsonResult update_jax(Student student){
        JsonResult result=new JsonResult(false);
        studentService.updateStudent(student);
        result.setSuccess(true);
        result.setExtendData(student);
        return result;
    }

    @RequestMapping("/delete")
    public String delete(Integer id){
        studentService.deleteStudentById(id);
        return "redirect:/detail?id=1";
    }
    @RequestMapping("/delete_ajax")
    @ResponseBody
    public JsonResult delete_ajax(Integer id){
        JsonResult result=new JsonResult(false);
        studentService.deleteStudentById(id);
        result.setSuccess(true);
        return result;
    }
    @RequestMapping("/list")
    public String list(Integer pageNo, Model model){
        int pageSize=5;
        if(pageNo==null)
            pageNo=1;
        int begin=(pageNo-1)*pageSize;
        List<Student> list=studentService.queryStudent(begin, pageSize);
        int count= studentService.getTotStudentCount();
        List<Integer> pageList=PageUtil.getPageList(count, pageSize);
        //计算总页数
        model.addAttribute("list", list);
        model.addAttribute("pageNo");
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("pageList",pageList);
        return "/tpls/student/list";
    }
}

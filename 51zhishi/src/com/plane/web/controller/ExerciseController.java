package com.plane.web.controller;

import com.plane.entity.Exercise;
import com.plane.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @RequestMapping("/adde")
    public String adde(){
        Exercise exercise=new Exercise();
        exercise.setShu("Chinese");
        exercise.setStudent_id(5);
        exerciseService.addExercise(exercise);
        return "/tpls/adde";
    }

    @RequestMapping("/deletee")
    public String deletee(Integer id){
        exerciseService.deleteExercise(id);
        return "/tpls/exercise/detaile";
    }



}

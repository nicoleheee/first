package com.plane.service.impl;

import com.plane.entity.Exercise;
import com.plane.mapper.ExerciseMapper;
import com.plane.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseMapper exerciseMapper;

    @Override
    public void addExercise(Exercise exercise){

        exerciseMapper.addExercise(exercise);
    }

    @Override
    public void deleteExercise(Integer id){
        exerciseMapper.deleteExercise(id);
    }

}

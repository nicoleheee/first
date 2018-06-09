package com.plane.mapper;

import com.plane.entity.Exercise;
import org.springframework.web.bind.annotation.RequestMapping;

public interface ExerciseMapper {
    @RequestMapping
    void addExercise(Exercise exercise);

    @RequestMapping
    void deleteExercise(Integer id);
}

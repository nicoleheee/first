package com.plane.service;

import com.plane.entity.Exercise;

public interface ExerciseService {
    void addExercise(Exercise exercise);

    void deleteExercise(Integer id);
}

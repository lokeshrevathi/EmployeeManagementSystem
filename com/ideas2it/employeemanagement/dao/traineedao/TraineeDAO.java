package com.ideas2it.employeemanagement.dao.traineedao;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.employeemanagement.model.traineemodel.Trainee;

public class TraineeDAO {
    static List<Trainee> traineeList = new ArrayList<Trainee>();

    public void addTrainee(Trainee trainee) {
        traineeList.add(trainee);
    }

    public List<Trainee> getTraineeList() {
        return traineeList;
    }

    public void updateTrainee(int traineeIndex, Trainee trainee) {
        traineeList.set(traineeIndex, trainee);
    }

    public void deleteTrainee(int traineeIndex) {
        traineeList.remove(traineeIndex);
    }
}
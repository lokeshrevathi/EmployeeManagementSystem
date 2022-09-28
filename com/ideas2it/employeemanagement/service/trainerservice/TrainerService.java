package com.ideas2it.employeemanagement.service.trainerservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

import com.ideas2it.employeemanagement.dao.traineedao.TraineeDAO;
import com.ideas2it.employeemanagement.dao.trainerdao.TrainerDAO;
import com.ideas2it.employeemanagement.model.traineemodel.Trainee;
import com.ideas2it.employeemanagement.model.trainermodel.Trainer;
import com.ideas2it.employeemanagement.exception.CustomException;
import com.ideas2it.employeemanagement.util.Role;
import com.ideas2it.employeemanagement.util.UtilValidation;

public class TrainerService {
    TrainerDAO trainerDAO = new TrainerDAO();

    public void addDetails(Trainer trainer) {
        trainerDAO.addTrainer(trainer);
    }

    public void assignTrainee(int id, Trainer trainer) {
        int trainerIndex = getTrainerIndex(id);
        trainerDAO.updateTrainer(trainerIndex, trainer);
    }

    public List<Trainer> getAllDetails() {
        return trainerDAO.getTrainerList();
    }

    public List<Trainee> getTraineeList(int id) {
        int trainerIndex = getTrainerIndex(id);
        return getAllDetails().get(trainerIndex).getTrainee();
    }

    public boolean ifTrainerListEmpty() {
        return trainerDAO.getTrainerList().isEmpty();
    }

    public int getTrainerIndex(int id) {
        int length = getAllDetails().size();

        for (int trainerIndex = 0; trainerIndex < length; trainerIndex++) {
            if ((getAllDetails().get(trainerIndex)
                .getId()) == id) {
                return trainerIndex;
            }
        }
        return 0;
    }

    public int getTraineeIndex(int trainerId, int traineeId) {
        int traineeIndex = 0;
        List<Trainee> traineeList = getTraineeList(trainerId);
        for (Trainee trainee : traineeList) {
            if (trainee.getId() == traineeId) {
                return traineeIndex;
            }
            traineeIndex++;
        }
        return 0;
    }

    public Trainer getTrainer(int id) {
        int trainerIndex = getTrainerIndex(id);
        return getAllDetails().get(trainerIndex);
    }

    public String displayTrainer(int id) {
        int trainerIndex = getTrainerIndex(id);
        return getAllDetails().get(trainerIndex).toString();
    }

    public boolean ifTrainerPresent(int id) throws CustomException {
        int length = getAllDetails().size();

        for (int trainerIndex = 0; trainerIndex < length; trainerIndex++) {
            if ((getAllDetails().get(trainerIndex)
                .getId()) == id) {
                return true;
            }
        }
        throw new CustomException("Trainer ID doesn't exist....!");
    }

    public void updateTrainer(int id, String fieldName,
                              String fieldValue) {
        float experience;
        Trainer trainer = getTrainer(id);
        int trainerIndex = getTrainerIndex(id);
        LocalDate dob;
        switch (fieldName) {
            case "name":
                trainer.setName(fieldValue);
                break;

            case "dob":
                dob = LocalDate.parse(fieldValue);
                trainer.setDob(dob);
                break;

            case "role":
                trainer.setRole(Role.valueOf(fieldValue));
                break;

            case "phoneNo":
                trainer.setPhoneNo(Long.parseLong(fieldValue));
                break;

            case "mailId":
                trainer.setMailId(fieldValue);
                break;

            case "experience":
                experience = Float.parseFloat(fieldValue);
                trainer.setExperience(experience);
                break;

            case "traineeList":
                
        }
        trainerDAO.updateTrainer(trainerIndex, trainer);
    }

    public void updateTrainee(int trainerId, int traineeId,
                              Trainee trainee) {
        int traineeIndex = getTraineeIndex(trainerId, traineeId);
        Trainer trainer = getTrainer(trainerId);
        trainer.getTrainee().set(traineeIndex, trainee);
        trainerDAO.updateTrainer(getTrainerIndex(trainerId), trainer);
    }

    public void updateTraineeList(List<Trainee> traineeList,
                                  int trainerId) {
        Trainer trainer = getTrainer(trainerId);
        trainer.setTrainee(traineeList);
        trainerDAO.updateTrainer(getTrainerIndex(trainerId), trainer);
    }

    public String getName(int id) {
        int trainerIndex = getTrainerIndex(id);
        return getAllDetails().get(trainerIndex).getName();
    }

    public Role getRole(int id) {
        int trainerIndex = getTrainerIndex(id);
        return getAllDetails().get(trainerIndex).getRole();
    }

    public void deleteTrainee(int trainerId, int traineeId) {
        int traineeIndex = getTraineeIndex(trainerId, traineeId);
        List<Trainee> traineeList = getTraineeList(trainerId);
        traineeList.remove(traineeIndex);
        Trainer trainer = getTrainer(trainerId);
        trainer.setTrainee(traineeList);
        trainerDAO.updateTrainer(getTrainerIndex(trainerId), trainer);
    }

    public void deleteTrainer(int id) {
        int trainerIndex = getTrainerIndex(id);
        trainerDAO.deleteTrainer(trainerIndex);
    }

    public boolean checkTraineeListIsEmpty(int id) {
        int trainerIndex = getTrainerIndex(id);
        return getAllDetails().get(trainerIndex).getTrainee().isEmpty();
    }

    public boolean isDuplicate(String fieldName, String fieldValue) {
        boolean isDuplicate = true;
        TraineeDAO traineeDAO = new TraineeDAO();
        List<Trainer> trainerList = getAllDetails();

        if (fieldName.equals("phoneNo")) {
            isDuplicate = isDuplicatePhoneNo(isDuplicate, fieldValue);
        } else if(fieldName.equals("mailId")) {
            isDuplicate = isDuplicateMailId(isDuplicate, fieldValue);
        } else {
            isDuplicate = true;
        }
        return ifCustomExceptionThrown(isDuplicate);
    }

    public boolean ifCustomExceptionThrown(boolean isDuplicate) {
        try {
            if (isDuplicate) {
                return isDuplicate;
            } else {
                CustomException existException = new CustomException("Is this already exist....!");
                throw existException;
            }
        } catch (CustomException exception) {
            System.out.println(exception);
            return isDuplicate;
        }
    }

    public boolean isDuplicatePhoneNo(boolean isDuplicate, String fieldValue) {
        TraineeDAO traineeDAO = new TraineeDAO();
        List<Trainer> trainerList = getAllDetails();
        List<Trainee> traineeList = traineeDAO.getTraineeList();

        if (!(trainerList.isEmpty())) {
            for (Trainer trainer : trainerList) {
                if ((Long.toString(trainer.getPhoneNo())).equals(fieldValue)) {
                    return false;
                }
            }
        }
        if (!(traineeList.isEmpty())) {
            for (Trainee trainee : traineeList) {
                if ((Long.toString(trainee.getPhoneNo())).equals(fieldValue)) {
                    return false;
                }
            }
        }
        return isDuplicate;
    }

    public boolean isDuplicateMailId(boolean isDuplicate, String fieldValue) {
        TraineeDAO traineeDAO = new TraineeDAO();
        List<Trainer> trainerList = getAllDetails();
        List<Trainee> traineeList = traineeDAO.getTraineeList();

        if (!(trainerList.isEmpty())) {
            for (Trainer trainer : trainerList) {
                if ((trainer.getMailId()).equals(fieldValue)) {
                    return false;
                }
            }
        }
        if (!(traineeList.isEmpty())) {
            for (Trainee trainee : traineeList) {
                if ((trainee.getMailId()).equals(fieldValue)) {
                    return false;
                }
            }
        }
        return isDuplicate;
    }
}
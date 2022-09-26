package com.ideas2it.ems.service.trainerservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

import com.ideas2it.ems.dao.traineedao.TraineeDAO;
import com.ideas2it.ems.dao.trainerdao.TrainerDAO;
import com.ideas2it.ems.model.traineemodel.Trainee;
import com.ideas2it.ems.model.trainermodel.Trainer;
import com.ideas2it.ems.exception.AlreadyExistException;
import com.ideas2it.ems.util.Role;
import com.ideas2it.ems.util.UtilValidation;

public class TrainerService {
    TrainerDAO trainerDAO = new TrainerDAO();

    public void addDetails(Trainer trainer) {
        trainerDAO.addTrainer(trainer);
    }

    public void assignTrainee(Integer id, Trainer trainer) {
        int trainerIndex = getTrainerIndex(id);
        trainerDAO.updateTrainer(trainerIndex, trainer);
    }

    public List<Trainer> getAllDetails() {
        return trainerDAO.getTrainerList();
    }

    public List<Trainee> getTraineeList(Integer id) {
        int trainerIndex = getTrainerIndex(id);
        return getAllDetails().get(trainerIndex).getTrainee();
    }

    public boolean ifTrainerListEmpty() {
        return trainerDAO.getTrainerList().isEmpty();
    }

    public int getTrainerIndex(Integer id) {
        int length = getAllDetails().size();

        for (int trainerIndex = 0; trainerIndex < length; trainerIndex++) {
            if ((getAllDetails().get(trainerIndex)
                .getId()).equals(id)) {
                return trainerIndex;
            }
        }
        return 0;
    }

    public int getTraineeIndex(Integer trainerId, Integer traineeId) {
        int traineeIndex = 0;
        List<Trainee> traineeList = getTraineeList(trainerId);
        for (Trainee trainee : traineeList) {
            if (trainee.getId().equals(traineeId)) {
                return traineeIndex;
            }
            traineeIndex++;
        }
        return 0;
    }

    public Trainer getTrainer(Integer id) {
        int trainerIndex = getTrainerIndex(id);
        return getAllDetails().get(trainerIndex);
    }

    public String displayTrainer(Integer id) {
        int trainerIndex = getTrainerIndex(id);
        return getAllDetails().get(trainerIndex).toString();
    }

    public boolean ifTrainerPresent(Integer id) throws AlreadyExistException {
        int length = getAllDetails().size();

        for (int trainerIndex = 0; trainerIndex < length; trainerIndex++) {
            if ((getAllDetails().get(trainerIndex)
                .getId()).equals(id)) {
                return true;
            }
        }
        throw new AlreadyExistException("Trainer ID doesn't exist....!");
    }

    public void updateTrainer(Integer id, String fieldName,
                              String fieldValue) {
        float experience;
        Trainer trainer = getTrainer(id);
        int trainerIndex = getTrainerIndex(id);
        switch (fieldName) {
            case "name":
                trainer.setName(fieldValue);
                break;

            case "dob":
                trainer.setDob(fieldValue);
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

    public void updateTrainee(Integer trainerId, Integer traineeId,
                              Trainee trainee) {
        int traineeIndex = getTraineeIndex(trainerId, traineeId);
        Trainer trainer = getTrainer(trainerId);
        trainer.getTrainee().set(traineeIndex, trainee);
        trainerDAO.updateTrainer(getTrainerIndex(trainerId), trainer);
    }

    public void updateTraineeList(List<Trainee> traineeList,
                                  Integer trainerId) {
        Trainer trainer = getTrainer(trainerId);
        trainer.setTrainee(traineeList);
        trainerDAO.updateTrainer(getTrainerIndex(trainerId), trainer);
    }

    public String getName(Integer id) {
        int trainerIndex = getTrainerIndex(id);
        return getAllDetails().get(trainerIndex).getName();
    }

    public Role getRole(Integer id) {
        int trainerIndex = getTrainerIndex(id);
        return getAllDetails().get(trainerIndex).getRole();
    }

    public void deleteTrainee(Integer trainerId, Integer traineeId) {
        int traineeIndex = getTraineeIndex(trainerId, traineeId);
        List<Trainee> traineeList = getTraineeList(trainerId);
        traineeList.remove(traineeIndex);
        Trainer trainer = getTrainer(trainerId);
        trainer.setTrainee(traineeList);
        trainerDAO.updateTrainer(getTrainerIndex(trainerId), trainer);
    }

    public void deleteTrainer(Integer id) {
        int trainerIndex = getTrainerIndex(id);
        trainerDAO.deleteTrainer(trainerIndex);
    }

    public boolean checkTraineeListIsEmpty(Integer id) {
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
                AlreadyExistException existException = new AlreadyExistException("Is this already exist....!");
                throw existException;
            }
        } catch (AlreadyExistException exception) {
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
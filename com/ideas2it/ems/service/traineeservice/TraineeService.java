package com.ideas2it.ems.service.traineeservice;

import java.util.List;

import com.ideas2it.ems.dao.traineedao.TraineeDAO;
import com.ideas2it.ems.dao.trainerdao.TrainerDAO;
import com.ideas2it.ems.exception.AlreadyExistException;
import com.ideas2it.ems.model.traineemodel.Trainee;
import com.ideas2it.ems.model.trainermodel.Trainer;

public class TraineeService {
    TraineeDAO traineeDAO = new TraineeDAO();

    public void addDetails(Trainee trainee) {
        traineeDAO.addTrainee(trainee);
    }

    public List<Trainee> getAllDetails() {
        return traineeDAO.getTraineeList();
    }

    public boolean checkListIsEmpty() {
        return getAllDetails().isEmpty();
    }

    public int getTraineeIndex(Integer id) {
        int length = getAllDetails().size();

        for (int traineeIndex = 0; traineeIndex < length; traineeIndex++) {
            if ((getAllDetails().get(traineeIndex)
                .getId()).equals(id)) {
                return traineeIndex;
            }
        }
        return 0;
    }

    public Trainee getTrainee(Integer id) {
        int traineeIndex = getTraineeIndex(id);
        return getAllDetails().get(traineeIndex);
    }

    public String displayTrainee(Integer id) {
        int traineeIndex = getTraineeIndex(id);
        return getAllDetails().get(traineeIndex).toString();
    }

    public boolean ifTraineePresent(Integer id) throws AlreadyExistException {
        int length = getAllDetails().size();

        for (int traineeIndex = 0; traineeIndex < length; traineeIndex++) {
            if ((getAllDetails().get(traineeIndex).getId()).equals(id)) {
                return true;
            }
        }
        throw new AlreadyExistException("Trainee ID doesn't exist....!");
    }

    public void updateTrainee(Integer id, String fieldName,
                              String fieldValue) {
        Trainee trainee = getTrainee(id);
        int traineeIndex = getTraineeIndex(id);
        switch (fieldName) {
            case "name":
                trainee.setName(fieldValue);
                break;

            case "dob":
                trainee.setDob(fieldValue);
                break;

            case "role":
                trainee.setRole(fieldValue);
                break;

            case "phoneNo":
                trainee.setPhoneNo(Long.parseLong(fieldValue));
                break;

            case "mailId":
                trainee.setMailId(fieldValue);
                break;

            case "status":
                trainee.setStatus(fieldValue);
                break;

            case "trainerId":
                Integer trainerId = Integer.parseInt(fieldValue);
                trainee.setTrainerId(trainerId);
                break;
        }
        traineeDAO.updateTrainee(traineeIndex, trainee);
    }

    public String getName(Integer id) {
        int traineeIndex = getTraineeIndex(id);
        return getAllDetails().get(traineeIndex).getName();
    }

    public Integer getTrainerId(Integer id) {
        int traineeIndex = getTraineeIndex(id);
        return getAllDetails().get(traineeIndex).getTrainerId();
    }

    public String getStatus(Integer id) {
        int traineeIndex = getTraineeIndex(id);
        return getAllDetails().get(traineeIndex).getStatus();
    }

    public void deleteDetails(Integer id) {
        int traineeIndex = getTraineeIndex(id);
        traineeDAO.deleteTrainee(traineeIndex);
    }

    public boolean isDuplicate(String fieldName, String fieldValue) {
        boolean isDuplicate = true;
        TrainerDAO trainerDAO = new TrainerDAO();
        List<Trainee> traineeList = getAllDetails();

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
        TrainerDAO trainerDAO = new TrainerDAO();
        List<Trainee> traineeList = getAllDetails();
        List<Trainer> trainerList = trainerDAO.getTrainerList();

        if (!(traineeList.isEmpty())) {
            for (Trainee trainee : traineeList) {
                if ((Long.toString(trainee.getPhoneNo())).equals(fieldValue)) {
                    return false;
                }
            }
        }
        if (!(trainerList.isEmpty())) {
            for (Trainer trainer : trainerList) {
                if ((Long.toString(trainer.getPhoneNo())).equals(fieldValue)) {
                    return false;
                }
            }
        }
        return isDuplicate;
    }

    public boolean isDuplicateMailId(boolean isDuplicate, String fieldValue) {
        TrainerDAO trainerDAO = new TrainerDAO();
        List<Trainee> traineeList = getAllDetails();
        List<Trainer> trainerList = trainerDAO.getTrainerList();

        if (!(traineeList.isEmpty())) {
            for (Trainee trainee : traineeList) {
                if ((trainee.getMailId()).equals(fieldValue)) {
                    return false;
                }
            }
        }
        if (!(trainerList.isEmpty())) {
            for (Trainer trainer : trainerList) {
                if ((trainer.getMailId()).equals(fieldValue)) {
                    return false;
                }
            }
        }
        return isDuplicate;
    }
}
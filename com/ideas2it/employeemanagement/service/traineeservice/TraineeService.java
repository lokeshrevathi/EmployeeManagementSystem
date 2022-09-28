package com.ideas2it.employeemanagement.service.traineeservice;

import java.time.LocalDate;
import java.util.List;

import com.ideas2it.employeemanagement.dao.traineedao.TraineeDAO;
import com.ideas2it.employeemanagement.dao.trainerdao.TrainerDAO;
import com.ideas2it.employeemanagement.exception.CustomException;
import com.ideas2it.employeemanagement.model.traineemodel.Trainee;
import com.ideas2it.employeemanagement.model.trainermodel.Trainer;
import com.ideas2it.employeemanagement.util.Role;

public class TraineeService {
    TraineeDAO traineeDAO = new TraineeDAO();

    public void addDetails(Trainee trainee) {
        traineeDAO.addTrainee(trainee);
    }

    public List<Trainee> getAllDetails() {
        return traineeDAO.getTraineeList();
    }

    public boolean ifTraineeListEmpty() {
        return getAllDetails().isEmpty();
    }

    public int getTraineeIndex(int id) {
        int length = getAllDetails().size();

        for (int traineeIndex = 0; traineeIndex < length; traineeIndex++) {
            if ((getAllDetails().get(traineeIndex)
                .getId()) == id) {
                return traineeIndex;
            }
        }
        return 0;
    }

    public Trainee getTrainee(int id) {
        int traineeIndex = getTraineeIndex(id);
        return getAllDetails().get(traineeIndex);
    }

    public String displayTrainee(int id) {
        int traineeIndex = getTraineeIndex(id);
        return getAllDetails().get(traineeIndex).toString();
    }

    public boolean ifTraineePresent(int id) throws CustomException {
        int length = getAllDetails().size();

        for (int traineeIndex = 0; traineeIndex < length; traineeIndex++) {
            if ((getAllDetails().get(traineeIndex).getId()) == id) {
                return true;
            }
        }
        throw new CustomException("Trainee ID doesn't exist....!");
    }

    public void updateTrainee(int id, String fieldName,
                              String fieldValue) {
        int trainerId;
        Trainee trainee = getTrainee(id);
        int traineeIndex = getTraineeIndex(id);
        LocalDate dob;
        switch (fieldName) {
            case "name":
                trainee.setName(fieldValue);
                break;

            case "dob":
                dob = LocalDate.parse(fieldValue);
                trainee.setDob(dob);
                break;

            case "role":
                trainee.setRole(Role.valueOf(fieldValue));
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
                trainerId = Integer.parseInt(fieldValue);
                trainee.setTrainerId(trainerId);
                break;
        }
        traineeDAO.updateTrainee(traineeIndex, trainee);
    }

    public String getName(int id) {
        int traineeIndex = getTraineeIndex(id);
        return getAllDetails().get(traineeIndex).getName();
    }

    public int getTrainerId(int id) {
        int traineeIndex = getTraineeIndex(id);
        return getAllDetails().get(traineeIndex).getTrainerId();
    }

    public String getStatus(int id) {
        int traineeIndex = getTraineeIndex(id);
        return getAllDetails().get(traineeIndex).getStatus();
    }

    public Role getRole(int id) {
        int traineeIndex = getTraineeIndex(id);
        return getAllDetails().get(traineeIndex).getRole();
    }

    public void deleteDetails(int id) {
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
                CustomException existException = new CustomException("Is this already exist....!");
                throw existException;
            }
        } catch (CustomException exception) {
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
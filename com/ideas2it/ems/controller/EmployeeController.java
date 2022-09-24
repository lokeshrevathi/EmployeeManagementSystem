package com.ideas2it.ems.controller;

import java.lang.NumberFormatException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.ideas2it.ems.exception.AlreadyExistException;
import com.ideas2it.ems.model.traineemodel.Trainee;
import com.ideas2it.ems.model.trainermodel.Trainer;
import com.ideas2it.ems.service.traineeservice.TraineeService;
import com.ideas2it.ems.service.trainerservice.TrainerService;
import com.ideas2it.ems.util.UtilValidation;

public class EmployeeController {
    int traineeId = 1;
    int trainerId = 1;
    TraineeService traineeService = new TraineeService();
    TrainerService trainerService = new TrainerService();
    Scanner scanner = new Scanner(System.in);
    static Logger logger = LogManager.getLogger(EmployeeController.class);

    public static void main(String args[]) {
        int choice;
        Scanner scanInput = new Scanner(System.in);
        EmployeeController controller = new EmployeeController();

        do {
            logger.info("\n1->Enroll for trainer."
                        .concat("\n2->Enroll for trainee.")
                        .concat("\n3->Manipulate team.")
                        .concat("\n4<-Exit.\n"));
            choice = controller.getChoice();
            switch (choice) {
                case 1:
                    controller.manageTrainer();
                    break;

                case 2:
                    controller.manageTrainee();
                    break;

                case 3:
                    controller.manipulateTeam();
                    break;

                case 4:
                    logger.info("-----<EXIT>-----");
                    break;

                default :
                    logger.warn("Please select valid option!\n");
            }
        } while (4 != choice);
    }

    public void manageTrainee() {
        int choice;
        do {
            logger.info("\n1->Create\n2->Read\n3->Search\n4->Update" 
                        .concat("\n5->Delete\n6<-Go Back").concat("\n"));
            choice = getChoice();
            switch (choice) {
                case 1:
                    createTrainee();
                    break;

                case 2:
                    readTrainee();
                    break;

                case 3:
                    searchTrainee();
                    break;

                case 4:
                    updateTrainee();
                    break;

                case 5:
                    deleteTrainee();
                    break;

                case 6:
                    break;

                default:
                    logger.warn("Please select valid option!\n");
            }
        } while (6 != choice);
    }

    public void createTrainee() {
        Trainee trainee = new Trainee(getId("trainee"), getName("trainee"),
                                      getDob("trainee"), getRole("trainee"),
                                      getPhoneNo("trainee"), getMailId("trainee"),
                                      "not assigned", 0);
        traineeService.addDetails(trainee);
        logger.info("Trainee details added!\n");
    }

    public Integer getId(String fieldName) {
        
        if (fieldName.equals("trainee")) {
            Integer id1 = new Integer(traineeId++);
            logger.info("\nYours " + fieldName + " ID                  : "
                               + id1 + "\n");
            return id1;
        } else {
            Integer id2 = new Integer(trainerId++);
            logger.info("\nYours " + fieldName + " ID                  : "
                               + id2 + "\n");
            return id2;
        }
    }

    public String getName(String fieldName) {
        String name;
        boolean isValidName = false;

        do {
            logger.info("Enter " + fieldName + " name                : ");
            name = scanner.nextLine();
            isValidName = !(UtilValidation.isValid(UtilValidation.regexName, name));
            if (isValidName) {
                logger.warn("Please enter valid name....!\n");
            }
        } while (isValidName);
        return name;
    }

    public String getDob(String fieldName) {
        String dob;
        boolean isValidDob = false;

        do {
            logger.info("Enter " + fieldName + " D.O.B(DD-MM-YYYY)   : ");
            dob = scanner.nextLine();
            isValidDob = !(UtilValidation.checkDob(dob));
            if (isValidDob) {
                logger.warn("Please enter valid D.O.B....!\n");
            }
        } while (isValidDob);
        return dob;
    }

    public String getRole(String fieldName) {
        String role;
        boolean isValidRole = false;

        do {
            logger.info("Enter "  + fieldName + " role                : ");
            role = scanner.nextLine();
            isValidRole = !(UtilValidation.isValid(UtilValidation.regexRole, role));
            if (isValidRole) {
                logger.warn("Please enter valid role....!\n");
            }
        } while (isValidRole);
        return role;
    }

    public long getPhoneNo(String fieldName) {
        long phoneNo = 0;
        boolean isValidPhoneNo = false;
        do {
            logger.info("Enter " + fieldName + " phone number        : ");
            try {
                phoneNo = Long.parseLong(scanner.nextLine());
                isValidPhoneNo = !(UtilValidation.isValid(UtilValidation.regexPhoneNo, Long.toString(phoneNo)));
                if (isValidPhoneNo) {
                    logger.warn("Please enter valid phone number....!\n");
                } else {
                    if (fieldName.equals("trainee")) {
                        isValidPhoneNo = !(traineeService.isDuplicate("phoneNo", Long.toString(phoneNo)));
                    
                    } else {
                        isValidPhoneNo = !(trainerService.isDuplicate("phoneNo", Long.toString(phoneNo)));
                    }
                }
            } catch (NumberFormatException exception) {
                logger.error("Enter numbers only....!\n");
                isValidPhoneNo = true;
            }
        } while (isValidPhoneNo);
        return phoneNo;
    }

    public String getMailId(String fieldName) {
        String mailId;
        boolean isValidMailId = false;

        do {
            logger.info("Enter " + fieldName + " mail Id             : ");
            mailId = scanner.nextLine();
            isValidMailId = !(UtilValidation.isValid(UtilValidation.regexMailId, mailId));  
            if (isValidMailId) {
                logger.warn("Please enter valid mail Id....!\n");
            } else {
                if (fieldName.equals("trainee")) {
                    isValidMailId = !(traineeService.isDuplicate("mailId", mailId));
                } else {
                    isValidMailId = !(trainerService.isDuplicate("mailId", mailId));
                }
            }
        } while (isValidMailId);
        return mailId;
    }

    public void readTrainee() {
        if (traineeService.ifTraineeListEmpty()) {
            logger.warn("Trainee details doesn't exist....!\n");
        } else {
            for (Trainee trainee : traineeService.getAllDetails()) {
                logger.info("\n" + trainee.toString() + "\n"); 
                Integer trainerID = trainee.getTrainerId();
                if (!(trainerID.equals(0))) {
                    logger.info("Trainee assigned under : "
                                       + trainerID + "\n");
                }
            }
        }
    }

    public void searchTrainee() {
        logger.info("\nEnter Trainee ID to search Trainee details: ");
        Integer id = getIdToManipulate();

        try {
            if (traineeService.ifTraineePresent(id)) {
                logger.info(traineeService.displayTrainee(id) + "\n");
                Integer trainerId1 = traineeService.getTrainerId(id);
                if (!(trainerId1.equals(0))) {
                    logger.info("Trainee assigned under : "
                                       + traineeService.getTrainerId(id) + "\n");
                }
            }
        } catch (AlreadyExistException existException) {
            logger.error(existException + "\n");
        }
    }

    public void updateTrainee() {
        int choice;
        logger.info("\nEnter trainee Id to update : ");
        Integer id = getIdToManipulate();

        try {
            if (traineeService.ifTraineePresent(id)) {
                do {
                    logger.info("\n1-->Trainee Name.\n2-->Trainee D.O.B."
                                .concat("\n3-->Trainee Role.")
                                .concat("\n4-->Trainee Phone number.")
                                .concat("\n5-->Trainee Mail Id.")
                                .concat("\n6-->Go back.").concat("\n").concat("\n"));
                    choice = getChoice();
                        switch (choice) {
                            case 1:
                                traineeService.updateTrainee(id, "name", getName("trainee"));
                                logger.info("Trainee name is updated!\n");
                                break;

                            case 2:
                                traineeService.updateTrainee(id, "dob", getDob("trainee"));
                                logger.info("Trainee D.O.B is updated!\n");
                                break;

                            case 3:
                                traineeService.updateTrainee(id, "role", getRole("trainee"));
                                logger.info("Trainee Role is updated!\n");
                                break;

                            case 4:
                                traineeService.updateTrainee(id, "phoneNo", Long.toString(getPhoneNo("trainee")));
                                logger.info("Trainee Address is updated!\n");
                                break;

                            case 5:
                                traineeService.updateTrainee(id, "mailId", getMailId("trainee"));
                                logger.info("Trainee MailID is updated!\n");
                                break;

                            case 6:
                                break;

                            default:
                                logger.warn("Invalid choice\n");
                        }
                        Integer trainerID = traineeService.getTrainerId(id);
                        if (!(trainerID.equals(0))) {
                            trainerService.updateTrainee(trainerID, id,
                                                         traineeService.getTrainee(id));
                        }
                } while (choice != 6);
            }
        } catch (AlreadyExistException existException) {
            logger.error(existException + "\n");
        }
    }

    public void deleteTrainee() {
        Integer id;
        Integer trainerID;
        List<Trainee> traineeList;
        logger.info("\nEnter trainee ID to delete employee details: ");
        id = getIdToManipulate();

        try {
            if (traineeService.ifTraineePresent(id)) {
                trainerID = traineeService.getTrainerId(id);
                if (!(trainerID.equals(0))) {
                    trainerService.deleteTrainee(trainerID, id);
                }
                traineeService.deleteDetails(id);
                logger.info("Trainee details deleted!\n");
            }
        } catch (AlreadyExistException existException) {
            logger.error(existException + "\n");
        }
    }

    public List<Trainee> assignTrainee(Integer trainerId1) {
        int choice;
        Integer id;
        List<Trainee> traineeList = new ArrayList<Trainee>();
        do {
            logger.info("\n1--->Assign already enrolled trainee."
                        .concat("\n2--->Enroll trainee and assign.")
                        .concat("\n3<---Go back.").concat("\n"));
            choice = getChoice();
            switch (choice) {
                case 1:
                    List<Trainee> list = assignEnrolledTrainee(trainerId1);
                    if (!(list.isEmpty())) {
                        traineeList.addAll(list);
                    }
                    break;

                case 2:
                    list = createAssignTrainee(trainerId1);
                    if (!(list.isEmpty())) {
                        traineeList.addAll(list);
                    }
                    break;

                case 3:
                    break;

                default:
                    logger.info("Invalid choice...!\n");
            }       
        } while (3 != choice);
        return traineeList;
    }

    public List<Trainee> assignEnrolledTrainee(Integer trainerId1) {
        List<Trainee> traineeList = new ArrayList<Trainee>();
        Integer id;
        if (traineeService.ifTraineeListEmpty()) {
            logger.info("trainee details does not"
                        .concat(" exist...!\nPlease enroll")
                        .concat(" trainee details...!\n"));
        } else {
            System.out.println();
            List<Trainee> listOfTrainee = traineeService.getAllDetails();
            for (Trainee trainee : listOfTrainee) {
                logger.info(trainee.getId() + "-->"
                            + trainee.getName() + "--"
                            + trainee.getStatus() + "\n");
            }
            logger.info("\nSelect trainee ID to"
                             .concat(" add trainee: "));
            id = getIdToManipulate();
            try {
                if (traineeService.ifTraineePresent(id)) {
                    if ((traineeService.getStatus(id))
                        .equals("not assigned")) {
                        traineeService.updateTrainee(id, "status", "assigned");
                        traineeService.updateTrainee(id, "trainerId", Integer
                                                     .toString(trainerId1));
                        traineeList.add(traineeService.getTrainee(id));
                    } else {
                        logger.info("This trainee is already"
                                           .concat(" assigned\n"));
                    }
                }
            } catch (AlreadyExistException existException) {
                logger.error(existException + "\n");
            }
        }
        return traineeList;
    }

    public List<Trainee> createAssignTrainee(Integer trainerId1) {
        List<Trainee> traineeList = new ArrayList<Trainee>();
        createTrainee();
        Integer setId = new Integer(traineeId - 1);
        traineeService.updateTrainee(setId, "status", "assigned");
        traineeService.updateTrainee(setId, "trainerId", Integer.toString(trainerId1));
        traineeList.add(traineeService.getTrainee(setId));
        return traineeList;
    }

    public void manipulateTeam() {
        int choice = 0;
        int trainerID = 0; 
        do {
            logger.info("\n1-->Show team details."
                        .concat("\n2-->Assign trainee for already enrolled trainer.")
                       .concat("\n3<--go back\n"));
            choice = getChoice();
        
            switch (choice) {
                case 1:
                    readTrainer("Team");
                    break;

                case 2:
                    if (displayTeamDetails()) {
                        logger.info("Select Trainer ID to assign trainee: ");
                        trainerID = getChoice();
                        try {
                            if (trainerService.ifTrainerPresent(trainerID)) {
                                List<Trainee> traineeList
                                    = trainerService.getTrainer(trainerID)
                                      .getTrainee();
                                traineeList.addAll(assignEnrolledTrainee(trainerID));
                                trainerService.updateTraineeList(traineeList,
                                                                 trainerID);
                            }
                        } catch (AlreadyExistException existException) {
                            logger.error(existException + "\n");
                        }
                    }
                    break;

                case 3:
                    break;

                default:
                    logger.info("Invalid choice...!\n");
            }
        } while (3 != choice);
    }

    public boolean displayTeamDetails() {
        boolean ifTeamExist = true;

        if (!(trainerService.ifTrainerListEmpty())) {
            for (Trainer trainer : trainerService.getAllDetails()) {
                logger.info("\n****************************\n"
                            + "Trainer\n");
                logger.info(trainer.getId() + "-->" + trainer.getName());
                if (trainer.getTrainee().isEmpty()) {
                    logger.info("\nNo trainee's can assigned for -->" 
                                       + trainer.getName() + "\n");
                } else {
                    logger.info("\ntrainee's assigned for -->" 
                                       + trainer.getName() + "\n");
                    for (Trainee trainee : trainer.getTrainee()) {
                        logger.info(trainee.getId() + "-->"
                                           + trainee.getName() + "\n");
                    }
                }
                logger.info("****************************\n");
            }
        } else {
            logger.info("\nTrainer doesn't exist....!\n");
            return false;
        } 
        return ifTeamExist;
    }

    public void manageTrainer() {
        int choice;
        do {
            logger.info("\n1-->Create\n2-->Read\n3-->Search\n4-->Update" 
                        .concat("\n5-->Delete\n6<--Go Back"));
            choice = getChoice();
            switch (choice) {
                case 1:
                    createTrainer();
                    break;

                case 2:
                    readTrainer("Trainer");
                    break;

                case 3:
                    searchTrainer();
                    break;

                case 4:
                    updateTrainer();
                    break;

                case 5:
                    deleteTrainer();
                    break;

                case 6:
                    break;

                default:
                    logger.warn("Please select valid option!\n");
            }
        } while (6 != choice);
    }

    public void createTrainer() {
        Integer id = getId("trainer");
        Trainer trainer = new Trainer(id, getName("trainer"),
                                      getDob("trainer"),
                                      getRole("trainer"),
                                      getPhoneNo("trainer"),
                                      getMailId("trainer"),
                                      getExperience());
        trainerService.addDetails(trainer);
        trainer.setTrainee(assignTrainee(id));
        trainerService.assignTrainee(id, trainer);
        logger.info("Trainer details added!\n");
    }

    public void readTrainer(String fieldName) {

        if (trainerService.ifTrainerListEmpty()) {
            logger.warn("\n" + fieldName + " doesn't exist....!\n");
        } else {
            for (Trainer trainer : trainerService.getAllDetails()) {
                if ((!(trainer.getTrainee().isEmpty())
                    && (fieldName.equals("Team")))
                    || fieldName.equals("Trainer")) {
                    logger.info("*****************************"
                                .concat("*********************\n"));
                    logger.info(trainer.toString() + "\n");
                    if (!(trainer.getTrainee().isEmpty())) {
                        logger.info("\nTrainee's assigned for -> "
                                    + trainer.getName() + "\n");
                        for (Trainee trainee : trainer.getTrainee()) {
                            logger.info(trainee.toString() + "\n");
                        }
                    }
                    logger.info("*****************************"
                                .concat("*********************\n"));
                }
            }
        }
    }

    public void searchTrainer() {
        logger.info("\nEnter Trainer ID to search Trainer details: ");
        Integer id = getIdToManipulate();

        try {
            if (trainerService.ifTrainerPresent(id)) {
                logger.info(trainerService.displayTrainer(id) + "\n");
                if (!(trainerService.checkTraineeListIsEmpty(id))) {
                    logger.info("\nTrainee's assigned for -> "
                                       + trainerService.getName(id) + "\n");
                    for (Trainee trainee : trainerService.getTraineeList(id)) {
                        logger.info(trainee.toString() + "\n");
                    }
                }
            }
        } catch (AlreadyExistException existException) {
            logger.error(existException + "\n");
        }
    }

    public void updateTrainer() {
        int choice;
        logger.info("\nEnter trainer Id to update : ");
        Integer id = getIdToManipulate();
        try {
            if (trainerService.ifTrainerPresent(id)) {
                do {
                    logger.info("\n1-->Trainer Name.\n2-->Trainer D.O.B."
                                + "\n3-->Trainer Role."
                                + "\n4-->Trainer Phone number."
                                + "\n5-->Trainer Mail Id."
                                + "\n6.Trainer Experience."
                                + "\n7<--Go back.\n");
                    choice = getChoice();
                        switch (choice) {
                            case 1:
                                trainerService.updateTrainer(id, "name",
                                                             getName("trainer"));
                                logger.info("Trainer name is updated!\n");
                                break;

                            case 2:
                                trainerService.updateTrainer(id, "dob",
                                                             getDob("trainer"));
                                logger.info("Trainer D.O.B is updated!\n");
                                break;

                            case 3:
                                trainerService.updateTrainer(id, "role",
                                                             getRole("trainer"));
                                logger.info("Trainer Role is updated!\n");
                                break;

                            case 4:
                                trainerService.updateTrainer(id, "phoneNo",
                                        Long.toString(getPhoneNo("trainer")));
                                logger.info("Trainer Phone number "
                                                   .concat("is updated!\n"));
                                break;

                            case 5:
                                trainerService.updateTrainer(id, "mailId",
                                                             getMailId("trainer"));
                                logger.info("Trainer MailID is updated!\n");
                                break;

                            case 6:
                                trainerService.updateTrainer(id, "experience",
                                        Float.toString(getExperience()));
                                logger.info("Trainer experience "
                                                   .concat("is updated!\n"));
                                break;

                            default:
                                logger.warn("Invalid choice\n");
                        }
                } while (7 != choice);
            }
        } catch (AlreadyExistException existException) {
            logger.error(existException + "\n");
        }
    }

    public void deleteTrainer() {
        logger.info("\nEnter trainer ID to delete employee details: ");
        Integer id = getIdToManipulate();
        try {
            if (trainerService.ifTrainerPresent(id)) {
                for (Trainee trainee : trainerService.getTraineeList(id)) {
                    traineeService.updateTrainee(trainee.getId(),
                                                 "status",
                                                 "not assigned");
                    traineeService.updateTrainee(trainee.getId(),
                                                 "trainerId", Integer
                                                 .toString(0));
                }
                trainerService.deleteTrainer(id);
                logger.info("Trainer details deleted!\n");
            }
        } catch (AlreadyExistException existException) {
            logger.error(existException + "\n");
        }
    }

    public float getExperience() {
        int flag = 0;
        float experience = 0;
        do {
            try {
                logger.info("Enter Years of experience         : ");
                experience = Float.parseFloat(scanner.nextLine());
                flag = 0;
            } catch (NumberFormatException exception) {
                flag = 1;
                logger.error("invalid value....!\n");
            }
        } while (1 == flag);
        return experience;
    }

    public int getChoice() {
        int flag = 0;
        int choice = 0;
        do {
            try {
                logger.info("\nEnter your choice         : ");
                choice = Integer.parseInt(scanner.nextLine());
                flag = 0;
            } catch (NumberFormatException exception) {
                flag = 1;
                logger.error("invalid option....!");
            }
        } while (1 == flag);
        return choice;
    }

    public int getIdToManipulate() {
        int flag = 0;
        int choice = 0;
        do {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                flag = 0;
            } catch (NumberFormatException exception) {
                flag = 1;
                logger.error("invalid option....!\n");
            }
        } while (1 == flag);
        return choice;
    }
}
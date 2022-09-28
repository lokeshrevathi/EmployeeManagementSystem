package com.ideas2it.employeemanagement.controller;

import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NumberFormatException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.ideas2it.employeemanagement.exception.CustomException;
import com.ideas2it.employeemanagement.model.traineemodel.Trainee;
import com.ideas2it.employeemanagement.model.trainermodel.Trainer;
import com.ideas2it.employeemanagement.service.traineeservice.TraineeService;
import com.ideas2it.employeemanagement.service.trainerservice.TrainerService;
import com.ideas2it.employeemanagement.util.UtilValidation;
import com.ideas2it.employeemanagement.util.Role;

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
            logger.info("\n1->Enroll for trainer.");
            logger.info("\n2->Enroll for trainee.");
            logger.info("\n3->Manipulate team.");
            logger.info("\n4<-Exit.\n");
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
                    logger.info("\n-----<EXIT>-----");
                    break;

                default :
                    logger.warn("Please select valid option!\n");
            }
        } while (4 != choice);
    }

    public void manageTrainee() {
        int choice;

        do {
            logger.info("\n1->Create\n2->Read\n3->Search");
            logger.info("\n4->Update\n5->Delete\n6<-Go Back\n");
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
        LocalDate dob;
        Trainee trainee = new Trainee(getId("trainee"),
                                      getName("trainee"),
                                      dob = getDob("trainee"),
                                      getAge(dob),
                                      getRole("trainee"),
                                      getPhoneNo("trainee"),
                                      getMailId("trainee"),
                                      "not assigned", 0);
        traineeService.addDetails(trainee);
        logger.info("Trainee details added!\n");
    }

    public int getId(String fieldName) {
        
        if (fieldName.equals("trainee")) {
            int id1 = traineeId++;
            logger.info("\nYours " + fieldName + " ID                  : ");
            logger.info(id1 + "\n");
            return id1;
        } else {
            int id2 = trainerId++;
            logger.info("\nYours " + fieldName + " ID                  : ");
            logger.info(id2 + "\n");
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

    public LocalDate getDob(String fieldName) {
        String date;
        boolean isValidDob = false;
        LocalDate dob = null;
        LocalDate currentDate;

        do {
            logger.info("Enter " + fieldName + " D.O.B(YYYY-MM-DD)   : ");
            date = scanner.nextLine();
            try {
                dob = LocalDate.parse(date);
                isValidDob = false;
                currentDate = LocalDate.now();
                isValidDob = !(18 < Period.between(dob, currentDate).getYears()
                            && Period.between(dob, currentDate).getYears() <= 60);
                if (isValidDob) {
                    logger.warn("Please enter valid D.O.B....!\n");
                }
            } catch (DateTimeException exception) {
                logger.warn("Please enter valid D.O.B....!\n");
                isValidDob = true;
            }
        } while (isValidDob);
        return dob;
    }

    public int getAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }

    public Role getRole(String fieldName) {
        int choice = 0;
        Role[] roles = Role.values();
        Role role = roles[0];
        boolean isRedo = false;

        do {
            displayRoleMessage(fieldName);
            choice = getChoice();
            try {
                if ("trainee".equals(fieldName) && (choice == 1
                    || choice == 2)) {
                    role = roles[choice - 1];
                    return role;
                } else if ("trainer".equals(fieldName) && choice > 0) {
                    role = roles[choice + 1];
                    return role;
                } else {
                    logger.warn("Please enter valid option....!\n");
                    isRedo = true;
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                isRedo = true;
                logger.error("Please enter valid option....!\n");
            }
        } while (isRedo);
        return role;
    }

    public void displayRoleMessage(String fieldName) {
        if ("trainer".equals(fieldName)) {
            logger.info("\n" + fieldName + " roles");
            logger.info("\n1-->Junior software developer.");
            logger.info("\n(Experience 1 to 4 years)\n");
            logger.info("\n2-->Junior software tester.");
            logger.info("\n(Experience 1 to 4 years)\n");
            logger.info("\n3-->Senior software developer.");
            logger.info("\n(Experience above 4 years)\n");
            logger.info("\n4-->Senior software tester.");
            logger.info("\n(Experience above 4 years)\n");
        } else {
            logger.info("\n" + fieldName + " roles");
            logger.info("\n1-->Software developer trainee.");
            logger.info("\n2-->software tester trainee.\n");
        }
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
        int trainerID;

        if (traineeService.ifTraineeListEmpty()) {
            logger.warn("Trainee details doesn't exist....!\n");
        } else {
            logger.info("*************************");
            logger.info("*************************\n");
            for (Trainee trainee : traineeService.getAllDetails()) {
                logger.info(trainee.toString() + "\n"); 
                trainerID = trainee.getTrainerId();
                if (!(trainerID == 0)) {
                    logger.info("Trainee assigned under : ");
                    logger.info(trainerID + "\n");
                }
            logger.info("*************************");
            logger.info("*************************\n");
            }
        }
    }

    public int searchTrainee() {
        int id = 0;
        int trainerId1;
        boolean isRedo = false;

        do {
            try {
                if (!(traineeService.ifTraineeListEmpty())) {
                    id = getIdToManipulate("trainee");
                    if (traineeService.ifTraineePresent(id)) {
                        isRedo = false;
                        logger.info("*************************");
                        logger.info("*************************\n");
                        logger.info(traineeService.displayTrainee(id) + "\n");
                        trainerId1 = traineeService.getTrainerId(id);
                        if (!(trainerId1 == 0)) {
                            logger.info("Trainee assigned under : ");
                            logger.info(traineeService.getTrainerId(id) + "\n");
                        }
                        logger.info("*************************");
                        logger.info("*************************\n");
                    }
                } else {
                    logger.warn("Trainee details doesn't exist....!\n");
                }
            } catch (CustomException existException) {
                logger.error(existException + "\n");
                isRedo = true;
            }
        } while (isRedo);
        return id;
    }

    public void updateTrainee() {
        int choice;
        int trainerID;
        int id = searchTrainee();

        try {
            if (traineeService.ifTraineePresent(id)) {
                do {
                    logger.info("\n1-->Trainee Name.");
                    logger.info("\n2-->Trainee D.O.B.");
                    logger.info("\n3-->Trainee Role.");
                    logger.info("\n4-->Trainee Phone number.");
                    logger.info("\n5-->Trainee Mail Id.");
                    logger.info("\n6-->Update all.");
                    logger.info("\n7-->Go back.\n");
                    choice = getChoice();
                        switch (choice) {
                            case 1:
                                traineeService.updateTrainee(id, "name",
                                        getName("trainee"));
                                logger.info("Trainee name is updated!\n");
                                break;

                            case 2:
                                traineeService.updateTrainee(id, "dob",
                                        getDob("trainee").toString());
                                logger.info("Trainee D.O.B is updated!\n");
                                break;

                            case 3:
                                traineeService.updateTrainee(id, "role",
                                        getRole("trainee").name());
                                logger.info("Trainee Role is updated!\n");
                                break;

                            case 4:
                                traineeService.updateTrainee(id, "phoneNo",
                                        Long.toString(getPhoneNo("trainee")));
                                logger.info("Trainee Address is updated!\n");
                                break;

                            case 5:
                                traineeService.updateTrainee(id, "mailId",
                                        getMailId("trainee"));
                                logger.info("Trainee MailID is updated!\n");
                                break;

                            case 6:
                                traineeService.updateTrainee(id, "name",
                                        getName("trainee"));
                                traineeService.updateTrainee(id, "dob",
                                        getDob("trainee").toString());
                                traineeService.updateTrainee(id, "role",
                                        getRole("trainee").name());
                                traineeService.updateTrainee(id, "phoneNo",
                                        Long.toString(getPhoneNo("trainee")));
                                traineeService.updateTrainee(id, "mailId",
                                        getMailId("trainee"));
                                logger.info("Trainee details is updated!\n");
                                break;

                            case 7:
                                break;

                            default:
                                logger.warn("Invalid choice\n");
                        }
                        trainerID = traineeService.getTrainerId(id);
                        if (!(trainerID == 0)) {
                            trainerService.updateTrainee(trainerID, id,
                                    traineeService.getTrainee(id));
                        }
                } while (7 != choice);
            }
        } catch (CustomException existException) {
        }
    }

    public void deleteTrainee() {
        int id;
        int trainerID;
        boolean isRedo = false;
        List<Trainee> traineeList;

        do {
            try {
                if (!(traineeService.ifTraineeListEmpty())) {
                    id = getIdToManipulate("trainee");
                    if (traineeService.ifTraineePresent(id)) {
                        isRedo = false;
                        trainerID = traineeService.getTrainerId(id);
                        if (!(trainerID == 0)) {
                            trainerService.deleteTrainee(trainerID, id);
                        }
                        traineeService.deleteDetails(id);
                        logger.info("Trainee details deleted!\n");
                    }
                } else {
                    logger.warn("Trainee details doesn't exist....!\n");
                }
            } catch (CustomException existException) {
                logger.error(existException + "\n");
                isRedo = true;
            }
        } while (isRedo);
    }

    public List<Trainee> assignTrainee(int trainerId1) {
        int choice;
        int id;
        List<Trainee> traineeList = new ArrayList<Trainee>();
        do {
            logger.info("\n1--->Assign already enrolled trainee.");
            logger.info("\n2--->Enroll trainee and assign.");
            logger.info("\n3<---Go back.\n");
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

    public List<Trainee> assignEnrolledTrainee(int trainerId1) {
        List<Trainee> traineeList = new ArrayList<Trainee>();
        int id;
        if (traineeService.ifTraineeListEmpty()) {
            logger.info("\ntrainee details does not exist...!");
            logger.info("\nPlease enroll trainee...!\n");
        } else {
            System.out.println();
            List<Trainee> listOfTrainee = traineeService.getAllDetails();
            for (Trainee trainee : listOfTrainee) {
                logger.info(trainee.getId() + "-->");
                logger.info(trainee.getName() + "-->");
                logger.info(trainee.getStatus() + "-->"); 
                logger.info(trainee.getRole() + "\n");
            }
            id = getIdToManipulate("trainee");
            try {
                if (traineeService.ifTraineePresent(id)) {
                    if ((traineeService.getStatus(id))
                        .equals("not assigned")) {
                        if (ifRoleMisMatch(trainerId1, id)) {
                            traineeService.updateTrainee(id, "status", "assigned");
                            traineeService.updateTrainee(id, "trainerId", Integer
                                                         .toString(trainerId1));
                            traineeList.add(traineeService.getTrainee(id));
                        } else {
                            logger.info("\nTrainer, trainee role mismatch....!");
                        }
                    } else {
                        logger.info("This trainee is already assigned....!\n");
                    }
                }
            } catch (CustomException existException) {
                logger.error(existException + "\n");
            }
        }
        return traineeList;
    }

    public List<Trainee> createAssignTrainee(int trainerId1) {
        List<Trainee> traineeList = new ArrayList<Trainee>();
        createTrainee();
        int setId = traineeId - 1;
        if (ifRoleMisMatch(trainerId1, setId)) {
            traineeService.updateTrainee(setId, "status", "assigned");
            traineeService.updateTrainee(setId, "trainerId", Integer.toString(trainerId1));
            traineeList.add(traineeService.getTrainee(setId));
        } else {
            logger.info("\nTrainee details enrolled....!");
            logger.info("\nTrainer, trainee role mismatch....!");
            logger.info("\nTrainee doesn't assigned....!\n");
        }
        return traineeList;
    }

    public boolean ifRoleMisMatch(int trainerID, int traineeID) {
        Role[] roles = Role.values();
        return (((trainerService.getRole(trainerID).equals(roles[2])
                || trainerService.getRole(trainerID).equals(roles[4]))
                && traineeService.getRole(traineeID).equals(roles[0]))
                || ((trainerService.getRole(trainerID).equals(roles[3])
                || trainerService.getRole(trainerID).equals(roles[5]))
                && traineeService.getRole(traineeID).equals(roles[1])));
    }

    public void manipulateTeam() {
        int choice = 0; 
        do {
            logger.info("\n1-->Show team details.");
            logger.info("\n2-->Assign trainee for already enrolled trainer.");
            logger.info("\n3<--go back\n");
            choice = getChoice();
        
            switch (choice) {
                case 1:
                    readTrainer("Team");
                    break;

                case 2:
                    assignTraineeToEnrolledTrainer();
                    break;

                case 3:
                    break;

                default:
                    logger.info("Invalid choice...!\n");
            }
        } while (3 != choice);
    }

    public void assignTraineeToEnrolledTrainer() {
        int trainerID = 0;
        if (displayTeamDetails()) {
            trainerID = getIdToManipulate("trainer");
            try {
                if (trainerService.ifTrainerPresent(trainerID)) {
                List<Trainee> traineeList
                        = trainerService.getTrainer(trainerID)
                        .getTrainee();
                traineeList.addAll(assignTrainee(trainerID));
                trainerService.updateTraineeList(traineeList,
                                                 trainerID);
                }
            } catch (CustomException existException) {
                logger.error(existException + "\n");
            }
        }
    }

    public boolean displayTeamDetails() {
        boolean ifTeamExist = true;

        if (!(trainerService.ifTrainerListEmpty())) {
            for (Trainer trainer : trainerService.getAllDetails()) {
                logger.info("\n****************************\n");
                logger.info("Trainer\n");
                logger.info(trainer.getId() + "-->");
                logger.info(trainer.getName() + "-->");
                logger.info(trainer.getRole());
                if (trainer.getTrainee().isEmpty()) {
                    logger.info("\nNo trainee's can assigned for -->");
                    logger.info(trainer.getName() + "\n");
                } else {
                    logger.info("\ntrainee's assigned for -->");
                    logger.info(trainer.getName() + "\n");
                    for (Trainee trainee : trainer.getTrainee()) {
                        logger.info(trainee.getId() + "-->");
                        logger.info(trainee.getName() + "\n");
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
            logger.info("\n1-->Create\n2-->Read");
            logger.info("\n3-->Search\n4-->Update");
            logger.info("\n5-->Delete\n6<--Go Back\n");
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
        LocalDate dob;
        int id = getId("trainer");
        Role role = null; // Role.SOFTWARE_DEVELOPER_TRAINEE;
        Trainer trainer = new Trainer(id, getName("trainer"),
                                      dob = getDob("trainer"),
                                      getAge(dob),
                                      role = getRole("trainer"),
                                      getPhoneNo("trainer"),
                                      getMailId("trainer"),
                                      getExperience(role));
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
                    logger.info("*************************");
                    logger.info("*************************\n");
                    logger.info(trainer.toString() + "\n");
                    if (!(trainer.getTrainee().isEmpty())) {
                        logger.info("\nTrainee's assigned for -> ");
                        logger.info(trainer.getName() + "\n");
                        for (Trainee trainee : trainer.getTrainee()) {
                            logger.info(trainee.toString() + "\n");
                        }
                    }
                    logger.info("*************************");
                    logger.info("*************************\n");
                }
            }
        }
    }

    public int searchTrainer() {
        int id = 0;
        boolean isRedo = false;

        do {
            try {
                if (!(trainerService.ifTrainerListEmpty())) {
                    id = getIdToManipulate("trainer");
                    if (trainerService.ifTrainerPresent(id)) {
                        isRedo = false;
                        logger.info("*************************");
                        logger.info("*************************\n");
                        logger.info(trainerService.displayTrainer(id) + "\n");
                        if (!(trainerService.checkTraineeListIsEmpty(id))) {
                            logger.info("\nTrainee's assigned for -> ");
                            logger.info(trainerService.getName(id) + "\n");
                            for (Trainee trainee : trainerService.getTraineeList(id)) {
                                logger.info(trainee.toString() + "\n");
                            }
                        }
                        logger.info("*************************");
                        logger.info("*************************\n");
                    }
                } else {
                    logger.warn("Trainer details doesn't exist....!\n");
                } 
            } catch (CustomException existException) {
                logger.error(existException + "\n");
                isRedo = true;
            }
        } while (isRedo);
        return id;
    }

    public void updateTrainer() {
        int choice;
        int id = searchTrainer();
        try {
            if (trainerService.ifTrainerPresent(id)) {
                do {
                    logger.info("\n1-->Trainer Name.");
                    logger.info("\n2-->Trainer D.O.B.");
                    logger.info("\n3-->Trainer Role.");
                    logger.info("\n4-->Trainer Phone number.");
                    logger.info("\n5-->Trainer Mail Id.");
                    logger.info("\n6-->Trainer Experience.");
                    logger.info("\n7-->Update all.");
                    logger.info("\n8<--Go back.\n");
                    choice = getChoice();
                        switch (choice) {
                            case 1:
                                trainerService.updateTrainer(id, "name",
                                        getName("trainer"));
                                logger.info("Trainer name is updated!\n");
                                break;

                            case 2:
                                trainerService.updateTrainer(id, "dob",
                                        getDob("trainer").toString());
                                logger.info("Trainer D.O.B is updated!\n");
                                break;

                            case 3:
                                trainerService.updateTrainer(id, "role",
                                        getRole("trainer").name());
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
                                    Float.toString(getExperience(trainerService
                                    .getTrainer(id).getRole())));
                                logger.info("Trainer experience "
                                                   .concat("is updated!\n"));
                                break;

                            case 7:
                                trainerService.updateTrainer(id, "name",
                                        getName("trainer"));
                                trainerService.updateTrainer(id, "dob",
                                        getDob("trainer").toString());
                                trainerService.updateTrainer(id, "role",
                                        getRole("trainer").name());
                                trainerService.updateTrainer(id, "phoneNo",
                                        Long.toString(getPhoneNo("trainer")));
                                trainerService.updateTrainer(id, "mailId",
                                        getMailId("trainer"));
                                trainerService.updateTrainer(id, "experience",
                                    Float.toString(getExperience(trainerService
                                    .getTrainer(id).getRole())));
                                break;

                            case 8:
                                break;

                            default:
                                logger.warn("Invalid choice\n");
                        }
                } while (8 != choice);
            }
        } catch (CustomException existException) {
        }
    }

    public void deleteTrainer() {
        int id = 0;
        boolean isRedo = false;

        do {
            try {
                if (!(trainerService.ifTrainerListEmpty())) {
                    id = getIdToManipulate("trainer");
                    if (trainerService.ifTrainerPresent(id)) {
                        isRedo = false;
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
                } else {
                    logger.warn("Trainer details doesn't exist....!\n");
                }
            } catch (CustomException existException) {
                logger.error(existException + "\n");
                isRedo = true;
            }
        } while (isRedo);
    }

    public float getExperience(Role role) {
        int flag = 0;
        float experience = 0;
        do {
            try {
                logger.info("Enter Years of experience         : ");
                scanner = new Scanner(System.in);
                experience = Float.parseFloat(scanner.nextLine());
                if (role.isValidExperience((float)experience)) {
                    flag = 0;
                } else {
                    flag = 1;
                    logger.error("invalid experience....!\n");
                }
            } catch (NumberFormatException exception) {
                flag = 1;
                logger.error("invalid value....!\n");
            } catch (NoSuchElementException exception) {
                flag = 1;
                logger.error("invalid option....!");
            }
        } while (1 == flag);
        return Float.valueOf(String.format("%.1f", experience));
    }

    public int getChoice() {
        int flag = 0;
        int choice = 0;
        do {
            try {
                logger.info("\nEnter your choice                 : ");
                scanner = new Scanner(System.in);
                choice = Integer.parseInt(scanner.nextLine());
                flag = 0;
            } catch (NumberFormatException exception) {
                flag = 1;
                logger.error("invalid option....!");
            } catch (NoSuchElementException exception) {
                flag = 1;
                logger.error("invalid option....!");
            }
        } while (1 == flag);
        return choice;
    }

    public int getIdToManipulate(String fieldName) {
        int flag = 0;
        int choice = 0;
        do {
            try {
                logger.info("\nEnter " + fieldName + " ID : ");
                scanner = new Scanner(System.in);
                choice = Integer.parseInt(scanner.nextLine());
                flag = 0;
            } catch (NumberFormatException exception) {
                flag = 1;
                logger.error("invalid option....!\n");
            } catch (NoSuchElementException exception) {
                flag = 1;
                logger.error("invalid option....!");
            }
        } while (1 == flag);
        return choice;
    }
}
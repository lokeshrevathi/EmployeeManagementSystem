package com.ideas2it.ems.model.trainermodel;

import java.util.List;

import com.ideas2it.ems.model.traineemodel.Trainee;

public class Trainer {
    private Integer id;
    private String name;
    private String dob;
    private String role;
    private float experience;
    private long phoneNo;
    private String mailId;
    private List<Trainee> trainee;

    public Trainer(Integer id, String name, String dob,
                   String role, long phoneNo,
                   String mailId, float experience) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.role = role;
        this.experience = experience;
        this.phoneNo = phoneNo;
        this.mailId = mailId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public float getExperience() {
        return experience;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getMailId() {
        return mailId;
    }

    public void setTrainee(List<Trainee> trainee) {
        this.trainee = trainee;
    }

    public List<Trainee> getTrainee() {
        return trainee;
    } 

    @Override
    public String toString() {
        StringBuilder details = new StringBuilder();
        details.append("\nTrainer ID          : ").append(id)
               .append("\nTrainer name        : ").append(name)
               .append("\nTrainer D.O.B       : ").append(dob)
               .append("\nTrainer role        : ").append(role)
               .append("\nTrainer phone no    : ").append(phoneNo)
               .append("\nTrainer mail ID     : ").append(mailId)
               .append("\nYears of experience : ").append(experience);
        return details.toString();
    }
}

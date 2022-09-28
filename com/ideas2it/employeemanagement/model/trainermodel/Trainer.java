package com.ideas2it.employeemanagement.model.trainermodel;

import java.time.LocalDate;
import java.util.List;

import com.ideas2it.employeemanagement.model.traineemodel.Trainee;
import com.ideas2it.employeemanagement.util.Role;

public class Trainer {
    private int id;
    private String name;
    private LocalDate dob;
    private int age;
    private Role role;
    private float experience;
    private long phoneNo;
    private String mailId;
    private List<Trainee> trainee;

    public Trainer(int id, String name, LocalDate dob, int age,
                   Role role, long phoneNo,
                   String mailId, float experience) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.age = age;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
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
               .append("\nTrainer age         : ").append(age)
               .append("\nTrainer role        : ").append(role)
               .append("\nTrainer phone no    : ").append(phoneNo)
               .append("\nTrainer mail ID     : ").append(mailId)
               .append("\nYears of experience : ").append((float)experience);
        return details.toString();
    }
}

package com.ideas2it.employeemanagement.model.traineemodel;

import java.time.LocalDate;

import com.ideas2it.employeemanagement.util.Role;

public class Trainee {
    private int id;
    private String name;
    private LocalDate dob;
    private int age;
    private Role role;
    private long phoneNo;
    private String mailId;
    private String status;
    private int trainerId;

    public Trainee(int id, String name, LocalDate dob, int age,
                   Role role, long phoneNo, String mailId, String status, int trainerId) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.age = age;
        this.role = role;
        this.phoneNo = phoneNo;
        this.mailId = mailId;
        this.status = status;
        this.trainerId = trainerId;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
       
    }

    public int getTrainerId() {
        return trainerId;
    }

    @Override
    public String toString() {
        StringBuilder details = new StringBuilder();
        details.append("\nTrainee ID          : ").append(id)
               .append("\nTrainee name        : ").append(name)
               .append("\nTrainee D.O.B       : ").append(dob)
               .append("\nTrainee age         : ").append(age)
               .append("\nTrainee role        : ").append(role)
               .append("\nTrainee phone no    : ").append(phoneNo)
               .append("\nTrainee mail ID     : ").append(mailId);
        return details.toString();
    }
}
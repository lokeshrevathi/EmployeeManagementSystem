package com.ideas2it.ems.model.traineemodel;

public class Trainee {
    private Integer id;
    private String name;
    private String dob;
    private String role;
    private long phoneNo;
    private String mailId;
    private String status;
    private Integer trainerId;

    public Trainee(Integer id, String name, String dob,
                   String role, long phoneNo, String mailId, String status, Integer trainerId) {
        this.id = id;
        this.name = name;
        this.dob = dob;
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

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
       
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public String toString() {
        StringBuilder details = new StringBuilder();
        details.append("\nTrainee ID          : ").append(id)
               .append("\nTrainee name        : ").append(name)
               .append("\nTrainee D.O.B       : ").append(dob)
               .append("\nTrainee role        : ").append(role)
               .append("\nTrainee phone no    : ").append(phoneNo)
               .append("\nTrainee mail ID     : ").append(mailId);
        return details.toString();
    }
}
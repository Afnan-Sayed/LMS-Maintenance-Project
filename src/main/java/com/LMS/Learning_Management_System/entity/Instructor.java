package com.LMS.Learning_Management_System.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "instructor")
public class Instructor {
    @Id
    private int userAccountId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;

    private String firstName;
    private String lastName;
    public Instructor() {

    }
    public Instructor(int userAccountId, Users userId, String firstName, String lastName) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Instructor(Users savedUser) {
        this.userId = savedUser;
    }
    public int getUserAccountId() {
        return userAccountId;
    }
    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }
    public Users getUserId() {
        return userId;

    }
    public void setUserId(Users userId) {
        this.userId = userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}

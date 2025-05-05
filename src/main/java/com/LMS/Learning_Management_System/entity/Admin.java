package com.LMS.Learning_Management_System.entity;


import jakarta.persistence.*;

@Entity
@Table(name ="admin")
public class Admin {
    @Id
    private int userAccountId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;


    private String firstName;
    private String lastName;
    public Admin() {

    }
    public Admin(int userAccountId, Users userId, String firstName, String lastName) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Admin(Users savedUser) {
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
        return "Admin{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

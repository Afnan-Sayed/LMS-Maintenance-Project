package com.LMS.Learning_Management_System.entity;

import jakarta.persistence.*;


@Entity
@Table(name ="student")
public class Student {
    @Id
    private int userAccountId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    @MapsId
    //    This annotation tells JPA that the primary key of the current entity
    //    (userAccountId) should be mapped to the primary key of the Users entity through userId.
    //     This means that both entities share the same primary key value.
    private Users userId;
    private String firstName;
    private String lastName;
    public Student() {

    }
    public Student(int userAccountId, Users userId, String firstName, String lastName) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(Users savedUser) {
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
        return "Student{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

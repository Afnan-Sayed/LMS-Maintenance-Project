package com.LMS.Learning_Management_System.dto;

public class StudentDto {
    private int userAccountId;
    private String firstName;
    private String lastName;

    public StudentDto( int userAccountId, String firstName, String lastName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.userAccountId = userAccountId;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
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
}

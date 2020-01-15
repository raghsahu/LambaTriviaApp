package com.trivia.lambatriviaapp.Model_Class;

public class ContactModel {
    public String name;
    public String phoneNumber;


    public ContactModel(String name, String phoneNumber) {
        this.name=name;
        this.phoneNumber=phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }
}

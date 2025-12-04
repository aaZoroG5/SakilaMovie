package com.pluralsight.models;

public class Actor {

    //properties taken from sakila DB
    int actorID;
    String firstName;
    String lastName;

    //constructor
    public Actor(int actorID, String firstName, String lastName) {
        this.actorID = actorID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //toString to print out actor details
    @Override
    public String toString() {
        return "Actor{" +
                "actorID=" + actorID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}

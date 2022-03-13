package com.ubb.postuniv.domain;

import java.time.LocalDate;
import java.util.Date;

public class ClientCard extends Entity{
    private String firstName;
    private String lastName;
    private String cnp;
    private LocalDate birthDate;
    private LocalDate registrationDate;

    public ClientCard(String id, String firstName, String lastName, String cnp, LocalDate birthDate, LocalDate registrationDate) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return "ClientCard{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cnp='" + cnp + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                '}';
    }
}

package com.ubb.postuniv.domain;

public class ClientCard {
    // id, nume, prenume, CNP, data nașterii (`dd.mm.yyyy`), data înregistrării (`dd.mm.yyyy`). CNP-ul trebuie să fie unic.
    private String id;
    private String firstName;
    private String lastName;
    private String cnp;
    private String birthDate;
    private String registrationDate;

    public ClientCard(String id, String firstName, String lastName, String cnp, String birthDate, String registrationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }

    public String getId() {
        return id;
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

    public String getBirthDate() {
        return birthDate;
    }

    public String getRegistrationDate() {
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

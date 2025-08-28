package com.example.e_Voting.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Voters")
public class Voters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int voterId;

    private String name;

    private String gender;

    private String email;

    @Column(nullable = true)
    private String phone;

    private LocalDate dob;

    private String address;

    private String password;


    public Voters() {
    }

    public Voters(int voterId, String name, String gender, String email, String phone,LocalDate dob, String address, String password) {
        this.voterId = voterId;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.dob=dob;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVoterId() {
        return voterId;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

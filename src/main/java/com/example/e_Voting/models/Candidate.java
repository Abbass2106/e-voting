package com.example.e_Voting.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long candidateId;

    private String name;
    private String position;
    private String password;

    public Candidate() {
    }

    public Candidate(long candidateId, String name, String position, String password) {
        this.candidateId = candidateId;
        this.name = name;
        this.position = position;
        this.password = password;
    }


    public long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return position;
    }

    public void setStatus(String status) {
        this.position = position;
    }
}

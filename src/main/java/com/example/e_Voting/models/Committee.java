package com.example.e_Voting.models;

import jakarta.persistence.*;

@Entity
@Table(name = "committee")
public class Committee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long committeeId;
    private String name;
    private String password;

    public Committee() {
    }

    public Committee(Long committeeId, String name, String password) {
        this.committeeId = committeeId;
        this.name = name;
        this.password = password;
    }

    public Long getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(Long committeeId) {
        this.committeeId = committeeId;
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
}

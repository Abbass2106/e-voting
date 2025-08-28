package com.example.e_Voting.models;


import jakarta.persistence.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Entity
@Table(name = "elections")
@Controller
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int electionId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public Election() {
    }

    public Election(int electionId, String title, String description, LocalDate startDate, LocalDate endDate) {
        this.electionId = electionId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getElectionId() {
        return electionId;
    }

    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

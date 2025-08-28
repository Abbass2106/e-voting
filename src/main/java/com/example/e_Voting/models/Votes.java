package com.example.e_Voting.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Controller;

@Entity
@Table(name = "votes")
public class Votes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int voteId;
    private int electionId;
    private int voterId;
    private int candidateId;

    public Votes() {
    }

    public Votes(int voteId, int candidateId, int voterId, int electionId) {
        this.voteId = voteId;
        this.candidateId = candidateId;
        this.voterId = voterId;
        this.electionId = electionId;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getVoterId() {
        return voterId;
    }

    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

    public int getElectionId() {
        return electionId;
    }

    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }
}

package com.example.e_Voting.repositories;

import com.example.e_Voting.models.Votes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotesRepository extends JpaRepository<Votes, Integer> {
    boolean existsByVoterIdAndElectionId(int voterId, int electionId);
}





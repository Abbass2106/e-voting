package com.example.e_Voting.repositories;

import com.example.e_Voting.models.Candidate;
import com.example.e_Voting.models.Voters;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voters, Long > {
    Optional<Voters> findByName(String Name);
    Optional<Voters> findByVoterId(Integer voterId);
}

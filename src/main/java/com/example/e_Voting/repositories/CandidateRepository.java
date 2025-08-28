package com.example.e_Voting.repositories;

import com.example.e_Voting.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository <Candidate, Long >{
    Optional<Candidate> findByName(String Name);
}

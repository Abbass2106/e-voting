package com.example.e_Voting.repositories;

import com.example.e_Voting.models.CandidateElection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateElectionRepository extends JpaRepository<CandidateElection, Long> {
    List<CandidateElection> findByCandidateId(Long candidateId);
    List<CandidateElection> findByElectionId(Long electionId);

    boolean existsByCandidateIdAndElectionId(Long candidateId, Long electionId);
}

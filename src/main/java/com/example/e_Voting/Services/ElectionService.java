package com.example.e_Voting.Services;

import com.example.e_Voting.models.CandidateElection;
import com.example.e_Voting.models.Election;
import com.example.e_Voting.repositories.CandidateElectionRepository;
import com.example.e_Voting.repositories.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private CandidateElectionRepository candidateElectionRepository;

    public List<Election> getAllAvailableElections(Long candidateId) {
        // Find elections the candidate is already enrolled in
        List<Long> enrolledElectionIds = candidateElectionRepository
                .findByCandidateId(candidateId)
                .stream()
                .map(CandidateElection::getElectionId)
                .collect(Collectors.toList());

        // Return elections the candidate is NOT yet enrolled in
        return electionRepository.findAll()
                .stream()
                .filter(e -> !enrolledElectionIds.contains(e.getElectionId()))
                .collect(Collectors.toList());
    }
}

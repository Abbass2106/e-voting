package com.example.e_Voting.Services;

import com.example.e_Voting.models.CandidateElection;
import com.example.e_Voting.models.Election;
import com.example.e_Voting.models.Votes;
import com.example.e_Voting.repositories.CandidateElectionRepository;
import com.example.e_Voting.repositories.ElectionRepository;
import com.example.e_Voting.repositories.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class VotesService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private CandidateElectionRepository candidateElectionRepository;

    @Autowired
    private VotesRepository votesRepository;

    public List<Map<String, Object>> getActiveElections() {
        List<Election> allElections = electionRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Election election : allElections) {
            if (!isActive(election)) continue;

            List<CandidateElection> candidates =
                    candidateElectionRepository.findByElectionId((long) election.getElectionId());

            Map<String, Object> electionMap = new HashMap<>();
            electionMap.put("election", election);
            electionMap.put("candidates", candidates);

            result.add(electionMap);
        }

        return result;
    }

    private boolean isActive(Election election) {
        LocalDate today = LocalDate.now();
        return !today.isBefore(election.getStartDate()) && !today.isAfter(election.getEndDate());
    }

    public boolean castVote(int voterId, int electionId, int candidateId) {
        if (votesRepository.existsByVoterIdAndElectionId(voterId, electionId)) {
            return false;
        }

        Votes vote = new Votes();
        vote.setVoterId(voterId);
        vote.setElectionId(electionId);
        vote.setCandidateId(candidateId);

        votesRepository.save(vote);
        return true;
    }
}

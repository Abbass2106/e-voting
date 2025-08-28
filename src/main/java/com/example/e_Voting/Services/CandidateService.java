package com.example.e_Voting.Services;

import com.example.e_Voting.models.Candidate;
import com.example.e_Voting.models.CandidateElection;
import com.example.e_Voting.repositories.CandidateRepository;
import com.example.e_Voting.repositories.CandidateElectionRepository;
import com.example.e_Voting.repositories.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private CandidateElectionRepository candidateElectionRepository;

    public boolean register(Candidate candidate) {
        if (findByUsername(candidate.getName()) != null) {
            return false;
        }

        candidateRepository.save(candidate);
        return true;
    }

    public Candidate login(String username, String password) {
        Candidate candidate = findByUsername(username);

        if (candidate == null) {
            return null;
        }

        if (candidate.getPassword().equals(password)) {
            return candidate;
        } else {
            return null;
        }
    }

    public Candidate findByUsername(String username) {
        Optional<Candidate> optional = candidateRepository.findByName(username);
        return optional.orElse(null);
    }

    public boolean enrollElection(Long candidateId, Long electionId, String name,String position) {
        boolean alreadyEnrolled = candidateElectionRepository.existsByCandidateIdAndElectionId(candidateId, electionId);
        if (alreadyEnrolled) return false;

        CandidateElection enrollment = new CandidateElection();
        enrollment.setCandidateId(candidateId);
        enrollment.setElectionId(electionId);
        enrollment.setName(name);
        enrollment.setPosition(position);
        candidateElectionRepository.save(enrollment);

        return true;
    }
}

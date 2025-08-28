package com.example.e_Voting.repositories;

import com.example.e_Voting.models.Committee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommitteeRepository extends JpaRepository<Committee, Long> {
    Optional<Committee> findByName(String name);
}

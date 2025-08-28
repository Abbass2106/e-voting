package com.example.e_Voting.repositories;

import com.example.e_Voting.models.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {
}

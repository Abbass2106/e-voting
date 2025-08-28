package com.example.e_Voting.controllers;

import ch.qos.logback.core.model.Model;
import com.example.e_Voting.models.Election;
import com.example.e_Voting.repositories.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ElectionController {

    @Autowired
    private ElectionRepository electionRepository;

    @GetMapping("/createElection")
    public String showForm(Model model){
        return "createElection";
    }

    @PostMapping("/createElection")
    public String ProcessForm(@ModelAttribute Model model, Election election){
        electionRepository.save(election);
        return "ElectionCommittee";
    }


}

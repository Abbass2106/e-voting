package com.example.e_Voting.controllers;

import com.example.e_Voting.models.Committee;
import com.example.e_Voting.repositories.CommitteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ✅ Correct import for Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class CommitteeController {

    @Autowired
    private CommitteeRepository committeeRepository;

    @GetMapping("/ElectionCommittee")
    public String home() {
        return "ElectionCommittee";
    }

    @GetMapping("/committeeLogin")
    public String showLogin(Model model) {
        model.addAttribute("committee", new Committee());
        return "committeeLogin";
    }

    @PostMapping("/committeeLogin")
    public String processLogin(@ModelAttribute("committee") Committee formCommittee, Model model) {
        Optional<Committee> optionalCommittee = committeeRepository.findByName(formCommittee.getName());

        if (optionalCommittee.isPresent()) {
            Committee committee = optionalCommittee.get();
            if (committee.getPassword().equals(formCommittee.getPassword())) {
                return "redirect:/ElectionCommittee";
            } else {
                model.addAttribute("loginError", "Invalid password.");
            }
        } else {
            model.addAttribute("loginError", "No account found with that name.");
        }

        return "committeeLogin";
    }
}

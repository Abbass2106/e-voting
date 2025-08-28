package com.example.e_Voting.controllers;

import com.example.e_Voting.Services.CandidateService;
import com.example.e_Voting.Services.ElectionService;
import com.example.e_Voting.models.Candidate;
import com.example.e_Voting.models.Election;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private ElectionService electionService;


    @GetMapping("/candidates")
    public String home(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("userName");
        Long candidateId = (Long) session.getAttribute("candidateId");

        if (userName == null || candidateId == null) {
            model.addAttribute("errorMessage", "User session not found. Please log in.");
            return "error";
        }

        model.addAttribute("userName", userName);
        model.addAttribute("candidateId", candidateId);
        return "candidates";
    }


    @GetMapping("/candidateLogin")
    public String showLogin(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "candidateLogin";
    }

    @PostMapping("/candidateLogin")
    public String processLogin(Model model,
                               @ModelAttribute("candidate") Candidate formCandidate,
                               HttpSession session) {
        Candidate candidate = candidateService.login(formCandidate.getName(), formCandidate.getPassword());

        if (candidate != null) {

            session.setAttribute("userName", candidate.getName());
            session.setAttribute("candidateId", candidate.getCandidateId());

            return "redirect:/candidates";
        }

        model.addAttribute("loginError", "Invalid credentials.");
        return "candidateLogin";
    }

    @GetMapping("/candidateRegister")
    public String showRegistrationForm(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "candidateRegister";
    }

    @PostMapping("/candidateRegister")
    public String processRegistration(@ModelAttribute("candidate") Candidate candidate, Model model) {
        boolean registered = candidateService.register(candidate);

        if (!registered) {
            model.addAttribute("registerError", "Name is already registered.");
            return "candidateRegister";
        }

        return "redirect:/candidateLogin";
    }


    @GetMapping("/enrollElection")
    public String showEnrollElectionPage(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("userName");
        Long candidateId = (Long) session.getAttribute("candidateId");

        if (userName == null || candidateId == null) {
            model.addAttribute("errorMessage", "User session not found. Please log in.");
            return "error";
        }

        List<Election> availableElections = electionService.getAllAvailableElections(candidateId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Optional: format election titles for easier rendering
        List<String> electionDisplayTitles = availableElections.stream()
                .map(e -> e.getTitle() + " (" + e.getStartDate().format(formatter) + " to " + e.getEndDate().format(formatter) + ")")
                .toList();

        model.addAttribute("userName", userName);
        model.addAttribute("availableElections", availableElections);
        model.addAttribute("electionDisplayTitles", electionDisplayTitles);

        return "enrollElection";
    }





    @PostMapping("/enrollElection")
    public String enrollCandidate(@RequestParam("electionId") Long electionId,
                                  @RequestParam("position") String position,
                                  HttpSession session,
                                  Model model) {
        String userName = (String) session.getAttribute("userName");
        Long candidateId = (Long) session.getAttribute("candidateId");

        if (userName == null || candidateId == null) {
            model.addAttribute("errorMessage", "User session not found. Please log in.");
            return "error";
        }

        boolean success = candidateService.enrollElection(candidateId, electionId, userName, position);

        if (success) {
            model.addAttribute("successMessage", "Successfully enrolled in the selected election.");
        } else {
            model.addAttribute("errorMessage", "You are already enrolled or something went wrong.");
        }

        List<Election> availableElections = electionService.getAllAvailableElections(candidateId);
        model.addAttribute("availableElections", availableElections);
        model.addAttribute("userName", userName);

        return "enrollElection";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/candidateLogin";
    }


}

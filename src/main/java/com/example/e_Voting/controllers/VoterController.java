package com.example.e_Voting.controllers;

import com.example.e_Voting.Services.VotesService;
import com.example.e_Voting.models.Voters;
import com.example.e_Voting.repositories.VoterRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class VoterController {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private VotesService votesService;

    @GetMapping("/voters")
    public String home(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("userName");
        Integer voterId = (Integer) session.getAttribute("voterId");

        if (userName == null || voterId == null) {
            model.addAttribute("errorMessage", "User session not found. Please log in.");
            return "error";
        }

        model.addAttribute("userName", userName);
        model.addAttribute("voterId", voterId);

        return "voters";
    }

    @GetMapping("/voterLogin")
    public String showLogin(Model model) {
        model.addAttribute("voter", new Voters());
        return "voterLogin";
    }

    @PostMapping("/voterLogin")
    public String processLogin(@ModelAttribute("voter") Voters formVoter, HttpSession session, Model model) {
        Optional<Voters> optionalVoter = voterRepository.findByName(formVoter.getName());

        if (optionalVoter.isPresent()) {
            Voters voter = optionalVoter.get();
            if (voter.getPassword().equals(formVoter.getPassword())) {
                session.setAttribute("userName", voter.getName());
                session.setAttribute("voterId", voter.getVoterId());
                return "redirect:/voters";
            } else {
                model.addAttribute("loginError", "Invalid password.");
            }
        } else {
            model.addAttribute("loginError", "No account found with that name.");
        }

        return "voterLogin";
    }

    @GetMapping("/voterRegister")
    public String showRegistrationForm(Model model) {
        model.addAttribute("voter", new Voters());
        return "voterRegister";
    }

    @PostMapping("/voterRegister")
    public String processRegistration(@ModelAttribute("voter") Voters voter, Model model) {
        if (voterRepository.findByName(voter.getName()).isPresent()) {
            model.addAttribute("registerError", "Name is already registered.");
            return "voterRegister";
        }

        voterRepository.save(voter);
        return "redirect:/voterLogin";
    }

    @PostMapping("/voterLogout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/voterLogin";
    }

    @GetMapping("/voteElection")
    public String showVoteElection(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        Integer voterId = (Integer) session.getAttribute("voterId");

        List<Map<String, Object>> activeElections = votesService.getActiveElections();

        model.addAttribute("userName", userName);
        model.addAttribute("voterId", voterId);
        model.addAttribute("activeElections", activeElections);
        return "voteElection";
    }

    @PostMapping("/submitVote")
    public String submitVote(@RequestParam("electionId") Long electionId,
                             @RequestParam Map<String, String> allParams,
                             HttpSession session, Model model) {

        Integer voterIdObj = (Integer) session.getAttribute("voterId");
        if (voterIdObj == null) {
            model.addAttribute("message", "Session expired or invalid.");
            return "redirect:/voterLogin";
        }

        int voterId = voterIdObj;
        String candidateKey = "candidateId_" + electionId;
        int candidateId = Integer.parseInt(allParams.get(candidateKey));
        int electionIdInt = electionId.intValue();

        boolean voted = votesService.castVote(voterId, electionIdInt, candidateId);

        if (voted) {
            model.addAttribute("vote", "Vote has been submitted successfully.");
        } else {
            model.addAttribute("message", "You already voted or the vote failed.");
        }

        return "redirect:/voters";
    }

    @GetMapping("/voterDetails")
    public String showVoterDetailsForm(Model model, HttpSession session) {
        Integer voterId = (Integer) session.getAttribute("voterId");

        if (voterId == null) {
            model.addAttribute("errorMessage", "Session expired. Please log in again.");
            return "voterLogin";
        }

        Optional<Voters> optionalVoter = voterRepository.findByVoterId(voterId);
        if (optionalVoter.isPresent()) {
            model.addAttribute("voter", optionalVoter.get());
        } else {
            model.addAttribute("voter", new Voters());
            model.addAttribute("errorMessage", "Voter not found.");
        }

        return "voterDetails";
    }

    @PostMapping("/voterDetails")
    public String uploadDetails(@ModelAttribute("voter") Voters formVoter,
                                HttpSession session,
                                Model model) {

        Integer voterId = (Integer) session.getAttribute("voterId");

        Optional<Voters> optionalVoter = voterRepository.findByVoterId(voterId);

        Voters existingVoter = optionalVoter.get();

        existingVoter.setAddress(formVoter.getAddress());
        existingVoter.setDob(formVoter.getDob());
        existingVoter.setGender(formVoter.getGender());
        existingVoter.setEmail(formVoter.getEmail());
        existingVoter.setPhone(formVoter.getPhone());

        voterRepository.save(existingVoter);

        model.addAttribute("voter", existingVoter);
        model.addAttribute("successMessage", "Details saved successfully.");

        return "voterDetails";
    }

    @GetMapping("/viewDetails")
    public  String showVoterDetails(HttpSession session,Model model){
        Integer voterId = (Integer) session.getAttribute("voterId");

        Optional<Voters> optionalVoters =voterRepository.findByVoterId(voterId);
        model.addAttribute("voter",optionalVoters.get());
        return "viewDetails";
    }
}

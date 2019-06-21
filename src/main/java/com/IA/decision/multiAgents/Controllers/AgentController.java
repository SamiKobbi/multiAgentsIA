package com.IA.decision.multiAgents.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.repositories.AgentRepository;

@Service
public class AgentController {
	 @Autowired
	 private AgentRepository agentRepo;
	 
	 public void saveAgent(Agent agent)
	 {
		 agentRepo.save(agent);
	 }
	 public List<Agent> getAll()
	 {
		 return agentRepo.findAll();
	 }
}

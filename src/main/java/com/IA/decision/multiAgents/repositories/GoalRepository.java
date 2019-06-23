package com.IA.decision.multiAgents.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.Goal;


public interface GoalRepository extends JpaRepository<Goal, Long>  {
	Goal findByAgent(Agent agent);
}

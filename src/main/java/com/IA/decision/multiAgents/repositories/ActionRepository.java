package com.IA.decision.multiAgents.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IA.decision.multiAgents.BO.Action;
import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.EventName;



public interface ActionRepository extends JpaRepository<Action, Long> {
	List<Action> findByAgentDest(Agent agentDest);
}

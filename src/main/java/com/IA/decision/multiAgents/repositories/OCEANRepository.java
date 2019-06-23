package com.IA.decision.multiAgents.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.OCEAN;

public interface OCEANRepository extends JpaRepository<OCEAN, Long>  {
	OCEAN findByAgent(Agent agent);
}

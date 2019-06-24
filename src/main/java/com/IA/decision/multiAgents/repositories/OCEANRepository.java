package com.IA.decision.multiAgents.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.OCEAN;

@Repository
public interface OCEANRepository extends JpaRepository<OCEAN, Long>  {
	OCEAN findByAgent(Agent agent);
}

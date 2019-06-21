package com.IA.decision.multiAgents.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.IA.decision.multiAgents.BO.Agent;


@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

	
}

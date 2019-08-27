package com.IA.decision.multiAgents.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.OCC;

public interface OCCRepository extends JpaRepository<OCC, Long>  {
	Optional<OCC> findByAgent(Agent agent);
}

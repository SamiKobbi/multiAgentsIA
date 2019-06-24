package com.IA.decision.multiAgents.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.Goal;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long>  {
	List<Goal> findByAgent(Agent agent);
}

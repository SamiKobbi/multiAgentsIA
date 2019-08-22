package com.IA.decision.multiAgents.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.EventName;
import com.IA.decision.multiAgents.BO.GoalName;
@Repository
public interface EventNameRepository extends JpaRepository<EventName, Long>  {
	
	List<EventName> findByGoalName(GoalName goalName);
	List<EventName> findByAgent(Agent agent);
}

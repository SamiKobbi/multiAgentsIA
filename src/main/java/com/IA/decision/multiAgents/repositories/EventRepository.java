package com.IA.decision.multiAgents.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.Event;
import com.IA.decision.multiAgents.BO.GoalName;
@Repository
public interface EventRepository extends JpaRepository<Event, Long>  {
	List<Event> findByGoalName(GoalName goalName);
	List<Event> findByAgent(Agent agent);
}

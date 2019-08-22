package com.IA.decision.multiAgents.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IA.decision.multiAgents.BO.EventInfo;
import com.IA.decision.multiAgents.BO.EventName;
import com.IA.decision.multiAgents.BO.EventReaction;
import com.IA.decision.multiAgents.BO.GoalName;

public interface EventReactionRepository extends JpaRepository<EventReaction, Long>   {
	EventReaction findByEventInfo(EventInfo eventInfo);
}

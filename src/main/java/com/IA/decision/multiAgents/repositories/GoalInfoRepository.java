package com.IA.decision.multiAgents.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.GoalName;
import com.IA.decision.multiAgents.BO.GoalInfo;

public interface GoalInfoRepository extends JpaRepository<GoalInfo, Long> {
	List<GoalInfo> findByAgent(Agent agent);
	List<GoalInfo> findByGoalName(GoalName goalName);
}

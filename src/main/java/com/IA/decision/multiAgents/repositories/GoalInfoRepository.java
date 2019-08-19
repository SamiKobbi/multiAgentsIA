package com.IA.decision.multiAgents.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.GoalName;
import com.IA.decision.multiAgents.BO.GoalInfo;

public interface GoalInfoRepository extends JpaRepository<GoalInfo, Long> {
	List<GoalInfo> findByAgent(Agent agent);
	List<GoalInfo> findByGoalName(GoalName goalName);
	@Query("SELECT goalInfo FROM GoalInfo goalInfo WHERE goalInfo.agent.id = ?1 and goalInfo.goalName.id = ?2")
	GoalInfo findByGoalNameAndAgent(Long agentId, Long goal_name_id);
}

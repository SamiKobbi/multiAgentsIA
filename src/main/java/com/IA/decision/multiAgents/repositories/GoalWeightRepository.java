package com.IA.decision.multiAgents.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IA.decision.multiAgents.BO.Goal;
import com.IA.decision.multiAgents.BO.GoalWeight;

public interface GoalWeightRepository extends JpaRepository<GoalWeight, Long> {

}

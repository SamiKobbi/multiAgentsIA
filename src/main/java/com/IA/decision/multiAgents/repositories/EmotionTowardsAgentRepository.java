package com.IA.decision.multiAgents.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.IA.decision.multiAgents.BO.EmotionTowardsAgent;

public interface EmotionTowardsAgentRepository extends JpaRepository<EmotionTowardsAgent, Long> {
	@Query("SELECT emotionTowardsAgent FROM EmotionTowardsAgent emotionTowardsAgent WHERE emotionTowardsAgent.agent.id = ?1 and emotionTowardsAgent.agentDest.id = ?2")
	EmotionTowardsAgent findEmotionValue(Long agentSrcId, Long agentDstId);
}

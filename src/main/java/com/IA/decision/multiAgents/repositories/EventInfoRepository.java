package com.IA.decision.multiAgents.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.IA.decision.multiAgents.BO.EventInfo;

@Repository
public interface EventInfoRepository extends JpaRepository<EventInfo, Long>  {
	@Query("SELECT eventInfo FROM EventInfo eventInfo WHERE eventInfo.agent.id = ?1 and eventInfo.eventName.id = ?2")
	Optional<EventInfo> findByEventNameAndAgent(Long agentId, Long event_name_id);
}

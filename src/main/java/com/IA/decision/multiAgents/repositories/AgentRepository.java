package com.IA.decision.multiAgents.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.IA.decision.multiAgents.BO.Agent;

@RepositoryRestResource(collectionResourceRel = "agent", path = "agent")
@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

}

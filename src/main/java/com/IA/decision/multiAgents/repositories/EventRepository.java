package com.IA.decision.multiAgents.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IA.decision.multiAgents.BO.Event;

public interface EventRepository extends JpaRepository<Event, Long>  {

}

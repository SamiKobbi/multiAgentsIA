package com.IA.decision.multiAgents.Jade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.IA.decision.multiAgents.BO.Action;
import com.IA.decision.multiAgents.BO.Event;
import com.IA.decision.multiAgents.BO.GoalInfo;
import com.IA.decision.multiAgents.BO.OCC;
import com.IA.decision.multiAgents.config.ApplicationContextProvider;
import com.IA.decision.multiAgents.repositories.ActionRepository;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.repositories.EventRepository;
import com.IA.decision.multiAgents.repositories.GoalInfoRepository;
import com.IA.decision.multiAgents.repositories.GoalNameRepository;
import com.IA.decision.multiAgents.repositories.OCCRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.util.leap.Iterator;

public class AgentTemplate extends Agent {

	private Object[] obj = null;
	String Rea;
	String msg2;
	String message;
	int index = 0;

	protected void setup() {
		System.out.println(getLocalName() + "started");
		// ---------------
		try {

			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			// Enregistrement de la description de l'agent1 dans DF (Directory Facilitator)
			DFService.register(this, dfd);
			System.out.println(getLocalName() + " enregistr� par le <DF>");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		addBehaviour(new AgentBehaviour3());
		// addBehaviour(new AgentBehaviour());
		// addBehaviour(new AgentBehaviour2());
	}

	private class AgentBehaviour3 extends Behaviour {
		private int k = 0, i = 1, p = 0;

		double Weight = 0.00;

		public void action() {
			ACLMessage msg2 = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			EventRepository eventRepo = ApplicationContextProvider.getApplicationContext()
					.getBean(EventRepository.class);
			AgentRepository agentRepo = ApplicationContextProvider.getApplicationContext()
					.getBean(AgentRepository.class);
			ActionRepository actionRepo = ApplicationContextProvider.getApplicationContext()
					.getBean(ActionRepository.class);
				GoalInfoRepository goalInfoRepo = ApplicationContextProvider.getApplicationContext()
					.getBean(GoalInfoRepository.class);
			OCCRepository OCCRepo = ApplicationContextProvider.getApplicationContext().getBean(OCCRepository.class);

			if (msg2 != null ) {
				String message = msg2.getContent();
				String msgType = (message.split(":"))[0];
				if(msgType.equals("event"))
				{
				System.out.println("AgentTemplate received from " + msg2.getContent() + " Sender : " + msg2.getSender());
				Long agentId = agentRepo.findAll().stream().filter(agent -> agent.getName().equals(getLocalName()))
						.findAny().get().getId();
				Optional<Event> event = eventRepo.findById(Long.parseLong((message.split(":"))[1]));
				List<GoalInfo> goalInfos = goalInfoRepo.findByGoalName(event.get().getGoalName());
				Optional<GoalInfo> goalInfo = goalInfos.stream()
						.filter(goalInf -> goalInf.getAgent().getId() == agentId).findAny();
				Double impact = event.get().getEventIntensityLevel() * goalInfo.get().getWeight();
				OCC occ = new OCC();
				Optional<com.IA.decision.multiAgents.BO.Agent> agent = agentRepo.findById(agentId);
				occ.setAgent(agent.get());
				if (event.get().getEventDegree()) {
					occ.setJoy(impact);
				} else {
					occ.setDistress(impact);
				}
				OCCRepo.save(occ);
				


				p++;
				}
				else if (msgType.equals("action"))
				{
					Optional<Action> action = actionRepo.findById(Long.parseLong((message.split(":"))[1]));
					action.get();
					p++;
				}
			}
		}

		public boolean done() {
			// TODO Auto-generated method stub

			return p == 4;

		}

	}

}

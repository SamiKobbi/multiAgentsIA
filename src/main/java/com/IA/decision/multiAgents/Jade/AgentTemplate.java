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

import com.IA.decision.multiAgents.BO.EventInfo;
import com.IA.decision.multiAgents.BO.EventName;
import com.IA.decision.multiAgents.BO.EventReaction;
import com.IA.decision.multiAgents.BO.GoalInfo;
import com.IA.decision.multiAgents.BO.OCC;
import com.IA.decision.multiAgents.config.ApplicationContextProvider;
import com.IA.decision.multiAgents.repositories.ActionRepository;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.repositories.EventInfoRepository;
import com.IA.decision.multiAgents.repositories.EventNameRepository;
import com.IA.decision.multiAgents.repositories.EventReactionRepository;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class AgentTemplate extends Agent {
	private static final Logger logger = LogManager.getLogger(AgentTemplate.class);
	
	String msg2;
	String message;
	int index = 0;

	protected void setup() {
		logger.info(getLocalName() + " started");
		// ---------------
		try {

			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			// Enregistrement de la description de l'agent1 dans DF (Directory Facilitator)
			DFService.register(this, dfd);
			logger.info(getLocalName() + " registred in the <DF>");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		addBehaviour(new AgentTemplateSendBehaviour());
		addBehaviour(new AgentTemplateReceiveBehaviour());
		// addBehaviour(new AgentBehaviour2());
	}
	private class AgentTemplateReceiveBehaviour extends Behaviour {
		/**
		 * 
		 */
		private  final	EventReactionRepository eventReactionRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(EventReactionRepository.class);
		private static final long serialVersionUID = 1L;
		int index;
		@Override
		public void action() {
			
			// TODO Auto-generated method stub
			ACLMessage msg2 = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			if (msg2 != null) {		
				index++;
				logger.info("Diffuseur received " + msg2.getContent() + " from "+ msg2.getSender());
			}
		}

		@Override
		public boolean done() {
//			// TODO Auto-generated method stub
//
		
			return index == eventReactionRepo.findAll().size();
		}

	}
	private class AgentTemplateSendBehaviour extends Behaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int p = 0;
		private final EventNameRepository eventNameRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(EventNameRepository.class);
		private  final EventInfoRepository eventInfoRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(EventInfoRepository.class);
		private  final AgentRepository agentRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(AgentRepository.class);

		private  final	GoalInfoRepository goalInfoRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(GoalInfoRepository.class);
		private  final	EventReactionRepository eventReactionRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(EventReactionRepository.class);
		private  final OCCRepository OCCRepo = ApplicationContextProvider.getApplicationContext().getBean(OCCRepository.class);

		public void action() {
			ACLMessage msg2 = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		
			if (msg2 != null) {
				String message = msg2.getContent();
				String msgType = (message.split(":"))[0];
				Long agentId = agentRepo.findAll().stream().filter(agent -> agent.getName().equals(getLocalName()))
						.findAny().get().getId();
				Optional<com.IA.decision.multiAgents.BO.Agent> agent = agentRepo.findById(agentId);
				if (msgType.equals("event")) {
					logger.info(
							"AgentTemplate received event message " + msg2.getContent() + " from Sender : " + msg2.getSender());

					Optional<EventName> eventName = eventNameRepo.findById(Long.parseLong((message.split(":"))[1]));
					GoalInfo goalInfo = goalInfoRepo.findByGoalNameAndAgent(agentId,eventName.get().getGoalName().getId()
							);
					EventInfo eventInfo = eventInfoRepo.findByEventNameAndAgent( agentId, eventName.get().getId());
					Double impact = eventInfo.getEventIntensityLevel() * goalInfo.getWeight();
					Optional<OCC> optocc = OCCRepo.findById(agent.get().getId());
					OCC occ;
					if (optocc.isPresent()) {
						occ = optocc.get();
					} else {
						occ = new OCC();
					}
					occ.setAgent(agent.get());
					occ.setAgentId(agent.get().getId());
					if (eventInfo.getEventDegree()) {
						occ.setJoy(impact);
					} else {
						occ.setDistress(impact);
					}
					OCCRepo.save(occ);
					EventReaction eventReaction = eventReactionRepo.findByEventInfo(eventInfo);
					ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);
					msg3.setContent("eventReaction:" + eventReaction.getId());
					for(com.IA.decision.multiAgents.BO.Agent ag : agentRepo.findAll())
					{
					msg3.addReceiver(new AID(ag.getName(), AID.ISLOCALNAME));
					}
					send(msg3);

					p++;
				}
//				else if (msgType.equals("action"))
//				{
//			
//					Optional<OCC> optocc = OCCRepo.findById(agent.get().getId());
//					OCC occ;
//					if(optocc.isPresent())
//					{
//						occ = optocc.get();
//					}
//					else
//					{
//						occ = new OCC();
//					}
//					//praise worthy
//					//blame worthy
//					//it is a variable storing the value returned from the formula updating the occ vector
//					//praise worthy = action approval level * degree 
//					//proud should be equal to that value
//					Optional<Action> action = actionRepo.findById(Long.parseLong((message.split(":"))[1]));
//					double praiseWorthy ;
//					double blameWorthy ;
//					if(action.get().getActionDegree())
//					{
//						 praiseWorthy = action.get().getApprovalDegreeLevel();
//					 	 occ.setProud(praiseWorthy);
//					}
//					else
//					{
//						blameWorthy = action.get().getApprovalDegreeLevel();
//						occ.setShame(blameWorthy);
//					}
//					
//					occ.setAgent(agent.get());
//					occ.setAgentId(agent.get().getId());
//				
//					OCCRepo.save(occ);
//					p++;
//				}
			}
		}

		public boolean done() {
			// TODO Auto-generated method stub

			return p == eventNameRepo.findAll().size();

		}

	}

}

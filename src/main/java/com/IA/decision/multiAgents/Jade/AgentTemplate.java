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
		addBehaviour(new AgentTemplateReceiveBehaviour());
		addBehaviour(new AgentTemplateSendBehaviour());
		// addBehaviour(new AgentBehaviour2());
	}
	private class AgentTemplateSendBehaviour extends Behaviour {
		/**
		 * 
		 */
		private int index = 0;
		private static final long serialVersionUID = 2L;
		private  final AgentRepository agentRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(AgentRepository.class);
		private  final	ActionRepository actionRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(ActionRepository.class);
		@Override
		public void action() {
			Long agentId = agentRepo.findAll().stream().filter(agent -> agent.getName().equals(getLocalName()))
					.findAny().get().getId();
			Optional<com.IA.decision.multiAgents.BO.Agent> agent = agentRepo.findById(agentId);
			List<Action> actions = actionRepo.findByAgentSrc(agent.get());
			for(Action action : actions)
			{
						ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);
						msg3.setContent("action:" + action.getId());
						msg3.addReceiver(new AID(action.getAgentDest().getName(), AID.ISLOCALNAME));
						logger.info("Sending action from "+agent.get().getName()+ " to "+action.getAgentDest().getName() +" with id "+ action.getId());
						send(msg3);
						index++;
						//logger.info("action index:"+index);
				
			}
		}

		@Override
		public boolean done() {
			Long agentId = agentRepo.findAll().stream().filter(agent -> agent.getName().equals(getLocalName()))
					.findAny().get().getId();
			Optional<com.IA.decision.multiAgents.BO.Agent> agent = agentRepo.findById(agentId);
			List<Action> actions = actionRepo.findByAgentSrc(agent.get());
			return index == actions.size();
		}
//
}
	private class AgentTemplateReceiveBehaviour extends Behaviour {
		/**
		 * 
		 */
		private void updateOCCVector(Optional<OCC> optocc, Optional<com.IA.decision.multiAgents.BO.Agent> agent, Double impact, Boolean eventDegree)
		{
			OCC occ;
			logger.info("Updating OCC agent: "+ agent.get().getName()+" impact: "+impact+" event degree : "+eventDegree);
			if (optocc.isPresent()) {
				occ = optocc.get();
				
				if (eventDegree) {
					
					if(impact + occ.getJoy() > 1)
					{
						occ.setJoy(1);
					}
					else
					{
						occ.setJoy(impact + occ.getJoy());
					}
					if(occ.getDistress()-impact < 0)
					{
						occ.setDistress(0);
					}
					else
					{
						occ.setDistress(occ.getDistress()-impact);
					}
					
				}
				else
				{
					if(impact + occ.getDistress() > 1)
					{
						occ.setDistress(1);
					}
					else
					{
						occ.setDistress(impact + occ.getDistress());
					}
					if(occ.getJoy()-impact < 0)
					{
						occ.setJoy(0);
					}
					else
					{
						occ.setJoy(occ.getJoy()-impact);
					}
				}
			} else {
				
				occ = new OCC();
				if (eventDegree) {
					occ.setJoy(impact);		
				}
				else
				{
				occ.setDistress(impact);
				}
			}
			occ.setAgent(agent.get());
			OCCRepo.save(occ);
		}
		
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
		private  final	ActionRepository actionRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(ActionRepository.class);
		
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
							getLocalName()+ " received event message id: " +(message.split(":"))[1]+" event name:" + msg2.getContent() );

					Optional<EventName> eventName = eventNameRepo.findById(Long.parseLong((message.split(":"))[1]));
					GoalInfo goalInfo = goalInfoRepo.findByGoalNameAndAgent(agentId,eventName.get().getGoalName().getId()
							);
					EventInfo eventInfo = eventInfoRepo.findByEventNameAndAgent( agentId, eventName.get().getId());
					if(eventInfo != null)
					{

					Optional<OCC> optocc = OCCRepo.findByAgent(agent.get());
					Double impact = eventInfo.getEventIntensityLevel() * goalInfo.getWeight();
					
					updateOCCVector(optocc, agent , impact , eventName.get().getEventDegree());
					
					EventReaction eventReaction = eventReactionRepo.findByEventInfo(eventInfo);
					ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);
					msg3.setContent("eventReaction:" + eventReaction.getId());
					for(com.IA.decision.multiAgents.BO.Agent agentDiff : agentRepo.findAll())
					{
						if(!agentDiff.getName().equals(agent.get().getName()))
						{
							msg3.addReceiver(new AID(agentDiff.getName(), AID.ISLOCALNAME));
							logger.info("Sending event reaction to "+agentDiff.getName()+ " with id "+ eventReaction.getId());
						}
					}
					send(msg3);

					p++;
					
				}
				}
				else if (msgType.equals("eventReaction"))
				{
					logger.info( getLocalName()+" received event message reaction : " + msg2.getContent() + " from "+ msg2.getSender().getLocalName());

					p++;
				}
				else if (msgType.equals("action"))
				{
			
					Optional<OCC> optocc = OCCRepo.findByAgent(agent.get());
					Optional<Action> action = actionRepo.findById(Long.parseLong((message.split(":"))[1]));
					logger.info(
							getLocalName()+ " received " + msg2.getContent() + " from Sender : " + msg2.getSender().getLocalName());

					//praise worthy
					//blame worthy
					//it is a variable storing the value returned from the formula updating the occ vector
					//praise worthy = action approval level * degree 
					//proud should be equal to that value
					updateOCCVector(optocc, agent , action.get().getApprovalDegreeLevel() , action.get().getActionDegree());
					p++;
				}
			}
		}

		public boolean done() {
			// TODO Auto-generated method stub

			return p == eventNameRepo.findAll().size()+eventReactionRepo.findAll().size() + actionRepo.findAll().size() ;

		}

	}

}

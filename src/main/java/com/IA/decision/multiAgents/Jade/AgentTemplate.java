package com.IA.decision.multiAgents.Jade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;

import com.IA.decision.multiAgents.BO.Action;

import com.IA.decision.multiAgents.BO.EventInfo;
import com.IA.decision.multiAgents.BO.EventName;
import com.IA.decision.multiAgents.BO.EventReaction;
import com.IA.decision.multiAgents.BO.GoalInfo;
import com.IA.decision.multiAgents.BO.LikingTowardsAgent;
import com.IA.decision.multiAgents.BO.OCC;
import com.IA.decision.multiAgents.BO.OCCsTowardsAgent;
import com.IA.decision.multiAgents.BO.OCEAN;
import com.IA.decision.multiAgents.config.ApplicationContextProvider;
import com.IA.decision.multiAgents.repositories.ActionRepository;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.repositories.EventInfoRepository;
import com.IA.decision.multiAgents.repositories.EventNameRepository;
import com.IA.decision.multiAgents.repositories.EventReactionRepository;
import com.IA.decision.multiAgents.repositories.GoalInfoRepository;
import com.IA.decision.multiAgents.repositories.GoalNameRepository;
import com.IA.decision.multiAgents.repositories.OCCRepository;
import com.IA.decision.multiAgents.repositories.OCCsTowardsAgentRepository;
import com.IA.decision.multiAgents.repositories.OCEANRepository;

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
		//addBehaviour(new AgentTemplateSendBehaviour());
		// addBehaviour(new AgentBehaviour2());
	}
//	private class AgentTemplateSendBehaviour extends Behaviour {
//		/**
//		 * 
//		 */
//		private int index = 0;
//		private static final long serialVersionUID = 2L;
//		private  final AgentRepository agentRepo = ApplicationContextProvider.getApplicationContext()
//				.getBean(AgentRepository.class);
//		private  final	ActionRepository actionRepo = ApplicationContextProvider.getApplicationContext()
//				.getBean(ActionRepository.class);
//		
//		@Override
//		public void action() {
//			Long agentId = agentRepo.findAll().stream().filter(agent -> agent.getName().equals(getLocalName()))
//					.findAny().get().getId();
//			Optional<com.IA.decision.multiAgents.BO.Agent> agent = agentRepo.findById(agentId);
//			List<Action> actions = actionRepo.findByAgentSrc(agent.get());
//			for(Action action : actions)
//			{
//						ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);
//						msg3.setContent("action:" + action.getId());
//						msg3.addReceiver(new AID(action.getAgentDest().getName(), AID.ISLOCALNAME));
//						logger.info("Sending action from "+agent.get().getName()+ " to "+action.getAgentDest().getName() +" with id "+ action.getId());
//						send(msg3);
//						index++;
//						//logger.info("action index:"+index);
//				
//			}
//		}
//
//		@Override
//		public boolean done() {
//			Long agentId = agentRepo.findAll().stream().filter(agent -> agent.getName().equals(getLocalName()))
//					.findAny().get().getId();
//			Optional<com.IA.decision.multiAgents.BO.Agent> agent = agentRepo.findById(agentId);
//			List<Action> actions = actionRepo.findByAgentSrc(agent.get());
//			return index == actions.size();
//		}
////
//}
	private class AgentTemplateReceiveBehaviour extends Behaviour {
		/**
		 * 
		 */
		public double addNewOCCValue(double oldOCCValue, double occUpdate)
		{
			double newOCCValue;
			if(occUpdate + oldOCCValue > 1)
			{
				newOCCValue = 1;
			}
			else
			{
				newOCCValue = oldOCCValue + occUpdate;
			}
			return newOCCValue;
		
		}
		
		public double subNewOCCValue(double oldOCCValue, double occUpdate)
		{
			double newOCCValue;
			newOCCValue = Math.abs(oldOCCValue - occUpdate);
			if(newOCCValue < oldOCCValue)
			{
				return newOCCValue;
			}
			return oldOCCValue;
		}
		public OCCsTowardsAgent getOCCTowardsAgent(List<OCCsTowardsAgent> listOCCTowardsAgent, com.IA.decision.multiAgents.BO.Agent agentDest)
		{
			OCCsTowardsAgent occTowardsAgentDest;
			Optional<OCCsTowardsAgent>	occTowardsAgentDestopt = listOCCTowardsAgent.stream()
					.filter(happyfor->happyfor.getAgentDest().getName().equals(agentDest.getName())).findAny();
			if(!occTowardsAgentDestopt.isPresent())
			{
				occTowardsAgentDest = new OCCsTowardsAgent();
				occTowardsAgentDest.setAgentDest(agentDest);
			}
			else
			{
				occTowardsAgentDest = occTowardsAgentDestopt.get();
			}
			return occTowardsAgentDest;

		}
		private void updateOCCVector(String emotionType, Optional<OCC> optocc, com.IA.decision.multiAgents.BO.Agent agentSrc,com.IA.decision.multiAgents.BO.Agent agentDest, LikingTowardsAgent likingTowardsAgent,Double occUpdate, Boolean eventDegree)
		{
			OCC occ;
			
			double newNegOCC = 0, newPosOCC = 0;
			logger.info("Updating OCC agent: "+ agentSrc.getName()+" occUpdate: "+occUpdate+" event degree : "+eventDegree + ",emotional couple:"+emotionType);
			
			if (!optocc.isPresent()) {
				occ = new OCC();
				if (eventDegree) {
					newPosOCC = occUpdate;
				}
				else
				{
					newNegOCC = occUpdate;
				}
				
			}
			else {
				
				occ = optocc.get();
				
				if(emotionType.equals("joy/distress"))
				{
					if(eventDegree)
					{
						newPosOCC = addNewOCCValue(occ.getJoy(), occUpdate);
						newNegOCC = subNewOCCValue(occ.getDistress(), occUpdate);
					}
					else
					{
						newPosOCC = subNewOCCValue(occ.getJoy(), occUpdate);
						newNegOCC = addNewOCCValue(occ.getDistress(), occUpdate);
					}
					
					
				}
				else if(emotionType.equals("hope/fear"))
				{
					if(eventDegree)
					{
						newPosOCC = addNewOCCValue(occ.getHope(), occUpdate);
						newNegOCC = subNewOCCValue(occ.getFear(), occUpdate);
					}
					else
					{
						newPosOCC = subNewOCCValue(occ.getHope(), occUpdate);
						newNegOCC = addNewOCCValue(occ.getFear(), occUpdate);
					}

				}
				else if(emotionType.equals("satisfaction/fear confirmed"))
				{
					if(eventDegree)
					{
						newPosOCC = occ.getHope();
						newNegOCC = 0;
					}
					else
					{
						newPosOCC = 0;
						newNegOCC = occ.getFear();
					}
					
				}
				else if(emotionType.equals("relief/disappointment"))
				{
					if(eventDegree)
					{
						newPosOCC = 0;
						newNegOCC = occ.getHope();
					}
					else
					{
						newPosOCC = occ.getFear();
						newNegOCC = 0;
					}
					
				}
				else if(emotionType.equals("happyFor/sorryFor"))
				{
					
					if(eventDegree)
					{
						newPosOCC = occUpdate;
						newNegOCC = 0;
						
					}
					else
					{
						newPosOCC = 0;
						newNegOCC = occUpdate;
						
						
					}	
				}
				else if(emotionType.equals("pity/gloating"))
				{
					
					if(!eventDegree)
					{
						double gloatingValue = 1-occUpdate*likingTowardsAgent.getLikingValue();
						double pityValue = occUpdate*likingTowardsAgent.getLikingValue();
						if(likingTowardsAgent.getLikingValue()==0)
						{
							newPosOCC = 0;
							newNegOCC = gloatingValue;
						}
						else
						{
							newPosOCC = pityValue;
							newNegOCC = 0;
						}
					
						
					}
				}
				else if( emotionType.equals("admiration/reproach"))
				{
					if(eventDegree)
					{
						newPosOCC = occUpdate;
						newNegOCC = 0;					
					}
					else
					{
						newPosOCC = 0;
						newNegOCC = occUpdate;
					}	
				}
				else if( emotionType.equals("pride/shame"))
				{
					if(eventDegree)
					{
						newPosOCC = occUpdate;
						newNegOCC = 0;					
					}
					else
					{
						newPosOCC = 0;
						newNegOCC = occUpdate;
					}	
				}
			}
			
			if(emotionType.equals("joy/distress"))
			{
				occ.setJoy(newPosOCC);
				occ.setDistress(newNegOCC);
				occ.setAgent(agentSrc);
				OCCRepo.save(occ);
			}
			else if(emotionType.equals("hope/fear") )
			{
				occ.setHope(newPosOCC);
				occ.setFear(newNegOCC);
				occ.setAgent(agentSrc);
				OCCRepo.save(occ);
			}
			else if(emotionType.equals("satisfaction/fear confirmed") )
			{
				occ.setSatisfaction(newPosOCC);
				occ.setFearConfirmed(newNegOCC);
				occ.setAgent(agentSrc);
				OCCRepo.save(occ);
			}
			else if(emotionType.equals("relief/disappointment") )
			{
				occ.setRelief(newPosOCC);
				occ.setDisappointment(newNegOCC);
				occ.setAgent(agentSrc);
				OCCRepo.save(occ);
			}	
			else if(emotionType.equals("pity/gloating") )
			{
				OCCsTowardsAgent pityTowardsAgent = getOCCTowardsAgent(occ.getPity(),likingTowardsAgent.getAgentDest());
			
				pityTowardsAgent.setOCCValue(newPosOCC);
				occTowardsAgentRepo.save(pityTowardsAgent);
			
				
				OCCsTowardsAgent gloatingTowardsAgent = getOCCTowardsAgent(occ.getGloating(),likingTowardsAgent.getAgentDest());
				gloatingTowardsAgent.setOCCValue(newNegOCC);
				occTowardsAgentRepo.save(gloatingTowardsAgent);
				
			
				
			}	
			else if (emotionType.equals("happyFor/sorryFor") )
			{
				OCCsTowardsAgent happyForTowardsAgent = getOCCTowardsAgent(occ.getHappyFor(),likingTowardsAgent.getAgentDest());
				OCCsTowardsAgent sorryForTowardsAgent = getOCCTowardsAgent(occ.getSorryFor(),likingTowardsAgent.getAgentDest());
				happyForTowardsAgent.setOCCValue(newPosOCC);
				occTowardsAgentRepo.save(happyForTowardsAgent);
				
			
				sorryForTowardsAgent.setOCCValue(newNegOCC);
				occTowardsAgentRepo.save(sorryForTowardsAgent);
				
	
			}
			else if (emotionType.equals("admiration/reproach") )
			{
				OCCsTowardsAgent admirationTowardsAgent = getOCCTowardsAgent(occ.getAdmiration(),agentDest);
				admirationTowardsAgent.setOCCValue(newPosOCC);
				occTowardsAgentRepo.save(admirationTowardsAgent);
				
				
				
		
				OCCsTowardsAgent reproachTowardsAgent = getOCCTowardsAgent(occ.getReproach(),agentDest);
				reproachTowardsAgent.setOCCValue(newPosOCC);
				occTowardsAgentRepo.save(reproachTowardsAgent);
				
			
			}
			else if( emotionType.equals("pride/shame"))
			{
				occ.setProud(newPosOCC);
				occ.setShame(newNegOCC);
				occ.setAgent(agentSrc);
				OCCRepo.save(occ);
			}
		
		}
		
		private static final long serialVersionUID = 1L;
		private int p = 0;
		private  final	OCCsTowardsAgentRepository occTowardsAgentRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(OCCsTowardsAgentRepository.class);
		private final EventNameRepository eventNameRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(EventNameRepository.class);
		private  final EventInfoRepository eventInfoRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(EventInfoRepository.class);
		private  final AgentRepository agentRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(AgentRepository.class);
		private  final OCEANRepository oceanRepo = ApplicationContextProvider.getApplicationContext()
				.getBean(OCEANRepository.class);
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
				Optional<EventName> eventName = eventNameRepo.findById(Long.parseLong((message.split(":"))[1]));
				if(eventName.isPresent())
				{
					
				
				Optional<EventInfo> eventInfoOpt = eventInfoRepo.findByEventNameAndAgent( agentId, eventName.get().getId());
				if(eventInfoOpt.isPresent())
				{
				EventInfo eventInfo = eventInfoOpt.get();
				
				Optional<com.IA.decision.multiAgents.BO.Agent> agent = agentRepo.findById(agentId);
				Optional<OCC> optocc ;
				if (msgType.equals("event")) {
					logger.info(
							getLocalName()+ " received event message id: " +(message.split(":"))[1]+" event name:" + eventName.get().getName() );

					
					GoalInfo goalInfo = goalInfoRepo.findByGoalNameAndAgent(agentId,eventName.get().getGoalName().getId()
							);
					//update occ values for the agent related to himself
						
					if(eventInfo != null)
					{

					
					Double desirability = eventInfo.getEventIntensityLevel() * goalInfo.getWeight();
					
					if(eventName.get().getProspected())
					{	optocc = OCCRepo.findByAgent(agent.get());
						int likelihood  = eventName.get().getLikelihood();
						updateOCCVector("hope/fear", optocc, agent.get() ,null, null, likelihood*0.25*desirability , eventName.get().getEventDegree());	
					
					}
					else
					{
						if(eventName.get().getConfirmed())
						{
						optocc = OCCRepo.findByAgent(agent.get());
						updateOCCVector("joy/distress", optocc, agent.get() ,null,null, desirability , eventName.get().getEventDegree());
						 optocc = OCCRepo.findByAgent(agent.get());	
						updateOCCVector("satisfaction/fear confirmed", optocc, agent.get() ,null,null, desirability , eventName.get().getEventDegree());
						}
						else {
							 optocc = OCCRepo.findByAgent(agent.get());
						updateOCCVector("disappointment/relief", optocc, agent.get() ,null, null, desirability , eventName.get().getEventDegree());
							
								
						}
					}
					//update occ values for the agent related to himself
					//update occ values for other
				
					for(LikingTowardsAgent likingTowardAgent: agent.get().getLikingTowardAgent())
					{
						optocc = OCCRepo.findByAgent(agent.get());
						updateOCCVector("happyFor/sorryFor", optocc, agent.get() , null, likingTowardAgent, desirability*likingTowardAgent.getLikingValue() , eventName.get().getEventDegree());
						optocc = OCCRepo.findByAgent(agent.get());
						updateOCCVector("pity/gloating", optocc, agent.get() ,null, likingTowardAgent, desirability*likingTowardAgent.getLikingValue() , eventName.get().getEventDegree());
					}
					
					
					
				
					//Diffuse the event reaction
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
					Long idEventReaction = Long.parseLong((message.split(":"))[1]);
					Optional<EventReaction> eventReaction = eventReactionRepo.findById(idEventReaction);
					logger.info( getLocalName()+" received event message reaction : " + eventReaction.get().getReactionName() + " from "+ msg2.getSender().getLocalName());
					
					
					if(eventReaction.get().getReactionName().equals("Asking for help"))
					{
						Action action =  new Action();
						action.setAgentSrc(agent.get());
						action.setAgentDest(eventReaction.get().getEventInfo().getAgent());
						OCEAN oceanAgent = oceanRepo.findByAgent(agent.get()); 
						double approvalDegreeLevel;
						ACLMessage msg3 ;
						if(oceanAgent.getAgreeableness()>=0.5)
						{
							approvalDegreeLevel = Math.round(ThreadLocalRandom.current().nextDouble(0.5 , 1)* 100d) / 100d;
							action.setMessage("Accepting help");
							action.setActionDegree(true);
						}
						else
						{
							approvalDegreeLevel = Math.round(ThreadLocalRandom.current().nextDouble(0 ,0.5 )* 100d) / 100d;
							action.setMessage("Rejecting help");
							action.setActionDegree(false);
						}
						action.setApprovalDegreeLevel(approvalDegreeLevel);
						Action savedAction = actionRepo.save(action);
						double blamePraiseWorthy=0;
						blamePraiseWorthy = action.getApprovalDegreeLevel();
						optocc = OCCRepo.findByAgent(agent.get());
						updateOCCVector("pride/shame", optocc,agent.get(), action.getAgentDest() ,null, blamePraiseWorthy , eventName.get().getEventDegree());

						//diffuse
						msg3 = new ACLMessage(ACLMessage.INFORM);
						for(com.IA.decision.multiAgents.BO.Agent agentDiff : agentRepo.findAll())
						{
							if(!agentDiff.getName().equals(agent.get().getName()))
							{
								msg3.setContent("action:"+savedAction.getId());
								msg3.addReceiver(new AID(agentDiff.getName(), AID.ISLOCALNAME));
								logger.info("Sending action to "+agentDiff.getName()+ " with id "+ savedAction.getId());
							}
						}
					}
					p++;
				}
				else if (msgType.equals("action"))
				{
			
					 
					Optional<Action> action = actionRepo.findById(Long.parseLong((message.split(":"))[1]));
					double blamePraiseWorthyForOther=0;
					Boolean actionDegree = action.get().getActionDegree();
					blamePraiseWorthyForOther = eventInfo.getEventIntensityLevel();
					optocc = OCCRepo.findByAgent(agent.get());
					updateOCCVector("admiration/reproach", optocc, agent.get() ,action.get().getAgentDest(),null, blamePraiseWorthyForOther , actionDegree);
				
					logger.info(
							getLocalName()+ " received " + action.get().getMessage() + " from Sender : " + msg2.getSender().getLocalName());
			
					//praise worthy
					//blame worthy
					//it is a variable storing the value returned from the formula updating the occ vector
					//praise worthy = action approval level * degree 
					//proud should be equal to that value
					//updateOCCVector("joy/distress",optocc, agent ,null, action.get().getApprovalDegreeLevel() , action.get().getActionDegree());
					
					//p++;
				}
			}
				else
				{
					logger.info("event info is present for agent ID:"+agentId +"eventID" +eventName.get().getId());
				}
			}
				else
				{
					logger.info("execution is finished successfully");

				}
			}
		
			
		}

		public boolean done() {
			// TODO Auto-generated method stub

			return p == eventNameRepo.findAll().size()+eventReactionRepo.findAll().size()  ;

		}

	}

}

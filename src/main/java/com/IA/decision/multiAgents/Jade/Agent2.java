package com.IA.decision.multiAgents.Jade;



import java.io.File;
	import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
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
	
public class Agent2 extends Agent {
	

	
		// Repr�sente l'objet � envoyer dans le message vers l'agent Portail
		private Object[] obj=null;
		String Rea;
		String msg2;
		String message;
	int index=0;
		
		
		protected void setup()  {
			System.out.println(getLocalName()+" d�marr�");
			//---------------
			try{
			  // Cr�ation de desciprion de l'agent1
			  DFAgentDescription dfd = new DFAgentDescription();
			  dfd.setName(getAID());
			  // Enregistrement de la description de l'agent1 dans DF (Directory Facilitator)
			  DFService.register(this, dfd);
			  System.out.println(getLocalName()+" enregistr� par le <DF>");
			}catch (FIPAException e){
				e.printStackTrace();	
			}
			addBehaviour(new AgentBehaviour2());
	//addBehaviour(new AgentBehaviour());
			//addBehaviour(new AgentBehaviour2());
		}
		
			
		private class AgentBehaviour2 extends Behaviour{
			private int k =0,i=0;
			double ED;
			double GoalImp;
			String ch3;
			double EIL;
			 double Des;
			 double imp;
			 double imptemp;
			 double WGoal;
			 double Emotion;
			 String em ;
			 
			 public void action() {
					ACLMessage msg2 = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
					if (msg2 != null) {	
						System.out.println("Agent2 received from" +msg2.getContent()+ msg2.getSender());

					
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setContent("Need help");
					msg.addReceiver(new AID("Diffuseur", AID.ISLOCALNAME));	
					msg.addReceiver(new AID("Agent1", AID.ISLOCALNAME));	
					//msg.addReceiver(new AID("Agent3", AID.ISLOCALNAME));
					send (msg);
					
					
						//System.out.println("-R�action envoy�e: <Agent2> -> <Diffuseur>"+Rea);
					i++;
			 }
			 }	
				public boolean done() {
					// TODO Auto-generated method stub
					
					return i==4;
							
				}
				
				

}
		
			
			
		
				
					
						
							
		}
								
								
								
							
					
									
									
									
									
				
				
				
				 
					
				
			
			
			
			
		

	






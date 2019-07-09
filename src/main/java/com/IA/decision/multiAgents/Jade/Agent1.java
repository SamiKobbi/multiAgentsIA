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
	
public class Agent1 extends Agent {
	

	
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
			addBehaviour(new AgentBehaviour3());
	//addBehaviour(new AgentBehaviour());
			//addBehaviour(new AgentBehaviour2());
		}
		
			
		private class AgentBehaviour3 extends Behaviour{
			private int k =0,i=1,p=0;
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
			 String Ev= "EVENT";
			 String Ac= "REACTION";
			 
			 double Weight = 0.00;
			 public void action() {
					ACLMessage msg2 = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
					if (msg2 != null) {	
						System.out.println("Agent1 received from" +msg2.getContent()+ msg2.getSender());
					ch3 = msg2.getContent();
				
					try {
						
						 }
	
					
					
						
						catch (Exception e) {
							System.err.println("Erreur lors de l'acc�s au fichier !");
								e.printStackTrace();
							}
					
					
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setContent(Rea);
					msg.addReceiver(new AID("Diffuseur", AID.ISLOCALNAME));	
					msg.addReceiver(new AID("Agent2", AID.ISLOCALNAME));	
					//msg.addReceiver(new AID("Agent3", AID.ISLOCALNAME));
					send (msg);

					
					
					p++;
			 }	
					}
				public boolean done() {
					// TODO Auto-generated method stub
					
					return p==4;
							
				}
				
				
				

}
		
			
			
		
				
					
						
							
		}
								
								
								
							
					
									
									
									
									
				
				
				
				 
					
				
			
			
			
			
		

	






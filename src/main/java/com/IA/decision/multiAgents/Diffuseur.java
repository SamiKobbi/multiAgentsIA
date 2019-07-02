package com.IA.decision.multiAgents;

import java.io.File;
import java.io.FileInputStream;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Diffuseur extends Agent {
	String  ch;
	int index ;
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
			addBehaviour(new DiffBehaviour());
			addBehaviour(new DiffBehaviour2());
		}
		private class DiffBehaviour extends Behaviour{
			private int i =0,o=0;
			
				public void action() {
					
					System.out.println("<Diffuseur Agent: searching for events from the network>");
					FileInputStream fichier;
					try {
						
						 	ACLMessage msg3 = new ACLMessage(ACLMessage.INFORM);
				               msg3.setContent("");
							
								msg3.addReceiver(new AID("Agent1", AID.ISLOCALNAME));
								msg3.addReceiver(new AID("Agent2", AID.ISLOCALNAME));
								send(msg3); 
						
						    o++;
						   }
					catch (Exception e) {
						System.err.println("Erreur lors de l'acc�s au fichier !");
							e.printStackTrace();
						}
									
						    	  
						    	 
						   
						   }
								                
					@Override
					public boolean done() {
						
						return  o==2;
					}
					}
				
				
		private class DiffBehaviour2 extends Behaviour{

			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage msg2 = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
				if (msg2 != null) {	
					System.out.println("Diffuseur received" +msg2.getContent()+ msg2.getSender());
				
			
					index++;
				}
				}

			@Override
			public boolean done() {
				// TODO Auto-generated method stub
				
				return index==2;
			}
			
			
			
		}
									
					    	   
					    
		
				
				protected void takeDown() {
					
					// Suppression de l'agent [Acheteur] depuis le DF
					try {
						DFService.deregister(this);
						System.out.println(getLocalName()+" DEREGISTERED WITH THE DF");
					} catch (FIPAException e) {
						e.printStackTrace();
					}
				}	
					
		
    	

}
				

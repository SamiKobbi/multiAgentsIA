package com.IA.decision.multiAgents.Jade;



import java.io.File;
	import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
	
public class Agent3 extends Agent {
	

	
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
						System.out.println("Agent3 received from" +msg2.getContent()+ msg2.getSender());
					ch3 = msg2.getContent();
					InputStream fichier;
					try {
					
//					       for (int x=1; x<10;x++)
//					       {
//						      Row row = sheet.getRow(x) ;
//						    
//					
//						    	  
//						    	 if (ch3.contains(row.getCell(0).getStringCellValue()))
//						    	 {
//						    		System.out.println("Agent 1 : found"); 
//						    		
//                                    ED = row.getCell(1).getNumericCellValue();
//						    		
//						    		if (ED>=0)
//						    		{
//						    			
//						    			EIL = 1.00;
//						    		}
//						    		else {EIL=-1.00;}
//						    		
//						    	
//						    	    GoalImp=row.getCell(3).getNumericCellValue();
//						    	    
//						    		WGoal = row.getCell(4).getNumericCellValue();
//						    		
//						    		imp=EIL * ED * WGoal;
//			
//						    		
//						    		Des = imp*GoalImp;
//						    		System.out.println("Agent 1 Desirablity:"+Des);
//						    		Emotion = Des;
//						    	if (Des>0.00) {
//						    		em= "Joy";
//						    			System.out.println("Agent 1 Update internal emotion state:"+em+":" +Emotion);
//						    			Weight =Des;
//						    		}
//						    	else {
//						    		em="Distress";
//						    		System.out.println("Agent 1 Update internal emotion state:"+em+":" +Emotion);
//						    		Weight =Des;
//						    		Cell cell = row.createCell(6);
//						    		cell.setCellValue(Des);
//						    		// wb.write(fichier);
//						    	}
//						    	System.out.println("Reaction sent:" +row.getCell(5).getStringCellValue());
//						    	Rea= row.getCell(5).getStringCellValue();
//						    	}
//						    	// event reaction weight update//
//						    	 
//						    	 else {
//						    		 System.out.println("still searching");
//				
//						   
//						    		
//						    		 System.out.print(x);
//						    		 //row = sheet.getRow(i) ;
//						    		 
//						    		}
//					       }
//					
//						  
						    	
						  }
					
					
						
						catch (Exception e) {
							System.err.println("Erreur lors de l'acc�s au fichier !");
								e.printStackTrace();
							}
				
					
					
					
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setContent(Rea);
					msg.addReceiver(new AID("Diffuseur", AID.ISLOCALNAME));	
					msg.addReceiver(new AID("Agent1", AID.ISLOCALNAME));	
					msg.addReceiver(new AID("Agent2", AID.ISLOCALNAME));
					send (msg);
					
					
						//System.out.println("-R�action envoy�e: <Agent3> -> <Diffuseur>"+Rea);
					i++;
			 }
			 }	
				public boolean done() {
					// TODO Auto-generated method stub
					
					return i==4;
							
				}
				
				

}
		
			
			
		
				
					
						
							
		}
								
								
								
							
					
									
									
									
									
				
				
				
				 
					
				
			
			
			
			
		

	






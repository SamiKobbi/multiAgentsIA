package learning;



import java.io.File;
	import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	

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
	

	
		// Représente l'objet à envoyer dans le message vers l'agent Portail
		private Object[] obj=null;
		String Rea;
		String msg2;
		String message;
	int index=0;
		
		
		protected void setup()  {
			System.out.println(getLocalName()+" démarré");
			//---------------
			try{
			  // Création de desciprion de l'agent1
			  DFAgentDescription dfd = new DFAgentDescription();
			  dfd.setName(getAID());
			  // Enregistrement de la description de l'agent1 dans DF (Directory Facilitator)
			  DFService.register(this, dfd);
			  System.out.println(getLocalName()+" enregistré par le <DF>");
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
				
					FileInputStream fichier;
					try {
						fichier = new FileInputStream(new File("C:\\Users\\USER\\Documents\\AGENT.xlsx"));
						 XSSFWorkbook wb = new XSSFWorkbook(fichier);
					       XSSFSheet sheet = wb.getSheetAt(0);
					       FormulaEvaluator formulaEvaluator = 
				                     wb.getCreationHelper().createFormulaEvaluator();
					  
					       
					       for (int x=1; x<10;x++)
					       {
						      Row row = sheet.getRow(x) ;
						    
					
						    	  
						    	 if (ch3.contains(row.getCell(0).getStringCellValue()))
						    	 {
						    		System.out.println("Agent 1 : found"); 
						    		
                                       ED = row.getCell(1).getNumericCellValue();
						    		
						    		if (ED>=0)
						    		{
						    			
						    			EIL = 1.00;
						    		}
						    		else {EIL=-1.00;}
						    		
						    	
						    	    GoalImp=row.getCell(3).getNumericCellValue();
						    	    
						    		WGoal = row.getCell(4).getNumericCellValue();
						    		
						    		imp=EIL * ED * WGoal;
			
						    		
						    		Des = imp*GoalImp;
						    		System.out.println("Agent 1 Desirablity:"+Des);
						    		Emotion = Des;
						    	if (Des>0.00) {
						    		em= "Joy";
						    			System.out.println("Agent 1 Update internal emotion state:"+em+":" +Emotion);
						    			Weight =Des;
						    		}
						    	else {
						    		em="Distress";
						    		System.out.println("Agent 1 Update internal emotion state:"+em+":" +Emotion);
						    		Weight =Des;
						    		Cell cell = row.createCell(6);
						    		cell.setCellValue(Des);
						    		// wb.write(fichier);
						    	}
						    	System.out.println("Reaction sent:" +row.getCell(5).getStringCellValue());
						    	Rea= row.getCell(5).getStringCellValue();
						    	}
						    	// event reaction weight update//
						    	 
						    	 else {
						    		 System.out.println("still searching");
				
						   
						    		
						    		 System.out.print(x);
						    		 //row = sheet.getRow(i) ;
						    		 
						    		}
					       }
						 }
	
					
					
						
						catch (Exception e) {
							System.err.println("Erreur lors de l'accès au fichier !");
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
								
								
								
							
					
									
									
									
									
				
				
				
				 
					
				
			
			
			
			
		

	






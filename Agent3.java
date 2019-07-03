package learning;



import java.io.File;
	import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
	
public class Agent3 extends Agent {
	

	
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
						fichier = new FileInputStream(new File("C:\\Users\\USER\\Documents\\AGENT.xlsx"));
						 XSSFWorkbook wb = new XSSFWorkbook(fichier);
					       XSSFSheet sheet = wb.getSheetAt(0);
					       FormulaEvaluator formulaEvaluator = 
				                     wb.getCreationHelper().createFormulaEvaluator();
					  
						      Row row = sheet.getRow(1) ;
						     // Row row1 = sheet.getRow(2);
						//  while (row != null) {
						    	  
						    	 if (ch3.equalsIgnoreCase(row.getCell(0).getStringCellValue()))
						    	 {
						    		System.out.println("found"); 
						    		ED = row.getCell(1).getNumericCellValue();
						    		System.out.println("ED"+ED);
						    		//GoalImp=row.getCell(3).getNumericCellValue();
						    		//System.out.println(GoalImp);
						    		//System.out.println("Computing desirability");
						    		if (ED>=0)
						    		{
						    			Random r = new Random();
						    			//EIL = 0.00 + (1.00 - 0.00) * r.nextDouble();
						    		//	EIL= (0.00 + Math.random() * (1.00 - 0.00 + 1.00));
						    			
						    			EIL = 1.00;
						    		}
						    		else {EIL=-1.00;}
						    		
						    		//System.out.println("GoalImp"+row.getCell(3));
						    	    GoalImp=row.getCell(3).getNumericCellValue();
						    	    
						    		WGoal = row.getCell(4).getNumericCellValue();
						    		System.out.println("WGoal"+WGoal);
						    		//imptemp=row.getCell(3).
						    		imp=EIL * ED * WGoal;
			
						    		//imp =  imptemp/ 4.00;
						    		//System.out.println("Imptemporel:"+imptemp);
						    		System.out.println("EventImpact:"+imp);
						    		System.out.println("GoalImpact"+GoalImp);
						    		//System.out.println("GoalWeight"+);
						    		Des = imp*GoalImp;
						    		System.out.println("Desirablity:"+Des);
						    		Emotion = Des;
						    	if (Des>0.00) {
						    		em= "Joy";
						    			System.out.println("Update internal emotion state:"+em+":" +Emotion);
						    		}
						    	else {
						    		em="Distress";
						    		System.out.println("Update internal emotion state:"+em+":" +Emotion);
						    		
						    	}
						    	
						    			
						    		}
						    		//System.out.println("nouveau vecteur "+ EIL);
						    		
						    	 	 
						    	
					
						  
						    	
						  }
					
					
						
						catch (Exception e) {
							System.err.println("Erreur lors de l'accès au fichier !");
								e.printStackTrace();
							}
					
					FileInputStream fichier2;
					try {
						fichier2 = new FileInputStream(new File("C:\\Users\\USER\\Documents\\AGENT3.xlsx"));
						 XSSFWorkbook wb2 = new XSSFWorkbook(fichier2);
					       XSSFSheet sheet2 = wb2.getSheetAt(0);
					       FormulaEvaluator formulaEvaluator = 
				                     wb2.getCreationHelper().createFormulaEvaluator();
					  
						      Row row2 = sheet2.getRow(1) ;
						     // Row row1 = sheet.getRow(2);
						//  while (row != null) {
						    	  
						    	 if (ch3.equalsIgnoreCase(row2.getCell(0).getStringCellValue()))
						    	 {
						    		System.out.println("found"); 
						    		System.out.println("Reaction loading");
						    		Rea = row2.getCell(1).getStringCellValue();
						    		System.out.println(Rea);
						    		
					
					}}
						    	 catch (Exception e) {
										System.err.println("Erreur lors de l'accès au fichier !");
											e.printStackTrace();
										}
					
					
					
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setContent(Rea);
					msg.addReceiver(new AID("Diffuseur", AID.ISLOCALNAME));	
					msg.addReceiver(new AID("Agent1", AID.ISLOCALNAME));	
					msg.addReceiver(new AID("Agent2", AID.ISLOCALNAME));
					send (msg);
					
					
						//System.out.println("-Réaction envoyée: <Agent3> -> <Diffuseur>"+Rea);
					i++;
			 }
			 }	
				public boolean done() {
					// TODO Auto-generated method stub
					
					return i==4;
							
				}
				
				

}
		
			
			
		
				
					
						
							
		}
								
								
								
							
					
									
									
									
									
				
				
				
				 
					
				
			
			
			
			
		

	






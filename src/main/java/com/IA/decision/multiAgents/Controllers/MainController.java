package com.IA.decision.multiAgents.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.Event;
import com.IA.decision.multiAgents.BO.Goal;
import com.IA.decision.multiAgents.BO.OCEAN;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.repositories.EventRepository;
import com.IA.decision.multiAgents.repositories.GoalRepository;
import com.IA.decision.multiAgents.repositories.OCEANRepository;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;


@Service
public class MainController {
	 	@FXML
	    public ComboBox<Agent> agentsComboBox;
	 	@FXML
	 	public Button goButton;
	 	@FXML
	    public Button saveAgent;
	 	@FXML
	 	public TextField agentName;
	 	@FXML
	 	public TextField openness;
	 	@FXML
	 	public TextField conscientiousness;
	 	@FXML
	 	public TextField extraversion;
	 	@FXML
	 	public TextField agreeableness;
	 	@FXML
	 	public TextField neuroticism;
	 	@FXML
	 	public TextField goalName;
		@FXML
	 	public TextField goalWeight;
	 	@FXML
	    public ComboBox<Goal> goalComboBox;
	 	@FXML
	    public Button saveGoal;
	 	
	 	@Autowired
	 	private EventRepository eventRepo;
	 	
	    @Autowired
	    private AgentRepository agentRepo;
	    @Autowired
	    private OCEANRepository OCEANRepo;
	    @Autowired
	    private GoalRepository goalRepo;
	    @FXML
	    public void initialize() {
	    	agentsComboBox.setConverter(new AgentNameStringConverter());
	    	goalComboBox.setConverter(new GoalNameStringConverter());
	    	
	    	agentsComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
	    	agentsComboBox.getSelectionModel().selectFirst();
	    	goalComboBox.getSelectionModel().selectFirst();
	    	
	    	goButton.setOnAction(
	    			event -> {
	    	    		
	    				Agent agent = new Agent("sami");
	    				
	    				Goal goal = new Goal("succeed the exam", 0.5);
	    				goal.setAgent(agent);
	    				
	    				Event ev = new Event("Exams will be difficult",false,false,0.3);

	    				ev.setGoal(goal);
	    				
	    				agentRepo.save(agent);
	    				goalRepo.save(goal);
	    				eventRepo.save(ev);
//	    				try {
//							
//							  Runtime rt = Runtime.instance();
//							  ProfileImpl p = new ProfileImpl(false);
//							  AgentContainer container =rt.createAgentContainer(p);
//							  AgentController Agent=null;			
//							  Agent = container.createNewAgent("Diffuseur", "learning.Diffuseur", null);
//							  Agent.start();	
//					
//							  Agent = container.createNewAgent("Agent1", "learning.Agent1", null);
//							  Agent.start();	
//
//							  Agent = container.createNewAgent("Agent2", "learning.Agent2", null);
//							  Agent.start();
//							 
//						
//							  
//							  } catch (Exception any) {
//							    any.printStackTrace();    
//							  }
							  
	    	    	}
	    			);
	    	
	    	saveGoal.setOnAction(event -> {
	    		
	    		Goal goal = new Goal(goalName.getText(),Double.parseDouble(goalWeight.getText()));
	    		goalComboBox.getItems().add(goal);
		    		
	    	});
	    	
	    	saveAgent.setOnAction(event -> {
				 if(validate())
				 {
					 
				
			     Agent agent = agentRepo.save(new Agent(agentName.getText()));
			     agentsComboBox.getItems().add(agent);
			     goalComboBox.getItems().stream().forEach(goalCombo->{goalCombo.setAgent(agent);});
			     OCEAN ocean = new OCEAN(
			    		Double.parseDouble(openness.getText()) , 
			    		Double.parseDouble(conscientiousness.getText()) , 
			    		Double.parseDouble(extraversion.getText()) ,
			    		Double.parseDouble(agreeableness.getText()) ,
			    		Double.parseDouble(neuroticism.getText())); 
			     ocean.setAgent(agent);
			     OCEANRepo.save(ocean);
			    List<Goal> goals = goalComboBox.getItems();
			     goalRepo.saveAll(goals);
			     clearSelection();
				 }
				 else
				 {
					   Alert alert = new Alert(Alert.AlertType.INFORMATION);
					    alert.setTitle("Validation Failed");
					    alert.setHeaderText("Missing agent information");
					    alert.setContentText("Please enter valid information!");
					    alert.showAndWait();	
				 }
	    	
	    	});
		     agentsComboBox.valueProperty().addListener((ChangeListener<Agent>) (ov, oldValue, newAgent) -> {
					
						 if(newAgent != null)
						 {
							 OCEAN oceanByAgent = OCEANRepo.findByAgent(newAgent);
							 openness.setText(oceanByAgent.getOpenness().toString());
							 conscientiousness.setText(oceanByAgent.getConscientiousness().toString());
							 extraversion.setText(oceanByAgent.getExtraversion().toString());
							 agreeableness.setText(oceanByAgent.getAgreeableness().toString());
							 neuroticism.setText(oceanByAgent.getNeuroticism().toString());
							 goalComboBox.setItems(FXCollections.observableArrayList(goalRepo.findByAgent(newAgent)));
							 
						 }
					 });
	    	
	    }
	    private Boolean validate()
	    {
	    	return goalComboBox.getItems().size()>0 && !agentName.getText().trim().equals("") 
	    			&& !openness.getText().trim().equals("") && !conscientiousness.getText().trim().equals("") 
	    			&& !extraversion.getText().trim().equals("") && !agreeableness.getText().trim().equals("")
	    			&& !neuroticism.getText().trim().equals("") ;
	    }
	    private void clearSelection()
	    {
	        goalComboBox.getItems().clear();
	        agentName.clear();
		 	openness.clear();
		 	conscientiousness.clear();
		 	extraversion.clear();
		 	agreeableness.clear();
		 	neuroticism.clear();
		 	goalName.clear();
		 	goalWeight.clear();
	        
	    }
	    private static class AgentNameStringConverter extends StringConverter<Agent> {
	        @Override
	        public String toString(Agent object) {
	            return object.getName();
	        }
	 
	        @Override
	        public Agent fromString(String string) {
	            return null;
	        }
	    }
	    private static class GoalNameStringConverter extends StringConverter<Goal> {
	        @Override
	        public String toString(Goal object) {
	            return object.getName();
	        }
	 
	        @Override
	        public Goal fromString(String string) {
	            return null;
	        }
	    }
}

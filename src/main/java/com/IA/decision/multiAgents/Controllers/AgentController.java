package com.IA.decision.multiAgents.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.BO.Goal;
import com.IA.decision.multiAgents.BO.OCEAN;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.repositories.OCEANRepository;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;


@Service
public class AgentController {
	 	@FXML
	    public ComboBox<Agent> agentsComboBox;
	 
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
	    private AgentRepository agentRepo;
	    @Autowired
	    private OCEANRepository OCEANRepo;
	    @FXML
	    public void initialize() {
	    	agentsComboBox.setConverter(new AgentNameStringConverter());
	    	agentsComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
	    	agentsComboBox.getSelectionModel().selectFirst();
	    	
	    	
	    	saveGoal.setOnAction(event -> {
	    		
	    		Goal goal = new Goal(goalName.getText(),Double.parseDouble(goalWeight.getText()));
	    		
	    		
	    	});
	    	
	    	saveAgent.setOnAction(event -> {
				 
			     Agent agent = agentRepo.save(new Agent(agentName.getText()));
			     OCEAN ocean = new OCEAN(
			    		Double.parseDouble(openness.getText()) , 
			    		Double.parseDouble(conscientiousness.getText()) , 
			    		Double.parseDouble(extraversion.getText()) ,
			    		Double.parseDouble(agreeableness.getText()) ,
			    		Double.parseDouble(neuroticism.getText())); 
			     ocean.setAgent(agent);
			     OCEANRepo.save(ocean);
			     agentsComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
			     agentsComboBox.getSelectionModel().selectFirst();
			     agentsComboBox.valueProperty().addListener((ChangeListener<Agent>) (ov, oldValue, newAgent) -> {
					
					 if(newAgent != null)
					 {
						 OCEAN oceanByAgent = OCEANRepo.findByAgent(newAgent);
						 openness.setText(oceanByAgent.getOpenness().toString());
						 conscientiousness.setText(oceanByAgent.getConscientiousness().toString());
						 extraversion.setText(oceanByAgent.getExtraversion().toString());
						 agreeableness.setText(oceanByAgent.getAgreeableness().toString());
						 neuroticism.setText(oceanByAgent.getNeuroticism().toString());
						 
					 }
				 });
			 
	    	
	    	});
	    	
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
}

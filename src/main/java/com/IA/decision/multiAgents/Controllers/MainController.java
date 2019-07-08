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
import com.IA.decision.multiAgents.repositories.ActionRepository;
import com.IA.decision.multiAgents.repositories.AgentRepository;
import com.IA.decision.multiAgents.repositories.EventRepository;
import com.IA.decision.multiAgents.repositories.GoalRepository;
import com.IA.decision.multiAgents.repositories.OCCRepository;
import com.IA.decision.multiAgents.repositories.OCEANRepository;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;


@Service
public class MainController {
	//agent
	 	@FXML
	 	public Button goButton;
	 	@FXML
	    public Button saveAgent;	
	 	@FXML
	    public ComboBox<Agent> agentsComboBox;
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
	//agent
		
	 	
	//goal 	
	 	@FXML
	    public Button saveGoal;
	 	@FXML
	    public ComboBox<Goal> goalComboBox;
	 	@FXML
	 	public TextField goalName;
	 	@FXML
	 	public TextField goalWeight;	
	 //goal	 	
	 	
	 
	 //event 	
	 	@FXML
	 	public TextField eventName;
		@FXML
	 	public TextField eventIntensity;
	 	@FXML
	    public CheckBox confirmCheckBox;
	 	@FXML
	    public CheckBox degreeCheckBox;
	 	@FXML
	 	public ComboBox<Event> eventComboBox;
	 	@FXML
	 	public TextField eventReaction;
	 	@FXML
	    public Button addEvent;
	 //event 

	 	
	 //Action	
	 	@FXML
	 	public ComboBox<Agent> agentSrc;
	 	@FXML
	 	public ComboBox<Agent> agentDest;
	 	@FXML
	 	public TextField actionMessage;
	 	@FXML
	 	public TextField actionApprDegLvl;	
	 	@FXML
	    public CheckBox reqCheckbox;
	 	@FXML
	    public CheckBox resCheckbox;
	 	@FXML
	    public CheckBox actionDeg;
	 	@FXML
	 	public ComboBox<Agent> actionAgentComboBox;
	 	@FXML
	    public Button addAction;
	 	//Action		
	 	//reports
	 	@FXML 
	 	LineChart emotionsChart;
		//reports
	 	@Autowired
	 	private EventRepository eventRepo;
	 	@Autowired
	 	private OCCRepository OCCRepo;
	 	@Autowired
	 	private ActionRepository actionRepo;
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
	    	eventComboBox.setConverter(new EventNameStringConverter());
	    	actionAgentComboBox.setConverter(new AgentNameStringConverter());
	    	agentsComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
	    	agentsComboBox.getSelectionModel().selectFirst();
	    	goalComboBox.getSelectionModel().selectFirst();
	    	
	    	goButton.setOnAction(
	    			event -> {
	    	    		
//	    				Agent agent = new Agent("sami");
//	    				
//	    				Goal goal = new Goal("succeed the exam", 0.5);
//	    				goal.setAgent(agent);
//	    				
//	    				Event ev = new Event("Exams will be difficult",false,false,0.3);
//
//	    				ev.setGoal(goal);
//	    				
//	    				agentRepo.save(agent);
//	    				goalRepo.save(goal);
//	    				eventRepo.save(ev);	  
	    	    	}
	    			);
	    	goButton.setOnAction(event -> {
	    		System.out.println("Go Clicked");	
	    	});
	    	saveGoal.setOnAction(event -> {
	    		
	    		Goal goal = new Goal(goalName.getText(),Double.parseDouble(goalWeight.getText()));
	    		Agent agent = agentsComboBox.getSelectionModel().getSelectedItem();
	    		goal.setAgent(agent);
	    		goalComboBox.getItems().add(goal);
			    goalRepo.saveAll(goalComboBox.getItems());

		    		
	    	});
	    	//public Event(String name, Boolean confirmed, Boolean eventDegree, Double eventIntensityLevel) 
	    	addEvent.setOnAction(
	    			event -> {
	    				Event ev = new Event(eventName.getText() , confirmCheckBox.isSelected() , degreeCheckBox.isSelected() , Double.parseDouble(eventIntensity.getText()) );
	    				ev.setGoal(goalComboBox.getSelectionModel().getSelectedItem());
	    				eventComboBox.getItems().add(ev);
	    			}
	    			
	    			);
	 
	    	
	    	saveAgent.setOnAction(event -> {
				 if(validateAgent())
				 {
					 
				
			     Agent agent = agentRepo.save(new Agent(agentName.getText()));
			     agentsComboBox.getItems().add(agent);
			     //goalComboBox.getItems().stream().forEach(goalCombo->{goalCombo.setAgent(agent);});
			     OCEAN ocean = new OCEAN(
			    		Double.parseDouble(openness.getText()) , 
			    		Double.parseDouble(conscientiousness.getText()) , 
			    		Double.parseDouble(extraversion.getText()) ,
			    		Double.parseDouble(agreeableness.getText()) ,
			    		Double.parseDouble(neuroticism.getText())); 
			     ocean.setAgent(agent);
			     OCEANRepo.save(ocean);
			     clearSelectionAgent();
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
	    private Boolean validateAgent()
	    {
	    	return 	!agentName.getText().trim().equals("") 
	    			&& !openness.getText().trim().equals("") && !conscientiousness.getText().trim().equals("") 
	    			&& !extraversion.getText().trim().equals("") && !agreeableness.getText().trim().equals("")
	    			&& !neuroticism.getText().trim().equals("") ;
	    }
	    private void clearSelectionAgent()
	    {
	        agentName.clear();
		 	openness.clear();
		 	conscientiousness.clear();
		 	extraversion.clear();
		 	agreeableness.clear();
		 	neuroticism.clear();
		 	goalName.clear();
		 	goalWeight.clear();   
	    }
	    private void clearSelectionGoal()
	    {
	    	goalComboBox.getItems().clear();
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
	    private static class EventNameStringConverter extends StringConverter<Event> {
	        @Override
	        public String toString(Event object) {
	            return object.getName();
	        }
	 
	        @Override
	        public Event fromString(String string) {
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

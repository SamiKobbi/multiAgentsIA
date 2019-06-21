package com.IA.decision.multiAgents.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IA.decision.multiAgents.BO.Agent;
import com.IA.decision.multiAgents.repositories.AgentRepository;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	    @Autowired
	    private AgentRepository agentRepo;
	 
	    @FXML
	    public void initialize() {
	    	agentsComboBox.setConverter(new AgentNameStringConverter());
	    	agentsComboBox.setItems(FXCollections.observableArrayList(agentRepo.findAll()));
	    	agentsComboBox.getSelectionModel().selectFirst();
	    	saveAgent.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent event) {
	                 agentRepo.save(new Agent(agentName.getText()));
	             }
	         });
	    	agentsComboBox.setOnAction((e) -> {
	             System.out.println("ssssss");
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

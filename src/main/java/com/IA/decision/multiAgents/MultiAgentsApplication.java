package com.IA.decision.multiAgents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

@SpringBootApplication
public class MultiAgentsApplication  extends Application{

	 private ConfigurableApplicationContext context;
	    private Parent rootNode;
	 
	    @Override
	    public void init() throws Exception {
	        SpringApplicationBuilder builder = new SpringApplicationBuilder(MultiAgentsApplication.class);
	        context = builder.run(getParameters().getRaw().toArray(new String[0]));
	 
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
	        loader.setControllerFactory(context::getBean);
	        rootNode = loader.load();
	    }
	 
	 
	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        ObservableList<String> names = FXCollections
	                .observableArrayList();
	            ObservableList<String> data = FXCollections.observableArrayList();

	            ListView<String> listView = new ListView<String>(data);
	            listView.setPrefSize(200, 250);
	            listView.setEditable(true);

	            names.addAll("A", "B", "C", "D", "E");

	            data.add("Double Click to Select Value");

	            listView.setItems(data);
	            listView.setCellFactory(ComboBoxListCell.forListView(names));

	            StackPane root = new StackPane();
	            root.getChildren().add(listView);
	            primaryStage.setScene(new Scene(root, 200, 250));
	            primaryStage.show();
	    }
	 
	    @Override
	    public void stop() throws Exception {
	        context.close();
	    }


		public static void main(String[] args) {
			launch(args);
		}

}

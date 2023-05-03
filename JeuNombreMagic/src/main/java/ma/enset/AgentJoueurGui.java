package ma.enset;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AgentJoueurGui extends Application {
    public static final int SEND_EVENT =1 ;
    private TextField textField;
    private Button sendButton;
    private ListView<String> messageList;
    private AgentJoueur myAgent;

    public void setAgent(AgentJoueur agent) {
        myAgent = agent;
    }

    public void displayMessage(String message) {
        Platform.runLater(() -> messageList.getItems().add(message));
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        mainPane.setPrefSize(400, 300);

        Label label = new Label("Entrez un nombre entre 0 et 100 :");
        textField = new TextField();
        sendButton = new Button("Envoyer");
        sendButton.setOnAction(e -> {
            String message = textField.getText().trim();
            if (!message.isEmpty()) {
                myAgent.sendMessage(message);
                textField.clear();
            }
        });

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(label, textField, sendButton);

        messageList = new ListView<>();
        messageList.setPrefHeight(200);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(vbox);

        mainPane.setCenter(messageList);
        mainPane.setBottom(hbox);

        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Joueur");
        primaryStage.show();
    }

    public void addMessage(String content) {
        Platform.runLater(() -> messageList.getItems().add(content));
    }
}


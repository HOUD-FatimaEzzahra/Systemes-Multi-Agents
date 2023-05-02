package ma.enset.sma;

import jade.core.AID;
import jade.core.behaviours.ReceiverBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AgentClient1 extends GuiAgent {
    private static final String FXML_PATH = "/AgentClient1GUI.fxml";
    private static final String WINDOW_TITLE = "Agent Client 1";
    private static final AID SERVER_AGENT_AID = new AID("server-agent", AID.ISLOCALNAME);

    private List<String> messageList = new ArrayList<>();

    @FXML
    private TextField guessTextField;

    @FXML
    private Button guessButton;

    @FXML
    private ListView<String> messageListView;

    @Override
    protected void setup() {
        super.setup();

        // Add receive behaviour to listen to messages from the server
        addBehaviour(new ReceiverBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equals("WIN")) {
                        // Display win message and stop the agent
                        addMessage("Congratulations, you won!");
                        doDelete();
                    } else if (content.equals("OVER_MAX_GUESSES")) {
                        // Display over max guesses message and stop the agent
                        addMessage("Game over, you reached the maximum number of guesses");
                        doDelete();
                    } else {
                        // Display hint message
                        addMessage(content);
                    }
                } else {
                    block();
                }
            }
        });
    }

    @Override
    protected void onGuiSetup() {
        super.onGuiSetup();

        // Load FXML GUI from file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
        loader.setController(this);
        try {
            VBox root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(WINDOW_TITLE);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onGuessButtonClicked(ActionEvent event) {
        String guessStr = guessTextField.getText();
        if (guessStr.isEmpty()) {
            return;
        }

        // Send guess to server
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(SERVER_AGENT_AID);
        msg.setContent(guessStr);
        send(msg);

        // Clear guess text field
        guessTextField.clear();
    }

    private void addMessage(String message) {
        messageList.add(message);
        Platform.runLater(() -> {
            messageListView.getItems().setAll(messageList);
        });
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        if (guiEvent.getType() == 1) {
            // Send guess to server
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(SERVER_AGENT_AID);
            msg.setContent((String) guiEvent.getParameter(0));
            send(msg);
        }
    }
}

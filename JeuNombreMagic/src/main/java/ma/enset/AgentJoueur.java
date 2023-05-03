package ma.enset;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import ma.enset.AgentJoueurGui;

public class AgentJoueur extends Agent {
    private AgentJoueurGui myGui;

    public void setGui(AgentJoueurGui gui) {
        myGui = gui;
    }

    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    if (msg.getPerformative() == ACLMessage.INFORM) {
                        String content = msg.getContent();
                        Platform.runLater(() -> myGui.addMessage(content));
                    }
                } else {
                    block();
                }
            }
        });
    }

    @Override
    protected void takeDown() {
        try {
            myGui.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(getAID("server"));
        msg.setContent(message);
        send(msg);
    }
}

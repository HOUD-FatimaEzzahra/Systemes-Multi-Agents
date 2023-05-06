package ma.enset;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class AgentClient extends GuiAgent {
    private AgentClientGui agentClientGui;
    private int guess;
    private int min = 0;
    private int max = 100;

    @Override
    protected void setup() {
        System.out.println("*** la méthode setup *****");
        agentClientGui = (AgentClientGui) getArguments()[0];
        agentClientGui.setAgentClient(this);

        // Ajouter un comportement pour envoyer des devinettes toutes les 5 secondes
        addBehaviour(new TickerBehaviour(this, 5000) {
            @Override
            protected void onTick() {
                guess = new Random().nextInt(max - min) + min;
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("server", AID.ISLOCALNAME));
                msg.setContent(String.valueOf(guess));
                send(msg);
                System.out.println("J'ai envoyé la devinette : " + guess);
                agentClientGui.showMessage("==> J'ai envoyé la devinette : " + guess);
            }
        });

        // Ajouter un comportement pour recevoir les réponses du serveur
        addBehaviour(new Behaviour() {
            private boolean done = false;

            @Override
            public void action() {
                ACLMessage receivedMSG = receive();
                if (receivedMSG != null) {
                    agentClientGui.showMessage("<<==" + receivedMSG.getContent());
                    System.out.println(receivedMSG.getContent());
                    done = true;
                } else {
                    block();
                }
            }

            @Override
            public boolean done() {
                return done;
            }
        });
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        if (guiEvent.getType() == 1) {
            // L'utilisateur a changé les bornes
            min = (int) guiEvent.getParameter(0);
            max = (int) guiEvent.getParameter(1);
        }
    }
}

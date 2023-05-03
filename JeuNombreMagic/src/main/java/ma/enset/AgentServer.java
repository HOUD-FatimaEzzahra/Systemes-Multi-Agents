package ma.enset;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.Random;

public class AgentServer extends Agent {
    private int magicNumber;
    private boolean gameStarted;

    @Override
    protected void setup() {
        gameStarted = false;
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                if (!gameStarted) {
                    block();
                } else {
                    ACLMessage msg = receive();
                    if (msg != null) {
                        if (msg.getPerformative() == ACLMessage.REQUEST) {
                            int guess = Integer.parseInt(msg.getContent());
                            String result;
                            if (guess == magicNumber) {
                                result = "Bravo! Le nombre magique était " + magicNumber;
                                ACLMessage endGameMsg = new ACLMessage(ACLMessage.INFORM);
                                endGameMsg.setContent("Le nombre magique était " + magicNumber);
                                endGameMsg.addReceiver(msg.getSender());
                                send(endGameMsg);
                                doDelete();
                            } else if (guess < magicNumber) {
                                result = "Le nombre magique est plus grand que " + guess;
                            } else {
                                result = "Le nombre magique est plus petit que " + guess;
                            }
                            ACLMessage reply = msg.createReply();
                            reply.setPerformative(ACLMessage.INFORM);
                            reply.setContent(result);
                            send(reply);
                        } else {
                            block();
                        }
                    }
                }
            }
        });
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                Random rand = new Random();
                magicNumber = rand.nextInt(101);
                System.out.println("Le nombre magique est " + magicNumber);
                gameStarted = true;
                ACLMessage startGameMsg = new ACLMessage(ACLMessage.INFORM);
                startGameMsg.setContent("Le jeu commence! Le nombre magique est entre 0 et 100.");
                send(startGameMsg);
            }
        });
    }

    public void postGuiEvent(GuiEvent guiEvent) {
        String parameter = (String) guiEvent.getParameter(0);
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(getAID());
        message.setContent(parameter);
        send(message);
    }
}

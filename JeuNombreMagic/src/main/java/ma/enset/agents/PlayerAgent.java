package ma.enset.agents;

import jdk.internal.agent.Agent;

import java.util.Random;

public class PlayerAgent extends Agent {
    private int guess;
    private boolean gameOver;

    protected void setup() {
        System.out.println(getAID().getName() + " is ready.");

        // Comportement pour gérer les messages entrants
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage msg = receive(mt);
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equals("game-over")) {
                        System.out.println(getAID().getName() + ": Game over, the magic number was " + guess);
                        gameOver = true;
                    } else {
                        int response = Integer.parseInt(content);
                        System.out.println(getAID().getName() + ": The magic number is " + response);
                        if (response == guess) {
                            System.out.println(getAID().getName() + ": I win!");
                            ACLMessage winMsg = new ACLMessage(ACLMessage.INFORM);
                            winMsg.addReceiver(new jade.core.AID("server", jade.core.AID.ISLOCALNAME));
                            winMsg.setContent(getAID().getName() + " wins with " + response);
                            send(winMsg);
                            gameOver = true;
                        } else if (response < guess) {
                            System.out.println(getAID().getName() + ": Too low!");
                        } else {
                            System.out.println(getAID().getName() + ": Too high!");
                        }
                    }
                } else {
                    block();
                }
            }
        });

        // Comportement pour envoyer des propositions de nombre magique
        addBehaviour(new Behaviour() {
            private boolean done = false;

            public void action() {
                if (!gameOver) {
                    int proposal = new Random().nextInt(100);
                    ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
                    msg.addReceiver(new jade.core.AID("server", jade.core.AID.ISLOCALNAME));
                    msg.setContent(String.valueOf(proposal));
                    send(msg);
                    System.out.println(getAID().getName() + ": Proposal sent: " + proposal);
                    block(500);
                } else {
                    done = true;
                }
            }

            public boolean done() {
                return done;
            }
        });
    }

    protected void takeDown() {
        System.out.println(getAID().getName() + " is terminating.");
    }
}

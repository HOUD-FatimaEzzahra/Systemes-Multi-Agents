package ma.enset;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.HashMap;
import java.util.Map;

public class BuyerAgent extends Agent {
    DFAgentDescription[] dfAgentD;
    private AID bestSeller;
    private double bestPrice=Double.MAX_VALUE;
    private  int cpt=0;
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                DFAgentDescription ad= new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("pc");
                ad.addServices(sd);
                try {
                    dfAgentD=DFService.search(myAgent, ad);
                    for (DFAgentDescription a:dfAgentD) {
                        AID agentAID = a.getName();
                        ACLMessage aclMessage = new ACLMessage(ACLMessage.CFP);
                        aclMessage.setContent("hp");
                        aclMessage.addReceiver(agentAID);
                        send(aclMessage);
                    }
                } catch (FIPAException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMsg = receive();
                if (receivedMsg!=null){
                    switch (receivedMsg.getPerformative()){
                        case ACLMessage.PROPOSE :
                            double price = Double.parseDouble(receivedMsg.getContent());
                            if (price<bestPrice){
                                bestPrice=price;
                                bestSeller=receivedMsg.getSender();
                            }
                            if (dfAgentD.length==cpt){
                                ACLMessage aclMessage = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                                aclMessage.addReceiver(bestSeller);
                                aclMessage.setContent("Proposel accepted");
                                send(aclMessage);
                            }
                            break;
                        case ACLMessage.AGREE:
                            ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
                            aclMessage.addReceiver(receivedMsg.getSender());
                            aclMessage.setContent("i want to buy it");
                            send(aclMessage);
                            break;
                        case ACLMessage.CONFIRM:
                            System.out.println(receivedMsg.getSender()+" : "+receivedMsg.getContent());
                            break;
                    }
                }else
                    block();
            }
        });
    }
}

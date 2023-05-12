package ma.enset;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class SimpleContainer {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profileImpl = new ProfileImpl();
        profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
        AgentContainer agentContainer=runtime.createAgentContainer(profileImpl);
        AgentController saller1=agentContainer.createNewAgent("saller1", SallerAgent1.class.getName(), new Object[]{});
        AgentController saller2=agentContainer.createNewAgent("saller2", SallerAgent2.class.getName(), new Object[]{});
        AgentController buyer=agentContainer.createNewAgent("buyer", BuyerAgent.class.getName(), new Object[]{});
        saller1.start();
        saller2.start();
        buyer.start();
    }
}

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
        AgentController saller1=agentContainer.createNewAgent("saller1", SallerAgent.class.getName(), new Object[]{"12000"});
        AgentController saller2=agentContainer.createNewAgent("saller2", SallerAgent.class.getName(), new Object[]{"10000"});
        AgentController buyer=agentContainer.createNewAgent("buyer", BuyerAgent.class.getName(), new Object[]{});
        saller1.start();
        buyer.start();
    }
}

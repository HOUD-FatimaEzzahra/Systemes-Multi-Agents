package ma.enset.tp_sm1_new;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

public class MainContainer {
    // hadchi mzyan ikhrj haka?
    //ah rah aslan kaan kharj flwl
    //kaykhes ikhrj w mli n creyi agent w n executiii l'interface graphique ykhdm kolchi
    // interface graphiue dial server ola client ?
    // bzouj
    //ma kaynch aslan hnaya server
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("gui","true");
        AgentContainer agentContainer=runtime.createMainContainer(profile);
        agentContainer.start();
    }
}

package ma.enset.signature;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ClientAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String documents="Hello server!";
                String encodedPRK=(String) getArguments()[0];
                byte[] decodedPRK=Base64.getDecoder().decode(encodedPRK);
                try {
                    KeyFactory keyFactory=KeyFactory.getInstance("RSA");
                    PrivateKey privateKey=keyFactory.generatePrivate(new X509EncodedKeySpec(decodedPRK));
                    Signature signature=Signature.getInstance("RSAWithSHA256");
                    signature.initSign(privateKey);
                    signature.update(documents.getBytes());
                    byte[]sign=signature.sign();
                    String encodedSign=Base64.getEncoder().encodeToString(sign);
                    String encodedDocument=Base64.getEncoder().encodeToString(documents.getBytes());
                    String documentEncoded=encodedDocument+"|"+encodedSign;
                    ACLMessage aclMessage=new ACLMessage(ACLMessage.INFORM);
                    aclMessage.setContent(documentEncoded);
                    aclMessage.addReceiver(new AID("server",AID.ISLOCALNAME));
                    send(aclMessage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

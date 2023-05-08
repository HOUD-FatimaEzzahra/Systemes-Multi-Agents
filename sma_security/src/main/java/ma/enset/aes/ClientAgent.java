package ma.enset.aes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ClientAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                String message="Hello server!";
                String password=(String) getArguments()[0];
                SecretKey secretKey=new SecretKeySpec(password.getBytes(),"AES");
                try {
                    Cipher cipher= Cipher.getInstance("AES");
                    cipher.init(Cipher.ENCRYPT_MODE,secretKey);
                    byte[] encryptedMessage=cipher.doFinal(message.getBytes());
                    String encryptedMessageAsString= Base64.getEncoder().encodeToString(encryptedMessage);
                    ACLMessage aclMessage=new ACLMessage(ACLMessage.INFORM);
                    aclMessage.setContent(encryptedMessageAsString);
                    aclMessage.addReceiver(new AID("server",AID.ISLOCALNAME));
                    send(aclMessage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

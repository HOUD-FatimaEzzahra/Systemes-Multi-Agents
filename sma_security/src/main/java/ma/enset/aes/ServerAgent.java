package ma.enset.aes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ServerAgent extends Agent {
    @Override
    protected void setup() {
        String password=(String) getArguments()[0];
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMessage=receive();
                if (receivedMessage!=null){
                   String encryptedMessageAsString=receivedMessage.getContent();
                    byte[] encrybterdMsg= Base64.getDecoder().decode(encryptedMessageAsString);
                    SecretKey secretKey=new SecretKeySpec(password.getBytes(),"AES");
                    try {
                        Cipher cipher=Cipher.getInstance("AES");
                        cipher.init(Cipher.DECRYPT_MODE,secretKey);
                        byte[] decryptedMessage=cipher.doFinal(encrybterdMsg);
                        System.out.println("Decrypted message: "+new String(decryptedMessage));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    //System.out.println("Encrypted message: "+encryptedMessageAsString);
                }else
                    block();
            }
        });
    }
}

package ma.enset.rsa;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class ServerAgent extends Agent {
    @Override
    protected void setup() {
        String encodedPRK=(String) getArguments()[0];
        byte[] decodedPRK=Base64.getDecoder().decode(encodedPRK);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMessage=receive();
                if (receivedMessage!=null){
                   String encryptedMessageAsString=receivedMessage.getContent();
                    byte[] encrybterdMsg= Base64.getDecoder().decode(encryptedMessageAsString);
                    try {
                        KeyFactory keyFactory= KeyFactory.getInstance("RSA");
                        PrivateKey privateKey=keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedPRK));
                        Cipher cipher= Cipher.getInstance("RSA");
                        cipher.init(Cipher.DECRYPT_MODE,privateKey);
                        byte[] decryptedMessage=cipher.doFinal(encrybterdMsg);
                        System.out.println(new String(decryptedMessage));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else
                    block();
            }
        });
    }
}

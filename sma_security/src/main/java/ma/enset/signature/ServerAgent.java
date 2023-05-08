package ma.enset.signature;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class ServerAgent extends Agent {
    @Override
    protected void setup() {
        String encodedPBK=(String) getArguments()[0];
        byte[] decodedPBK=Base64.getDecoder().decode(encodedPBK);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMessage=receive();
                if (receivedMessage!=null){
                   String documentSgn=receivedMessage.getContent();
                   String documentSplit[]=documentSgn.split("\\|");
                  byte[] document=Base64.getDecoder().decode(documentSplit[0]);
                  byte[] sign=Base64.getDecoder().decode(documentSplit[1]);
                    try {
                        KeyFactory keyFactory= KeyFactory.getInstance("RSA");
                        PublicKey publicKey=keyFactory.generatePublic(new PKCS8EncodedKeySpec(decodedPBK));
                        Signature signature=Signature.getInstance("RSAWithSHA256");
                        signature.initVerify(publicKey);
                        signature.update(document);
                        boolean verify=signature.verify(sign);
                        System.out.println("Document is verified: "+verify);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else
                    block();
            }
        });
    }
}

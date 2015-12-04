package cl.wisse.datomed.core.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by aestartus on 04-12-15.
 */
public class Utiles {

    public static  String encriptar(String in){
        String stringB64 = null;
        try{
            MessageDigest md = MessageDigest.getInstance("Sha1");
            byte[] inDigest = md.digest(in.getBytes("ISO-8859-1"));
            stringB64 = new String(Base64.encodeBase64(inDigest), StandardCharsets.UTF_8);

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return stringB64;
    }
}

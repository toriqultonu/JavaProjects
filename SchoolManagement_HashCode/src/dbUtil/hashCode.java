package dbUtil;
/*
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hashCode {
    public static String createHashCode(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte[] byteData = md.digest();
        StringBuffer sb = new StringBuffer();
        for(int i =0; i<byteData.length; i++){
            sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
        }

        String hashpassword = new String(sb.toString());
        return hashpassword;
    }

}
*/

package com.credu.library;
import java.security.*;

 /**
 * <p>����: �������� ��ȣȭ(�ܹ��� ��ȣ �˰���)</p>
 * <p>����: </p>
 * <p>Copyright: </p>
 * <p>Company:  </p>
 */
public class HashCipher {

     public HashCipher() {		
	}

    //�ؽ� ����
    public static String createHash(String data) throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] bytedata = data.getBytes("euc-kr");
        md.update(bytedata);
        byte[] digest = md.digest();       //�迭�� ����
                        
        StringBuffer result = new StringBuffer();
        for (int i=0; i<digest.length; i++) {
            result.append(Integer.toHexString(digest[i] & 0xff));
        }
        return result.toString();
    }

}

package common;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.MessageDigest;

public class CryptCPUtil {
	

	
	//단방향 암호화
	public static String encrypt(String str) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return encoder(md.digest(str.getBytes()));
    }
	
	
	public static String encoder(byte[] arrByte) throws Exception{
        return Base64.encode(arrByte);
    }
	

	
//	/**
//	 * @param args
//	 * @throws Exception
//	 */
//	public static void main(String[] args) throws Exception {
//
//		
//		String text = "2014HUNET0618";
//		String rs_val = "";
//		
//		System.out.println(" ################## SHA256 단방향 암호화 시작 #################### ");
//		rs_val  = encrypt(text);
//		System.out.println("sha256_val>>>>>>>>>>>>>>>>>>>>>>>>> "+rs_val);
//	}
	
}

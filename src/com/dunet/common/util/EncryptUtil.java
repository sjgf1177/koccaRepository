package com.dunet.common.util;

import com.credu.library.ErrorManager;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * Created by IntelliJ IDEA.
 * User: pkh
 * Date: 2011. 1. 29
 * Time: ¿ÀÈÄ 3:18:16
 * To change this template use File | Settings | File Templates.
 */
public class EncryptUtil {
    private Cipher rijndael;
    private SecretKeySpec key;
    private IvParameterSpec initalVector;

    /**
     * Creates a StringEncrypter instance.
     *
     * @param key           A key string which is converted into UTF-8 and hashed by MD5.
     *                      Null or an empty string is not allowed.
     * @param initialVector An initial vector string which is converted into UTF-8
     *                      and hashed by MD5. Null or an empty string is not allowed.
     * @throws Exception
     */
    public EncryptUtil(String key, String initialVector) throws Exception {
        if (key == null || key.equals(""))
			throw new Exception("util.EncryptUtil.The key can not be null or an empty string");

        if (initialVector == null || initialVector.equals(""))
            throw new Exception("util.EncryptUtil.The initial vector can not be null or an empty string");


        // Create a AES algorithm.
        this.rijndael = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Initialize an encryption key and an initial vector.
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        this.key = new SecretKeySpec(md5.digest(key.getBytes("UTF8")), "AES");
        this.initalVector = new IvParameterSpec(md5.digest(initialVector.getBytes("UTF8")));
    }

    /**
     * Encrypts a string.
     *
     * @param value A string to encrypt. It is converted into UTF-8 before being encrypted.
     *              Null is regarded as an empty string.
     * @return An encrypted string.
     * @throws Exception
     */
    public String encrypt(String value) throws Exception {
        if (value == null)
            value = "";

        // Initialize the cryptography algorithm.
        this.rijndael.init(Cipher.ENCRYPT_MODE, this.key, this.initalVector);

        // Get a UTF-8 byte array from a unicode string.
        value = value.replace("+","@").replace("/","$");
        byte[] utf8Value = value.getBytes("UTF8");

        // Encrypt the UTF-8 byte array.
        byte[] encryptedValue = this.rijndael.doFinal(utf8Value);

        // Return a base64 encoded string of the encrypted byte array.
        return Base64EncrypterUtil.encode(encryptedValue).replace("+","@").replace("/","$");
    }

    /**
     * Decrypts a string which is encrypted with the same key and initial vector.
     *
     * @param value A string to decrypt. It must be a string encrypted with the same key and initial vector.
     *              Null or an empty string is not allowed.
     * @return A decrypted string
     * @throws Exception
     */
    public String decrypt(String value) throws Exception {
        if (value == null || value.equals(""))
            throw new Exception("util.EncryptUtil.The cipher string can not be null or an empty string");


        // Initialize the cryptography algorithm.
        this.rijndael.init(Cipher.DECRYPT_MODE, this.key, this.initalVector);

        // Get an encrypted byte array from a base64 encoded string.
        value = value.replace("@","+").replace("$","/");
        byte[] encryptedValue = Base64EncrypterUtil.decode(value);

        // Decrypt the byte array.
        byte[] decryptedValue = this.rijndael.doFinal(encryptedValue);

        // Return a string converted from the UTF-8 byte array.
        return new String(decryptedValue, "UTF8");
	}
}

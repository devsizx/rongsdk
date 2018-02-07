package com.rongmzw.frame.sdk.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesStoreSecurity {
	public static byte[] encrypt(byte[] buff) throws Exception {
		IvParameterSpec zeroIv = new IvParameterSpec("0102030405060708".getBytes());
		SecretKeySpec key = new SecretKeySpec("craigdvsevendays".getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(buff);
		return encryptedData;

	}

	public static byte[] decrypt(byte[] buff) throws Exception {

		IvParameterSpec zeroIv = new IvParameterSpec("0102030405060708".getBytes());
		SecretKeySpec key = new SecretKeySpec("craigdvsevendays".getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte[] decryptedData = cipher.doFinal(buff);

		return decryptedData;
	}
}

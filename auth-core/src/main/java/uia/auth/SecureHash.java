package uia.auth;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

/**
 * The code tool.
 *
 * @author ONBON
 *
 */
public final class SecureHash {

    /**
     * sha1.
     *
     * @param value Value to be handled.
     * @return Result.
     * @throws Exception Failed to handle.
     */
    public static String sha1(String value) throws Exception {
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(value.getBytes("UTF-8"));
        return new BigInteger(1, crypt.digest()).toString(16);
    }

    /**
     * sha256.
     *
     * @param value Value to be handled.
     * @return Result.
     * @throws Exception Failed to handle.
     */
    public static String sha256(String value) throws Exception {
        MessageDigest crypt = MessageDigest.getInstance("SHA-256");
        crypt.reset();
        crypt.update(value.getBytes("UTF-8"));
        return new BigInteger(1, crypt.digest()).toString(16);
    }

    /**
     * sha512.
     *
     * @param value Value to be handled.
     * @return Result.
     * @throws Exception Failed to handle.
     */
    public static String sha512(String value) throws Exception {
        MessageDigest crypt = MessageDigest.getInstance("SHA-512");
        crypt.reset();
        crypt.update(value.getBytes("UTF-8"));
        return new BigInteger(1, crypt.digest()).toString(16);
    }

    /**
     * md5.
     *
     * @param value Value to be handled.
     * @return Result.
     * @throws Exception Failed to handle.
     */
    public static String md5(String value) throws Exception {
        MessageDigest crypt = MessageDigest.getInstance("MD5");
        crypt.reset();
        crypt.update(value.getBytes("UTF-8"));
        return new BigInteger(1, crypt.digest()).toString(16);
    }

    /**
     * md5.
     *
     * @param data Value to be handled.
     * @return Result.
     * @throws Exception Failed to handle.
     */
    public static String md5(byte[] data) throws Exception {
        MessageDigest crypt = MessageDigest.getInstance("MD5");
        crypt.reset();
        crypt.update(data);
        byte[] digi = crypt.digest();
        String result = "";
        for(byte d : digi) {
        	result += String.format("%02x", d); 
        }
        return result;
    }

    /**
     * md5.
     *
     * @param file Value to be handled.
     * @return Result.
     * @throws Exception Failed to handle.
     */
    public static String md5(File file) throws Exception {
        byte[] data = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(data);
        fis.close();

        return md5(data);
    }

    /**
     * Convert to base64.
     *
     * @param text Value to be handled.
     * @return Result.
     * @throws Exception Failed to handle.
     */
    public static String toBase64(String text) throws Exception {
        return new Base64().encodeAsString(text.getBytes("UTF-8"));
    }

    /**
     * Convert from base64.
     *
     * @param text Base64 text.
     * @return Result.
     * @throws Exception Failed to handle.
     */
    public static String fromBase64(String text) throws Exception {
        return new String(new Base64().decode(text), "UTF-8");
    }
}

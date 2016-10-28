package com.xpx.base.util.security.crypt;

import com.xpx.base.util.security.common.BinaryUtil;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHA1 {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String ALGORITHM = "HmacSHA1";
    private static final Object LOCK = new Object();
    private static Mac macInstance;

    public String getAlgorithm() {
        return ALGORITHM;
    }

    public String encrypt(String key, String data){
        try{
            byte[] signData = sign(
                    key.getBytes(DEFAULT_ENCODING),
                    data.getBytes(DEFAULT_ENCODING));

            return BinaryUtil.toBase64String(signData);
        }
        catch(UnsupportedEncodingException ex){
            throw new RuntimeException("Unsupported algorithm: " + DEFAULT_ENCODING);
        }
    }


    private byte[] sign(byte[] key, byte[] data){
        try{
            if (macInstance == null) {
                synchronized (LOCK) {
                    if (macInstance == null) {
                        macInstance = Mac.getInstance(ALGORITHM);
                    }
                }
            }

            Mac mac = null;
            try {
                mac = (Mac)macInstance.clone();
            } catch (CloneNotSupportedException e) {
                mac = Mac.getInstance(ALGORITHM);
            }
            mac.init(new SecretKeySpec(key, ALGORITHM));
            return mac.doFinal(data);
        }
        catch(NoSuchAlgorithmException ex){
            throw new RuntimeException("Unsupported algorithm: " + ALGORITHM);
        }
        catch(InvalidKeyException ex){
            throw new RuntimeException("InvalidKeyException:" + ex);
        }
    }
}
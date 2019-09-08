package com.cashbook.cashbook.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.security.MessageDigest;

public class MD5Util {

    private static String byteArrayToHexString(@NonNull byte b[]){
        StringBuffer resultSb = new StringBuffer();
        for(int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b){
        int n = b;
        if(n < 0) {
            n += 256;
        }
        int d1 = n/16;
        int d2 = n%16;
        return hexDigits[d1] + hexDigits[d2];
    }

    @Nullable
    public static String MD5Encode(@NonNull String origin, @Nullable String charsetname){
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if(charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch(Exception exception) {
        }
        return resultString;
    }

    @Nullable
    public static String encode(@NonNull String origin){
        return MD5Encode(origin,"utf-8");
    }

    private static final  String hexDigits[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

}

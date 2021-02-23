package com.tientt.utils;

import com.google.common.hash.Hashing;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class HashingUtil implements Serializable {

    public static String hashPassword(String password) {
        String hashed
                = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        return hashed;
    }


}

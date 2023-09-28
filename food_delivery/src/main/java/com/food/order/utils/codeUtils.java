package com.food.order.utils;

import java.util.Random;

public class codeUtils {

    public static String codeGenerate(int length){
        String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code=new StringBuilder();
        Random rd=new Random();
        for (int i = 0; i < length; i++) {
            char ch=alphabet.charAt(rd.nextInt(alphabet.length()));
            code.append(ch);
        }
        return code.toString();
    }
}

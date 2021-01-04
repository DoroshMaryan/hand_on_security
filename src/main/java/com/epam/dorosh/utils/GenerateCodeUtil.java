package com.epam.dorosh.utils;

import java.util.Random;

public class GenerateCodeUtil {
    private GenerateCodeUtil() {
    }

    public static String generateCode() {
        Random random = new Random();
        int c = random.nextInt(9000) + 1000;
        return String.valueOf(c);
    }
}

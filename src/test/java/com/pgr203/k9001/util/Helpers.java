package com.pgr203.k9001.util;

import java.util.Random;

public class Helpers {
    public static String PickOne(String[] alternatives) {
        Random random = new Random();
        return alternatives[random.nextInt(alternatives.length)];
    }
}

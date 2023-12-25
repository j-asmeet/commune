package com.group6.commune.Utils;

import java.util.Random;

public class IDgenerator {
    public static int generateId() {
        Random random = new Random();
        return Math.abs(random.nextInt());
    }
}

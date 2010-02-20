package com.killard;

public class Main {

    public static void main(String[] args) throws Exception {
        String str = "10:3";
        int i = str.indexOf(":");
        System.out.println(Integer.parseInt(str.substring(0, i)) + " | " + Integer.parseInt(str.substring(i + 1)));
    }
}

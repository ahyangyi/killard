package com.killard;

import java.net.URLDecoder;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(URLDecoder.decode("cardPosition=3&skillIndex=1&target%5B%5D=1%3A3&target%5B%5D=self"));
    }
}

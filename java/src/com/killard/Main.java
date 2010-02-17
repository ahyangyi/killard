package com.killard;

import java.util.LinkedHashSet;

public class Main {

    public static void main(String[] args) throws Exception {
        LinkedHashSet<String> set = new LinkedHashSet<String>();
        set.add("a");
        set.add("c");
        set.add("b");
        for (String str : set) {
            System.out.println(str);
        }
    }
}

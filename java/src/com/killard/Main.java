package com.killard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outBuf);

        A a = new A();
        out.writeObject(a);
        out.close();
        System.out.println("write:" + a);
        System.out.println("size:" + outBuf.toByteArray().length);

        ByteArrayInputStream inBuf = new ByteArrayInputStream(outBuf.toByteArray());
        ObjectInputStream in = new ObjectInputStream(inBuf);
        a = (A) in.readObject();
        in.close();
        System.out.println("read:" + a);
        a.test();
    }
}

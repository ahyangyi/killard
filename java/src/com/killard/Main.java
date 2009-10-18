package com.killard;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Main {

    public void makeNumberImage(int number) throws IOException {
        BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = image.createGraphics();
        graphics.setFont(new Font("Serif", Font.BOLD, 24));
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.valueOf(number), 0, 24);
        graphics.dispose();

        Iterator writers = ImageIO.getImageWritersByFormatName("png");
        ImageWriter writer = (ImageWriter) writers.next();
        ImageIO.setUseCache(false);
        ImageOutputStream ios = ImageIO.createImageOutputStream(new File("server/web/images/board/" + String.valueOf(number) + ".png"));
        writer.setOutput(ios);
        writer.write(image);
        writer.dispose();
    }

    public void random() {
        for (int i = 0; i < 20; i++) {
            System.out.print((int) (1 * Math.random()) + " ");
        }
    }

    public void split(String s) {
        String[] items = s.split("/");
        for (String item : items) System.out.println(item);
    }

    public static void main(String[] args) throws Exception {
//        new GameBoard();
        Main main = new Main();
        main.split("/package/a/b/c/t.html");
//        for (int i = 0; i < 100; i++) main.makeNumberImage(i);
    }
}

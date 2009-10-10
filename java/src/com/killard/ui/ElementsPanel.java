package com.killard.ui;

import com.killard.pack.magic.MagicElementSchool;
import com.killard.ui.event.ElementListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: iodine
 * Date: Sep 4, 2009
 * Time: 10:54:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElementsPanel{
    JLabel elements[] = new JLabel[6];

    public ElementsPanel(PlayerPanel player, GameBoard board, boolean top, int earth, int fire, int air, int water, int life, int death) {
        elements[0] = new JLabel(String.valueOf(earth));
        elements[0].setBackground(Color.GRAY);
        elements[0].addMouseListener(new ElementListener(player, MagicElementSchool.EARTH));
        elements[1] = new JLabel(String.valueOf(fire));
        elements[1].setBackground(Color.RED);
        elements[1].addMouseListener(new ElementListener(player, MagicElementSchool.FIRE));
        elements[2] = new JLabel(String.valueOf(air));
        elements[2].setBackground(Color.CYAN);
        elements[2].addMouseListener(new ElementListener(player, MagicElementSchool.AIR));
        elements[3] = new JLabel(String.valueOf(water));
        elements[3].setBackground(Color.BLUE);
        elements[3].addMouseListener(new ElementListener(player, MagicElementSchool.WATER));
        elements[4] = new JLabel(String.valueOf(life));
        elements[4].setBackground(Color.GREEN);
        elements[4].addMouseListener(new ElementListener(player, MagicElementSchool.LIFE));
        elements[5] = new JLabel(String.valueOf(death));
        elements[5].setBackground(Color.BLACK);
        elements[5].addMouseListener(new ElementListener(player, MagicElementSchool.DEATH));
        for(int i=0;i<6;i++){
            board.add(elements[i]);
            if(top)
                elements[i].setBounds(300+i*30, 50, 30, 30);
            else
                elements[i].setBounds(300+i*30, 500, 30, 30);
            elements[i].setForeground(Color.WHITE);
            elements[i].setOpaque(true);
        }
        board.invalidate();
    }

    public void setElementNo(MagicElementSchool school, int no) {
        this.elements[school.ordinal()].setText(String.valueOf(no));
    }
}

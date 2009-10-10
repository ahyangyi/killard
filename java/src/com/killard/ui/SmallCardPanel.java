package com.killard.ui;

import com.killard.card.Card;

import javax.swing.*;
import java.awt.*;

public class SmallCardPanel extends JPanel {

    private Card card;

    private PlayerPanel playerPanel;

    private boolean selected = false;

    public SmallCardPanel(Card card, PlayerPanel playerPanel) {
        this.card = card;
        this.playerPanel = playerPanel;
        this.init();

    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if(selected == true)
            System.out.println("Select "+card);
        this.init();
        this.revalidate();
        System.out.println(this.selected);
    }

    private void init() {

        this.removeAll();

        setPreferredSize(new Dimension(50, 50));
        setLayout(new BorderLayout());
        if (card != null) {
            add(new JLabel("A:" + card.getAttack()), BorderLayout.NORTH);
            add(new JLabel(card.getClass().getSimpleName()), BorderLayout.CENTER);
        }
    }

    public void paint(Graphics g) {
        System.out.println("paint "+card+" "+selected);
        if (selected){
            System.out.println("hahahhaha");
            g.setColor(Color.BLACK);
        }
        else
            g.setColor(Color.WHITE);
        g.fillRect(0, 0, 50, 50);
        if (card != null) {
            if (selected)
                g.setColor(Color.WHITE);
            else
                g.setColor(Color.BLACK);
            g.drawString(card.getClass().getSimpleName(), 10, 30);
            g.drawString("A:" + card.getAttack(), 10, 10);
        }
    }

    public void setCard(Card card) {
        this.card = card;
        this.init();
        this.invalidate();
    }


}
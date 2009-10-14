package com.killard.board.ui;

import com.killard.board.card.MetaCard;
import com.killard.board.ui.event.CardListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: iodine
 * Date: Sep 4, 2009
 * Time: 10:21:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class CardsPanel {

    private final int CARD_ON_BOARD = 5;
    private CardPanel[] cards = new CardPanel[CARD_ON_BOARD];
    private PlayerPanel player;
    private boolean top;
    private GameBoard board;

    public CardsPanel(PlayerPanel player, GameBoard board, boolean top) {
        //this.setSize(500,100);
        this.top = top;
        this.player = player;
        this.board = board;
        for (int i = 0; i < CARD_ON_BOARD; i++) {
            cards[i] = new CardPanel(null, player);
            cards[i].addMouseListener(new CardListener(player, i));
            board.add(cards[i]);
            if (top)
                cards[i].setBounds(i * 110 + 30, 160, 100, 120);
            else
                cards[i].setBounds(i * 110 + 30, 290, 100, 120);
        }
    }

    public void setCard(int index, MetaCard card) {
        if (index < 0 || index >= CARD_ON_BOARD)
            throw new IllegalArgumentException("index out of bound");
        cards[index].setCard(card);

    }

    public CardPanel[] getAllCards() {
        return this.cards;
    }

    public void doAttack(int index) {
        Point loc = cards[index].getLocation();
        for (int i = 0; i < 3; i++) {
            if (top)
                cards[index].setLocation((int) loc.getX(), (int) loc.getY() + 15 * i);
            else
                cards[index].setLocation((int) loc.getX(), (int) loc.getY() - 15 * i);
            board.invalidate();
            try {
                Thread.sleep(50);
            }

            catch (Exception e) {
            }
        }

        loc = cards[index].getLocation();
        for (int i = 0; i < 3; i++) {
            if (top)
                cards[index].setLocation((int) loc.getX(), (int) loc.getY() - 15 * i);
            else
                cards[index].setLocation((int) loc.getX(), (int) loc.getY() + 15 * i);
            board.invalidate();
            try {
                Thread.sleep(20);
            }
            catch (Exception e) {
            }
        }
    }

    public void showDamage(int index, int damage) {
        JLabel label = new JLabel("-" + String.valueOf(damage));
        label.setOpaque(true);
        label.setForeground(Color.RED);
        cards[index].addComp(label,80,10,15,15);
        label.repaint();
        //cards[index].repaint();
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {
        }
        //label.setVisible(false);
        cards[index].removeComp(label);
        cards[index].repaint(); 
    }
}

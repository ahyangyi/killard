package com.killard.board.ui.event;

import com.killard.board.card.Card;
import com.killard.board.ui.PlayerPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by IntelliJ IDEA.
 * User: iodine
 * Date: Sep 4, 2009
 * Time: 11:05:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmallCardListener implements MouseListener {
    private PlayerPanel playerPanel;
    private Card card;
    private int index;

    public SmallCardListener(PlayerPanel playerPanel, int index, Card card) {
        this.playerPanel = playerPanel;
        this.index = index;
        this.card = card;
    }

    public void mouseClicked(MouseEvent event) {
        System.out.println("Small Evenet");
        playerPanel.selectCard(index, card);

    }

    public void mousePressed(MouseEvent event) {
    }

    public void mouseReleased(MouseEvent event) {

    }

    public void mouseEntered(MouseEvent event) {

    }

    public void mouseExited(MouseEvent event) {

    }
}
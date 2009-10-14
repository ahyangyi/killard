package com.killard.board.ui.event;

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
public class CardListener implements MouseListener {
    private PlayerPanel playerPanel;
    int index;

    public CardListener(PlayerPanel playerPanel, int index) {
        this.playerPanel = playerPanel;
        this.index = index;
    }

    public void mouseClicked(MouseEvent event) {
        System.out.println("Big Event");
        playerPanel.putCard(index);

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
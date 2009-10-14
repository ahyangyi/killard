package com.killard.board.ui.event;

import com.killard.board.packages.magic.MagicElementSchool;
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
public class ElementListener implements MouseListener {
    private PlayerPanel playerPanel;
    MagicElementSchool school;

    public ElementListener(PlayerPanel playerPanel, MagicElementSchool school) {
        this.playerPanel = playerPanel;
        this.school = school;
    }

    public void mouseClicked(MouseEvent event) {
        playerPanel.setElementSchool(school);

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

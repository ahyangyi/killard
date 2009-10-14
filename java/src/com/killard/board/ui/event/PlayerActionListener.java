package com.killard.board.ui.event;

import com.killard.board.ui.PlayerPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerActionListener implements ActionListener {

    private PlayerPanel playerPanel;

    public PlayerActionListener(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }

    public void actionPerformed(ActionEvent event) {
        playerPanel.end();
    }
}

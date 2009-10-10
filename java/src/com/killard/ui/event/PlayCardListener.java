package com.killard.ui.event;

import com.killard.ui.PlayerPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayCardListener implements ActionListener {

    private PlayerPanel playerPanel;

    public PlayCardListener(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }

    public void actionPerformed(ActionEvent event) {
        JButton cardBtn = (JButton) event.getSource();
        cardBtn.setEnabled(false);
    }
}

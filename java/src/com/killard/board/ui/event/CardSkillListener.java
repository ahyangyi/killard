package com.killard.board.ui.event;

import com.killard.board.card.Card;
import com.killard.board.ui.PlayerPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardSkillListener implements ActionListener {

    private PlayerPanel playerPanel;

    private Card card;

    public CardSkillListener(PlayerPanel playerPanel, Card card) {
        this.playerPanel = playerPanel;
        this.card = card;
    }

    public void actionPerformed(ActionEvent event) {
        JButton cardBtn = (JButton) event.getSource();
        cardBtn.setEnabled(false);
        playerPanel.getBoard().updatePlayersStatus();
    }

}

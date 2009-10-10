package com.killard.ui;

import com.killard.card.Player;
import com.killard.environment.BoardManager;
import com.killard.environment.DefaultBoardManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameBoard extends JFrame {

    private Map<String, PlayerPanel> playerPanels = new HashMap<String, PlayerPanel>();

    private BoardManager boardManager;

    public GameBoard() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }

    private void init() {

        boardManager = new DefaultBoardManager();

        //setLayout(new BorderLayout());
        setLayout(null);
        setPreferredSize(new Dimension(600, 600));
        this.setResizable(false);



        PlayerPanel p1=buildPlayerPanel("Tom", true, true);
        PlayerPanel p2=buildPlayerPanel("Jerry", false, false);

        p1.setOpponent(p2);
        p2.setOpponent(p1);

        pack();
        setVisible(true);
    }

    private void end() {
        for (PlayerPanel p : playerPanels.values()) {
            if (p.getPlayer().getHealth() > 0) {
                System.out.println(p.getPlayer().getName() + " wins!");
            }
        }
        System.exit(0);
    }

    private PlayerPanel buildPlayerPanel(String name, boolean begin, boolean top) {
        Player player = null;//boardManager.addPlayer(name, 50);
        PlayerPanel panel = new PlayerPanel(player, this, begin, top);
        playerPanels.put(name, panel);
        return panel;
    }

    public void nextPlayer(Player player) {
        for (PlayerPanel p : playerPanels.values()) {
            if (p.getPlayer() != player) p.begin();
        }
    }

    public void updatePlayersStatus() {
        for (PlayerPanel p : playerPanels.values()) {
            p.updateHp();
            if (p.getPlayer().getHealth() <= 0) {
                end();
                init();
            }
        }
    }
}

package com.killard.board.ui;

import com.killard.board.card.MetaCard;
import com.killard.board.card.Player;
import com.killard.board.packages.magic.MagicElement;
import com.killard.board.ui.event.PlayerActionListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerPanel {


    private Player player;

    private GameBoard board;

    private JLabel nameLbl;

    private JLabel hpLbl;

    private JButton actBtn = new JButton("Act");

    private CardsPanel cardsPanel;

    private ElementsPanel elementsPanel;

    private SmallCardsPanel smallCardsPanel;

    private final int CARD_ON_BOARD = 5;

    private Map<MagicElement, ArrayList<MetaCard>> allCards = new HashMap<MagicElement, ArrayList<MetaCard>>();

    private MetaCard selectedCard = null;
    private boolean cardSelected = false;

    private boolean top;

    private PlayerPanel opponent;

    public PlayerPanel(Player player, GameBoard board, boolean begin, boolean top) {
        this.player = player;
        this.board = board;
        this.top = top;
        nameLbl = new JLabel(player.getId());
        hpLbl = new JLabel("HP:" + player.getHealth());
        board.add(nameLbl);
        board.add(hpLbl);
        if (top) {
            nameLbl.setBounds(100, 50, 30, 30);
            hpLbl.setBounds(135, 50, 60, 30);
        } else {
            nameLbl.setBounds(100, 500, 30, 30);
            hpLbl.setBounds(135, 500, 60, 30);
        }

        actBtn.addActionListener(new PlayerActionListener(this));

        board.add(actBtn);
        if (top) {
            actBtn.setBounds(210, 50, 80, 30);
        } else {
            actBtn.setBounds(210, 500, 80, 30);
        }

        cardsPanel = new CardsPanel(this, board, top);

        elementsPanel = new ElementsPanel(this, board, top, 5, 5, 5, 5, 5, 5);
        smallCardsPanel = new SmallCardsPanel(this, board, top);

        for (MagicElement school : MagicElement.values()) {
            ArrayList<MetaCard> cards = new ArrayList<MetaCard>();
            allCards.put(school, cards);
        }


        if (begin) begin();
        else end();

        this.setElement(MagicElement.EARTH);
    }

    public void setCard(int index, MetaCard card) {
        if(!cardSelected){
            cardsPanel.setCard(index, card);
            cardSelected=true;
        }
    }

    public void setOpponent(PlayerPanel opponent){
        this.opponent = opponent;
    }

    public void setElement(MagicElement element) {
        ArrayList<MetaCard> cards = allCards.get(element);
        smallCardsPanel.clearAll();
        for (int i = 0; i < cards.size(); i++) {
            this.smallCardsPanel.setCard(i, cards.get(i));
        }
        this.smallCardsPanel.unselectAll();
    }

    public void selectCard(int index, MetaCard card) {
        this.selectedCard = card;
        this.smallCardsPanel.setSelected(index);
    }

    public void putCard(int index) {
        setCard(index, selectedCard);
        selectedCard = null;
        this.smallCardsPanel.unselectAll();
    }

    public void updateHp() {
        hpLbl.setText("HP:" + player.getHealth());
    }

    public void begin() {
        actBtn.setEnabled(true);
        cardSelected = false;
        for (CardPanel comp : cardsPanel.getAllCards()) {
            comp.begin();
        }
        board.invalidate();
    }

    public void showDamage(int index, int damage){
        cardsPanel.showDamage(index, damage);
    }
    public void end() {
        new Thread() {
            public void run() {
                actBtn.setEnabled(false);
                for (int i = 0; i < 5; i++){
                    cardsPanel.doAttack(i);
                    opponent.showDamage(i,5);
                }
                for (CardPanel comp : cardsPanel.getAllCards()) {
                    comp.end();
                }

                board.invalidate();
                board.nextPlayer(player);
            }
        }.start();

    }

    public Player getPlayer() {
        return player;
    }

    public GameBoard getBoard() {
        return board;
    }
}

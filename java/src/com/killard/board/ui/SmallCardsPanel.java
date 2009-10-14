package com.killard.board.ui;

import com.killard.board.card.MetaCard;
import com.killard.board.ui.event.SmallCardListener;

import java.awt.event.MouseListener;

/**
 * Created by IntelliJ IDEA.
 * User: iodine
 * Date: Sep 4, 2009
 * Time: 10:21:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmallCardsPanel{

    private final int CARD_ON_BOARD = 5;
    private SmallCardPanel[] cards = new SmallCardPanel[CARD_ON_BOARD];
    PlayerPanel player;

    public SmallCardsPanel(PlayerPanel player, GameBoard board, boolean top) {
        this.player = player;
        for (int i = 0; i < CARD_ON_BOARD; i++) {
            cards[i] = new SmallCardPanel(null, player);
            board.add(cards[i]);
            if(top)
                cards[i].setBounds(160+i*55,90, 50, 50);
            else
                cards[i].setBounds(160+i*55, 440, 50, 50);
        }
        board.invalidate();
    }

    public void setCard(int index, MetaCard card) {
        if (index < 0 || index >= CARD_ON_BOARD)
            throw new IllegalArgumentException("index out of bound");
        cards[index].setCard(card);
        for (MouseListener listener : cards[index].getMouseListeners()) {
            cards[index].removeMouseListener(listener);
        }
        if (card != null) {
            cards[index].addMouseListener(new SmallCardListener(player, index, card));
        }
        cards[index].invalidate();
    }

    public void setSelected(int index) {
        if (index < 0 || index >= CARD_ON_BOARD)
            throw new IllegalArgumentException("index out of bound");
        cards[index].setSelected(true);
        cards[index].invalidate();
        for (int i = 0; i < CARD_ON_BOARD; i++)
            if (i != index) {
                cards[i].setSelected(false);
                cards[i].invalidate();
            }
    }

    public void unselectAll() {
        for (int i = 0; i < CARD_ON_BOARD; i++) {

            cards[i].setSelected(false);
            cards[i].invalidate();
        }
    }

    public void clearAll() {
        for (int i = 0; i < CARD_ON_BOARD; i++) {
            cards[i].setCard(null);
        }
    }
}
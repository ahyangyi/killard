package com.killard.board.packages.magic;

import com.killard.board.card.MetaCard;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class MagicCardFactory {

    public static final Map<ElementSchool, List<MetaCard>> cards = new HashMap<ElementSchool, List<MetaCard>>();

    static {
//        EarthCardFactory.registerCards();
//        FireCardFactory.registerCards();
//        AirCardFactory.registerCards();
//        WaterCardFactory.registerCards();
//        LifeCardFactory.registerCards();
//        DeathCardFactory.registerCards();
    }

    public static void registerCard(MetaCard card) {
        List<MetaCard> list = cards.get(card.getElementSchool());
        if (list == null) {
            list = new LinkedList<MetaCard>();
            cards.put(card.getElementSchool(), list);
        }
        list.add(card);
    }

    public MagicCardFactory() {
    }

    public Map<ElementSchool, Integer> allocateElementsForNextPlayer() {
        Map<ElementSchool, Integer> elements = new HashMap<ElementSchool, Integer>();
        elements.put(MagicElementSchool.EARTH, 5);
        elements.put(MagicElementSchool.FIRE, 5);
        elements.put(MagicElementSchool.AIR, 5);
        elements.put(MagicElementSchool.WATER, 5);
        elements.put(MagicElementSchool.LIFE, 5);
        elements.put(MagicElementSchool.DEATH, 5);
        return elements;
    }

    public List<MetaCard> allocateCardsForNextPlayer(List<Player> players, int cardAmount) {
        List<MetaCard> newMetaCards = new LinkedList<MetaCard>();
        for (ElementSchool elementSchool : cards.keySet()) {
            List<MetaCard> remaining = getRemainingCards(elementSchool, players);
            randomSelect(remaining, cardAmount);
            newMetaCards.addAll(remaining);
        }
        return newMetaCards;
    }

    protected List<MetaCard> getRemainingCards(ElementSchool elementSchool, List<Player> players) {
        List<MetaCard> remaining = new LinkedList<MetaCard>();
        for (MetaCard card : cards.get(elementSchool)) {
            if (isCardAllocated(players, elementSchool, card)) continue;
            remaining.add(card);
        }
        return remaining;
    }

    protected boolean isCardAllocated(List<Player> players, ElementSchool elementSchool, MetaCard card) {
        for (Player player : players) if (isCardAllocated(player.getDealtCards(elementSchool), card)) return true;
        return false;
    }

    protected boolean isCardAllocated(MetaCard[] allocatedMetaCards, MetaCard card) {
        for (MetaCard allocated : allocatedMetaCards) {
            if (card.getClass().getSimpleName().equals(allocated.getClass().getSimpleName())) return true;
        }
        return false;
    }

    protected void randomSelect(List<MetaCard> remainingMetaCards, int cardAmount) {
        int size = remainingMetaCards.size() - cardAmount;
        for (int i = 0; i < size; i++) {
            int index = (int)Math.floor(remainingMetaCards.size() * Math.random());
            remainingMetaCards.remove(index);
        }
    }
}

package com.killard.packages.magic;

import com.killard.card.Card;
import com.killard.card.ElementSchool;
import com.killard.card.Player;

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

    public static final Map<ElementSchool, List<Card>> cards = new HashMap<ElementSchool, List<Card>>();

    static {
//        EarthCardFactory.registerCards();
//        FireCardFactory.registerCards();
//        AirCardFactory.registerCards();
//        WaterCardFactory.registerCards();
//        LifeCardFactory.registerCards();
//        DeathCardFactory.registerCards();
    }

    public static void registerCard(Card card) {
        List<Card> list = cards.get(card.getElementSchool());
        if (list == null) {
            list = new LinkedList<Card>();
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

    public List<Card> allocateCardsForNextPlayer(List<Player> players, int cardAmount) {
        List<Card> newCards = new LinkedList<Card>();
        for (ElementSchool elementSchool : cards.keySet()) {
            List<Card> remaining = getRemainingCards(elementSchool, players);
            randomSelect(remaining, cardAmount);
            newCards.addAll(remaining);
        }
        return newCards;
    }

    protected List<Card> getRemainingCards(ElementSchool elementSchool, List<Player> players) {
        List<Card> remaining = new LinkedList<Card>();
        for (Card card : cards.get(elementSchool)) {
            if (isCardAllocated(players, elementSchool, card)) continue;
            remaining.add(card);
        }
        return remaining;
    }

    protected boolean isCardAllocated(List<Player> players, ElementSchool elementSchool, Card card) {
        for (Player player : players) if (isCardAllocated(player.getDealtCards(elementSchool), card)) return true;
        return false;
    }

    protected boolean isCardAllocated(Card[] allocatedCards, Card card) {
        for (Card allocated : allocatedCards) {
            if (card.getClass().getSimpleName().equals(allocated.getClass().getSimpleName())) return true;
        }
        return false;
    }

    protected void randomSelect(List<Card> remainingCards, int cardAmount) {
        int size = remainingCards.size() - cardAmount;
        for (int i = 0; i < size; i++) {
            int index = (int)Math.floor(remainingCards.size() * Math.random());
            remainingCards.remove(index);
        }
    }
}

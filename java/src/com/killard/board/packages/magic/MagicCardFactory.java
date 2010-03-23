package com.killard.board.packages.magic;

import com.killard.board.card.Element;
import com.killard.board.card.MetaCard;
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

    public static final Map<Element, List<MetaCard>> cards = new HashMap<Element, List<MetaCard>>();

    static {
//        EarthCardFactory.registerCards();
//        FireCardFactory.registerCards();
//        AirCardFactory.registerCards();
//        WaterCardFactory.registerCards();
//        LifeCardFactory.registerCards();
//        DeathCardFactory.registerCards();
    }

    public static void registerCard(MetaCard card) {
        List<MetaCard> list = cards.get(card.getElement());
        if (list == null) {
            list = new LinkedList<MetaCard>();
            cards.put(card.getElement(), list);
        }
        list.add(card);
    }

    public MagicCardFactory() {
    }

    public Map<Element, Integer> allocateElementsForNextPlayer() {
        Map<Element, Integer> elements = new HashMap<Element, Integer>();
        elements.put(MagicElement.EARTH, 5);
        elements.put(MagicElement.FIRE, 5);
        elements.put(MagicElement.AIR, 5);
        elements.put(MagicElement.WATER, 5);
        elements.put(MagicElement.LIFE, 5);
        elements.put(MagicElement.DEATH, 5);
        return elements;
    }

    public List<MetaCard> allocateCardsForNextPlayer(List<Player> players, int cardAmount) {
        List<MetaCard> newMetaCards = new LinkedList<MetaCard>();
        for (Element element : cards.keySet()) {
            List<MetaCard> remaining = getRemainingCards(element, players);
            randomSelect(remaining, cardAmount);
            newMetaCards.addAll(remaining);
        }
        return newMetaCards;
    }

    protected List<MetaCard> getRemainingCards(Element element, List<Player> players) {
        List<MetaCard> remaining = new LinkedList<MetaCard>();
        for (MetaCard card : cards.get(element)) {
            if (isCardAllocated(players, element, card)) continue;
            remaining.add(card);
        }
        return remaining;
    }

    protected boolean isCardAllocated(List<Player> players, Element element, MetaCard card) {
        for (Player player : players) if (isCardAllocated(player.getDealtCards(element), card)) return true;
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

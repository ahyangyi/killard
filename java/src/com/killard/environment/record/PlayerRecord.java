package com.killard.environment.record;

import com.killard.card.Card;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.Role;
import com.killard.environment.event.StateListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class PlayerRecord extends AbstractPlayerRecord {

    private final String name;

    private final Role role;

    private int health;

    private boolean cardPlayed = false;

    private final Map<ElementSchool, Integer> elements = new HashMap<ElementSchool, Integer>();

    private final List<Card> holdedCards = new ArrayList<Card>();

    private final SortedSet<CardInstance> livingCards = new TreeSet<CardInstance>();

    private int turnCount = 0;

    public PlayerRecord(String name, int health) {
        this.name = name;
        this.health = health;
        this.role = null;
    }

    public PlayerRecord(String name, int health, StateListener listener) {
        this(name, health);
        addStateListener(listener);
    }

    public PlayerRecord(String name, int health, List<Card> cards) {
        this.name = name;
        this.health = health;
        this.holdedCards.addAll(cards);
        this.role = null;
    }

    public PlayerRecord(String name, int health, List<Card> cards, Map<ElementSchool, Integer> elements) {
        this(name, health, cards);
        this.elements.putAll(elements);
    }

    public PlayerRecord(String name, int health, List<Card> cards,
                        Map<ElementSchool, Integer> elements, StateListener listener) {
        this(name, health, cards, elements);
        addStateListener(listener);
    }

    public String getId() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public int getHealth() {
        return health;
    }

    public int getElementAmount(ElementSchool elementSchool) {
        return elements.get(elementSchool);
    }

    public Card getHoldedCard(Integer cardIndex) {
        return holdedCards.get(cardIndex);
    }

    public Card[] getHoldedCards(ElementSchool elementSchool) {
        List<Card> cards = new ArrayList<Card>();
        for (Card card : holdedCards) if (card.getElementSchool() == elementSchool) cards.add(card);
        return cards.toArray(new Card[cards.size()]);
    }

    public CardInstance[] getLivingCards() {
        return livingCards.toArray(new CardInstance[livingCards.size()]);
    }

    public CardInstance getLivingCard(Integer pos) {
        for (CardInstance card : livingCards) if (card.getPosition() == pos) return card;
        return null;
    }

    public boolean isCardPlayed() {
        return cardPlayed;
    }

    public int getTurnCount() {
        return turnCount;
    }

    protected void setCardPlayed(boolean cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    protected boolean addLivingCard(CardInstance card) {
        setCardPlayed(true);
        return livingCards.add(card);
    }

    protected boolean removeLivingCard(CardInstance card) {
        return livingCards.remove(card);
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    protected void setElementAmount(ElementSchool elementSchool, int amount) {
        elements.put(elementSchool, amount);
    }

    protected void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

}

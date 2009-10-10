package com.killard.environment.record;

import com.killard.card.Action;
import com.killard.card.Attack;
import com.killard.card.Card;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.Player;
import com.killard.environment.Record;
import com.killard.environment.event.StateEvent;
import com.killard.environment.event.StateListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class PlayerRecord implements Player, Record {

    private final String name;

    private int health;

    private boolean cardPlayed = false;

    private final Map<ElementSchool, Integer> elements = new HashMap<ElementSchool, Integer>();

    private final List<Card> holdedCards = new ArrayList<Card>();

    private final Set<CardInstance> livingCards = new HashSet<CardInstance>();

    private final Set<StateListener> stateListeners = new HashSet<StateListener>();

    private int turnCount = 0;

    public PlayerRecord(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public PlayerRecord(String name, int health, StateListener listener) {
        this(name, health);
        this.stateListeners.add(listener);
    }

    public PlayerRecord(String name, int health, List<Card> cards) {
        this.name = name;
        this.health = health;
        this.holdedCards.addAll(cards);
    }

    public PlayerRecord(String name, int health, List<Card> cards, Map<ElementSchool, Integer> elements) {
        this(name, health, cards);
        this.elements.putAll(elements);
    }

    public PlayerRecord(String name, int health, List<Card> cards,
                        Map<ElementSchool, Integer> elements, StateListener listener) {
        this(name, health, cards, elements);
        this.stateListeners.add(listener);
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getElementAmount(ElementSchool elementSchool) {
        return elements.get(elementSchool);
    }

    public ElementSchool[] getAllElementSchool() {
        Set<ElementSchool> list = new HashSet<ElementSchool>();
        for (Card card : holdedCards) list.add(card.getElementSchool());
        return list.toArray(new ElementSchool[list.size()]);
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
        List<CardInstance> copy = new ArrayList<CardInstance>(livingCards);
        Collections.sort(copy, new Comparator<CardInstance>() {
            public int compare(CardInstance card1, CardInstance card2) {
                return card1.getPosition() - card2.getPosition();
            }
        });
        return copy.toArray(new CardInstance[copy.size()]);
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

    public void addStateListener(StateListener listener) {
        stateListeners.add(listener);
    }

    public void removeStateListener(StateListener listener) {
        stateListeners.remove(listener);
    }

    protected void setCardPlayed(boolean cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    protected void addLivingCard(CardInstance card) {
        livingCards.add(card);
        setCardPlayed(true);
    }

    protected void removeLivingCard(CardInstance card) {
        livingCards.remove(card);
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

    protected void nextTurn() {
        for (CardInstance instance : getLivingCards()) {
            CardRecord record = (CardRecord) instance;
            record.setCasted(false);
        }
        setCardPlayed(false);
        setTurnCount(getTurnCount() + 1);
    }

    protected void changeHealth(Attack healthChange, Action action) {
        setHealth(getHealth() - healthChange.getValue());
        if (getHealth() < 0) setHealth(0);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeElementForSchool(ElementSchool elementSchool, int elementChange, Action action) {
        setElementAmount(elementSchool, getElementAmount(elementSchool) + elementChange);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void addLivingCard(CardInstance card, Action action) {
        addLivingCard(card);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void removeLivingCard(CardInstance card, Action action) {
        removeLivingCard(card);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void nextTurn(Action action) {
        nextTurn();
        fireStateChanged(new StateEvent(this, action));
    }

    protected void fireStateChanged(StateEvent event) {
        for (StateListener listener : stateListeners) listener.stateChanged(event);
    }

}

package com.killard.board.environment.record;

import com.killard.board.card.Card;
import com.killard.board.card.CardInstance;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.Role;
import com.killard.board.environment.event.StateListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final RoleRecord role;

    private int health;

    private boolean cardPlayed = false;

    private final Map<ElementSchool, Integer> elements = new HashMap<ElementSchool, Integer>();

    private final List<Card> dealtCards = new ArrayList<Card>();

    private final SortedSet<CardInstance> equippedCards = new TreeSet<CardInstance>();

    private boolean alive;

    private boolean called;

    private boolean winner;

    private boolean loser;

    private int turnCount = 0;

    public PlayerRecord(Role role, String name, int health) {
        this.name = name;
        this.health = health;
        this.role = new RoleRecord(role);
        this.called = false;
    }

    public PlayerRecord(Role role, String name, int health, StateListener listener) {
        this(role, name, health);
        addStateListener(listener);
    }

    public PlayerRecord(Role role, String name, int health, List<Card> cards) {
        this.name = name;
        this.health = health;
        this.called = false;
        this.dealtCards.addAll(cards);
        this.role = new RoleRecord(role);
    }

    public PlayerRecord(Role role, String name, int health, List<Card> cards, Map<ElementSchool, Integer> elements) {
        this(role, name, health, cards);
        this.elements.putAll(elements);
    }

    public PlayerRecord(Role role, String name, int health, List<Card> cards,
                        Map<ElementSchool, Integer> elements, StateListener listener) {
        this(role, name, health, cards, elements);
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

    public Card getDealtCard(Integer cardIndex) {
        return dealtCards.get(cardIndex);
    }

    public Card[] getDealtCards(ElementSchool elementSchool) {
        List<Card> cards = new ArrayList<Card>();
        for (Card card : dealtCards) if (card.getElementSchool() == elementSchool) cards.add(card);
        return cards.toArray(new Card[cards.size()]);
    }

    public CardInstance[] getEquippedCards() {
        return equippedCards.toArray(new CardInstance[equippedCards.size()]);
    }

    public CardInstance getEquippedCard(Integer pos) {
        for (CardInstance card : equippedCards) if (card.getPosition() == pos) return card;
        return null;
    }

    public boolean isCardPlayed() {
        return cardPlayed;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isCalled() {
        return called;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isLoser() {
        return loser;
    }

    public int getTurnCount() {
        return turnCount;
    }

    protected boolean addDealtCard(Card card) {
        return dealtCards.add(card);
    }

    protected boolean removeDealtCard(Card card) {
        return dealtCards.remove(card);
    }

    protected void setCardPlayed(boolean cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    protected boolean addEquippedCard(CardInstance card) {
        setCardPlayed(true);
        return equippedCards.add(card);
    }

    protected boolean removeEquippedCard(CardInstance card) {
        return equippedCards.remove(card);
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    protected void setElementAmount(ElementSchool elementSchool, int amount) {
        elements.put(elementSchool, amount);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    protected void setCalled(boolean called) {
        this.called = called;
    }

    protected void setRoleVisible(boolean visible) {
        role.setVisible(visible);
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public void setLoser(boolean loser) {
        this.loser = loser;
    }

    protected void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

}

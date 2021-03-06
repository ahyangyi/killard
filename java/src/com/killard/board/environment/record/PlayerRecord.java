package com.killard.board.environment.record;

import com.killard.board.card.Card;
import com.killard.board.card.Element;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Role;
import com.killard.board.card.record.AbstractPlayerRecord;
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
public class PlayerRecord extends AbstractPlayerRecord<PlayerRecord> {

    private final String name;

    private final int number;

    private final RoleRecord role;

    private int health;

    private boolean cardPlayed = false;

    private final Map<Element, Integer> elements = new HashMap<Element, Integer>();

    private final List<MetaCard> dealtCards = new ArrayList<MetaCard>();

    private final SortedSet<Card> equippedCards = new TreeSet<Card>();

    private boolean alive;

    private boolean called;

    private boolean winner;

    private boolean loser;

    private int turnCount = 0;

    private final Map<String, Object> properties = new HashMap<String, Object>();

    public PlayerRecord(Role role, String name, int health, int number) {
        this.name = name;
        this.health = health;
        this.number = number;
        this.role = new RoleRecord(role);
        this.called = false;
    }

    public PlayerRecord(Role role, String name, int health, StateListener listener, int number) {
        this(role, name, health, number);
        addStateListener(listener);
    }

    public PlayerRecord(Role role, String name, int health, List<MetaCard> metaCards, int number) {
        this.name = name;
        this.health = health;
        this.number = number;
        this.called = false;
        this.dealtCards.addAll(metaCards);
        this.role = new RoleRecord(role);
    }

    public PlayerRecord(Role role, String name, int health, List<MetaCard> metaCards,
                        Map<Element, Integer> elements, int number) {
        this(role, name, health, metaCards, number);
        this.elements.putAll(elements);
    }

    public PlayerRecord(Role role, String name, int health, List<MetaCard> metaCards,
                        Map<Element, Integer> elements, StateListener listener, int number) {
        this(role, name, health, metaCards, elements, number);
        addStateListener(listener);
    }

    public String getId() {
        return name;
    }

    public String getNickname() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public Role getRole() {
        return role;
    }

    public int getHealth() {
        return health;
    }

    public int getElementResource(Element element) {
        return elements.get(element);
    }

    public MetaCard getDealtCard(String elementName, String cardName) {
        return null;
    }

    public MetaCard[] getDealtCards(Element element) {
        List<MetaCard> metaCards = new ArrayList<MetaCard>();
        for (MetaCard metaCard : dealtCards) if (metaCard.getElement() == element) metaCards.add(metaCard);
        return metaCards.toArray(new MetaCard[metaCards.size()]);
    }

    public Card[] getEquippedCards() {
        return equippedCards.toArray(new Card[equippedCards.size()]);
    }

    public Card getEquippedCard(Integer pos) {
        for (Card card : equippedCards) if (card.getPosition() == pos) return card;
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

    protected boolean addDealtCard(MetaCard metaCard) {
        return dealtCards.add(metaCard);
    }

    protected boolean removeDealtCard(MetaCard card) {
        return dealtCards.remove(card);
    }

    protected void setCardPlayed(boolean cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    protected boolean addEquippedCard(Card card) {
        setCardPlayed(true);
        return equippedCards.add(card);
    }

    protected boolean removeEquippedCard(Card card) {
        return equippedCards.remove(card);
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    protected void setElementResource(Element element, int resource) {
        elements.put(element, resource);
    }

    protected void setProperty(String name, Object data) {
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

    public Object getProperty(String name) {
        return properties.get(name);
    }
}

package com.killard.board.card;

public interface Player<T extends Player> extends Record<T> {

    public String getId();

    public String getNickname();

    public int getNumber();

    public Role getRole();

    public int getHealth();

    public int getElementResource(Element element);

    public MetaCard[] getDealtCards(Element element);

    public MetaCard getDealtCard(String elementName, String cardName);

    public Card[] getEquippedCards();

    public Card getEquippedCard(Integer position);

    public boolean isCardPlayed();

    public boolean isAlive();

    public boolean isCalled();

    public boolean isWinner();

    public boolean isLoser();

    public int getTurnCount();
}

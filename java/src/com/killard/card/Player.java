package com.killard.card;

public interface Player {

    public String getId();

    public Role getRole();

    public int getHealth();

    public int getElementAmount(ElementSchool elementSchool);

    public Card[] getHoldedCards(ElementSchool elementSchool);

    public Card getHoldedCard(Integer cardIndex);

    public CardInstance[] getLivingCards();

    public CardInstance getLivingCard(Integer position);

    public boolean isCardPlayed();

    public int getTurnCount();
}

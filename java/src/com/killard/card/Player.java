package com.killard.card;

public interface Player {

    public String getName();

    public int getHealth();

    public int getElementAmount(ElementSchool elementSchool);

    public ElementSchool[] getAllElementSchool();

    public Card[] getHoldedCards(ElementSchool elementSchool);

    public Card getHoldedCard(Integer cardIndex);

    public CardInstance[] getLivingCards();

    public CardInstance getLivingCard(Integer position);

    public boolean isCardPlayed();
}

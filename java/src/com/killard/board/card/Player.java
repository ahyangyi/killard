package com.killard.board.card;

public interface Player {

    public String getId();

    public Role getRole();

    public int getHealth();

    public int getElementAmount(ElementSchool elementSchool);

    public MetaCard[] getDealtCards(ElementSchool elementSchool);

    public MetaCard getDealtCard(Integer cardIndex);

    public CardInstance[] getEquippedCards();

    public CardInstance getEquippedCard(Integer position);

    public boolean isCardPlayed();

    public boolean isAlive();

    public boolean isCalled();

    public boolean isWinner();

    public boolean isLoser();

    public int getTurnCount();
}

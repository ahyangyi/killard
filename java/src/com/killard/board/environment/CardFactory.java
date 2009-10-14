package com.killard.board.environment;

import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;

import java.util.Map;

public interface CardFactory {

    public ElementSchool[] getAllElementSchool();

    public ElementSchool getElementSchool();

    public void changeElementAmount(int elementAmountChange);

    public int getElementAmount();

    public Map<String, Card> getAvailableCards();
}

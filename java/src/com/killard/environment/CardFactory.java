package com.killard.environment;

import com.killard.card.Card;
import com.killard.card.ElementSchool;

import java.util.Map;

public interface CardFactory {

    public ElementSchool[] getAllElementSchool();

    public ElementSchool getElementSchool();

    public void changeElementAmount(int elementAmountChange);

    public int getElementAmount();

    public Map<String, Card> getAvailableCards();
}

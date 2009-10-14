package com.killard.packages.magic;

import com.killard.card.Card;
import com.killard.card.ElementSchool;

public enum MagicElementSchool implements ElementSchool {

    EARTH,
    FIRE,
    AIR,
    WATER,
    LIFE,
    DEATH;

    public String getName() {
        return name();
    }

    public Card[] getCards() {
        return new Card[0];
    }
}

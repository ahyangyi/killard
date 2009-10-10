package com.killard.pack.magic;

import com.killard.card.Card;
import com.killard.card.ElementSchool;

public enum MagicElementSchool implements ElementSchool {

    EARTH,
    FIRE,
    AIR,
    WATER,
    LIFE,
    DEATH;

    public String getId() {
        return name();
    }

    public Card[] getCards() {
        return new Card[0];
    }
}

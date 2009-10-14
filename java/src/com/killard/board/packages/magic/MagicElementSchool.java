package com.killard.board.packages.magic;

import com.killard.board.card.MetaCard;
import com.killard.board.card.ElementSchool;

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

    public MetaCard[] getCards() {
        return new MetaCard[0];
    }
}

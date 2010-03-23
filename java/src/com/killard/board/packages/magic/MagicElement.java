package com.killard.board.packages.magic;

import com.killard.board.card.Element;
import com.killard.board.card.MetaCard;

public enum MagicElement implements Element {

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

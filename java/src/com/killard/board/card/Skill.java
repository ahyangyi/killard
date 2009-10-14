package com.killard.board.card;

import java.util.List;

public interface Skill {

    public String getName();

    public ElementSchool getElementSchool();

    public int getCost();

    public List<Action> execute(CardInstance owner, CardInstance target);
}

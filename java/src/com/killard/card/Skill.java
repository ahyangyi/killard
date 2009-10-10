package com.killard.card;

import java.util.List;

public interface Skill {

    public String getId();

    public ElementSchool getElementSchool();

    public int getCost();

    public List<Action> execute(CardInstance owner, CardInstance target);
}

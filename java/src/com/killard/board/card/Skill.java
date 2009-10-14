package com.killard.board.card;

import java.util.List;

public interface Skill {

    public String getName();

    public SkillTarget[] getTargets();

    public int getCost();

    public List<Action> execute(Card owner, Object... target);
}

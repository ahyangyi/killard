package com.killard.board.card;

import java.util.List;

public interface Skill<T extends Skill> extends Record<T> {

    public String getName();

    public SkillTarget[] getTargets();

    public int getCost();

    public List<Action> execute(Board board, Card owner, Object... targets);
}

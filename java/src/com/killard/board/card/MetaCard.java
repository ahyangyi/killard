package com.killard.board.card;

public interface MetaCard<T extends MetaCard> extends Record<T> {

    public String getName();

    public Element getElement();

    public int getLevel();

    public int getMaxHealth();

    public Attack getAttack();

    public boolean isEquippable();

    public boolean hasSkill();

    public Skill[] getSkills();

    public boolean hasAttribute();

    public Attribute[] getAttributes();

    public boolean hasVisibleAttribute();

    public Attribute[] getVisibleAttributes();
    
    public boolean hasHiddenAttribute();

    public Attribute[] getHiddenAttributes();

}

package com.killard.card;

public interface Card {

    public String getId();

    public ElementSchool getElementSchool();

    public int getLevel();

    public int getHealth();

    public int getMaxHealth();

    public Attack getAttack();

    public boolean hasSkill();

    public Skill[] getSkills();

    public boolean hasAttribute();

    public Attribute[] getAttributes();

    public boolean hasVisibleAttribute();

    public Attribute[] getVisibleAttributes();
    
    public boolean hasHiddenAttribute();

    public Attribute[] getHiddenAttributes();

}

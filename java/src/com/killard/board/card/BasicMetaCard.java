package com.killard.board.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * This class defines base for cards.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class BasicMetaCard<T extends BasicMetaCard> implements MetaCard<T> {

    private final Element element;

    private final int level;

    private final int maxHealth;

    private final Attack attack;

    private final boolean equippable;

    private final List<Skill> skills = new ArrayList<Skill>();

    private final List<Attribute> visibleAttributes = new ArrayList<Attribute>();

    private final List<Attribute> hiddenAttributes = new ArrayList<Attribute>();

    protected BasicMetaCard(Element element, int level, int maxHealth, int attack) {
        this.element = element;
        this.level = level;
        this.maxHealth = maxHealth;
        this.attack = new Attack(element, AttackType.PHYSICAL, attack);
        this.equippable = true;
    }

    protected BasicMetaCard(Element element, int level, Skill skill) {
        this(element, level, 0, 0, 0, skill);
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int attack, Skill skill) {
        this(element, level, maxHealth, maxHealth, attack, skill);
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int health, int attack, Skill skill) {
        this.element = element;
        this.level = level;
        this.maxHealth = maxHealth;
        this.attack = new Attack(element, AttackType.PHYSICAL, attack);
        this.skills.add(skill);
        this.equippable = true;
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int health, int attack, Skill[] skill) {
        this.element = element;
        this.level = level;
        this.maxHealth = maxHealth;
        this.attack = new Attack(element, AttackType.PHYSICAL, attack);
        this.skills.addAll(Arrays.asList(skill));
        this.equippable = true;
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int attack,
                        List<Attribute> attributes) {
        this(element, level, maxHealth, maxHealth, attack, new Skill[0], attributes);
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int attack, Skill skill,
                        List<Attribute> attributes) {
        this(element, level, maxHealth, maxHealth, attack, skill, attributes);
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int attack, Skill[] skills,
                        List<Attribute> attributes) {
        this(element, level, maxHealth, maxHealth, attack, skills, attributes);
    }
    
    protected BasicMetaCard(Element element, int level, int maxHealth, int health, int attack, Skill skill,
                        List<Attribute> attributes) {
        this(element, level, maxHealth, health, attack, skill);
        this.visibleAttributes.addAll(attributes);
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int health, int attack, Skill[] skills,
                        List<Attribute> attributes) {
        this(element, level, maxHealth, health, attack, skills);
        this.visibleAttributes.addAll(attributes);
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int health, int attack,
                        List<Attribute> attributes) {
        this(element, level, maxHealth, health, attack, new Skill[0], attributes);
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int attack,
                        Attribute[] attributes) {
        this(element, level, maxHealth, maxHealth, attack, new Skill[0], attributes);
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int attack, Skill skill,
                        Attribute[] attributes) {
        this(element, level, maxHealth, maxHealth, attack, skill, attributes);
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int attack, Skill[] skills,
                        Attribute[] attributes) {
        this(element, level, maxHealth, maxHealth, attack, skills, attributes);
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int health, int attack, Skill skill,
                        Attribute[] attributes) {
        this(element, level, maxHealth, health, attack, skill, Arrays.asList(attributes));
    }

    protected BasicMetaCard(Element element, int level, int maxHealth, int health, int attack, Skill[] skills,
                        Attribute[] attributes) {
        this(element, level, maxHealth, health, attack, skills, Arrays.asList(attributes));
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public Element getElement() {
        return element;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Attack getAttack() {
        return attack;
    }

    public boolean isEquippable() {
        return equippable;
    }

    public boolean hasSkill() {
        return getSkills().length > 0;
    }

    public Skill[] getSkills() {
        return skills.toArray(new Skill[skills.size()]);
    }

    public boolean hasAttribute() {
        return hasVisibleAttribute() || hasHiddenAttribute();
    }

    public Attribute[] getAttributes() {
        Attribute[] hidden = getHiddenAttributes();
        Attribute[] visible = getVisibleAttributes();
        Attribute[] attributes = new Attribute[hidden.length + visible.length];
        System.arraycopy(hidden, 0, attributes, 0, hidden.length);
        System.arraycopy(visible, 0, attributes, hidden.length, visible.length);
        return attributes;
    }

    public boolean hasVisibleAttribute() {
        return getVisibleAttributes().length > 0;
    }

    public Attribute[] getVisibleAttributes() {
        return visibleAttributes.toArray(new Attribute[visibleAttributes.size()]);
    }

    public boolean hasHiddenAttribute() {
        return getHiddenAttributes().length > 0;
    }

    public Attribute[] getHiddenAttributes() {
        return hiddenAttributes.toArray(new Attribute[hiddenAttributes.size()]);
    }

    protected boolean addAttribute(Attribute attribute) {
        return visibleAttributes.add(attribute);
    }
}

package com.killard.environment.record;

import com.killard.card.Attack;
import com.killard.card.AttackType;
import com.killard.card.Attribute;
import com.killard.card.Card;
import com.killard.card.ElementSchool;
import com.killard.card.Player;
import com.killard.card.Skill;
import com.killard.environment.event.StateListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * This class represents an instance for an underlying card.
 * It delegates operations to the underlying card, while maintaining properties like hp and attack by itself.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class CardRecord extends AbstractCardRecord {

    private String name;

    private ElementSchool elementSchool;

    private int level;

    private int health;

    private int maxHealth;

    private Attack attack;

    private boolean equippable;

    private Player owner;

    private Player target;

    private int position;

    private List<Skill> skills = new ArrayList<Skill>();

    private final List<Attribute> visibleAttributes = new ArrayList<Attribute>();

    private final List<Attribute> hiddenAttributes = new ArrayList<Attribute>();

    private boolean casted;

    public CardRecord(ElementSchool elementSchool, int level, int maxHealth, int health, int attack, Skill skill) {
        this.name = getClass().getSimpleName();
        this.elementSchool = elementSchool;
        this.level = level;
        this.maxHealth = maxHealth;
        this.health = health;
        this.attack = new Attack(elementSchool, AttackType.PHYSICAL, attack);
        this.equippable = true;
        this.skills.add(skill);
    }

    public CardRecord(Card card, Player owner, Player target, int position) {
        this.name = card.getName();
        this.elementSchool = card.getElementSchool();
        this.level = card.getLevel();
        this.health = card.getMaxHealth();
        this.maxHealth = card.getMaxHealth();
        this.attack = card.getAttack();
        this.equippable = true;
        this.owner = owner;
        this.target = target;
        this.position = position;
        this.skills.addAll(Arrays.asList(card.getSkills()));
        visibleAttributes.addAll(Arrays.asList(card.getVisibleAttributes()));
        hiddenAttributes.addAll(Arrays.asList(card.getHiddenAttributes()));
        this.casted = false;
    }

    public CardRecord(Card card, StateListener listener, Player owner, Player target, int position) {
        this(card, owner, target, position);
        addStateListener(listener);
    }

    public String getName() {
        return name;
    }

    public ElementSchool getElementSchool() {
        return elementSchool;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
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

    public Player getOwner() {
        return owner;
    }

    public Player getTarget() {
        return target;
    }

    public int getPosition() {
        return position;
    }

    public Skill[] getSkills() {
        return skills.toArray(new Skill[skills.size()]);
    }

    public Attribute[] getVisibleAttributes() {
        return visibleAttributes.toArray(new Attribute[visibleAttributes.size()]);
    }

    public Attribute[] getHiddenAttributes() {
        return hiddenAttributes.toArray(new Attribute[hiddenAttributes.size()]);
    }

    public boolean isCasted() {
        return casted;
    }

    protected boolean addAttribute(Attribute attribute) {
        if (attribute.isVisible()) return visibleAttributes.add(attribute);
        else return hiddenAttributes.add(attribute);
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    protected void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    protected void setAttack(Attack attack) {
        this.attack = attack;
    }

    protected void setOwner(Player owner) {
        this.owner = owner;
    }

    protected void setTarget(Player target) {
        this.target = target;
    }

    protected boolean removeAttribute(Attribute attribute) {
        if (attribute.isVisible()) return visibleAttributes.remove(attribute);
        else return hiddenAttributes.remove(attribute);
    }

    protected void setPosition(int position) {
        this.position = position;
    }

    protected boolean removeSkill(Skill skill) {
        return skills.remove(skill);
    }

    protected void setCasted(boolean casted) {
        this.casted = casted;
    }
}

package com.killard.board.environment.record;

import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.Attribute;
import com.killard.board.card.Element;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Player;
import com.killard.board.card.Skill;
import com.killard.board.card.record.AbstractCardRecord;
import com.killard.board.environment.event.StateListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CardRecord extends AbstractCardRecord<CardRecord> {

    private String name;

    private Element element;

    private int level;

    private int health;

    private int maxHealth;

    private Attack attack;

    private int range;

    private boolean equippable;

    private Player owner;

    private Player target;

    private int position;

    private List<Skill> skills = new ArrayList<Skill>();

    private final List<Attribute> visibleAttributes = new ArrayList<Attribute>();

    private final List<Attribute> hiddenAttributes = new ArrayList<Attribute>();

    private boolean casted;

    private final Map<String, Object> properties = new HashMap<String, Object>();

    public CardRecord(Element element, int level, int maxHealth, int health, int attack, Skill skill) {
        this.name = getClass().getSimpleName();
        this.element = element;
        this.level = level;
        this.maxHealth = maxHealth;
        this.health = health;
        this.attack = new Attack(element, AttackType.PHYSICAL, attack);
        this.equippable = true;
        this.skills.add(skill);
    }

    public CardRecord(MetaCard metaCard, Player owner, Player target, int position) {
        this.name = metaCard.getName();
        this.element = metaCard.getElement();
        this.level = metaCard.getLevel();
        this.health = metaCard.getMaxHealth();
        this.maxHealth = metaCard.getMaxHealth();
        this.attack = metaCard.getAttack();
        this.equippable = true;
        this.owner = owner;
        this.target = target;
        this.position = position;
        this.skills.addAll(Arrays.asList(metaCard.getSkills()));
        visibleAttributes.addAll(Arrays.asList(metaCard.getVisibleAttributes()));
        hiddenAttributes.addAll(Arrays.asList(metaCard.getHiddenAttributes()));
        this.casted = false;
    }

    public CardRecord(MetaCard metaCard, StateListener listener, Player owner, Player target, int position) {
        this(metaCard, owner, target, position);
        addStateListener(listener);
    }

    public String getName() {
        return name;
    }

    public Element getElement() {
        return element;
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

    public int getRange() {
        return range;
    }

    public boolean isEquippable() {
        return equippable;
    }

    public Player getOwner() {
        return owner;
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

    protected void setRange(int range) {
        this.range = range;
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

    protected void setProperty(String name, Object data) {
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

    public Object getProperty(String name) {
        return properties.get(name);
    }
}

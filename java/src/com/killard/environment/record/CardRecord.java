package com.killard.environment.record;

import com.killard.card.Action;
import com.killard.card.Attack;
import com.killard.card.Attribute;
import com.killard.card.BasicCard;
import com.killard.card.Card;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.Player;
import com.killard.card.Skill;
import com.killard.card.action.ChangeCardHealthAction;
import com.killard.card.action.ChangePlayerHealthAction;
import com.killard.environment.Record;
import com.killard.environment.event.StateEvent;
import com.killard.environment.event.StateListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class CardRecord extends BasicCard implements CardInstance, Record {

    private int health;

    private int maxHealth;

    private Attack attack;

    private Player owner;

    private Player target;

    private int position;

    private List<Skill> skills = new ArrayList<Skill>();

    private final List<Attribute> attributes = new ArrayList<Attribute>();

    private boolean casted;

    private final Set<StateListener> stateListeners = new HashSet<StateListener>();

    public CardRecord(ElementSchool elementSchool, int level, int maxHealth, int health, int attack, Skill skill) {
        super(elementSchool, level, maxHealth, health, attack, skill);
    }

    public CardRecord(Card card, Player owner, Player target, int position) {
        super(card.getElementSchool(), card.getLevel(), card.getMaxHealth(), card.getHealth(),
                card.getAttack().getValue(), card.getSkills(), card.getVisibleAttributes());
        this.health = card.getHealth();
        this.maxHealth = card.getMaxHealth();
        this.attack = card.getAttack();
        this.owner = owner;
        this.target = target;
        this.position = position;
        this.skills.addAll(Arrays.asList(card.getSkills()));
        attributes.addAll(Arrays.asList(card.getVisibleAttributes()));
        this.casted = false;
    }

    public CardRecord(Card card, StateListener listener, Player owner,
                      Player target, int position) {
        this(card, owner, target, position);
        stateListeners.add(listener);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public Attack getAttack() {
        return attack;
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

    @Override
    public Skill[] getSkills() {
        return skills.toArray(new Skill[skills.size()]);
    }

    @Override
    public Attribute[] getVisibleAttributes() {
        return attributes.toArray(new Attribute[attributes.size()]);
    }

    public boolean isCasted() {
        return casted;
    }

    public List<Action> cast(Integer skill, CardInstance target) {
        if (getSkills().length > skill)
            return getSkills()[skill].execute(this, target);
        else return new ArrayList<Action>();
    }

    public List<Action> attack() {
        List<Action> actions = new ArrayList<Action>();
        if (getTarget() != null && getTarget() != getOwner()) {
            if (getTarget().getLivingCard(getPosition()) != null) {
                actions.add(new ChangeCardHealthAction(this, getTarget().getLivingCard(getPosition()), getAttack()));
            } else {
                actions.add(new ChangePlayerHealthAction(this, getTarget(), getAttack()));
            }
        }
        return actions;
    }

    @Override
    protected boolean addAttribute(Attribute attribute) {
        return attributes.add(attribute);
    }

    public boolean addStateListener(StateListener listener) {
        return stateListeners.add(listener);
    }

    public boolean removeStateListener(StateListener listener) {
        return stateListeners.remove(listener);
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
        return attributes.remove(attribute);
    }

    protected void setPosition(int position) {
        this.position = position;
    }

    protected boolean removeSkill(Skill skill) {
        return skills.remove(skill);
    }

    protected void addAttribute(Attribute attribute, Action action) {
        addAttribute(attribute);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void removeAttribute(Attribute attribute, Action action) {
        removeAttribute(attribute);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void setCasted(boolean casted) {
        this.casted = casted;
    }

    protected void changeHealth(Attack healthChange, Action action) {
        setHealth(getHealth() - healthChange.getValue());
        if (getHealth() < 0) setHealth(0);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeMaxHealth(Attack maxHealthChange, Action action) {
        setMaxHealth(getMaxHealth() + maxHealthChange.getValue());
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeAttack(Attack attackChange, Action action) {
        setAttack(new Attack(getAttack().getElementSchool(), getAttack().getType(),
                getAttack().getValue() + attackChange.getValue()));
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeSkill(Skill skill, Action action) {
        removeSkill(skill);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void changeCasted(boolean casted, Action action) {
        setCasted(casted);
        fireStateChanged(new StateEvent(this, action));
    }

    protected void fireStateChanged(StateEvent event) {
        for (StateListener listener : stateListeners) listener.stateChanged(event);
    }
}

package com.killard.board.jdo.board.record;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.Attribute;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.Player;
import com.killard.board.card.Skill;
import com.killard.board.card.record.AbstractCardRecord;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.SkillDO;
import com.killard.board.jdo.board.property.MetaCardPropertyDO;
import com.killard.board.jdo.board.record.property.CardRecordPropertyDO;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CardRecordDO extends AbstractCardRecord<CardRecordDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key cardKey;

    @Persistent
    private Key ownerKey;

    @Persistent
    private int level;

    @Persistent
    private int health;

    @Persistent
    private int maxHealth;

    @Persistent
    private String attackType;

    @Persistent
    private int attackValue;

    @Persistent
    private boolean equippable;

    @Persistent
    private boolean visible;

    @Persistent
    private int position;

    @Persistent
    private List<Key> skillKeys;

    @Persistent
    private List<Key> attributeKeys;

    @Persistent
    private boolean casted;

    @Persistent(defaultFetchGroup = "false")
    @Element(dependent = "true")
    private Set<CardRecordPropertyDO> properties;

    @NotPersistent
    private BoardDO board;

    @NotPersistent
    private PlayerRecordDO owner;

    public CardRecordDO() {
    }

    public CardRecordDO(MetaCardDO card, BoardDO board, PlayerRecordDO owner, PlayerRecordDO target,
                        int position) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(owner.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), position);
        this.key = keyBuilder.getKey();

        this.cardKey = card.getKey();

        this.board = board;
        setOwner(owner);

        this.level = card.getLevel();
        this.maxHealth = card.getMaxHealth();
        this.health = card.getMaxHealth();
        this.attackType = card.getAttackType();
        this.attackValue = card.getAttackValue();
        this.equippable = card.isEquippable();
        this.visible = card.isVisible();
        this.position = position;

        this.skillKeys = new LinkedList<Key>();
        for (Skill skill : card.getSkills()) addSkill(skill);

        this.attributeKeys = new LinkedList<Key>();
        for (Attribute attribute : card.getAttributes()) addAttribute(attribute);

        this.casted = false;
        this.properties = new TreeSet<CardRecordPropertyDO>();
        for (MetaCardPropertyDO property : card.getProperties()) {
            this.properties.add(new CardRecordPropertyDO(key, property));
        }

        this.addStateListener(board);
    }

    public void restore(BoardDO board, PlayerRecordDO player) {
        this.board = board;
        this.owner = player;
        for (Key key : attributeKeys) board.addActionListener(board.getAttributeDO(key), getCard());
        addStateListener(board);
    }

    public Key getKey() {
        return key;
    }

    public MetaCardDO getCard() {
        return board.getMetaCardDO(cardKey);
    }

    public String getName() {
        return getCard().getName();
    }

    public CardRecordPropertyDO[] getProperties() {
        return properties.toArray(new CardRecordPropertyDO[properties.size()]);
    }

    public Object getProperty(String name) {
        for (CardRecordPropertyDO property : getProperties()) {
            if (property.getName().equals(name)) return property.getData();
        }
        return null;
    }

    protected void setProperty(String name, Object data) {
        for (CardRecordPropertyDO property : getProperties()) {
            if (property.getName().equals(name)) {
                property.setData(data.toString());
                return;
            }
        }
        properties.add(new CardRecordPropertyDO(key, name, data.toString()));
    }

    public ElementSchool getElementSchool() {
        return getCard().getElementSchool();
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
        return new Attack(getElementSchool(), AttackType.valueOf(attackType), attackValue);
    }

    public boolean isEquippable() {
        return equippable;
    }

    public boolean isVisible() {
        return visible;
    }

    public Skill[] getSkills() {
        Skill[] skills = new Skill[skillKeys.size()];
        for (int i = 0; i < skills.length; i++) skills[i] = board.getSkillDO(skillKeys.get(i));
        return skills;
    }

    public Attribute[] getHiddenAttributes() {
        List<Attribute> attributes = new LinkedList<Attribute>();
        for (Key key : attributeKeys) {
            AttributeDO attribute = board.getAttributeDO(key);
            if (!attribute.isVisible()) attributes.add(attribute);
        }
        return attributes.toArray(new Attribute[attributes.size()]);
    }

    public Attribute[] getVisibleAttributes() {
        List<Attribute> attributes = new LinkedList<Attribute>();
        for (Key key : attributeKeys) {
            AttributeDO attribute = board.getAttributeDO(key);
            if (attribute.isVisible()) attributes.add(attribute);
        }
        return attributes.toArray(new Attribute[attributes.size()]);
    }

    public Player getOwner() {
        return owner;
    }

    public int getPosition() {
        return position;
    }

    public boolean isCasted() {
        return casted;
    }

    protected void setCasted(boolean casted) {
        this.casted = casted;
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    protected void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    protected void setAttack(Attack attack) {
        this.attackType = attack.getType().name();
        this.attackValue = attack.getValue();
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    protected void setOwner(Player owner) {
        this.owner = (PlayerRecordDO) owner;
        this.ownerKey = this.owner.getKey();
    }

    protected void setPosition(int position) {
        this.position = position;
    }

    protected boolean addSkill(Skill skill) {
        SkillDO record = (SkillDO) skill;
        return !skillKeys.contains(record.getKey()) && skillKeys.add(record.getKey());
    }

    protected boolean removeSkill(Skill skill) {
        SkillDO record = (SkillDO) skill;
        return skillKeys.contains(record.getKey()) && skillKeys.remove(record.getKey());
    }

    protected boolean addAttribute(Attribute attribute) {
        return attributeKeys.add(((AttributeDO)attribute).getKey());
    }

    protected boolean removeAttribute(Attribute attribute) {
        return attributeKeys.remove(((AttributeDO)attribute).getKey());
    }

    public int compareTo(CardRecordDO record) {
        return key.compareTo(record.key);
    }
}

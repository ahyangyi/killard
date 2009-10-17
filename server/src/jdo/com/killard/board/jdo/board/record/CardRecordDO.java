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
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.BoardDO;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.SkillDO;
import com.killard.board.jdo.board.record.property.CardRecordPropertyDO;
import com.killard.board.jdo.board.property.MetaCardPropertyDO;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
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
public class CardRecordDO extends AbstractCardRecord {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key cardKey;

    @Persistent
    private Key ownerKey;

    @Persistent
    private Key targetKey;

    @Persistent
    private Integer level;

    @Persistent
    private Integer health;

    @Persistent
    private Integer maxHealth;

    @Persistent
    private String attackType;

    @Persistent
    private Integer attackValue;

    @Persistent
    private Boolean equippable;

    @Persistent
    private Boolean visible;

    @Persistent
    private Integer position;

    @Persistent(serialized = "true")
    private List<Key> skillKeys;

    @Persistent(serialized = "true")
    private List<Key> attributeKeys;

    @Persistent
    private Boolean casted;

    @Persistent
    private SortedSet<CardRecordPropertyDO> properties;

    @NotPersistent
    private MetaCardDO card;

    @NotPersistent
    private PlayerRecordDO owner;

    @NotPersistent
    private PlayerRecordDO target;

    @NotPersistent
    private List<Skill> skills;

    @NotPersistent
    private List<Attribute> visibleAttributes;

    @NotPersistent
    private List<Attribute> hiddenAttributes;

    public CardRecordDO() {
    }

    public CardRecordDO(MetaCardDO card, BoardDO board, PlayerRecordDO owner, PlayerRecordDO target,
                        int position) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(owner.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), position);
        this.key = keyBuilder.getKey();

        this.card = card;
        this.cardKey = card.getKey();

        setOwner(owner);
        setTarget(target);

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

    public void restore(BoardDO board) {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        this.card = pm.getObjectById(MetaCardDO.class, cardKey);
        this.owner = pm.getObjectById(PlayerRecordDO.class, ownerKey);
        this.target = pm.getObjectById(PlayerRecordDO.class, targetKey);
        for (Key key : skillKeys) {
            addSkill(pm.getObjectById(SkillDO.class, key));
        }
        for (Key key : attributeKeys) {
            AttributeDO attribute = pm.getObjectById(AttributeDO.class, key);
            addAttribute(attribute);
            board.addActionListener(attribute, card);
        }
        addStateListener(board);
    }

    public Key getKey() {
        return key;
    }

    public MetaCardDO getCard() {
        return card;
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
        return skills.toArray(new Skill[skills.size()]);
    }

    public Attribute[] getHiddenAttributes() {
        return hiddenAttributes.toArray(new Attribute[hiddenAttributes.size()]);
    }

    public Attribute[] getVisibleAttributes() {
        return visibleAttributes.toArray(new Attribute[visibleAttributes.size()]);
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

    protected void setTarget(Player target) {
        this.target = (PlayerRecordDO) target;
        this.targetKey = this.target.getKey();
    }

    protected void setPosition(int position) {
        this.position = position;
    }

    protected boolean addSkill(Skill skill) {
        SkillDO record = (SkillDO) skill;
        return !skillKeys.contains(record.getKey()) && skills.add(skill) && skillKeys.add(record.getKey());
    }

    protected boolean removeSkill(Skill skill) {
        SkillDO record = (SkillDO) skill;
        return skillKeys.contains(record.getKey()) && skills.remove(skill) && skillKeys.remove(record.getKey());
    }

    protected boolean addAttribute(Attribute attribute) {
        AttributeDO record = (AttributeDO) attribute;
        if (!attributeKeys.contains(record.getKey())) {
            if (attribute.isVisible()) {
                if (visibleAttributes.add(attribute)) return attributeKeys.add(record.getKey());
            } else {
                if (hiddenAttributes.add(attribute)) return attributeKeys.add(record.getKey());
            }
        }
        return false;
    }

    protected boolean removeAttribute(Attribute attribute) {
        AttributeDO record = (AttributeDO) attribute;
        if (attributeKeys.contains(record.getKey())) {
            if (attribute.isVisible()) {
                if (visibleAttributes.remove(attribute)) return attributeKeys.remove(record.getKey());
            } else {
                if (hiddenAttributes.remove(attribute)) return attributeKeys.remove(record.getKey());
            }
        }
        return false;
    }
}

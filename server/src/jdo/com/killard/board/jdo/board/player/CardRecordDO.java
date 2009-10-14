package com.killard.board.jdo.board.player;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.Attribute;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.Player;
import com.killard.board.card.Skill;
import com.killard.board.environment.record.AbstractCardRecord;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.BoardAttributeDO;
import com.killard.board.jdo.board.BoardCardDO;
import com.killard.board.jdo.board.BoardElementSchoolDO;
import com.killard.board.jdo.board.BoardManagerDO;
import com.killard.board.jdo.board.BoardSkillDO;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.List;
import java.util.LinkedList;

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
    private Key elementSchoolKey;

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

    @NotPersistent
    private BoardCardDO card;

    @NotPersistent
    private BoardElementSchoolDO elementSchool;

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
        skills = new LinkedList<Skill>();
        hiddenAttributes = new LinkedList<Attribute>();
        visibleAttributes = new LinkedList<Attribute>();
    }

    public CardRecordDO(BoardCardDO card, BoardManagerDO boardManager, PlayerRecordDO owner, PlayerRecordDO target,
                        int position) {
        this();
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(owner.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), position);
        this.key = keyBuilder.getKey();

        this.card = card;
        this.cardKey = card.getKey();

        this.elementSchool = (BoardElementSchoolDO) card.getElementSchool();
        this.elementSchoolKey = this.elementSchool.getKey();

        setOwner(owner);
        setTarget(target);

        this.level = card.getLevel();
        this.maxHealth = card.getMaxHealth();
        this.health = card.getHealth();
        this.attackType = card.getAttack().getType().name();
        this.attackValue = card.getAttack().getValue();
        this.equippable = card.isEquippable();
        this.visible = card.isVisible();
        this.position = position;

        this.skillKeys = new LinkedList<Key>();
        for (Skill skill : card.getSkills()) addSkill(skill);

        this.attributeKeys = new LinkedList<Key>();
        for (Attribute attribute : card.getAttributes()) addAttribute(attribute);

        this.casted = false;

        this.addStateListener(boardManager);
    }

    public void restore(BoardManagerDO boardManager) {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        this.card = pm.getObjectById(BoardCardDO.class, cardKey);
        this.elementSchool = pm.getObjectById(BoardElementSchoolDO.class, elementSchoolKey);
        this.owner = pm.getObjectById(PlayerRecordDO.class, ownerKey);
        this.target = pm.getObjectById(PlayerRecordDO.class, targetKey);
        for (Key key : skillKeys) {
            BoardSkillDO skill = pm.getObjectById(BoardSkillDO.class, key);
            skill.restore(boardManager);
            addSkill(skill);
        }
        for (Key key : attributeKeys) {
            BoardAttributeDO attribute = pm.getObjectById(BoardAttributeDO.class, key);
            attribute.restore(boardManager);
            addAttribute(attribute);
            boardManager.addActionListener(attribute, card);
        }
        addStateListener(boardManager);
    }

    public Key getKey() {
        return key;
    }

    public BoardCardDO getCard() {
        return card;
    }

    public String getName() {
        return getCard().getName();
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
        BoardSkillDO record = (BoardSkillDO) skill;
        return !skillKeys.contains(record.getKey()) && skills.add(skill) && skillKeys.add(record.getKey());
    }

    protected boolean removeSkill(Skill skill) {
        BoardSkillDO record = (BoardSkillDO) skill;
        return skillKeys.contains(record.getKey()) && skills.remove(skill) && skillKeys.remove(record.getKey());
    }

    protected boolean addAttribute(Attribute attribute) {
        BoardAttributeDO record = (BoardAttributeDO) attribute;
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
        BoardAttributeDO record = (BoardAttributeDO) attribute;
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

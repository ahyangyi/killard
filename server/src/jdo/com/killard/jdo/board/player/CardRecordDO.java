package com.killard.jdo.board.player;

import com.google.appengine.api.datastore.Key;
import com.killard.card.Attack;
import com.killard.card.AttackType;
import com.killard.card.Attribute;
import com.killard.card.ElementSchool;
import com.killard.card.Player;
import com.killard.card.Skill;
import com.killard.environment.record.CardRecord;
import com.killard.jdo.PersistenceHelper;
import com.killard.jdo.board.BoardAttributeDO;
import com.killard.jdo.board.BoardCardDO;
import com.killard.jdo.board.BoardElementSchoolDO;
import com.killard.jdo.board.BoardManagerDO;
import com.killard.jdo.board.BoardSkillDO;
import com.killard.jdo.card.SkillDO;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class CardRecordDO extends CardRecord {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key ownerKey;

    @Persistent
    private Key cardKey;

    @Persistent
    private Key elementSchoolKey;

    @Persistent
    private Key targetPlayerKey;

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
    private List<Key> skillKeys;

    @Persistent
    private Integer position;

    @Persistent
    private List<Key> attributeKeys;

    @Persistent
    private Boolean casted;

    @NotPersistent
    private List<Skill> skills;

    @NotPersistent
    private List<Attribute> visibleAttributes;

    @NotPersistent
    private List<Attribute> hiddenAttributes;

    public CardRecordDO() {
        super(null, 0, 0, 0, 0, null);
    }

    public CardRecordDO(BoardCardDO card, BoardManagerDO boardManager, PlayerRecordDO owner, PlayerRecordDO target, int position) {
        super(card, boardManager, owner, target, position);
        this.ownerKey = owner.getKey();
        this.cardKey = card.getKey();
        this.elementSchoolKey = ((BoardElementSchoolDO)card.getElementSchool()).getKey();
        this.targetPlayerKey = target.getKey();
        this.level = card.getLevel();
        this.maxHealth = card.getMaxHealth();
        this.health = card.getHealth();
        this.attackType = card.getAttack().getType().name();
        this.attackValue = card.getAttack().getValue();
        this.position = position;

        this.skills = Arrays.asList(card.getSkills());
        this.skillKeys = new ArrayList<Key>(skills.size());
        for (Skill skill : this.skills) this.skillKeys.add(((BoardSkillDO)skill).getKey());

        Attribute[] hidden = card.getHiddenAttributes();
        Attribute[] visible = card.getVisibleAttributes();
        this.hiddenAttributes = new ArrayList<Attribute>(hidden.length);
        this.visibleAttributes = new ArrayList<Attribute>(visible.length);
        this.attributeKeys = new ArrayList<Key>(hidden.length + visible.length);
        for (Attribute attribute : card.getHiddenAttributes()) {
            this.hiddenAttributes.add(attribute);
            this.attributeKeys.add(((BoardAttributeDO)attribute).getKey());
        }
        for (Attribute attribute : visible) {
            this.visibleAttributes.add(attribute);
            this.attributeKeys.add(((BoardAttributeDO)attribute).getKey());
        }

        this.casted = false;
    }

    public void restore(BoardManagerDO boardManager) {

        if (skillKeys == null) skillKeys = new ArrayList<Key>();
        if (attributeKeys == null) attributeKeys = new ArrayList<Key>();
        if (skills == null) skills = new ArrayList<Skill>();
        if (hiddenAttributes == null) hiddenAttributes = new ArrayList<Attribute>();
        if (visibleAttributes == null) visibleAttributes = new ArrayList<Attribute>();

        for (Player player : boardManager.getPlayers()) {
            PlayerRecordDO record = (PlayerRecordDO) player;
            if (record.getKey().equals(ownerKey)) setOwner(record);
            if (record.getKey().equals(targetPlayerKey)) setTarget(record);
        }
        for (Key key : skillKeys) {
            addBoardSkill(PersistenceHelper.getPersistenceManager().getObjectById(BoardSkillDO.class, key));
        }
        for (Key key : attributeKeys) {
            addBoardAttribute(PersistenceHelper.getPersistenceManager().getObjectById(BoardAttributeDO.class, key));
        }
        addStateListener(boardManager);
    }

    public Key getKey() {
        return key;
    }

    public BoardCardDO getCard() {
        return PersistenceHelper.getPersistenceManager().getObjectById(BoardCardDO.class, cardKey);
    }

    @Override
    public String getId() {
        return getCard().getId();
    }

    @Override
    public ElementSchool getElementSchool() {
        return PersistenceHelper.getPersistenceManager().getObjectById(BoardElementSchoolDO.class, elementSchoolKey);
    }

    @Override
    public int getLevel() {
        return level;
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
        return new Attack(getElementSchool(), AttackType.valueOf(attackType), attackValue);
    }

    @Override
    public Skill[] getSkills() {
        return skills.toArray(new Skill[skills.size()]);
    }

    @Override
    public Attribute[] getHiddenAttributes() {
        return hiddenAttributes.toArray(new Attribute[hiddenAttributes.size()]);
    }

    @Override
    public Attribute[] getVisibleAttributes() {
        return visibleAttributes.toArray(new Attribute[visibleAttributes.size()]);
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public boolean isCasted() {
        return casted;
    }

    @Override
    protected void setCasted(boolean casted) {
        this.casted = casted;
    }

    @Override
    protected void setHealth(int health) {
        System.out.println("set health: " + health + " " + getId());
        this.health = health;
    }

    @Override
    protected void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    protected void setAttack(Attack attack) {
        this.attackType = attack.getType().name();
        this.attackValue = attack.getValue();
    }

    @Override
    protected void setOwner(Player owner) {
        this.ownerKey = ((PlayerRecordDO)owner).getKey();
        super.setOwner(owner);
    }

    @Override
    protected void setTarget(Player target) {
        this.targetPlayerKey = ((PlayerRecordDO)target).getKey();
        super.setTarget(target);
    }

    @Override
    protected void setPosition(int position) {
        this.position = position;
    }

    @Override
    protected boolean removeSkill(Skill skill) {
        SkillDO record = (SkillDO) skill;
        removeBoardSkill(skill);
        return skillKeys.remove(record.getKey());
    }

    @Override
    protected boolean addAttribute(Attribute attribute) {
        BoardAttributeDO record = (BoardAttributeDO)attribute;
        addBoardAttribute(attribute);
        return attributeKeys.add(record.getKey());
    }

    @Override
    protected boolean removeAttribute(Attribute attribute) {
        BoardAttributeDO record = (BoardAttributeDO)attribute;
        removeBoardAttribute(attribute);
        return attributeKeys.remove(record.getKey());
    }

    protected boolean addBoardSkill(Skill skill) {
        return skills.add(skill);
    }

    protected boolean removeBoardSkill(Skill skill) {
        return skills.remove(skill);
    }

    protected boolean addBoardAttribute(Attribute attribute) {
        if (attribute.isHidden()) {
            return hiddenAttributes.add(attribute);
        } else {
            return visibleAttributes.add(attribute);
        }
    }

    protected boolean removeBoardAttribute(Attribute attribute) {
        if (attribute.isHidden()) {
            return hiddenAttributes.remove(attribute);
        } else {
            return visibleAttributes.remove(attribute);
        }
    }
}

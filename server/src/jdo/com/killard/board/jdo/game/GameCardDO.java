package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.Attribute;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Skill;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.SkillDO;
import com.killard.board.jdo.board.descriptor.MetaCardDescriptorDO;
import com.killard.board.jdo.game.descriptor.GameCardDescriptorDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
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
public class GameCardDO extends DescriptableDO<GameCardDO, GameCardDescriptorDO>
        implements MetaCard<GameCardDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private GameElementSchoolDO elementSchool;

    @Persistent
    private String name;

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

    @Persistent(mappedBy = "board", defaultFetchGroup = "false")
    private SortedSet<GameSkillDO> skills;

    @Persistent(serialized = "true")
    private SortedSet<String> visibleAttributes;

    @Persistent(serialized = "true")
    private SortedSet<String> hiddenAttributes;

    @Persistent
    private SortedSet<GameCardDescriptorDO> descriptors;

    public GameCardDO(GameElementSchoolDO elementSchool, MetaCardDO card) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(elementSchool.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), card.getKey().getId());
        this.key = keyBuilder.getKey();

        this.elementSchool = elementSchool;
        this.name = card.getName();
        this.level = card.getLevel();
        this.maxHealth = card.getMaxHealth();
        this.health = card.getMaxHealth();
        this.attackType = card.getAttackType();
        this.attackValue = card.getAttackValue();
        this.equippable = card.isEquippable();
        this.visible = card.isVisible();

        this.skills = new TreeSet<GameSkillDO>();
        for (SkillDO skill : card.getSkills()) {
            skills.add(new GameSkillDO(this, skill));
        }
        this.hiddenAttributes = new TreeSet<String>();
        for (AttributeDO attribute : card.getHiddenAttributes()) {
            hiddenAttributes.add(attribute.getName());
        }
        this.visibleAttributes = new TreeSet<String>();
        for (AttributeDO attribute : card.getVisibleAttributes()) {
            visibleAttributes.add(attribute.getName());
        }

        this.descriptors = new TreeSet<GameCardDescriptorDO>();
        for (MetaCardDescriptorDO descriptor : card.getAllDescriptors()) {
            this.descriptors.add(new GameCardDescriptorDO(this, descriptor));
        }
    }

    public Key getKey() {
        return key;
    }

    public ElementSchool getElementSchool() {
        return elementSchool;
    }

    public String getName() {
        return name;
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

    public boolean hasSkill() {
        return !skills.isEmpty();
    }

    public Skill[] getSkills() {
        return skills.toArray(new Skill[skills.size()]);
    }

    public boolean hasAttribute() {
        return hasVisibleAttribute() || hasHiddenAttribute();
    }

    public Attribute[] getAttributes() {
        Attribute[] result = new Attribute[hiddenAttributes.size() + visibleAttributes.size()];
        int i = 0;
        for (String id : hiddenAttributes) {
            result[i] = elementSchool.getAttribute(id);
            i++;
        }

        i = 0;
        for (String id : visibleAttributes) {
            result[i] = elementSchool.getAttribute(id);
            i++;
        }
        return result;
    }

    public boolean hasVisibleAttribute() {
        return !visibleAttributes.isEmpty();
    }

    public Attribute[] getVisibleAttributes() {
        Attribute[] result = new Attribute[visibleAttributes.size()];
        int i = 0;
        for (String id : visibleAttributes) {
            result[i] = elementSchool.getAttribute(id);
            i++;
        }
        return result;
    }

    public boolean hasHiddenAttribute() {
        return !hiddenAttributes.isEmpty();
    }

    public Attribute[] getHiddenAttributes() {
        Attribute[] result = new Attribute[hiddenAttributes.size()];
        int i = 0;
        for (String id : hiddenAttributes) {
            result[i] = elementSchool.getAttribute(id);
            i++;
        }
        return result;
    }

    protected SortedSet<GameCardDescriptorDO> getDescriptors() {
        return descriptors;
    }
}

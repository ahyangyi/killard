package com.killard.web.jdo.card;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.killard.card.Attack;
import com.killard.card.AttackType;
import com.killard.card.Attribute;
import com.killard.card.Card;
import com.killard.card.ElementSchool;
import com.killard.card.Skill;
import com.killard.web.PersistenceHelper;
import com.killard.web.jdo.DescriptableDO;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.ArrayList;
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
public class CardDO extends DescriptableDO<CardDescriptorDO> implements Card {

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key elementSchoolKey;

    @Persistent
    private Key packageKey;

    @Persistent
    private Text definition;

    @Persistent
    private Integer level;

    @Persistent
    private Integer health;

    @Persistent
    private Integer maxHealth;

    @Persistent
    private String attackType = AttackType.PHYSICAL.name();

    @Persistent
    private Integer attackValue;

    @Persistent(defaultFetchGroup = "false")
    private List<SkillDO> skills = new ArrayList<SkillDO>();

    @Persistent(defaultFetchGroup = "false")
    private List<AttributeDO> visibleAttributes = new ArrayList<AttributeDO>();

    @Persistent(defaultFetchGroup = "false")
    private List<AttributeDO> hiddenAttributes = new ArrayList<AttributeDO>();

    @Persistent(defaultFetchGroup = "false")
    private List<AttributeDO> newRecords = new ArrayList<AttributeDO>();

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<CardDescriptorDO> descriptors = new TreeSet<CardDescriptorDO>();

    public CardDO(String id, ElementSchoolDO elementSchool, String definition) {
        super(id);
        this.elementSchoolKey = elementSchool.getKey();
        this.packageKey = elementSchool.getPackageKey();
        this.definition = new Text(definition);
    }

    public Text getDefinition() {
        return definition;
    }

    public Key getElementSchoolKey() {
        return elementSchoolKey;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public ElementSchool getElementSchool() {
        return PersistenceHelper.getPersistenceManager().getObjectById(ElementSchoolDO.class, getElementSchoolKey());
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
        Attribute[] attributes = new Attribute[hiddenAttributes.size() + visibleAttributes.size()];
        for (int i = 0; i < hiddenAttributes.size(); i++)
            attributes[i] = hiddenAttributes.get(i);
        for (int i = 0; i < visibleAttributes.size(); i++)
            attributes[hiddenAttributes.size() + i] = visibleAttributes.get(i);
        return attributes;
    }

    public boolean hasVisibleAttribute() {
        return !visibleAttributes.isEmpty();
    }

    public Attribute[] getVisibleAttributes() {
        return visibleAttributes.toArray(new Attribute[visibleAttributes.size()]);
    }

    public boolean hasHiddenAttribute() {
        return !hiddenAttributes.isEmpty();
    }

    public Attribute[] getHiddenAttributes() {
        return hiddenAttributes.toArray(new Attribute[hiddenAttributes.size()]);
    }

    // Setters

    public void setDefinition(String definition) {
        this.definition = new Text(definition);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType.name();
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public boolean addSkill(SkillDO skill) {
        return skills.add(skill);
    }

    public boolean addAttribute(AttributeDO attribute) {
        if (attribute.isHidden()) return hiddenAttributes.add(attribute);
        else return visibleAttributes.add(attribute);
    }

    public boolean addNewRecord(AttributeDO record) {
        return newRecords.add(record);
    }

    protected SortedSet<CardDescriptorDO> getDescriptors() {
        return descriptors;
    }

    public CardDO clone(ElementSchoolDO elementSchool) {
        CardDO card = new CardDO(getId(), elementSchool, definition.getValue());
        card.setAttackType(AttackType.valueOf(attackType));
        card.setAttackValue(attackValue);
        card.setHealth(health);
        card.setMaxHealth(maxHealth);
        card.setLevel(level);
        for (SkillDO skill : skills) {
            card.addSkill(skill.clone(card));
        }
        for (AttributeDO attribute : visibleAttributes) {
            card.addAttribute(attribute.clone(card));
        }
        for (AttributeDO attribute : hiddenAttributes) {
            card.addAttribute(attribute.clone(card));
        }
        for (AttributeDO attribute : newRecords) {
            card.addAttribute(attribute.clone(card));
        }
        for (CardDescriptorDO descriptor : descriptors) {
            CardDescriptorDO cloneDescriptor = new CardDescriptorDO(card, descriptor.getLocale());
            card.addDescriptor(cloneDescriptor);
        }
        return card;
    }
}

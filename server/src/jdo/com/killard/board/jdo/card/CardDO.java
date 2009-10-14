package com.killard.board.jdo.card;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.killard.board.card.AttackType;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.card.descriptor.CardDescriptorDO;

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
public class CardDO extends DescriptableDO<CardDescriptorDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private ElementSchoolDO elementSchool;

    @Persistent
    private String name;

    @Persistent
    private Key packageKey;

    @Persistent
    private Text definition;

    @Persistent
    private Integer level;

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

    @Persistent(mappedBy = "card", defaultFetchGroup = "false")
    private SortedSet<SkillDO> skills;

    @Persistent(serialized = "true")
    private SortedSet<String> visibleAttributes;

    @Persistent(serialized = "true")
    private SortedSet<String> hiddenAttributes;

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<CardDescriptorDO> descriptors;

    public CardDO(String name, ElementSchoolDO elementSchool, String definition) {
        this.name = name;
        this.elementSchool = elementSchool;
        this.packageKey = elementSchool.getPackageKey();
        this.attackType = AttackType.PHYSICAL.name();
        this.skills = new TreeSet<SkillDO>();
        this.hiddenAttributes = new TreeSet<String>();
        this.visibleAttributes = new TreeSet<String>();
        this.descriptors = new TreeSet<CardDescriptorDO>();
        this.definition = new Text(definition);
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Text getDefinition() {
        return definition;
    }

    public Key getPackageKey() {
        return packageKey;
    }

    public ElementSchoolDO getElementSchool() {
        return elementSchool;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public String getAttackType() {
        return attackType;
    }

    public Integer getAttackValue() {
        return attackValue;
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

    public SkillDO[] getSkills() {
        return skills.toArray(new SkillDO[skills.size()]);
    }

    public boolean hasAttribute() {
        return hasVisibleAttribute() || hasHiddenAttribute();
    }

    public AttributeDO[] getAttributes() {
        AttributeDO[] result = new AttributeDO[hiddenAttributes.size() + visibleAttributes.size()];
        int i = 0;
        for (String name : hiddenAttributes) {
            result[i] = elementSchool.getAttribute(name);
            i++;
        }

        i = 0;
        for (String name : visibleAttributes) {
            result[i] = elementSchool.getAttribute(name);
            i++;
        }
        return result;
    }

    public boolean hasVisibleAttribute() {
        return !visibleAttributes.isEmpty();
    }

    public AttributeDO[] getVisibleAttributes() {
        AttributeDO[] result = new AttributeDO[visibleAttributes.size()];
        int i = 0;
        for (String name : visibleAttributes) {
            result[i] = elementSchool.getAttribute(name);
            i++;
        }
        return result;
    }

    public boolean hasHiddenAttribute() {
        return !hiddenAttributes.isEmpty();
    }

    public AttributeDO[] getHiddenAttributes() {
        AttributeDO[] result = new AttributeDO[hiddenAttributes.size()];
        int i = 0;
        for (String name : hiddenAttributes) {
            result[i] = elementSchool.getAttribute(name);
            i++;
        }
        return result;
    }

    // Setters

    public void setDefinition(String definition) {
        this.definition = new Text(definition);
    }

    public void setLevel(int level) {
        this.level = level;
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

    public void setEquippable(boolean equippable) {
        this.equippable = equippable;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean addSkill(SkillDO skill) {
        return skills.add(skill);
    }

    protected SortedSet<CardDescriptorDO> getDescriptors() {
        return descriptors;
    }

    public CardDO clone(ElementSchoolDO elementSchool) {
        CardDO card = new CardDO(getName(), elementSchool, definition.getValue());
        card.setAttackType(AttackType.valueOf(attackType));
        card.setAttackValue(attackValue);
        card.setMaxHealth(maxHealth);
        card.setLevel(level);
        for (SkillDO skill : skills) {
            card.addSkill(skill.clone(card));
        }
        for (String attribute : visibleAttributes) {
        }
        for (String attribute : hiddenAttributes) {
        }
        for (CardDescriptorDO descriptor : descriptors) {
            CardDescriptorDO cloneDescriptor = new CardDescriptorDO(card, descriptor.getLocale());
            card.addDescriptor(cloneDescriptor);
        }
        return card;
    }
}

package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.Attribute;
import com.killard.board.card.Element;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Skill;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.MetaCardDescriptorDO;
import com.killard.board.jdo.board.property.MetaCardPropertyDO;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.ArrayList;
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
public class MetaCardDO extends DescriptableDO<MetaCardDO, MetaCardPropertyDO, MetaCardDescriptorDO> implements MetaCard<MetaCardDO> {

    @Persistent
    private ElementDO element;

    @Persistent
    private int level;

    @Persistent
    private int maxHealth;

    @Persistent
    private String attackType;

    @Persistent
    private int attackValue;

    @Persistent
    private int range;

    @Persistent
    private boolean equippable;

    @Persistent
    private boolean visible;

    @Persistent
    private List<Key> skillKeys;

    @Persistent
    private List<Key> visibleAttributeKeys;

    @Persistent
    private List<Key> hiddenAttributeKeys;

    @Persistent
    @javax.jdo.annotations.Element(dependent = "true")
    private List<MetaCardPropertyDO> properties;

    @Persistent
    @javax.jdo.annotations.Element(dependent = "true")
    private transient List<MetaCardDescriptorDO> descriptors;

    @Persistent(defaultFetchGroup = "false")
    private transient Blob image;

    protected MetaCardDO(ElementDO element, String name) {
        super(element, name);
        this.element = element;
        this.attackType = AttackType.PHYSICAL.name();
        this.equippable = true;
        this.visible = true;
        this.skillKeys = new ArrayList<Key>();
        this.hiddenAttributeKeys = new ArrayList<Key>();
        this.visibleAttributeKeys = new ArrayList<Key>();
        this.properties = new ArrayList<MetaCardPropertyDO>();
        this.descriptors = new ArrayList<MetaCardDescriptorDO>();
    }

    protected MetaCardDO(ElementDO element, MetaCardDO source) {
        this(element, source.getName());
    }

    public MetaCardPropertyDO[] getProperties() {
        return properties.toArray(new MetaCardPropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new MetaCardPropertyDO(this, name, data));
    }

    public Element getElement() {
        return element;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Attack getAttack() {
        return new Attack(getElement(), AttackType.valueOf(attackType), attackValue);
    }

    public String getAttackType() {
        return attackType;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public int getRange() {
        return range;
    }

    public boolean isEquippable() {
        return equippable;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean hasSkill() {
        return !skillKeys.isEmpty();
    }

    public Skill[] getSkills() {
        Skill[] skills = new Skill[skillKeys.size()];
        for (int i = 0; i < skills.length; i++) skills[i] = element.getSkill(skillKeys.get(i));
        return skills;
    }

    public boolean hasAttribute() {
        return hasVisibleAttribute() || hasHiddenAttribute();
    }

    public Attribute[] getAttributes() {
        Attribute[] result = new Attribute[hiddenAttributeKeys.size() + visibleAttributeKeys.size()];
        int i = 0;
        for (Key key : hiddenAttributeKeys) {
            result[i] = element.getAttribute(key);
            i++;
        }

        i = 0;
        for (Key key : visibleAttributeKeys) {
            result[i] = element.getAttribute(key);
            i++;
        }
        return result;
    }

    public boolean hasVisibleAttribute() {
        return !visibleAttributeKeys.isEmpty();
    }

    public Attribute[] getVisibleAttributes() {
        Attribute[] result = new Attribute[visibleAttributeKeys.size()];
        int i = 0;
        for (Key key : visibleAttributeKeys) {
            result[i] = element.getAttribute(key);
            i++;
        }
        return result;
    }

    public boolean hasHiddenAttribute() {
        return !hiddenAttributeKeys.isEmpty();
    }

    public Attribute[] getHiddenAttributes() {
        Attribute[] result = new Attribute[hiddenAttributeKeys.size()];
        int i = 0;
        for (Key key : hiddenAttributeKeys) {
            result[i] = element.getAttribute(key);
            i++;
        }
        return result;
    }

    // Setters

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

    public void setRange(int range) {
        this.range = range;
    }

    public void setEquippable(boolean equippable) {
        this.equippable = equippable;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void clearSkill() {
        skillKeys.clear();
    }

    public void clearAttribute() {
        visibleAttributeKeys.clear();
        hiddenAttributeKeys.clear();
    }

    public boolean addSkill(SkillDO skill) {
        return !skillKeys.contains(skill.getKey()) && skillKeys.add(skill.getKey());
    }

    public boolean removeSkill(SkillDO skill) {
        return skillKeys.contains(skill.getKey()) && skillKeys.remove(skill.getKey());
    }

    public boolean addAttribute(AttributeDO attribute) {
        if (attribute.isVisible())
            return !visibleAttributeKeys.contains(attribute.getKey()) && visibleAttributeKeys.add(attribute.getKey());
        else
            return !hiddenAttributeKeys.contains(attribute.getKey()) && hiddenAttributeKeys.add(attribute.getKey());
    }

    public boolean removeAttribute(AttributeDO attribute) {
        if (attribute.isVisible())
            return visibleAttributeKeys.contains(attribute.getKey()) && visibleAttributeKeys.remove(attribute.getKey());
        else
            return hiddenAttributeKeys.contains(attribute.getKey()) && hiddenAttributeKeys.remove(attribute.getKey());
    }

    public MetaCardDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new MetaCardDescriptorDO[descriptors.size()]);
    }

    protected boolean addDescriptor(String locale, String name, String description) {
        return descriptors.add(new MetaCardDescriptorDO(this, locale, name, description));
    }
}

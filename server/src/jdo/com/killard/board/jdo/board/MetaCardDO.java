package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.killard.board.card.Attack;
import com.killard.board.card.AttackType;
import com.killard.board.card.Attribute;
import com.killard.board.card.Element;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Skill;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.MetaCardDescriptorDO;
import com.killard.board.jdo.board.property.MetaCardPropertyDO;
import com.killard.board.parser.Function;

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
    private boolean equippable;

    @Persistent
    private boolean visible;

    @Persistent
    @javax.jdo.annotations.Element(dependent = "true")
    private List<SkillDO> skills;

    @Persistent
    private List<String> visibleAttributes;

    @Persistent
    private List<String> hiddenAttributes;

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
        this.skills = new ArrayList<SkillDO>();
        this.hiddenAttributes = new ArrayList<String>();
        this.visibleAttributes = new ArrayList<String>();
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

    public Skill[] getSkills() {
        return skills.toArray(new Skill[skills.size()]);
    }

    public boolean hasAttribute() {
        return hasVisibleAttribute() || hasHiddenAttribute();
    }

    public Attribute[] getAttributes() {
        Attribute[] result = new Attribute[hiddenAttributes.size() + visibleAttributes.size()];
        int i = 0;
        for (String name : hiddenAttributes) {
            result[i] = element.getAttribute(name);
            i++;
        }

        i = 0;
        for (String name : visibleAttributes) {
            result[i] = element.getAttribute(name);
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
        for (String name : visibleAttributes) {
            result[i] = element.getAttribute(name);
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
        for (String name : hiddenAttributes) {
            result[i] = element.getAttribute(name);
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

    public void setEquippable(boolean equippable) {
        this.equippable = equippable;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public SkillDO newSkill(String name, List<String> targets, int cost, Function function) {
        SkillDO skill = new SkillDO(this, name, targets, cost, function);
        skills.add(skill);
        return skill;
    }

    public MetaCardDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new MetaCardDescriptorDO[descriptors.size()]);
    }

    protected boolean addDescriptor(String locale, String name, String description) {
        return descriptors.add(new MetaCardDescriptorDO(this, locale, name, description));
    }
}

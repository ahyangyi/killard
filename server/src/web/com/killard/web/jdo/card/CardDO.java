package com.killard.web.jdo.card;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.killard.card.AttackType;
import com.killard.web.jdo.DescriptableDO;

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
    private String id;

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

    @Persistent(mappedBy = "card", defaultFetchGroup = "false")
    private SortedSet<SkillDO> skills = new TreeSet<SkillDO>();

    @Persistent
    private SortedSet<String> visibleAttributes = new TreeSet<String>();

    @Persistent
    private SortedSet<String> hiddenAttributes = new TreeSet<String>();

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<CardDescriptorDO> descriptors = new TreeSet<CardDescriptorDO>();

    public CardDO(String id, ElementSchoolDO elementSchool, String definition) {
        this.id = id;
        this.elementSchool = elementSchool;
        this.packageKey = elementSchool.getPackageKey();
        this.definition = new Text(definition);
    }

    public Key getKey() {
        return key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getHealth() {
        return health;
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

    public AttributeDO[] getVisibleAttributes() {
        if (visibleAttributes == null) return new AttributeDO[0];
        AttributeDO[] result = new AttributeDO[visibleAttributes.size()];
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

    public AttributeDO[] getHiddenAttributes() {
        if (hiddenAttributes == null) return new AttributeDO[0];
        AttributeDO[] result = new AttributeDO[hiddenAttributes.size()];
        int i = 0;
        for (String id : hiddenAttributes) {
            result[i] = elementSchool.getAttribute(id);
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

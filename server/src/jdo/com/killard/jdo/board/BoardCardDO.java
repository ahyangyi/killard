package com.killard.jdo.board;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.killard.card.Attack;
import com.killard.card.AttackType;
import com.killard.card.Attribute;
import com.killard.card.Card;
import com.killard.card.ElementSchool;
import com.killard.card.Skill;
import com.killard.jdo.card.AttributeDO;
import com.killard.jdo.card.CardDO;
import com.killard.jdo.card.SkillDO;
import com.killard.jdo.card.descriptor.CardDescriptorDO;
import com.killard.jdo.DescriptableDO;
import com.killard.jdo.board.descriptor.BoardCardDescriptorDO;

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
public class BoardCardDO extends DescriptableDO<BoardCardDescriptorDO> implements Card {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private BoardElementSchoolDO elementSchool;

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

    @Persistent(mappedBy = "card", defaultFetchGroup = "false")
    private SortedSet<BoardSkillDO> skills;

    @Persistent(serialized = "true")
    private SortedSet<String> visibleAttributes;

    @Persistent(serialized = "true")
    private SortedSet<String> hiddenAttributes;

    @Persistent
    private SortedSet<BoardCardDescriptorDO> descriptors;

    public BoardCardDO(BoardElementSchoolDO elementSchool, CardDO card) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(elementSchool.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), card.getKey().getId());
        this.key = keyBuilder.getKey();

        this.elementSchool = elementSchool;
        this.name = card.getName();
        this.level = card.getLevel();
        this.maxHealth = card.getMaxHealth();
        this.health = card.getHealth();
        this.attackType = card.getAttackType();
        this.attackValue = card.getAttackValue();

        this.skills = new TreeSet<BoardSkillDO>();
        for (SkillDO skill : card.getSkills()) {
            skills.add(new BoardSkillDO(this, skill));
        }
        this.hiddenAttributes = new TreeSet<String>();
        for (AttributeDO attribute : card.getHiddenAttributes()) {
            hiddenAttributes.add(attribute.getName());
        }
        this.visibleAttributes = new TreeSet<String>();
        for (AttributeDO attribute : card.getVisibleAttributes()) {
            visibleAttributes.add(attribute.getName());
        }

        this.descriptors = new TreeSet<BoardCardDescriptorDO>();
        for (CardDescriptorDO descriptor : card.getAllDescriptors()) {
            this.descriptors.add(new BoardCardDescriptorDO(this, descriptor));
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

    protected SortedSet<BoardCardDescriptorDO> getDescriptors() {
        return descriptors;
    }
}

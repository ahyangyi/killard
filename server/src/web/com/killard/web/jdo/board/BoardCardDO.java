package com.killard.web.jdo.board;

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
import com.killard.web.jdo.card.AttributeDO;
import com.killard.web.jdo.card.CardDO;
import com.killard.web.jdo.card.SkillDO;

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
public class BoardCardDO extends BoardDescriptableDO implements Card {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private BoardElementSchoolDO elementSchool;

    @Persistent
    private String id;

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
    private SortedSet<BoardSkillDO> skills = new TreeSet<BoardSkillDO>();

    @Persistent
    private SortedSet<String> visibleAttributes = new TreeSet<String>();

    @Persistent
    private SortedSet<String> hiddenAttributes = new TreeSet<String>();

    @Persistent
    private String name;

    @Persistent
    private Text description;

    @Persistent(defaultFetchGroup = "false")
    private Blob image;

    public BoardCardDO(BoardElementSchoolDO elementSchool, CardDO card) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(elementSchool.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), card.getId());
        this.key = keyBuilder.getKey();

        this.elementSchool = elementSchool;
        this.id = card.getId();
        this.level = card.getLevel();
        this.maxHealth = card.getMaxHealth();
        this.health = card.getHealth();
        this.attackType = card.getAttackType();
        this.attackValue = card.getAttackValue();

        for (SkillDO skill : card.getSkills()) {
            skills.add(new BoardSkillDO(this, skill));
        }
        for (AttributeDO attribute : card.getHiddenAttributes()) {
            hiddenAttributes.add(attribute.getId());
        }
        for (AttributeDO attribute : card.getVisibleAttributes()) {
            visibleAttributes.add(attribute.getId());
        }

        this.name = card.getDescriptor().getName();
        this.description = new Text(card.getDescriptor().getDescription());
        this.image = new Blob(card.getDescriptor().getImageData());
    }

    public Key getKey() {
        return key;
    }

    public String getId() {
        return id;
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description.getValue();
    }

    public byte[] getImageData() {
        return image.getBytes();
    }
}

package com.killard.web.jdo.card;

import com.google.appengine.api.datastore.Key;
import com.killard.parser.Function;
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
public class SkillDO extends DescriptableDO<SkillDescriptorDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String id;

    @Persistent
    private CardDO card;

    @Persistent
    private Integer cost;

    @Persistent(serialized = "true")
    private Function function;

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<SkillDescriptorDO> descriptors = new TreeSet<SkillDescriptorDO>();

    public SkillDO(String id, CardDO card, int cost, Function function) {
        this.id = id;
        this.card = card;
        this.cost = cost;
        this.function = function;
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

    public CardDO getCard() {
        return card;
    }

    public int getCost() {
        return cost;
    }

    public Function getFunction() {
        return function;
    }

    public SortedSet<SkillDescriptorDO> getDescriptors() {
        return descriptors;
    }

    public SkillDO clone(CardDO card) {
        SkillDO skill = new SkillDO(getId(), card, cost, function);
        for (SkillDescriptorDO descriptor : descriptors) {
            SkillDescriptorDO cloneDescriptor = new SkillDescriptorDO(skill, descriptor.getLocale());
            skill.addDescriptor(cloneDescriptor);
        }
        return skill;
    }
}

package com.killard.jdo.card;

import com.google.appengine.api.datastore.Key;
import com.killard.jdo.DescriptableDO;
import com.killard.jdo.card.descriptor.SkillDescriptorDO;
import com.killard.parser.Function;

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
    private String name;

    @Persistent
    private CardDO card;

    @Persistent
    private Integer cost;

    @Persistent(serialized = "true")
    private Function function;

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<SkillDescriptorDO> descriptors;

    public SkillDO(String name, CardDO card, int cost, Function function) {
        this.name = name;
        this.card = card;
        this.cost = cost;
        this.function = function;
        this.descriptors = new TreeSet<SkillDescriptorDO>();
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
        SkillDO skill = new SkillDO(getName(), card, cost, function);
        for (SkillDescriptorDO descriptor : descriptors) {
            SkillDescriptorDO cloneDescriptor = new SkillDescriptorDO(skill, descriptor.getLocale());
            skill.addDescriptor(cloneDescriptor);
        }
        return skill;
    }
}

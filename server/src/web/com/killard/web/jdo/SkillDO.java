package com.killard.web.jdo;

import com.google.appengine.api.datastore.Key;
import com.killard.card.Action;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.Skill;
import com.killard.parser.Context;
import com.killard.parser.ExecutionException;
import com.killard.parser.Function;
import com.killard.parser.GlobalContext;
import com.killard.web.PersistenceHelper;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
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
public class SkillDO extends DescriptableDO<SkillDescriptorDO> implements Skill {

    @Persistent
    @Extension(vendorName = "datanucleus", key = "gae.parent-pk", value = "true")
    private Key cardKey;

    @Persistent
    private Integer cost;

    @Persistent(serialized = "true")
    private Function function;

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<SkillDescriptorDO> descriptors = new TreeSet<SkillDescriptorDO>();

    public SkillDO(String id, CardDO card, int cost, Function function) {
        super(id);
        this.cardKey = card.getKey();
        this.cost = cost;
        this.function = function;
    }

    public Key getCardKey() {
        return cardKey;
    }

    public ElementSchool getElementSchool() {
        return PersistenceHelper.getPersistenceManager().getObjectById(CardDO.class, getCardKey()).getElementSchool();
    }

    public int getCost() {
        return cost;
    }

    public List<Action> execute(CardInstance owner, CardInstance target) {
        Context ctx = new GlobalContext(owner);
        try {
            function.execute(ctx);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ctx.getActions();
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

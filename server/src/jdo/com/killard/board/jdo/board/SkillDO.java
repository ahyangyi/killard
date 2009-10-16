package com.killard.board.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.SkillDescriptorDO;
import com.killard.board.jdo.board.property.SkillPropertyDO;
import com.killard.board.parser.Function;
import com.killard.board.parser.ExecutionException;
import com.killard.board.parser.GlobalContext;
import com.killard.board.parser.Context;
import com.killard.board.card.SkillTarget;
import com.killard.board.card.Skill;
import com.killard.board.card.Action;
import com.killard.board.card.Card;
import com.killard.board.card.Board;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
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
public class SkillDO extends DescriptableDO<SkillDO, SkillPropertyDO, SkillDescriptorDO> implements Skill<SkillDO> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    private List<String> targets;

    @Persistent
    private Integer cost;

    @Persistent(serialized = "true")
    private Function function;

    @Persistent
    private SortedSet<SkillPropertyDO> properties;

    @Persistent(defaultFetchGroup = "false")
    private SortedSet<SkillDescriptorDO> descriptors;

    public SkillDO(String name, MetaCardDO card, int cost, Function function) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(card.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), name);
        this.key = keyBuilder.getKey();

        this.name = name;
        this.targets = new ArrayList<String>();
        this.targets.add(SkillTarget.self.name());
        this.cost = cost;
        this.function = function;
        this.properties = new TreeSet<SkillPropertyDO>();
        this.descriptors = new TreeSet<SkillDescriptorDO>();
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public SkillPropertyDO[] getProperties() {
        return properties.toArray(new SkillPropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new SkillPropertyDO(this, name, data));
    }

    protected boolean removeProperty(SkillPropertyDO property) {
        return properties.remove(property);
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillTarget[] getTargets() {
        SkillTarget[] result = new SkillTarget[targets.size()];
        for (int i = 0; i < result.length; i++) result[i] = SkillTarget.valueOf(targets.get(i));
        return result;
    }

    public int getCost() {
        return cost;
    }

    public Function getFunction() {
        return function;
    }

    public List<Action> execute(Board board, Card owner, Object... target) {
        Context ctx = new GlobalContext(owner);
        ctx.addVariable("targets", target);
        ctx.addVariable("board", board);
        try {
            function.execute(ctx);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ctx.getActions();
    }

    public SkillDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new SkillDescriptorDO[descriptors.size()]);
    }

    public boolean addDescriptor(SkillDescriptorDO descriptor) {
        return descriptors.add(descriptor);
    }

    public boolean removeDescriptor(SkillDescriptorDO descriptor) {
        return descriptors.add(descriptor);
    }

    public SkillDO clone(MetaCardDO card) {
        SkillDO skill = new SkillDO(getName(), card, cost, function);
        for (SkillDescriptorDO descriptor : descriptors) {
            SkillDescriptorDO cloneDescriptor = new SkillDescriptorDO(skill, descriptor.getLocale());
            skill.addDescriptor(cloneDescriptor);
        }
        return skill;
    }
}

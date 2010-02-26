package com.killard.board.jdo.board;

import com.killard.board.card.Action;
import com.killard.board.card.Board;
import com.killard.board.card.Card;
import com.killard.board.card.Skill;
import com.killard.board.card.SkillTarget;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.descriptor.SkillDescriptorDO;
import com.killard.board.jdo.board.property.SkillPropertyDO;
import com.killard.board.parser.Context;
import com.killard.board.parser.ExecutionException;
import com.killard.board.parser.Function;
import com.killard.board.parser.GlobalContext;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Persistent
    private List<String> targets;

    @Persistent
    private int cost;

    @Persistent(serialized = "true")
    private Function function;

    @Persistent(defaultFetchGroup = "true")
    @Element(dependent = "true")
    private Set<SkillPropertyDO> properties;

    @Persistent
    @Element(dependent = "true")
    private transient Set<SkillDescriptorDO> descriptors;

    protected SkillDO(MetaCardDO card, String name, List<String> targets, int cost, Function function) {
        super(card, name);

        this.targets = new ArrayList<String>(targets);
        this.targets.add(SkillTarget.self.name());

        this.cost = cost;
        this.function = function;

        this.properties = new HashSet<SkillPropertyDO>();
        this.descriptors = new HashSet<SkillDescriptorDO>();
    }

    protected SkillDO(MetaCardDO card, SkillDO source) {
        this(card, source.getName(), source.targets, source.cost, source.function);
        this.cost = source.cost;
    }

    protected boolean addProperty(String name, String data) {
        return properties.add(new SkillPropertyDO(this, name, data));
    }

    public SkillPropertyDO[] getProperties() {
        return properties.toArray(new SkillPropertyDO[properties.size()]);
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

    public List<Action> execute(Board board, Card owner, Object... targets) {
        Context ctx = new GlobalContext(owner);
        ctx.addVariable("targets", targets);
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

    protected boolean addDescriptor(String locale, String name, String description) {
        return descriptors.add(new SkillDescriptorDO(this, locale, name, description));
    }
}

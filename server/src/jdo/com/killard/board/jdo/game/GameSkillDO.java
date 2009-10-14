package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Action;
import com.killard.board.card.Card;
import com.killard.board.card.Skill;
import com.killard.board.card.SkillTarget;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.board.SkillDO;
import com.killard.board.jdo.board.descriptor.SkillDescriptorDO;
import com.killard.board.jdo.board.property.SkillPropertyDO;
import com.killard.board.jdo.game.descriptor.GameSkillDescriptorDO;
import com.killard.board.jdo.game.property.GameSkillPropertyDO;
import com.killard.board.parser.Context;
import com.killard.board.parser.ExecutionException;
import com.killard.board.parser.Function;
import com.killard.board.parser.GlobalContext;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.ArrayList;
import java.util.Arrays;
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
public class GameSkillDO extends DescriptableDO<GameSkillDO, GameSkillPropertyDO, GameSkillDescriptorDO> implements Skill<GameSkillDO> {

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
    private SortedSet<GameSkillPropertyDO> properties;

    @Persistent
    private SortedSet<GameSkillDescriptorDO> descriptors;

    @NotPersistent
    private BoardDO board;

    public GameSkillDO(GameCardDO card, SkillDO skill) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(card.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), skill.getKey().getId());
        this.key = keyBuilder.getKey();

        this.name = skill.getName();
        this.targets = new ArrayList<String>(Arrays.asList(skill.getTargets()));
        this.cost = skill.getCost();
        this.function = skill.getFunction();

        this.properties = new TreeSet<GameSkillPropertyDO>();
        for (SkillPropertyDO property : skill.getProperties()) {
            this.properties.add(new GameSkillPropertyDO(this, property));
        }

        this.descriptors = new TreeSet<GameSkillDescriptorDO>();
        for (SkillDescriptorDO descriptor : skill.getDescriptors()) {
            this.descriptors.add(new GameSkillDescriptorDO(this, descriptor));
        }
    }

    public void restore(BoardDO board) {
        this.board = board;
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public GameSkillPropertyDO[] getProperties() {
        return properties.toArray(new GameSkillPropertyDO[properties.size()]);
    }

    protected boolean addProperty(String name, String data) {
        return false;
    }

    protected boolean removeProperty(GameSkillPropertyDO property) {
        return false;
    }

    public SkillTarget[] getTargets() {
        SkillTarget[] result = new SkillTarget[targets.size()];
        for (int i = 0; i < result.length; i++) result[i] = SkillTarget.valueOf(targets.get(i));
        return result;
    }

    public GameSkillDescriptorDO[] getDescriptors() {
        return descriptors.toArray(new GameSkillDescriptorDO[descriptors.size()]);
    }

    public int getCost() {
        return cost;
    }

    public Function getFunction() {
        return function;
    }

    public List<Action> execute(Card owner, Object... target) {
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

    public Object getProperty(String name) {
        return null;
    }
}

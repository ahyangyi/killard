package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Action;
import com.killard.board.card.CardInstance;
import com.killard.board.card.Skill;
import com.killard.board.card.SkillTarget;
import com.killard.board.parser.Context;
import com.killard.board.parser.ExecutionException;
import com.killard.board.parser.Function;
import com.killard.board.parser.GlobalContext;
import com.killard.board.jdo.board.SkillDO;
import com.killard.board.jdo.DescriptableDO;
import com.killard.board.jdo.game.descriptor.GameSkillDescriptorDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.NotPersistent;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Arrays;

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
public class GameSkillDO extends DescriptableDO<GameSkillDescriptorDO> implements Skill {

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
    private SortedSet<GameSkillDescriptorDO> descriptors;

    @NotPersistent
    private BoardManagerDO boardManager;

    public GameSkillDO(GameMetaCardDO card, SkillDO skill) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(card.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), skill.getKey().getId());
        this.key = keyBuilder.getKey();

        this.name = skill.getName();
        this.targets = new ArrayList<String>(Arrays.asList(skill.getTargets()));
        this.cost = skill.getCost();
        this.function = skill.getFunction();

        this.descriptors = new TreeSet<GameSkillDescriptorDO>();
    }

    public void restore(BoardManagerDO boardManager) {
        this.boardManager = boardManager;
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public SkillTarget[] getTargets() {
        SkillTarget[] result = new SkillTarget[targets.size()];
        for (int i = 0; i < result.length; i++) result[i] = SkillTarget.valueOf(targets.get(i));
        return result;
    }

    protected SortedSet<GameSkillDescriptorDO> getDescriptors() {
        return descriptors;
    }

    public int getCost() {
        return cost;
    }

    public Function getFunction() {
        return function;
    }

    public List<Action> execute(CardInstance owner, Object... target) {
        Context ctx = new GlobalContext(owner);
        ctx.addVariable("targets", target);
        ctx.addVariable("board", boardManager);
        try {
            function.execute(ctx);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ctx.getActions();
    }
}

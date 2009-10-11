package com.killard.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.card.Action;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.Skill;
import com.killard.parser.Context;
import com.killard.parser.ExecutionException;
import com.killard.parser.Function;
import com.killard.parser.GlobalContext;
import com.killard.jdo.card.SkillDO;
import com.killard.jdo.DescriptorDO;
import com.killard.jdo.DescriptableDO;
import com.killard.jdo.board.descriptor.BoardSkillDescriptorDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.List;
import java.util.SortedSet;

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
public class BoardSkillDO extends DescriptableDO<BoardSkillDescriptorDO> implements Skill {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private BoardCardDO card;

    @Persistent
    private String name;

    @Persistent
    private Integer cost;

    @Persistent(serialized = "true")
    private Function function;

    @Persistent
    private SortedSet<BoardSkillDescriptorDO> descriptors;

    public BoardSkillDO(BoardCardDO card, SkillDO skill) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(card.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), skill.getKey().getId());
        this.key = keyBuilder.getKey();

        this.card = card;
        this.name = skill.getName();
        this.cost = skill.getCost();
        this.function = skill.getFunction();
    }

    public Key getKey() {
        return key;
    }

    public BoardCardDO getCard() {
        return card;
    }

    public String getName() {
        return name;
    }

    protected SortedSet<BoardSkillDescriptorDO> getDescriptors() {
        return descriptors;
    }

    public ElementSchool getElementSchool() {
        return card.getElementSchool();
    }

    public int getCost() {
        return cost;
    }

    public Function getFunction() {
        return function;
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
}

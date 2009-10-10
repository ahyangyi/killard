package com.killard.web.jdo.board;

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
import com.killard.web.jdo.card.SkillDO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.List;

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
public class BoardSkillDO extends BoardDescriptableDO implements Skill {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private BoardCardDO card;

    @Persistent
    private String id;

    @Persistent
    private Integer cost;

    @Persistent(serialized = "true")
    private Function function;

    public BoardSkillDO(BoardCardDO card, SkillDO skill) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(card.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), skill.getId());
        this.key = keyBuilder.getKey();

        this.card = card;
        this.id = skill.getId();
        this.cost = skill.getCost();
        this.function = skill.getFunction();
    }

    public Key getKey() {
        return key;
    }

    public BoardCardDO getCard() {
        return card;
    }

    public String getId() {
        return id;
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

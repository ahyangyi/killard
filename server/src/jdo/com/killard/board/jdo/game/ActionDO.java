package com.killard.board.jdo.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Action;
import com.killard.board.card.action.AddCardAttributeAction;
import com.killard.board.card.action.CardAction;
import com.killard.board.card.action.CastCardAction;
import com.killard.board.card.action.ChangeCardAttackAction;
import com.killard.board.card.action.ChangeCardHealthAction;
import com.killard.board.card.action.ChangePlayerElementAction;
import com.killard.board.card.action.ChangePlayerHealthAction;
import com.killard.board.card.action.EndTurnAction;
import com.killard.board.card.action.KillCardAction;
import com.killard.board.card.action.KillPlayerAction;
import com.killard.board.card.action.EquipCardAction;
import com.killard.board.card.action.PlayerAction;
import com.killard.board.card.action.RemoveCardAttributeAction;
import com.killard.board.card.action.BeginGameAction;
import com.killard.board.card.action.BeginTurnAction;
import com.killard.board.card.action.ChangeCardMaxHealthAction;
import com.killard.board.card.action.DiscardCardAction;
import com.killard.board.card.action.DrawCardAction;
import com.killard.board.card.action.EndGameAction;
import com.killard.board.card.action.EndPlayerCallAction;
import com.killard.board.card.action.LoseAction;
import com.killard.board.card.action.RemoveSkillAction;
import com.killard.board.card.action.RevealRoleAction;
import com.killard.board.card.action.RevivePlayerAction;
import com.killard.board.card.action.WinAction;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.Date;

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
public class ActionDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String actionClass;

    @Persistent
    private String playerId;

    @Persistent
    private String cardElementSchoolName;

    @Persistent
    private String cardName;

    @Persistent
    private Integer cardPosition;

    @Persistent
    private String targetPlayerId;

    @Persistent
    private Integer targetCardPosition;

    @Persistent
    private String elementSchoolName;

    @Persistent
    private String attackType;

    @Persistent
    private Integer attackValue;

    @Persistent
    private Integer element;

    @Persistent
    private String attributeName;

    @Persistent
    private Date date;

    public ActionDO(BoardManagerDO boardManager, Action action) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(boardManager.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), boardManager.getActions().length);
        this.key = keyBuilder.getKey();
        this.date = new Date();
        init(action);
    }

    public Key getKey() {
        return key;
    }

    public String getActionClass() {
        return actionClass;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public int getElement() {
        return element;
    }

    public int getCardPosition() {
        return cardPosition;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getTargetPlayerId() {
        return targetPlayerId;
    }

    public int getTargetCardPosition() {
        return targetCardPosition;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        if (actionClass.equals(AddCardAttributeAction.class.getSimpleName())) {
            return "Add attribute " + attributeName;
        } else if (actionClass.equals(BeginGameAction.class.getSimpleName())) {
            return "Game begin";
        } else if (actionClass.equals(BeginTurnAction.class.getSimpleName())) {
            return "Turn begin";
        } else if (actionClass.equals(CastCardAction.class.getSimpleName())) {
            return playerId + " cast board " + cardName;
        } else if (actionClass.equals(ChangeCardAttackAction.class.getSimpleName())) {
            return playerId + " ";
        } else if (actionClass.equals(ChangeCardHealthAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(ChangeCardMaxHealthAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(ChangePlayerElementAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(ChangePlayerHealthAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(DiscardCardAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(DrawCardAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(EndGameAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(EndPlayerCallAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(EndTurnAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(EquipCardAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(KillCardAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(KillPlayerAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(LoseAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(RemoveCardAttributeAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(RemoveSkillAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(RevealRoleAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(RevivePlayerAction.class.getSimpleName())) {
            ;
        } else if (actionClass.equals(WinAction.class.getSimpleName())) {
            ;
        }
        return actionClass + " : " + playerId;
    }

    protected void init(Action action) {
        this.actionClass = action.getClass().getSimpleName();
        if (action instanceof PlayerAction) populate((PlayerAction) action);
        if (action instanceof CardAction) populate((CardAction) action);
        if (action instanceof AddCardAttributeAction) populate((AddCardAttributeAction) action);
        if (action instanceof ChangeCardAttackAction) populate((ChangeCardAttackAction) action);
        if (action instanceof ChangeCardHealthAction) populate((ChangeCardHealthAction) action);
        if (action instanceof ChangePlayerElementAction) populate((ChangePlayerElementAction) action);
        if (action instanceof ChangePlayerHealthAction) populate((ChangePlayerHealthAction) action);
        if (action instanceof KillCardAction) populate((KillCardAction) action);
        if (action instanceof KillPlayerAction) populate((KillPlayerAction) action);
        if (action instanceof EquipCardAction) populate((EquipCardAction) action);
        if (action instanceof CastCardAction) populate((CastCardAction) action);
        if (action instanceof EndTurnAction) populate((EndTurnAction) action);
        if (action instanceof RemoveCardAttributeAction) populate((RemoveCardAttributeAction) action);
    }

    protected void populate(PlayerAction<Object> action) {
        this.playerId = action.getTarget().getId();
    }

    protected void populate(CardAction<Object> action) {
        this.playerId = action.getTarget().getOwner().getId();
        this.cardPosition = action.getTarget().getPosition();
    }

    protected void populate(AddCardAttributeAction action) {
        this.attributeName = action.getAttribute().getName();
    }

    protected void populate(ChangeCardAttackAction action) {
        this.attackType = action.getAttack().getType().name();
        this.attackValue = action.getAttack().getValue();
    }

    protected void populate(ChangeCardHealthAction action) {
        this.attackType = action.getHealthChange().getType().name();
        this.attackValue = action.getHealthChange().getValue();
    }

    protected void populate(ChangePlayerElementAction action) {
        this.elementSchoolName = action.getElementSchool().getName();
        this.element = action.getValue();
    }

    protected void populate(ChangePlayerHealthAction action) {
        this.attackValue = action.getHealthChange().getValue();
    }

    protected void populate(KillCardAction action) {
    }

    protected void populate(KillPlayerAction action) {
    }

    protected void populate(EquipCardAction action) {
    }

    protected void populate(CastCardAction action) {
        this.playerId = action.getSource().getId();
        if (action.getTargetCard() != null) {
            this.targetPlayerId = action.getTargetCard().getOwner().getId();
        } else {
            this.targetPlayerId = action.getTarget().getTarget().getId();
        }
        this.targetCardPosition = action.getTarget().getPosition();
    }

    protected void populate(EndTurnAction action) {
    }

    protected void populate(RemoveCardAttributeAction action) {
        this.attributeName = action.getAttribute().getName();
    }

    protected String tag(String tag, String value, String... attributes) {
        StringBuilder buf = new StringBuilder();
        buf.append(begin(tag, attributes));
        buf.append(value);
        buf.append(end(tag));
        return buf.toString();
    }

    protected String begin(String tag, String... attributes) {
        StringBuilder buf = new StringBuilder();
        buf.append("<");
        buf.append(tag);
        for (int i = 0; i < attributes.length - 1; i++) {
            buf.append(" ");
            buf.append(attributes[i]);
            buf.append("=\"");
            buf.append(attributes[i + 1]);
            buf.append("\"");
        }
        buf.append(">");
        return buf.toString();
    }

    protected String end(String tag) {
        StringBuilder buf = new StringBuilder();
        buf.append("</");
        buf.append(tag);
        buf.append(">");
        return buf.toString();
    }
}

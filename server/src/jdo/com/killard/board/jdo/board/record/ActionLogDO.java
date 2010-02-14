package com.killard.board.jdo.board.record;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.Action;
import com.killard.board.card.Record;
import com.killard.board.card.action.AddCardAttributeAction;
import com.killard.board.card.action.CardAction;
import com.killard.board.card.action.CastCardAction;
import com.killard.board.card.action.ChangeCardAttackAction;
import com.killard.board.card.action.ChangeCardHealthAction;
import com.killard.board.card.action.ChangePlayerElementAction;
import com.killard.board.card.action.ChangePlayerHealthAction;
import com.killard.board.card.action.DropCardAction;
import com.killard.board.card.action.EndTurnAction;
import com.killard.board.card.action.EquipCardAction;
import com.killard.board.card.action.KillPlayerAction;
import com.killard.board.card.action.PlayerAction;
import com.killard.board.card.action.PlayerJoinAction;
import com.killard.board.card.action.PlayerQuitAction;
import com.killard.board.card.action.RemoveCardAttributeAction;
import com.killard.board.jdo.board.BoardDO;

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
public class ActionLogDO {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Key boardKey;

    @Persistent
    private String actionClass;

    @Persistent
    private String playerId;

    @Persistent
    private String nickname;

    @Persistent
    private int number;

    @Persistent
    private String cardElementSchoolName;

    @Persistent
    private String cardName;

    @Persistent
    private int cardPosition;

    @Persistent
    private String targetPlayerId;

    @Persistent
    private int targetCardPosition;

    @Persistent
    private String elementSchoolName;

    @Persistent
    private String attackType;

    @Persistent
    private int attackValue;

    @Persistent
    private int element;

    @Persistent
    private String attributeName;

    @Persistent
    private Date time;

    public ActionLogDO(BoardDO board, Action action) {
        KeyFactory.Builder keyBuilder = new KeyFactory.Builder(board.getKey());
        keyBuilder.addChild(getClass().getSimpleName(), board.getActions().length + 1);
        this.key = keyBuilder.getKey();
        this.boardKey = board.getKey();
        this.time = new Date();
        init(action);
    }

    public Key getKey() {
        return key;
    }

    public Key getBoardKey() {
        return boardKey;
    }

    public String getActionClass() {
        return actionClass;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getNumber() {
        return number;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public int getElement() {
        return element;
    }

    public String getCardElementSchoolName() {
        return cardElementSchoolName;
    }

    public String getCardName() {
        return cardName;
    }

    public String getElementSchoolName() {
        return elementSchoolName;
    }

    public String getAttackType() {
        return attackType;
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

    public Date getTime() {
        return time;
    }

    protected void init(Action action) {
        this.actionClass = action.getClass().getSimpleName();

        if (action instanceof PlayerAction) {

            populate((PlayerAction) action);
            if (action instanceof KillPlayerAction) populate((KillPlayerAction) action);
            else if (action instanceof PlayerJoinAction) populate((PlayerJoinAction) action);
            else if (action instanceof PlayerQuitAction) populate((PlayerQuitAction) action);

        } else if (action instanceof CardAction) {

            populate((CardAction) action);
            if (action instanceof AddCardAttributeAction) populate((AddCardAttributeAction) action);
            else if (action instanceof ChangeCardAttackAction) populate((ChangeCardAttackAction) action);
            else if (action instanceof ChangeCardHealthAction) populate((ChangeCardHealthAction) action);
            else if (action instanceof ChangePlayerElementAction) populate((ChangePlayerElementAction) action);
            else if (action instanceof ChangePlayerHealthAction) populate((ChangePlayerHealthAction) action);
            else if (action instanceof DropCardAction) populate((DropCardAction) action);
            else if (action instanceof EquipCardAction) populate((EquipCardAction) action);
            else if (action instanceof CastCardAction) populate((CastCardAction) action);
            else if (action instanceof EndTurnAction) populate((EndTurnAction) action);
            else if (action instanceof RemoveCardAttributeAction) populate((RemoveCardAttributeAction) action);

        }
    }

    protected void populate(PlayerAction<? extends Record> action) {
        this.playerId = action.getTarget().getId();
        this.nickname = action.getTarget().getNickname();
        this.number = action.getTarget().getNumber();
    }

    protected void populate(KillPlayerAction action) {
    }

    protected void populate(PlayerJoinAction action) {
    }

    protected void populate(PlayerQuitAction action) {
    }

    protected void populate(CardAction<? extends Record> action) {
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

    protected void populate(DropCardAction action) {
    }

    protected void populate(EquipCardAction action) {
        this.playerId = action.getSource().getId();
        this.cardName = action.getTarget().getName();
        this.cardElementSchoolName = action.getTarget().getElementSchool().getName();
        this.cardPosition = action.getTarget().getPosition();
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
}

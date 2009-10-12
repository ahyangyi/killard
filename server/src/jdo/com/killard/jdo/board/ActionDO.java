package com.killard.jdo.board;

import com.google.appengine.api.datastore.Key;
import com.killard.card.Action;
import com.killard.card.action.AddCardAttributeAction;
import com.killard.card.action.CardAction;
import com.killard.card.action.CastCardAction;
import com.killard.card.action.ChangeCardAttackAction;
import com.killard.card.action.ChangeCardHealthAction;
import com.killard.card.action.ChangePlayerElementAction;
import com.killard.card.action.ChangePlayerHealthAction;
import com.killard.card.action.EndTurnAction;
import com.killard.card.action.KillCardAction;
import com.killard.card.action.KillPlayerAction;
import com.killard.card.action.PlayCardAction;
import com.killard.card.action.PlayerAction;
import com.killard.card.action.RemoveCardAttributeAction;
import com.killard.jdo.PersistenceHelper;

import javax.jdo.annotations.Extension;
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
    @Extension(vendorName = "datanucleus", key = "gae.parent-pk", value = "true")
    private Key boardManagerKey;

    @Persistent
    private String actionName;

    @Persistent
    private String playerName;

    @Persistent
    private Integer playerHealthChange;

    @Persistent
    private Key elementSchoolKey;

    @Persistent
    private Integer elementChange;

    @Persistent
    private Integer cardPosition;

    @Persistent
    private Integer cardHealthChange;

    @Persistent
    private Integer cardAttackChange;

    @Persistent
    private String attribute;

    @Persistent
    private String targetPlayer;

    @Persistent
    private Integer targetCardPosition;

    @Persistent
    private Date date;

    public ActionDO(BoardManagerDO boardManager, Action action) {
        this.boardManagerKey = boardManager.getKey();
        this.date = new Date();
        init(action);
    }

    public Key getKey() {
        return key;
    }

    public Key getBoardManagerKey() {
        return boardManagerKey;
    }

    public String getActionName() {
        return actionName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerHealthChange() {
        return playerHealthChange;
    }

    public BoardElementSchoolDO getElementSchool() {
        return PersistenceHelper.getPersistenceManager().getObjectById(BoardElementSchoolDO.class, elementSchoolKey);
    }

    public int getElementChange() {
        return elementChange;
    }

    public int getCardPosition() {
        return cardPosition;
    }

    public int getCardHealthChange() {
        return cardHealthChange;
    }

    public int getCardAttackChange() {
        return cardAttackChange;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getTargetPlayer() {
        return targetPlayer;
    }

    public int getTargetCardPosition() {
        return targetCardPosition;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        String playerHead = "Player " + playerName;
        if (cardPosition != null) {
            String cardHead = playerHead + "'s card at position " + cardPosition;
            if (cardHealthChange != null)
                return cardHead + " is changed health by " + cardHealthChange;
            if (cardAttackChange != null)
                return cardHead + " is changed attack by " + cardAttackChange;
            if (attribute != null) {
                if (actionName.equals(AddCardAttributeAction.class.getSimpleName()))
                    return cardHead + " is added attribute " + attribute;
                else
                    return cardHead + " is removed attribute " + attribute;
            }
            if (targetPlayer != null) return playerHead + " cast card to " + targetPlayer;
            return playerHead + " plays card";
        }
        if (playerHealthChange != null) {
            return playerHead + "'s health is changed by " + playerHealthChange;
        }
        if (elementChange != null) {
            return playerHead + "'s element of " + getElementSchool().getDescriptor().getName() + " is changed by "
                    + elementChange;
        }
        return actionName + " for player " + playerName;
    }

    public String getXml() {
        StringBuilder buf = new StringBuilder();
        buf.append(begin("action", "type", actionName));

        if (cardPosition != null) {
            buf.append(begin("card"));
            buf.append(tag("cardPosition", String.valueOf(cardPosition)));

            if (cardHealthChange != null)
                buf.append(tag("healthChange", String.valueOf(cardHealthChange)));
            if (cardAttackChange != null)
                buf.append(tag("attackChange", String.valueOf(cardAttackChange)));
            if (attribute != null) {
                if (actionName.equals(AddCardAttributeAction.class.getSimpleName()))
                    buf.append(tag("operation", "add"));
                else
                    buf.append(tag("operation", "remove"));
                buf.append(tag("attribute", attribute));
            }
            if (targetPlayer != null) {
                buf.append(tag("targetPlayer", targetPlayer));
                buf.append(tag("targetCardPosition", String.valueOf(targetCardPosition)));
            }
            buf.append(end("card"));
        } else {
            buf.append(begin("player"));
            if (playerHealthChange != null) {
                buf.append(tag("healthChange", String.valueOf(playerHealthChange)));
            } else if (elementChange != null) {
                buf.append(tag("elementSchool", getElementSchool().getDescriptor().getName()));
                buf.append(tag("elementChange", String.valueOf(elementChange)));
            }
            buf.append(end("player"));
        }

        buf.append(end("action"));
        return buf.toString();
    }

    protected void init(Action action) {
        this.actionName = action.getClass().getSimpleName();
        if (action instanceof PlayerAction) populate((PlayerAction) action);
        if (action instanceof CardAction) populate((CardAction) action);
        if (action instanceof AddCardAttributeAction) populate((AddCardAttributeAction) action);
        if (action instanceof ChangeCardAttackAction) populate((ChangeCardAttackAction) action);
        if (action instanceof ChangeCardHealthAction) populate((ChangeCardHealthAction) action);
        if (action instanceof ChangePlayerElementAction) populate((ChangePlayerElementAction) action);
        if (action instanceof ChangePlayerHealthAction) populate((ChangePlayerHealthAction) action);
        if (action instanceof KillCardAction) populate((KillCardAction) action);
        if (action instanceof KillPlayerAction) populate((KillPlayerAction) action);
        if (action instanceof PlayCardAction) populate((PlayCardAction) action);
        if (action instanceof CastCardAction) populate((CastCardAction) action);
        if (action instanceof EndTurnAction) populate((EndTurnAction) action);
        if (action instanceof RemoveCardAttributeAction) populate((RemoveCardAttributeAction) action);
    }

    protected void populate(PlayerAction<Object> action) {
        this.playerName = action.getTarget().getId();
    }

    protected void populate(CardAction<Object> action) {
        this.playerName = action.getTarget().getOwner().getId();
        this.cardPosition = action.getTarget().getPosition();
    }

    protected void populate(AddCardAttributeAction action) {
        this.attribute = action.getAttribute().getClass().getSimpleName();
    }

    protected void populate(ChangeCardAttackAction action) {
        this.cardAttackChange = action.getAttack().getValue();
    }

    protected void populate(ChangeCardHealthAction action) {
        this.cardHealthChange = action.getHealthChange().getValue();
    }

    protected void populate(ChangePlayerElementAction action) {
        this.elementSchoolKey = ((BoardElementSchoolDO) action.getElementSchool()).getKey();
        this.elementChange = action.getValue();
    }

    protected void populate(ChangePlayerHealthAction action) {
        this.playerHealthChange = action.getHealthChange().getValue();
    }

    protected void populate(KillCardAction action) {
    }

    protected void populate(KillPlayerAction action) {
    }

    protected void populate(PlayCardAction action) {
    }

    protected void populate(CastCardAction action) {
        if (action.getTargetCard() != null) {
            this.targetPlayer = action.getTargetCard().getOwner().getId();
        } else {
            this.targetPlayer = action.getTarget().getTarget().getId();
        }
        this.targetCardPosition = action.getTarget().getPosition();
    }

    protected void populate(EndTurnAction action) {
    }

    protected void populate(RemoveCardAttributeAction action) {
        this.attribute = action.getAttribute().getClass().getSimpleName();
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

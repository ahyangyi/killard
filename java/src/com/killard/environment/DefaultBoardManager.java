package com.killard.environment;

import com.killard.card.Action;
import com.killard.card.Attribute;
import com.killard.card.Card;
import com.killard.card.CardInstance;
import com.killard.card.ElementSchool;
import com.killard.card.Player;
import com.killard.card.BoardPackage;
import com.killard.card.action.CastCardAction;
import com.killard.card.action.ChangeCardHealthAction;
import com.killard.card.action.ChangePlayerElementAction;
import com.killard.card.action.ChangePlayerHealthAction;
import com.killard.card.action.KillCardAction;
import com.killard.card.action.KillPlayerAction;
import com.killard.card.action.PlayCardAction;
import com.killard.card.action.EndTurnAction;
import com.killard.environment.event.ActionListener;
import com.killard.environment.record.CardRecord;
import com.killard.environment.record.PlayerRecord;
import com.killard.pack.magic.MagicCardFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
public class DefaultBoardManager extends BoardManager implements ActionListener {

    private final BoardPackage boardPackage = null;

    private final List<PlayerRecord> roundQueue = new LinkedList<PlayerRecord>();

    public DefaultBoardManager() {
        addActionListener(this, this);
    }

    @Override
    public Player addPlayer(String playerName, int health) {
        MagicCardFactory factory = new MagicCardFactory();
        PlayerRecord player = new PlayerRecord(playerName, health,
                factory.allocateCardsForNextPlayer(Arrays.asList(getPlayers()), 2),
                factory.allocateElementsForNextPlayer(),
                this);
        roundQueue.add(player);
        return player;
    }

    public BoardPackage getPackage() {
        return boardPackage;
    }

    public int getPlayerAmount() {
        return roundQueue.size();
    }

    public Player[] getPlayers() {
        return roundQueue.toArray(new Player[roundQueue.size()]);
    }

    public Player getCurrentPlayer() {
        return roundQueue.get(0);
    }

    public Player getNextPlayer() {
        return roundQueue.get(1);
    }

    public Player getPreviousPlayer() {
        return roundQueue.get(roundQueue.size() - 1);
    }

    public Player getPlayer(int position) {
        return roundQueue.get(position);
    }

    @Override
    protected void moveToNext() {
        // Move current player to the end of queue
        roundQueue.add(roundQueue.remove(0));
    }

    @Override
    protected CardInstance createCardRecord(Card card, Player owner, Player target, int cardPosition) {
        return new CardRecord(card, this, owner, target, cardPosition);
    }

    @AfterAction(actionClass = EndTurnAction.class, selfTargeted = false)
    public Object after(BoardManager boardManager, EndTurnAction action) {
        boardManager.moveToNext();
        List<Action> actions = new ArrayList<Action>();
        for (ElementSchool elementSchool : getPackage().getElementSchools())
            actions.add(new ChangePlayerElementAction(boardManager, action.getTarget(), elementSchool, 1));
        return actions;
    }

    @AfterAction(actionClass = ChangePlayerHealthAction.class, selfTargeted = false)
    public Object after(BoardManager boardManager, ChangePlayerHealthAction action) {
        if (action.getHealthChange().getValue() > action.getTarget().getHealth())
            return new KillPlayerAction(action.getSource(), action.getTarget());
        else return null;
    }

    @ActionValidator(actionClass = PlayCardAction.class, selfTargeted = false)
    public Object validator(BoardManager boardManager, PlayCardAction action) {
        CardInstance card = action.getTarget();
        if (card.getLevel() <= card.getOwner().getElementAmount(card.getElementSchool())) {
            if (card.getMaxHealth() > 0) return null;
            if (card.hasSkill()) return new CastCardAction(card.getOwner(), card, card.getSkills()[0], card);
        }
        return false;
    }

    @AfterAction(actionClass = PlayCardAction.class, selfTargeted = false)
    public Object after(BoardManager boardManager, PlayCardAction action) {
        CardInstance card = action.getTarget();
        for (Attribute attribute : card.getAttributes()) boardManager.addActionListener(attribute, card);
        return new ChangePlayerElementAction(card, card.getOwner(), card.getElementSchool(), -card.getLevel());
    }

    @BeforeAction(actionClass = KillCardAction.class, selfTargeted = false)
    public void before(BoardManager boardManager, KillCardAction action) {
        for (Attribute attribute : action.getTarget().getAttributes()) boardManager.removeActionListener(attribute);
    }

    @AfterAction(actionClass = ChangeCardHealthAction.class, selfTargeted = false)
    public Object after(BoardManager boardManager, ChangeCardHealthAction action) {
        if (action.getHealthChange().getValue() > action.getTarget().getHealth())
            return new KillCardAction(action.getSource(), action.getTarget());
        else return null;
    }

    @ActionValidator(actionClass = CastCardAction.class, selfTargeted = false)
    public Object validator(BoardManager boardManager, CastCardAction action) {
        CardInstance card = action.getTarget();
        if (!card.isCasted() && card.getSkills().length > 0 && card.getSkills()[0].getCost() <= card.getOwner()
                .getElementAmount(card.getElementSchool())) return null;
        return false;
    }

    @AfterAction(actionClass = CastCardAction.class, selfTargeted = false)
    public List<Action> after(BoardManager boardManager, CastCardAction action) {
        return action.getSkill().execute(action.getTarget(), action.getTargetCard());
    }
}

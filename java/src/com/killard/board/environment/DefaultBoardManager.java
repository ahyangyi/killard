package com.killard.board.environment;

import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.BoardPackage;
import com.killard.board.card.Card;
import com.killard.board.card.ElementSchool;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Player;
import com.killard.board.card.action.CastCardAction;
import com.killard.board.card.action.ChangeCardHealthAction;
import com.killard.board.card.action.ChangePlayerElementAction;
import com.killard.board.card.action.ChangePlayerHealthAction;
import com.killard.board.card.action.DropCardAction;
import com.killard.board.card.action.EndTurnAction;
import com.killard.board.card.action.EquipCardAction;
import com.killard.board.card.action.KillPlayerAction;
import com.killard.board.environment.event.ActionListener;
import com.killard.board.environment.record.CardRecord;
import com.killard.board.environment.record.PlayerRecord;
import com.killard.board.packages.magic.MagicCardFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
public class DefaultBoardManager extends BoardManager<DefaultBoardManager> implements ActionListener<DefaultBoardManager> {

    private final BoardPackage boardPackage = null;

    private final List<PlayerRecord> roundQueue = new LinkedList<PlayerRecord>();

    public DefaultBoardManager() {
        addActionListener(this, this);
    }

    @Override
    public Player addPlayer(String playerName, int health) {
        MagicCardFactory factory = new MagicCardFactory();
        PlayerRecord player = new PlayerRecord(null, playerName, health,
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
    protected Card createCardRecord(MetaCard metaCard, Player owner, Player target, int cardPosition) {
        return new CardRecord(metaCard, this, owner, target, cardPosition);
    }

    @AfterAction(actionClass = EndTurnAction.class, selfTargeted = false)
    public Object after(DefaultBoardManager boardManager, DefaultBoardManager owner, EndTurnAction action) {
        boardManager.moveToNext();
        List<Action> actions = new ArrayList<Action>();
        for (ElementSchool elementSchool : getPackage().getElementSchools())
            actions.add(new ChangePlayerElementAction(boardManager, action.getTarget(), elementSchool, 1));
        return actions;
    }

    @AfterAction(actionClass = ChangePlayerHealthAction.class, selfTargeted = false)
    public Object after(DefaultBoardManager boardManager, DefaultBoardManager owner, ChangePlayerHealthAction action) {
        if (action.getHealthChange().getValue() > action.getTarget().getHealth())
            return new KillPlayerAction(action.getSource(), action.getTarget());
        else return null;
    }

    @ActionValidator(actionClass = EquipCardAction.class, selfTargeted = false)
    public Object validator(DefaultBoardManager boardManager, DefaultBoardManager owner, EquipCardAction action) {
        Card card = action.getTarget();
        if (card.getLevel() <= card.getOwner().getElementAmount(card.getElementSchool())) {
            if (card.getMaxHealth() > 0) return null;
            if (card.hasSkill()) return new CastCardAction(card.getOwner(), card, card.getSkills()[0], card);
        }
        return false;
    }

    @AfterAction(actionClass = EquipCardAction.class, selfTargeted = false)
    public Object after(DefaultBoardManager boardManager, DefaultBoardManager owner, EquipCardAction action) {
        Card card = action.getTarget();
        for (Attribute attribute : card.getAttributes()) boardManager.addActionListener(attribute, card);
        return new ChangePlayerElementAction(card, card.getOwner(), card.getElementSchool(), -card.getLevel());
    }

    @BeforeAction(actionClass = DropCardAction.class, selfTargeted = false)
    public void before(DefaultBoardManager boardManager, DefaultBoardManager owner, DropCardAction action) {
        for (Attribute attribute : action.getTarget().getAttributes()) boardManager.removeActionListener(attribute);
    }

    @AfterAction(actionClass = ChangeCardHealthAction.class, selfTargeted = false)
    public Object after(DefaultBoardManager boardManager, DefaultBoardManager owner, ChangeCardHealthAction action) {
        if (action.getHealthChange().getValue() > action.getTarget().getHealth())
            return new DropCardAction(action.getTarget().getOwner(), action.getTarget());
        else return null;
    }

    @ActionValidator(actionClass = CastCardAction.class, selfTargeted = false)
    public Object validator(DefaultBoardManager boardManager, DefaultBoardManager owner, CastCardAction action) {
        Card card = action.getTarget();
        if (!card.isCasted() && card.getSkills().length > 0 && card.getSkills()[0].getCost() <= card.getOwner()
                .getElementAmount(card.getElementSchool())) return null;
        return false;
    }

    @AfterAction(actionClass = CastCardAction.class, selfTargeted = false)
    public List<Action> after(DefaultBoardManager boardManager, DefaultBoardManager owner, CastCardAction action) {
        return action.getSkill().execute(action.getTarget(), action.getTargetCard());
    }

    public int compareTo(DefaultBoardManager compare) {
        return 0;
    }
}

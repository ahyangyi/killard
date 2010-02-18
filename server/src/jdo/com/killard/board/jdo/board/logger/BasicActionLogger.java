package com.killard.board.jdo.board.logger;

import com.killard.board.card.Action;
import com.killard.board.card.Card;
import com.killard.board.card.MetaCard;
import com.killard.board.card.Player;
import com.killard.board.jdo.board.ActionLogger;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class BasicActionLogger<T extends Action> implements ActionLogger<T> {

    protected String logPlayer(Player player) {
        StringBuilder buf = new StringBuilder("{");
        buf.append("\"nickname\":\"");
        buf.append(player.getNickname());
        buf.append("\",\"number\":");
        buf.append(player.getNumber());
        buf.append("}");
        return buf.toString();
    }

    protected String logMetaCard(MetaCard card) {
        StringBuilder buf = new StringBuilder("{");
        buf.append("\"name\":\"");
        buf.append(card.getName());
        buf.append("\",\"elementSchool\":\"");
        buf.append(card.getElementSchool().getName());
        buf.append("\",\"level\":");
        buf.append(card.getLevel());
        buf.append(",\"maxHealth\":");
        buf.append(card.getMaxHealth());
        buf.append(",\"health\":");
        buf.append(card.getMaxHealth());
        buf.append(",\"attack\":");
        buf.append(card.getAttack().getValue());
        buf.append("}");
        return buf.toString();
    }

    protected String logCard(Card card) {
        StringBuilder buf = new StringBuilder("{");
        buf.append("\"name\":\"");
        buf.append(card.getName());
        buf.append("\",\"elementSchool\":\"");
        buf.append(card.getElementSchool().getName());
        buf.append("\",\"level\":");
        buf.append(card.getLevel());
        buf.append(",\"maxHealth\":");
        buf.append(card.getMaxHealth());
        buf.append(",\"health\":");
        buf.append(card.getMaxHealth());
        buf.append(",\"attack\":");
        buf.append(card.getAttack().getValue());
        buf.append(",\"position\":");
        buf.append(card.getPosition());
        buf.append("}");
        return buf.toString();
    }
}

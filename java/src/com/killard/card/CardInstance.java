package com.killard.card;

import java.util.List;

/**
 * <p>
 * This interface defines the contract for .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * The implementations are not required to be thread safe.
 * </p>
 */
public interface CardInstance extends Card {

    public Player getOwner();

    public Player getTarget();

    public List<Action> cast(Integer skill, CardInstance target);

    public List<Action> attack();

    public int getPosition();

    public boolean isCasted();

}

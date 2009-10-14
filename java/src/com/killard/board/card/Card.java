package com.killard.board.card;

/**
 * <p>
 * This interface defines the contract for .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * The implementations are not required to be thread safe.
 * </p>
 */
public interface Card<T extends Card> extends MetaCard<T> {

    public Player getOwner();

    public Player getTarget();

    public int getHealth();

    public int getPosition();

    public boolean isCasted();

}

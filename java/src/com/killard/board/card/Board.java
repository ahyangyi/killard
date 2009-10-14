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
public interface Board<T extends Board> extends Record<T> {

    public BoardPackage getPackage();

    public int getPlayerAmount();

    public Player[] getPlayers();

    public Player getActivePlayer();

    public Player getCurrentPlayer();

    public Player getNextPlayer();

    public Player getPreviousPlayer();

    public Player getPlayer(int position);

}

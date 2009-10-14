package com.killard.board.parser;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class LoopControl implements Node {

    public abstract void init(Context context) throws ExecutionException;

    public abstract void update(Context context) throws ExecutionException;

}

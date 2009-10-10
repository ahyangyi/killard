package com.killard.parser;

import java.io.Serializable;

/**
 * <p>
 * This interface defines the contract for .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * The implementations are not required to be thread safe.
 * </p>
 */
public interface Node extends Serializable {

    public Object execute(Context context) throws ExecutionException;
}

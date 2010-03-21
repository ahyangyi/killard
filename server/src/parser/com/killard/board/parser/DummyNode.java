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
public class DummyNode implements Node {

    private static final long serialVersionUID = 1377778746463177600L;

    public Object execute(Context context) throws ExecutionException {
        return null;
    }
}

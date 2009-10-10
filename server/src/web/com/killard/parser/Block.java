package com.killard.parser;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class Block extends Sequence {

    public Block() {
    }

    @Override
    public Object execute(Context context) throws ExecutionException {
        return super.execute(new LocalContext(context));
    }
}

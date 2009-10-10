package com.killard.parser;

import java.lang.reflect.Array;
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
public class ArrayCall extends Call {

    private final Expression index;

    public ArrayCall(String text, Expression index) {
        super(text);
        this.index = index;
    }

    public Expression getIndex() {
        return index;
    }

    public Object execute(Context context) throws ExecutionException {
        Object owner = context.pop();
        Integer index = (Integer) getIndex().execute(context);
        if (owner.getClass().isArray()) {
            return Array.get(owner, index);
        }
        else if (owner instanceof List) {
            List list = (List) owner;
            return list.get(index);
        }
        return null;
    }
}

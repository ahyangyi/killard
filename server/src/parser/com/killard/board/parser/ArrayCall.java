package com.killard.board.parser;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

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
        Object index = getIndex().execute(context);
        if (owner.getClass().isArray()) {
            return Array.get(owner, toInteger(index));
        } else if (owner instanceof List) {
            List list = (List) owner;
            return list.get(toInteger(index));
        } else if (owner instanceof Map) {
            Map map = (Map) owner;
            return map.get(index);
        }
        return null;
    }
}

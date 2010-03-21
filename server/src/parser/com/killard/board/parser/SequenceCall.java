package com.killard.board.parser;

import java.util.LinkedList;
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
public class SequenceCall extends Call {

    private final List<Call> calls = new LinkedList<Call>();

    private static final long serialVersionUID = -6275865663068487140L;

    public SequenceCall(Call call) {
        super(call.getText());
        addCall(call);
    }

    public Call[] getCalls() {
        return calls.toArray(new Call[calls.size()]);
    }

    public void addCall(Call call) {
        calls.add(call);
    }

    public Object execute(Context context) throws ExecutionException {
        Object result = null;
        for (Call child : getCalls()) {
            result = child.execute(context);
            context.push(result);
        }
        context.pop();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        Call[] c = getCalls();
        buf.append(c[0].toString());
        for (int i = 1; i < c.length; i++) {
            if (!(c[i] instanceof ArrayCall)) buf.append(".");
            buf.append(c[i].toString());
        }
        return buf.toString();
    }
}

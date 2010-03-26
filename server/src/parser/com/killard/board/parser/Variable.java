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
public class Variable extends Expression {

    private final String text;

    private final Expression initializer;

    private static final long serialVersionUID = -3334541593058189202L;

    public Variable(String text) {
        this.text = text;
        this.initializer = null;
    }

    public Variable(String text, Expression initializer) {
        this.text = text;
        this.initializer = initializer;
    }

    public String getText() {
        return text;
    }

    public Expression getInitilizer() {
        return initializer;
    }

    public Object execute(Context context) throws ExecutionException {
        Object value;
        if (getInitilizer() == null) {
            value = null;
        } else {
            value = getInitilizer().execute(context);
        }
        context.addVariable(getText(), value);
        return value;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("var ");
        buf.append(text);
        if (initializer != null) {
            buf.append(" = ");
            buf.append(initializer.toString());
        }
        return buf.toString();
    }
}

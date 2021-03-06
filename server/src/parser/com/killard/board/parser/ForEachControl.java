package com.killard.board.parser;

import java.util.Arrays;
import java.util.Iterator;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class ForEachControl extends LoopControl {

    private final Variable var;

    private final Expression collection;

    private static final long serialVersionUID = 7288577278778848174L;

    public ForEachControl(Variable var, Expression collection) {
        this.var = var;
        this.collection = collection;
    }

    public Variable getVar() {
        return var;
    }

    public Expression getCollectionExpression() {
        return collection;
    }

    public Boolean execute(Context context) throws ExecutionException {
        Iterator it = (Iterator) context.peek();
        if (it.hasNext()) {
            context.setVariable(getVar().getText(), it.next());
            return true;
        } else {
            context.pop();
            return false;
        }
    }

    public void init(Context context) throws ExecutionException {
        Object data = getCollectionExpression().execute(context);
        Iterator it;
        if (data instanceof Iterable) {
            Iterable iterable = (Iterable) getCollectionExpression().execute(context);
            it = iterable.iterator();
        }
        else {
            Object[] array = (Object[]) data;
            it = Arrays.asList(array).iterator();
        }
        context.push(it);
    }

    public void update(Context context) throws ExecutionException {
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("for (");
        buf.append(var.getText());
        buf.append(" in ");
        buf.append(collection.toString());
        buf.append(")");
        return buf.toString();
    }
}

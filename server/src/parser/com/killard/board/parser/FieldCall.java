package com.killard.board.parser;

import com.killard.board.card.Record;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Array;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class FieldCall extends Call {

    private static final long serialVersionUID = 8001739029533765170L;

    public FieldCall(String text) {
        super(text);
    }

    public Object execute(Context context) throws ExecutionException {
        Object owner = context.pop();
        if (owner == null) throw new ExecutionException("Can not access property " + getText() + " from null.");
        if (owner.getClass().isArray() && getText().equals("length")) {
            return Array.getLength(owner);
        }
        if (owner instanceof Record) {
            Record record = (Record) owner;
            Object value = record.getProperty(getText());
            if (value != null) return value;
        }
        try {
            Object property = PropertyUtils.getProperty(owner, getText());
            return property;
        } catch (Exception e) {
            throw new ExecutionException("Can not access property " + getText(), e);
        }
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(getText());
        return buf.toString();
    }
}

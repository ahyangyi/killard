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
public class Block extends Sequence {

    private static final long serialVersionUID = -5162476635114345611L;

    public Block() {
    }

    @Override
    public Object execute(Context context) throws ExecutionException {
        return super.execute(new LocalContext(context));
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("{\n");
        for (Node node : getChildren()) {
            buf.append("\t");
            buf.append(node.toString());
            if (node instanceof Variable || node instanceof Expression) {
                buf.append(";");
            }
            buf.append("\n");
        }
        buf.append("}\n");
        return buf.toString();
    }
}

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
public class Function implements Node {

    private final String name;

    private final Sequence arguments;

    private final Block body;

    public Function(String name, Sequence arguments, Block body) {
        this.name = name;
        this.arguments = arguments;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public Sequence getArguments() {
        return arguments;
    }

    public Block getBody() {
        return body;
    }

    public Object execute(Context context) throws ExecutionException {
        return body.execute(context);
    }
}

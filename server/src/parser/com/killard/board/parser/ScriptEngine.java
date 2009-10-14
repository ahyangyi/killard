package com.killard.board.parser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import java.io.File;
import java.io.IOException;
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
public class ScriptEngine {

    public ScriptEngine() {
    }

    public Map parse(CharStream input) throws RecognitionException {
        ScriptLexer lex = new ScriptLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        ScriptParser parser = new ScriptParser(tokens);
        ScriptParser.object_return r = parser.object();
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(r.tree);
        nodes.setTokenStream(tokens);
        CardBuilder builder = new CardBuilder(nodes);
        return builder.object();
    }

    public Map parse(String definition) throws RecognitionException {
        return parse(new ANTLRStringStream(definition));
    }

    public Map parse(File file) throws RecognitionException, IOException {
        return parse(new ANTLRFileStream(file.getAbsolutePath()));
    }
}

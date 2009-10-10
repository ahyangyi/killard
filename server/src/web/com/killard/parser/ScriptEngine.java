package com.killard.parser;

import com.killard.card.Card;
import com.killard.card.ElementSchool;
import com.killard.pack.magic.MagicCardFactory;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

    public static void main(String[] args) throws Exception {
        File dir = new File("server/web/WEB-INF/magic");
        if (!dir.exists()) dir.mkdir();
        for (ElementSchool school : MagicCardFactory.cards.keySet()) {
            File elementDir = new File(dir, school.toString());
            if (!elementDir.exists()) elementDir.mkdir();
            List<Card> cards = MagicCardFactory.cards.get(school);
            for (Card card : cards) {
                File cardFile = new File(elementDir, card.getClass().getSimpleName() + ".js");
                System.out.println(cardFile.getAbsolutePath());
                PrintWriter writer = new PrintWriter(cardFile);
                writer.println("{");
                if (card.getMaxHealth() > 0) {
                    writer.println();
                    writer.println("\tlevel : " + card.getLevel() + ",");
                    writer.println();
                    writer.println("\thealth : " + card.getMaxHealth() + ",");
                    writer.println();
                    writer.println("\tattack : " + card.getAttack().getValue());
                    writer.println();
                } else {
                    writer.println();
                    writer.println("\tlevel : " + card.getLevel());
                    writer.println();
                }
                writer.println("}");
                writer.close();
            }
        }
    }
}

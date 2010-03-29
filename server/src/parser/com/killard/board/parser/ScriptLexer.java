// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 Script.g 2010-03-29 12:31:43

package com.killard.board.parser;


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class ScriptLexer extends Lexer {
    public static final int FUNCTION=10;
    public static final int FloatTypeSuffix=40;
    public static final int OctalLiteral=43;
    public static final int EOF=-1;
    public static final int BREAK=21;
    public static final int Identifier=32;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__90=90;
    public static final int LOOP=18;
    public static final int RETURN=22;
    public static final int VAR=15;
    public static final int COMMENT=45;
    public static final int ARRAY=9;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int ARGUMENTS=17;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int LINE_COMMENT=46;
    public static final int IntegerTypeSuffix=33;
    public static final int SWITCH=23;
    public static final int NULL=28;
    public static final int NUMBER=5;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int SEQUENCE=12;
    public static final int T__71=71;
    public static final int WS=44;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int FloatingPointLiteral=30;
    public static final int FIELD=8;
    public static final int CALL=16;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int FALSE=27;
    public static final int T__74=74;
    public static final int T__73=73;
    public static final int Letter=34;
    public static final int EscapeSequence=36;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int T__77=77;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int Exponent=41;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int HexDigit=37;
    public static final int IF=19;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int BOOLEAN=6;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int OBJECT=7;
    public static final int CONTINUE=20;
    public static final int COMMA=25;
    public static final int T__59=59;
    public static final int PAR=14;
    public static final int UNARY=11;
    public static final int T__50=50;
    public static final int HexLiteral=42;
    public static final int T__47=47;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int Digit=38;
    public static final int DecimalLiteral=29;
    public static final int TRUE=26;
    public static final int StringLiteral=31;
    public static final int LABEL=24;
    public static final int UnicodeEscape=35;
    public static final int BLOCK=13;
    public static final int OctalEscape=39;
    public static final int STRING=4;

    // delegates
    // delegators

    public ScriptLexer() {;} 
    public ScriptLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ScriptLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "Script.g"; }

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:7:7: ( ',' )
            // Script.g:7:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:8:7: ( 'true' )
            // Script.g:8:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:9:7: ( 'false' )
            // Script.g:9:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:10:7: ( 'null' )
            // Script.g:10:9: 'null'
            {
            match("null"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:11:7: ( '{' )
            // Script.g:11:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:12:7: ( '}' )
            // Script.g:12:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:13:7: ( '[' )
            // Script.g:13:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:14:7: ( ']' )
            // Script.g:14:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:15:7: ( 'function' )
            // Script.g:15:9: 'function'
            {
            match("function"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:16:7: ( '(' )
            // Script.g:16:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:17:7: ( ')' )
            // Script.g:17:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:18:7: ( ':' )
            // Script.g:18:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:19:7: ( '=' )
            // Script.g:19:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:20:7: ( ';' )
            // Script.g:20:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:21:7: ( 'var' )
            // Script.g:21:9: 'var'
            {
            match("var"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:22:7: ( 'if' )
            // Script.g:22:9: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:23:7: ( 'else' )
            // Script.g:23:9: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:24:7: ( 'for' )
            // Script.g:24:9: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:25:7: ( 'while' )
            // Script.g:25:9: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:26:7: ( 'do' )
            // Script.g:26:9: 'do'
            {
            match("do"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:27:7: ( 'switch' )
            // Script.g:27:9: 'switch'
            {
            match("switch"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:28:7: ( 'return' )
            // Script.g:28:9: 'return'
            {
            match("return"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:29:7: ( 'break' )
            // Script.g:29:9: 'break'
            {
            match("break"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:30:7: ( 'continue' )
            // Script.g:30:9: 'continue'
            {
            match("continue"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:31:7: ( 'in' )
            // Script.g:31:9: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:32:7: ( 'case' )
            // Script.g:32:9: 'case'
            {
            match("case"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:33:7: ( 'default' )
            // Script.g:33:9: 'default'
            {
            match("default"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:34:7: ( '+=' )
            // Script.g:34:9: '+='
            {
            match("+="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:35:7: ( '-=' )
            // Script.g:35:9: '-='
            {
            match("-="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:36:7: ( '*=' )
            // Script.g:36:9: '*='
            {
            match("*="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:37:7: ( '/=' )
            // Script.g:37:9: '/='
            {
            match("/="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:38:7: ( '?' )
            // Script.g:38:9: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "T__78"
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:39:7: ( '||' )
            // Script.g:39:9: '||'
            {
            match("||"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "T__79"
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:40:7: ( '&&' )
            // Script.g:40:9: '&&'
            {
            match("&&"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__79"

    // $ANTLR start "T__80"
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:41:7: ( '==' )
            // Script.g:41:9: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__80"

    // $ANTLR start "T__81"
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:42:7: ( '!=' )
            // Script.g:42:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__81"

    // $ANTLR start "T__82"
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:43:7: ( 'instanceof' )
            // Script.g:43:9: 'instanceof'
            {
            match("instanceof"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "T__83"
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:44:7: ( '<=' )
            // Script.g:44:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:45:7: ( '>=' )
            // Script.g:45:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "T__85"
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:46:7: ( '<' )
            // Script.g:46:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__85"

    // $ANTLR start "T__86"
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:47:7: ( '>' )
            // Script.g:47:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__86"

    // $ANTLR start "T__87"
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:48:7: ( '+' )
            // Script.g:48:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__87"

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:49:7: ( '-' )
            // Script.g:49:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__88"

    // $ANTLR start "T__89"
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:50:7: ( '*' )
            // Script.g:50:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__89"

    // $ANTLR start "T__90"
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:51:7: ( '/' )
            // Script.g:51:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__90"

    // $ANTLR start "T__91"
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:52:7: ( '%' )
            // Script.g:52:9: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__91"

    // $ANTLR start "T__92"
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:53:7: ( '++' )
            // Script.g:53:9: '++'
            {
            match("++"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__92"

    // $ANTLR start "T__93"
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:54:7: ( '--' )
            // Script.g:54:9: '--'
            {
            match("--"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__93"

    // $ANTLR start "T__94"
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:55:7: ( '~' )
            // Script.g:55:9: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__94"

    // $ANTLR start "T__95"
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:56:7: ( '!' )
            // Script.g:56:9: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__95"

    // $ANTLR start "T__96"
    public final void mT__96() throws RecognitionException {
        try {
            int _type = T__96;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:57:7: ( '.' )
            // Script.g:57:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__96"

    // $ANTLR start "IntegerTypeSuffix"
    public final void mIntegerTypeSuffix() throws RecognitionException {
        try {
            // Script.g:225:28: ( ( 'l' | 'L' ) )
            // Script.g:225:30: ( 'l' | 'L' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "IntegerTypeSuffix"

    // $ANTLR start "Letter"
    public final void mLetter() throws RecognitionException {
        try {
            // Script.g:228:5: ( '\\u0024' | '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\u00f6' | '\\u00f8' .. '\\u00ff' | '\\u0100' .. '\\u1fff' | '\\u3040' .. '\\u318f' | '\\u3300' .. '\\u337f' | '\\u3400' .. '\\u3d2d' | '\\u4e00' .. '\\u9fff' | '\\uf900' .. '\\ufaff' )
            // Script.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Letter"

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // Script.g:244:6: ( '\\\\' ( UnicodeEscape | 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' | '\\/' ) )
            // Script.g:244:10: '\\\\' ( UnicodeEscape | 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' | '\\/' )
            {
            match('\\'); 
            // Script.g:244:15: ( UnicodeEscape | 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' | '\\/' )
            int alt1=10;
            switch ( input.LA(1) ) {
            case 'u':
                {
                alt1=1;
                }
                break;
            case 'b':
                {
                alt1=2;
                }
                break;
            case 't':
                {
                alt1=3;
                }
                break;
            case 'n':
                {
                alt1=4;
                }
                break;
            case 'f':
                {
                alt1=5;
                }
                break;
            case 'r':
                {
                alt1=6;
                }
                break;
            case '\"':
                {
                alt1=7;
                }
                break;
            case '\'':
                {
                alt1=8;
                }
                break;
            case '\\':
                {
                alt1=9;
                }
                break;
            case '/':
                {
                alt1=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // Script.g:244:16: UnicodeEscape
                    {
                    mUnicodeEscape(); 

                    }
                    break;
                case 2 :
                    // Script.g:244:31: 'b'
                    {
                    match('b'); 

                    }
                    break;
                case 3 :
                    // Script.g:244:35: 't'
                    {
                    match('t'); 

                    }
                    break;
                case 4 :
                    // Script.g:244:39: 'n'
                    {
                    match('n'); 

                    }
                    break;
                case 5 :
                    // Script.g:244:43: 'f'
                    {
                    match('f'); 

                    }
                    break;
                case 6 :
                    // Script.g:244:47: 'r'
                    {
                    match('r'); 

                    }
                    break;
                case 7 :
                    // Script.g:244:51: '\\\"'
                    {
                    match('\"'); 

                    }
                    break;
                case 8 :
                    // Script.g:244:56: '\\''
                    {
                    match('\''); 

                    }
                    break;
                case 9 :
                    // Script.g:244:61: '\\\\'
                    {
                    match('\\'); 

                    }
                    break;
                case 10 :
                    // Script.g:244:66: '\\/'
                    {
                    match('/'); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "EscapeSequence"

    // $ANTLR start "UnicodeEscape"
    public final void mUnicodeEscape() throws RecognitionException {
        try {
            // Script.g:248:2: ( 'u' HexDigit HexDigit HexDigit HexDigit )
            // Script.g:248:4: 'u' HexDigit HexDigit HexDigit HexDigit
            {
            match('u'); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UnicodeEscape"

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // Script.g:252:2: ( '0' .. '9' | 'A' .. 'F' | 'a' .. 'f' )
            // Script.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "HexDigit"

    // $ANTLR start "Digit"
    public final void mDigit() throws RecognitionException {
        try {
            // Script.g:256:2: ( '0' .. '9' )
            // Script.g:256:4: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "Digit"

    // $ANTLR start "OctalEscape"
    public final void mOctalEscape() throws RecognitionException {
        try {
            // Script.g:260:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt2=3;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='\\') ) {
                int LA2_1 = input.LA(2);

                if ( ((LA2_1>='0' && LA2_1<='3')) ) {
                    int LA2_2 = input.LA(3);

                    if ( ((LA2_2>='0' && LA2_2<='7')) ) {
                        int LA2_5 = input.LA(4);

                        if ( ((LA2_5>='0' && LA2_5<='7')) ) {
                            alt2=1;
                        }
                        else {
                            alt2=2;}
                    }
                    else {
                        alt2=3;}
                }
                else if ( ((LA2_1>='4' && LA2_1<='7')) ) {
                    int LA2_3 = input.LA(3);

                    if ( ((LA2_3>='0' && LA2_3<='7')) ) {
                        alt2=2;
                    }
                    else {
                        alt2=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // Script.g:260:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // Script.g:260:14: ( '0' .. '3' )
                    // Script.g:260:15: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // Script.g:260:25: ( '0' .. '7' )
                    // Script.g:260:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // Script.g:260:36: ( '0' .. '7' )
                    // Script.g:260:37: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // Script.g:261:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // Script.g:261:14: ( '0' .. '7' )
                    // Script.g:261:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // Script.g:261:25: ( '0' .. '7' )
                    // Script.g:261:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // Script.g:262:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // Script.g:262:14: ( '0' .. '7' )
                    // Script.g:262:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "OctalEscape"

    // $ANTLR start "FloatTypeSuffix"
    public final void mFloatTypeSuffix() throws RecognitionException {
        try {
            // Script.g:265:26: ( ( 'f' | 'F' | 'd' | 'D' ) )
            // Script.g:265:28: ( 'f' | 'F' | 'd' | 'D' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='F'||input.LA(1)=='d'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "FloatTypeSuffix"

    // $ANTLR start "Exponent"
    public final void mExponent() throws RecognitionException {
        try {
            int _type = Exponent;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:267:9: ( ( 'e' | 'E' ) ( '-' )? ( '1' .. '9' ) ( Digit )* )
            // Script.g:267:11: ( 'e' | 'E' ) ( '-' )? ( '1' .. '9' ) ( Digit )*
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // Script.g:267:21: ( '-' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='-') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Script.g:267:21: '-'
                    {
                    match('-'); 

                    }
                    break;

            }

            // Script.g:267:26: ( '1' .. '9' )
            // Script.g:267:27: '1' .. '9'
            {
            matchRange('1','9'); 

            }

            // Script.g:267:37: ( Digit )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // Script.g:267:37: Digit
            	    {
            	    mDigit(); 

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Exponent"

    // $ANTLR start "FloatingPointLiteral"
    public final void mFloatingPointLiteral() throws RecognitionException {
        try {
            int _type = FloatingPointLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:270:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )? | '.' ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )? | ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )? | ( '0' .. '9' )+ FloatTypeSuffix )
            int alt15=4;
            alt15 = dfa15.predict(input);
            switch (alt15) {
                case 1 :
                    // Script.g:270:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )?
                    {
                    // Script.g:270:9: ( '0' .. '9' )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // Script.g:270:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt5 >= 1 ) break loop5;
                                EarlyExitException eee =
                                    new EarlyExitException(5, input);
                                throw eee;
                        }
                        cnt5++;
                    } while (true);

                    match('.'); 
                    // Script.g:270:25: ( '0' .. '9' )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // Script.g:270:26: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);

                    // Script.g:270:37: ( Exponent )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='E'||LA7_0=='e') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // Script.g:270:37: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }

                    // Script.g:270:47: ( FloatTypeSuffix )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='D'||LA8_0=='F'||LA8_0=='d'||LA8_0=='f') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // Script.g:270:47: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // Script.g:271:9: '.' ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )?
                    {
                    match('.'); 
                    // Script.g:271:13: ( '0' .. '9' )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // Script.g:271:14: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);

                    // Script.g:271:25: ( Exponent )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='E'||LA10_0=='e') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // Script.g:271:25: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }

                    // Script.g:271:35: ( FloatTypeSuffix )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='D'||LA11_0=='F'||LA11_0=='d'||LA11_0=='f') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // Script.g:271:35: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // Script.g:272:9: ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )?
                    {
                    // Script.g:272:9: ( '0' .. '9' )+
                    int cnt12=0;
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // Script.g:272:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt12 >= 1 ) break loop12;
                                EarlyExitException eee =
                                    new EarlyExitException(12, input);
                                throw eee;
                        }
                        cnt12++;
                    } while (true);

                    mExponent(); 
                    // Script.g:272:30: ( FloatTypeSuffix )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0=='D'||LA13_0=='F'||LA13_0=='d'||LA13_0=='f') ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // Script.g:272:30: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // Script.g:273:9: ( '0' .. '9' )+ FloatTypeSuffix
                    {
                    // Script.g:273:9: ( '0' .. '9' )+
                    int cnt14=0;
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( ((LA14_0>='0' && LA14_0<='9')) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // Script.g:273:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt14 >= 1 ) break loop14;
                                EarlyExitException eee =
                                    new EarlyExitException(14, input);
                                throw eee;
                        }
                        cnt14++;
                    } while (true);

                    mFloatTypeSuffix(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FloatingPointLiteral"

    // $ANTLR start "HexLiteral"
    public final void mHexLiteral() throws RecognitionException {
        try {
            int _type = HexLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:276:12: ( '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )? )
            // Script.g:276:14: '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )?
            {
            match('0'); 
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // Script.g:276:28: ( HexDigit )+
            int cnt16=0;
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>='0' && LA16_0<='9')||(LA16_0>='A' && LA16_0<='F')||(LA16_0>='a' && LA16_0<='f')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // Script.g:276:28: HexDigit
            	    {
            	    mHexDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt16 >= 1 ) break loop16;
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        throw eee;
                }
                cnt16++;
            } while (true);

            // Script.g:276:38: ( IntegerTypeSuffix )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='L'||LA17_0=='l') ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // Script.g:276:38: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HexLiteral"

    // $ANTLR start "DecimalLiteral"
    public final void mDecimalLiteral() throws RecognitionException {
        try {
            int _type = DecimalLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:278:16: ( ( '0' | '1' .. '9' ( '0' .. '9' )* ) ( IntegerTypeSuffix )? )
            // Script.g:278:18: ( '0' | '1' .. '9' ( '0' .. '9' )* ) ( IntegerTypeSuffix )?
            {
            // Script.g:278:18: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='0') ) {
                alt19=1;
            }
            else if ( ((LA19_0>='1' && LA19_0<='9')) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // Script.g:278:19: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // Script.g:278:25: '1' .. '9' ( '0' .. '9' )*
                    {
                    matchRange('1','9'); 
                    // Script.g:278:34: ( '0' .. '9' )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( ((LA18_0>='0' && LA18_0<='9')) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // Script.g:278:34: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop18;
                        }
                    } while (true);


                    }
                    break;

            }

            // Script.g:278:45: ( IntegerTypeSuffix )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='L'||LA20_0=='l') ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // Script.g:278:45: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DecimalLiteral"

    // $ANTLR start "OctalLiteral"
    public final void mOctalLiteral() throws RecognitionException {
        try {
            int _type = OctalLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:280:14: ( '0' ( '0' .. '7' )+ ( IntegerTypeSuffix )? )
            // Script.g:280:16: '0' ( '0' .. '7' )+ ( IntegerTypeSuffix )?
            {
            match('0'); 
            // Script.g:280:20: ( '0' .. '7' )+
            int cnt21=0;
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( ((LA21_0>='0' && LA21_0<='7')) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // Script.g:280:21: '0' .. '7'
            	    {
            	    matchRange('0','7'); 

            	    }
            	    break;

            	default :
            	    if ( cnt21 >= 1 ) break loop21;
                        EarlyExitException eee =
                            new EarlyExitException(21, input);
                        throw eee;
                }
                cnt21++;
            } while (true);

            // Script.g:280:32: ( IntegerTypeSuffix )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0=='L'||LA22_0=='l') ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // Script.g:280:32: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OctalLiteral"

    // $ANTLR start "StringLiteral"
    public final void mStringLiteral() throws RecognitionException {
        try {
            int _type = StringLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:283:5: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' | '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) ) '\\'' )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0=='\"') ) {
                alt25=1;
            }
            else if ( (LA25_0=='\'') ) {
                alt25=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // Script.g:283:8: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
                    {
                    match('\"'); 
                    // Script.g:283:12: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
                    loop23:
                    do {
                        int alt23=3;
                        int LA23_0 = input.LA(1);

                        if ( (LA23_0=='\\') ) {
                            alt23=1;
                        }
                        else if ( ((LA23_0>='\u0000' && LA23_0<='!')||(LA23_0>='#' && LA23_0<='[')||(LA23_0>=']' && LA23_0<='\uFFFF')) ) {
                            alt23=2;
                        }


                        switch (alt23) {
                    	case 1 :
                    	    // Script.g:283:14: EscapeSequence
                    	    {
                    	    mEscapeSequence(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // Script.g:283:31: ~ ( '\\\\' | '\"' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop23;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // Script.g:284:8: '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) ) '\\''
                    {
                    match('\''); 
                    // Script.g:284:13: ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0=='\\') ) {
                        alt24=1;
                    }
                    else if ( ((LA24_0>='\u0000' && LA24_0<='&')||(LA24_0>='(' && LA24_0<='[')||(LA24_0>=']' && LA24_0<='\uFFFF')) ) {
                        alt24=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 24, 0, input);

                        throw nvae;
                    }
                    switch (alt24) {
                        case 1 :
                            // Script.g:284:15: EscapeSequence
                            {
                            mEscapeSequence(); 

                            }
                            break;
                        case 2 :
                            // Script.g:284:32: ~ ( '\\'' | '\\\\' )
                            {
                            if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}


                            }
                            break;

                    }

                    match('\''); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "StringLiteral"

    // $ANTLR start "Identifier"
    public final void mIdentifier() throws RecognitionException {
        try {
            int _type = Identifier;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:288:5: ( Letter ( Letter | Digit )* )
            // Script.g:288:9: Letter ( Letter | Digit )*
            {
            mLetter(); 
            // Script.g:288:16: ( Letter | Digit )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0=='$'||(LA26_0>='0' && LA26_0<='9')||(LA26_0>='A' && LA26_0<='Z')||LA26_0=='_'||(LA26_0>='a' && LA26_0<='z')||(LA26_0>='\u00C0' && LA26_0<='\u00D6')||(LA26_0>='\u00D8' && LA26_0<='\u00F6')||(LA26_0>='\u00F8' && LA26_0<='\u1FFF')||(LA26_0>='\u3040' && LA26_0<='\u318F')||(LA26_0>='\u3300' && LA26_0<='\u337F')||(LA26_0>='\u3400' && LA26_0<='\u3D2D')||(LA26_0>='\u4E00' && LA26_0<='\u9FFF')||(LA26_0>='\uF900' && LA26_0<='\uFAFF')) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // Script.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Identifier"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:291:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // Script.g:291:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:295:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // Script.g:295:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // Script.g:295:14: ( options {greedy=false; } : . )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0=='*') ) {
                    int LA27_1 = input.LA(2);

                    if ( (LA27_1=='/') ) {
                        alt27=2;
                    }
                    else if ( ((LA27_1>='\u0000' && LA27_1<='.')||(LA27_1>='0' && LA27_1<='\uFFFF')) ) {
                        alt27=1;
                    }


                }
                else if ( ((LA27_0>='\u0000' && LA27_0<=')')||(LA27_0>='+' && LA27_0<='\uFFFF')) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // Script.g:295:42: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

            match("*/"); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Script.g:299:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // Script.g:299:7: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // Script.g:299:12: (~ ( '\\n' | '\\r' ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( ((LA28_0>='\u0000' && LA28_0<='\t')||(LA28_0>='\u000B' && LA28_0<='\f')||(LA28_0>='\u000E' && LA28_0<='\uFFFF')) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // Script.g:299:12: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            // Script.g:299:26: ( '\\r' )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0=='\r') ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // Script.g:299:26: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    public void mTokens() throws RecognitionException {
        // Script.g:1:8: ( COMMA | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | Exponent | FloatingPointLiteral | HexLiteral | DecimalLiteral | OctalLiteral | StringLiteral | Identifier | WS | COMMENT | LINE_COMMENT )
        int alt30=61;
        alt30 = dfa30.predict(input);
        switch (alt30) {
            case 1 :
                // Script.g:1:10: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 2 :
                // Script.g:1:16: T__47
                {
                mT__47(); 

                }
                break;
            case 3 :
                // Script.g:1:22: T__48
                {
                mT__48(); 

                }
                break;
            case 4 :
                // Script.g:1:28: T__49
                {
                mT__49(); 

                }
                break;
            case 5 :
                // Script.g:1:34: T__50
                {
                mT__50(); 

                }
                break;
            case 6 :
                // Script.g:1:40: T__51
                {
                mT__51(); 

                }
                break;
            case 7 :
                // Script.g:1:46: T__52
                {
                mT__52(); 

                }
                break;
            case 8 :
                // Script.g:1:52: T__53
                {
                mT__53(); 

                }
                break;
            case 9 :
                // Script.g:1:58: T__54
                {
                mT__54(); 

                }
                break;
            case 10 :
                // Script.g:1:64: T__55
                {
                mT__55(); 

                }
                break;
            case 11 :
                // Script.g:1:70: T__56
                {
                mT__56(); 

                }
                break;
            case 12 :
                // Script.g:1:76: T__57
                {
                mT__57(); 

                }
                break;
            case 13 :
                // Script.g:1:82: T__58
                {
                mT__58(); 

                }
                break;
            case 14 :
                // Script.g:1:88: T__59
                {
                mT__59(); 

                }
                break;
            case 15 :
                // Script.g:1:94: T__60
                {
                mT__60(); 

                }
                break;
            case 16 :
                // Script.g:1:100: T__61
                {
                mT__61(); 

                }
                break;
            case 17 :
                // Script.g:1:106: T__62
                {
                mT__62(); 

                }
                break;
            case 18 :
                // Script.g:1:112: T__63
                {
                mT__63(); 

                }
                break;
            case 19 :
                // Script.g:1:118: T__64
                {
                mT__64(); 

                }
                break;
            case 20 :
                // Script.g:1:124: T__65
                {
                mT__65(); 

                }
                break;
            case 21 :
                // Script.g:1:130: T__66
                {
                mT__66(); 

                }
                break;
            case 22 :
                // Script.g:1:136: T__67
                {
                mT__67(); 

                }
                break;
            case 23 :
                // Script.g:1:142: T__68
                {
                mT__68(); 

                }
                break;
            case 24 :
                // Script.g:1:148: T__69
                {
                mT__69(); 

                }
                break;
            case 25 :
                // Script.g:1:154: T__70
                {
                mT__70(); 

                }
                break;
            case 26 :
                // Script.g:1:160: T__71
                {
                mT__71(); 

                }
                break;
            case 27 :
                // Script.g:1:166: T__72
                {
                mT__72(); 

                }
                break;
            case 28 :
                // Script.g:1:172: T__73
                {
                mT__73(); 

                }
                break;
            case 29 :
                // Script.g:1:178: T__74
                {
                mT__74(); 

                }
                break;
            case 30 :
                // Script.g:1:184: T__75
                {
                mT__75(); 

                }
                break;
            case 31 :
                // Script.g:1:190: T__76
                {
                mT__76(); 

                }
                break;
            case 32 :
                // Script.g:1:196: T__77
                {
                mT__77(); 

                }
                break;
            case 33 :
                // Script.g:1:202: T__78
                {
                mT__78(); 

                }
                break;
            case 34 :
                // Script.g:1:208: T__79
                {
                mT__79(); 

                }
                break;
            case 35 :
                // Script.g:1:214: T__80
                {
                mT__80(); 

                }
                break;
            case 36 :
                // Script.g:1:220: T__81
                {
                mT__81(); 

                }
                break;
            case 37 :
                // Script.g:1:226: T__82
                {
                mT__82(); 

                }
                break;
            case 38 :
                // Script.g:1:232: T__83
                {
                mT__83(); 

                }
                break;
            case 39 :
                // Script.g:1:238: T__84
                {
                mT__84(); 

                }
                break;
            case 40 :
                // Script.g:1:244: T__85
                {
                mT__85(); 

                }
                break;
            case 41 :
                // Script.g:1:250: T__86
                {
                mT__86(); 

                }
                break;
            case 42 :
                // Script.g:1:256: T__87
                {
                mT__87(); 

                }
                break;
            case 43 :
                // Script.g:1:262: T__88
                {
                mT__88(); 

                }
                break;
            case 44 :
                // Script.g:1:268: T__89
                {
                mT__89(); 

                }
                break;
            case 45 :
                // Script.g:1:274: T__90
                {
                mT__90(); 

                }
                break;
            case 46 :
                // Script.g:1:280: T__91
                {
                mT__91(); 

                }
                break;
            case 47 :
                // Script.g:1:286: T__92
                {
                mT__92(); 

                }
                break;
            case 48 :
                // Script.g:1:292: T__93
                {
                mT__93(); 

                }
                break;
            case 49 :
                // Script.g:1:298: T__94
                {
                mT__94(); 

                }
                break;
            case 50 :
                // Script.g:1:304: T__95
                {
                mT__95(); 

                }
                break;
            case 51 :
                // Script.g:1:310: T__96
                {
                mT__96(); 

                }
                break;
            case 52 :
                // Script.g:1:316: Exponent
                {
                mExponent(); 

                }
                break;
            case 53 :
                // Script.g:1:325: FloatingPointLiteral
                {
                mFloatingPointLiteral(); 

                }
                break;
            case 54 :
                // Script.g:1:346: HexLiteral
                {
                mHexLiteral(); 

                }
                break;
            case 55 :
                // Script.g:1:357: DecimalLiteral
                {
                mDecimalLiteral(); 

                }
                break;
            case 56 :
                // Script.g:1:372: OctalLiteral
                {
                mOctalLiteral(); 

                }
                break;
            case 57 :
                // Script.g:1:385: StringLiteral
                {
                mStringLiteral(); 

                }
                break;
            case 58 :
                // Script.g:1:399: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 59 :
                // Script.g:1:410: WS
                {
                mWS(); 

                }
                break;
            case 60 :
                // Script.g:1:413: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 61 :
                // Script.g:1:421: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;

        }

    }


    protected DFA15 dfa15 = new DFA15(this);
    protected DFA30 dfa30 = new DFA30(this);
    static final String DFA15_eotS =
        "\6\uffff";
    static final String DFA15_eofS =
        "\6\uffff";
    static final String DFA15_minS =
        "\2\56\4\uffff";
    static final String DFA15_maxS =
        "\1\71\1\146\4\uffff";
    static final String DFA15_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\1";
    static final String DFA15_specialS =
        "\6\uffff}>";
    static final String[] DFA15_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\5\1\uffff\12\1\12\uffff\1\4\1\3\1\4\35\uffff\1\4\1\3\1\4",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA15_eot = DFA.unpackEncodedString(DFA15_eotS);
    static final short[] DFA15_eof = DFA.unpackEncodedString(DFA15_eofS);
    static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars(DFA15_minS);
    static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars(DFA15_maxS);
    static final short[] DFA15_accept = DFA.unpackEncodedString(DFA15_acceptS);
    static final short[] DFA15_special = DFA.unpackEncodedString(DFA15_specialS);
    static final short[][] DFA15_transition;

    static {
        int numStates = DFA15_transitionS.length;
        DFA15_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
        }
    }

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = DFA15_eot;
            this.eof = DFA15_eof;
            this.min = DFA15_min;
            this.max = DFA15_max;
            this.accept = DFA15_accept;
            this.special = DFA15_special;
            this.transition = DFA15_transition;
        }
        public String getDescription() {
            return "269:1: FloatingPointLiteral : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )? | '.' ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )? | ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )? | ( '0' .. '9' )+ FloatTypeSuffix );";
        }
    }
    static final String DFA30_eotS =
        "\2\uffff\3\50\7\uffff\1\60\1\uffff\11\50\1\101\1\104\1\106\1\112"+
        "\3\uffff\1\114\1\116\1\120\2\uffff\1\121\1\50\2\125\3\uffff\5\50"+
        "\2\uffff\1\50\1\135\1\137\1\50\1\uffff\1\65\1\50\1\143\6\50\25\uffff"+
        "\1\152\1\uffff\1\125\3\50\1\156\1\50\1\160\1\uffff\1\50\1\uffff"+
        "\1\50\1\65\1\50\1\uffff\6\50\1\uffff\1\172\2\50\1\uffff\1\175\1"+
        "\uffff\1\50\1\177\6\50\1\u0086\1\uffff\1\u0087\1\50\1\uffff\1\50"+
        "\1\uffff\1\u008a\3\50\1\u008e\1\50\2\uffff\2\50\1\uffff\1\50\1\u0093"+
        "\1\u0094\1\uffff\3\50\1\u0098\2\uffff\1\50\1\u009a\1\50\1\uffff"+
        "\1\u009c\1\uffff\1\50\1\uffff\1\u009e\1\uffff";
    static final String DFA30_eofS =
        "\u009f\uffff";
    static final String DFA30_minS =
        "\1\11\1\uffff\1\162\1\141\1\165\7\uffff\1\75\1\uffff\1\141\1\146"+
        "\1\55\1\150\1\145\1\167\1\145\1\162\1\141\1\53\1\55\1\75\1\52\3"+
        "\uffff\3\75\2\uffff\1\60\1\55\2\56\3\uffff\1\165\1\154\1\156\1\162"+
        "\1\154\2\uffff\1\162\2\44\1\163\1\uffff\1\44\1\151\1\44\1\146\1"+
        "\151\1\164\1\145\1\156\1\163\25\uffff\1\56\1\uffff\1\56\1\145\1"+
        "\163\1\143\1\44\1\154\1\44\1\uffff\1\164\1\uffff\1\145\1\44\1\154"+
        "\1\uffff\1\141\1\164\1\165\1\141\1\164\1\145\1\uffff\1\44\1\145"+
        "\1\164\1\uffff\1\44\1\uffff\1\141\1\44\1\145\1\165\1\143\1\162\1"+
        "\153\1\151\1\44\1\uffff\1\44\1\151\1\uffff\1\156\1\uffff\1\44\1"+
        "\154\1\150\1\156\1\44\1\156\2\uffff\1\157\1\143\1\uffff\1\164\2"+
        "\44\1\uffff\1\165\1\156\1\145\1\44\2\uffff\1\145\1\44\1\157\1\uffff"+
        "\1\44\1\uffff\1\146\1\uffff\1\44\1\uffff";
    static final String DFA30_maxS =
        "\1\ufaff\1\uffff\1\162\2\165\7\uffff\1\75\1\uffff\1\141\1\156\1"+
        "\154\1\150\1\157\1\167\1\145\1\162\1\157\4\75\3\uffff\3\75\2\uffff"+
        "\2\71\1\170\1\146\3\uffff\1\165\1\154\1\156\1\162\1\154\2\uffff"+
        "\1\162\2\ufaff\1\163\1\uffff\1\ufaff\1\151\1\ufaff\1\146\1\151\1"+
        "\164\1\145\1\156\1\163\25\uffff\1\146\1\uffff\1\146\1\145\1\163"+
        "\1\143\1\ufaff\1\154\1\ufaff\1\uffff\1\164\1\uffff\1\145\1\ufaff"+
        "\1\154\1\uffff\1\141\1\164\1\165\1\141\1\164\1\145\1\uffff\1\ufaff"+
        "\1\145\1\164\1\uffff\1\ufaff\1\uffff\1\141\1\ufaff\1\145\1\165\1"+
        "\143\1\162\1\153\1\151\1\ufaff\1\uffff\1\ufaff\1\151\1\uffff\1\156"+
        "\1\uffff\1\ufaff\1\154\1\150\1\156\1\ufaff\1\156\2\uffff\1\157\1"+
        "\143\1\uffff\1\164\2\ufaff\1\uffff\1\165\1\156\1\145\1\ufaff\2\uffff"+
        "\1\145\1\ufaff\1\157\1\uffff\1\ufaff\1\uffff\1\146\1\uffff\1\ufaff"+
        "\1\uffff";
    static final String DFA30_acceptS =
        "\1\uffff\1\1\3\uffff\1\5\1\6\1\7\1\10\1\12\1\13\1\14\1\uffff\1\16"+
        "\15\uffff\1\40\1\41\1\42\3\uffff\1\56\1\61\4\uffff\1\71\1\72\1\73"+
        "\5\uffff\1\43\1\15\4\uffff\1\64\11\uffff\1\34\1\57\1\52\1\35\1\60"+
        "\1\53\1\36\1\54\1\37\1\74\1\75\1\55\1\44\1\62\1\46\1\50\1\47\1\51"+
        "\1\63\1\65\1\66\1\uffff\1\67\7\uffff\1\20\1\uffff\1\31\3\uffff\1"+
        "\24\6\uffff\1\70\3\uffff\1\22\1\uffff\1\17\11\uffff\1\2\2\uffff"+
        "\1\4\1\uffff\1\21\6\uffff\1\32\1\3\2\uffff\1\23\3\uffff\1\27\4\uffff"+
        "\1\25\1\26\3\uffff\1\33\1\uffff\1\11\1\uffff\1\30\1\uffff\1\45";
    static final String DFA30_specialS =
        "\u009f\uffff}>";
    static final String[] DFA30_transitionS = {
            "\2\51\1\uffff\2\51\22\uffff\1\51\1\36\1\47\1\uffff\1\50\1\41"+
            "\1\35\1\47\1\11\1\12\1\31\1\27\1\1\1\30\1\43\1\32\1\45\11\46"+
            "\1\13\1\15\1\37\1\14\1\40\1\33\1\uffff\4\50\1\44\25\50\1\7\1"+
            "\uffff\1\10\1\uffff\1\50\1\uffff\1\50\1\25\1\26\1\22\1\20\1"+
            "\3\2\50\1\17\4\50\1\4\3\50\1\24\1\23\1\2\1\50\1\16\1\21\3\50"+
            "\1\5\1\34\1\6\1\42\101\uffff\27\50\1\uffff\37\50\1\uffff\u1f08"+
            "\50\u1040\uffff\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e"+
            "\50\u10d2\uffff\u5200\50\u5900\uffff\u0200\50",
            "",
            "\1\52",
            "\1\53\15\uffff\1\55\5\uffff\1\54",
            "\1\56",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\57",
            "",
            "\1\61",
            "\1\62\7\uffff\1\63",
            "\1\65\3\uffff\11\66\62\uffff\1\64",
            "\1\67",
            "\1\71\11\uffff\1\70",
            "\1\72",
            "\1\73",
            "\1\74",
            "\1\76\15\uffff\1\75",
            "\1\100\21\uffff\1\77",
            "\1\103\17\uffff\1\102",
            "\1\105",
            "\1\110\4\uffff\1\111\15\uffff\1\107",
            "",
            "",
            "",
            "\1\113",
            "\1\115",
            "\1\117",
            "",
            "",
            "\12\122",
            "\1\65\3\uffff\11\66",
            "\1\122\1\uffff\10\124\2\122\12\uffff\3\122\21\uffff\1\123\13"+
            "\uffff\3\122\21\uffff\1\123",
            "\1\122\1\uffff\12\126\12\uffff\3\122\35\uffff\3\122",
            "",
            "",
            "",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "",
            "",
            "\1\134",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\22"+
            "\50\1\136\7\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50"+
            "\u1040\uffff\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e"+
            "\50\u10d2\uffff\u5200\50\u5900\uffff\u0200\50",
            "\1\140",
            "",
            "\1\50\13\uffff\12\141\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\142",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\144",
            "\1\145",
            "\1\146",
            "\1\147",
            "\1\150",
            "\1\151",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\122\1\uffff\10\124\2\122\12\uffff\3\122\35\uffff\3\122",
            "",
            "\1\122\1\uffff\12\126\12\uffff\3\122\35\uffff\3\122",
            "\1\153",
            "\1\154",
            "\1\155",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\157",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "",
            "\1\161",
            "",
            "\1\162",
            "\1\50\13\uffff\12\141\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\163",
            "",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "\1\170",
            "\1\171",
            "",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\173",
            "\1\174",
            "",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "",
            "\1\176",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "\1\u0085",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\u0088",
            "",
            "\1\u0089",
            "",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\u008b",
            "\1\u008c",
            "\1\u008d",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\u008f",
            "",
            "",
            "\1\u0090",
            "\1\u0091",
            "",
            "\1\u0092",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "",
            "",
            "\1\u0099",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "\1\u009b",
            "",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            "",
            "\1\u009d",
            "",
            "\1\50\13\uffff\12\50\7\uffff\32\50\4\uffff\1\50\1\uffff\32"+
            "\50\105\uffff\27\50\1\uffff\37\50\1\uffff\u1f08\50\u1040\uffff"+
            "\u0150\50\u0170\uffff\u0080\50\u0080\uffff\u092e\50\u10d2\uffff"+
            "\u5200\50\u5900\uffff\u0200\50",
            ""
    };

    static final short[] DFA30_eot = DFA.unpackEncodedString(DFA30_eotS);
    static final short[] DFA30_eof = DFA.unpackEncodedString(DFA30_eofS);
    static final char[] DFA30_min = DFA.unpackEncodedStringToUnsignedChars(DFA30_minS);
    static final char[] DFA30_max = DFA.unpackEncodedStringToUnsignedChars(DFA30_maxS);
    static final short[] DFA30_accept = DFA.unpackEncodedString(DFA30_acceptS);
    static final short[] DFA30_special = DFA.unpackEncodedString(DFA30_specialS);
    static final short[][] DFA30_transition;

    static {
        int numStates = DFA30_transitionS.length;
        DFA30_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA30_transition[i] = DFA.unpackEncodedString(DFA30_transitionS[i]);
        }
    }

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = DFA30_eot;
            this.eof = DFA30_eof;
            this.min = DFA30_min;
            this.max = DFA30_max;
            this.accept = DFA30_accept;
            this.special = DFA30_special;
            this.transition = DFA30_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( COMMA | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | Exponent | FloatingPointLiteral | HexLiteral | DecimalLiteral | OctalLiteral | StringLiteral | Identifier | WS | COMMENT | LINE_COMMENT );";
        }
    }
 

}
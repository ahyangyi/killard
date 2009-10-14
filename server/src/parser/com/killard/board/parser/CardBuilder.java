// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 CardBuilder.g 2009-10-06 20:53:41

package com.killard.board.parser;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CardBuilder extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "STRING", "NUMBER", "BOOLEAN", "OBJECT", "FIELD", "ARRAY", "FUNCTION", "UNARY", "SEQUENCE", "BLOCK", "PAR", "VAR", "CALL", "ARGUMENTS", "LOOP", "IF", "CONTINUE", "BREAK", "RETURN", "SWITCH", "LABEL", "COMMA", "TRUE", "FALSE", "NULL", "DecimalLiteral", "FloatingPointLiteral", "StringLiteral", "Identifier", "IntegerTypeSuffix", "Letter", "UnicodeEscape", "EscapeSequence", "HexDigit", "Digit", "OctalEscape", "FloatTypeSuffix", "Exponent", "HexLiteral", "OctalLiteral", "WS", "COMMENT", "LINE_COMMENT", "'true'", "'false'", "'null'", "'{'", "'}'", "'['", "']'", "'function'", "'('", "')'", "':'", "'='", "';'", "'var'", "'if'", "'else'", "'for'", "'while'", "'do'", "'switch'", "'return'", "'break'", "'continue'", "'in'", "'case'", "'default'", "'+='", "'-='", "'*='", "'/='", "'?'", "'||'", "'&&'", "'=='", "'!='", "'instanceof'", "'<='", "'>='", "'<'", "'>'", "'+'", "'-'", "'*'", "'/'", "'%'", "'++'", "'--'", "'~'", "'!'", "'.'"
    };
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


        public CardBuilder(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public CardBuilder(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return CardBuilder.tokenNames; }
    public String getGrammarFileName() { return "CardBuilder.g"; }



    // $ANTLR start "value"
    // CardBuilder.g:24:1: value returns [Object result] : (l= literal | o= object | a= array | f= function );
    public final Object value() throws RecognitionException {
        Object result = null;

        Expression l = null;

        Map o = null;

        List a = null;

        Function f = null;


        try {
            // CardBuilder.g:25:2: (l= literal | o= object | a= array | f= function )
            int alt1=4;
            switch ( input.LA(1) ) {
            case STRING:
            case NUMBER:
            case TRUE:
            case FALSE:
            case NULL:
                {
                alt1=1;
                }
                break;
            case OBJECT:
                {
                alt1=2;
                }
                break;
            case ARRAY:
                {
                alt1=3;
                }
                break;
            case FUNCTION:
                {
                alt1=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return result;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // CardBuilder.g:25:4: l= literal
                    {
                    pushFollow(FOLLOW_literal_in_value56);
                    l=literal();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = l; 
                    }

                    }
                    break;
                case 2 :
                    // CardBuilder.g:26:4: o= object
                    {
                    pushFollow(FOLLOW_object_in_value65);
                    o=object();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = o; 
                    }

                    }
                    break;
                case 3 :
                    // CardBuilder.g:27:4: a= array
                    {
                    pushFollow(FOLLOW_array_in_value74);
                    a=array();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = a; 
                    }

                    }
                    break;
                case 4 :
                    // CardBuilder.g:28:4: f= function
                    {
                    pushFollow(FOLLOW_function_in_value83);
                    f=function();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = f; 
                    }

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "value"


    // $ANTLR start "literal"
    // CardBuilder.g:31:1: literal returns [Expression result] : ( ^( STRING s= StringLiteral ) | ^( NUMBER d= DecimalLiteral ) | TRUE | FALSE | NULL );
    public final Expression literal() throws RecognitionException {
        Expression result = null;

        CommonTree s=null;
        CommonTree d=null;

        try {
            // CardBuilder.g:32:2: ( ^( STRING s= StringLiteral ) | ^( NUMBER d= DecimalLiteral ) | TRUE | FALSE | NULL )
            int alt2=5;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt2=1;
                }
                break;
            case NUMBER:
                {
                alt2=2;
                }
                break;
            case TRUE:
                {
                alt2=3;
                }
                break;
            case FALSE:
                {
                alt2=4;
                }
                break;
            case NULL:
                {
                alt2=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return result;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // CardBuilder.g:32:4: ^( STRING s= StringLiteral )
                    {
                    match(input,STRING,FOLLOW_STRING_in_literal101); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    s=(CommonTree)match(input,StringLiteral,FOLLOW_StringLiteral_in_literal105); if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new StringLiteral((s!=null?s.getText():null).substring(1, (s!=null?s.getText():null).length() - 1)); 
                    }

                    }
                    break;
                case 2 :
                    // CardBuilder.g:33:4: ^( NUMBER d= DecimalLiteral )
                    {
                    match(input,NUMBER,FOLLOW_NUMBER_in_literal114); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    d=(CommonTree)match(input,DecimalLiteral,FOLLOW_DecimalLiteral_in_literal118); if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new NumberLiteral((d!=null?d.getText():null)); 
                    }

                    }
                    break;
                case 3 :
                    // CardBuilder.g:34:4: TRUE
                    {
                    match(input,TRUE,FOLLOW_TRUE_in_literal126); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new BooleanLiteral("true"); 
                    }

                    }
                    break;
                case 4 :
                    // CardBuilder.g:35:4: FALSE
                    {
                    match(input,FALSE,FOLLOW_FALSE_in_literal133); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new BooleanLiteral("false"); 
                    }

                    }
                    break;
                case 5 :
                    // CardBuilder.g:36:4: NULL
                    {
                    match(input,NULL,FOLLOW_NULL_in_literal140); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Null(); 
                    }

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "literal"


    // $ANTLR start "array"
    // CardBuilder.g:39:1: array returns [List list] : ^( ARRAY (v= value )+ ) ;
    public final List array() throws RecognitionException {
        List list = null;

        Object v = null;


         list = new LinkedList(); 
        try {
            // CardBuilder.g:41:2: ( ^( ARRAY (v= value )+ ) )
            // CardBuilder.g:41:4: ^( ARRAY (v= value )+ )
            {
            match(input,ARRAY,FOLLOW_ARRAY_in_array162); if (state.failed) return list;

            match(input, Token.DOWN, null); if (state.failed) return list;
            // CardBuilder.g:41:12: (v= value )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>=STRING && LA3_0<=NUMBER)||LA3_0==OBJECT||(LA3_0>=ARRAY && LA3_0<=FUNCTION)||(LA3_0>=TRUE && LA3_0<=NULL)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // CardBuilder.g:41:13: v= value
            	    {
            	    pushFollow(FOLLOW_value_in_array167);
            	    v=value();

            	    state._fsp--;
            	    if (state.failed) return list;
            	    if ( state.backtracking==0 ) {
            	       list.add(v); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
            	    if (state.backtracking>0) {state.failed=true; return list;}
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            match(input, Token.UP, null); if (state.failed) return list;

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return list;
    }
    // $ANTLR end "array"


    // $ANTLR start "object"
    // CardBuilder.g:44:1: object returns [Map result] : ^( OBJECT ( pair[result] )+ ) ;
    public final Map object() throws RecognitionException {
        Map result = null;

         result = new HashMap(); 
        try {
            // CardBuilder.g:46:2: ( ^( OBJECT ( pair[result] )+ ) )
            // CardBuilder.g:46:4: ^( OBJECT ( pair[result] )+ )
            {
            match(input,OBJECT,FOLLOW_OBJECT_in_object194); if (state.failed) return result;

            match(input, Token.DOWN, null); if (state.failed) return result;
            // CardBuilder.g:46:13: ( pair[result] )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==FIELD) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // CardBuilder.g:0:0: pair[result]
            	    {
            	    pushFollow(FOLLOW_pair_in_object196);
            	    pair(result);

            	    state._fsp--;
            	    if (state.failed) return result;

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
            	    if (state.backtracking>0) {state.failed=true; return result;}
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            match(input, Token.UP, null); if (state.failed) return result;

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "object"


    // $ANTLR start "pair"
    // CardBuilder.g:49:1: pair[Map map] : ^( FIELD key= Identifier v= value ) ;
    public final void pair(Map map) throws RecognitionException {
        CommonTree key=null;
        Object v = null;


        try {
            // CardBuilder.g:50:2: ( ^( FIELD key= Identifier v= value ) )
            // CardBuilder.g:50:4: ^( FIELD key= Identifier v= value )
            {
            match(input,FIELD,FOLLOW_FIELD_in_pair213); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            key=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_pair217); if (state.failed) return ;
            pushFollow(FOLLOW_value_in_pair221);
            v=value();

            state._fsp--;
            if (state.failed) return ;

            match(input, Token.UP, null); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               map.put((key!=null?key.getText():null), v); 
            }

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "pair"


    // $ANTLR start "function"
    // CardBuilder.g:53:1: function returns [Function result] : ^( FUNCTION (i= Identifier )? (a= argumentDecls )? ( '[' ']' )* b= block ) ;
    public final Function function() throws RecognitionException {
        Function result = null;

        CommonTree i=null;
        Sequence a = null;

        Block b = null;


        try {
            // CardBuilder.g:54:2: ( ^( FUNCTION (i= Identifier )? (a= argumentDecls )? ( '[' ']' )* b= block ) )
            // CardBuilder.g:54:4: ^( FUNCTION (i= Identifier )? (a= argumentDecls )? ( '[' ']' )* b= block )
            {
            match(input,FUNCTION,FOLLOW_FUNCTION_in_function240); if (state.failed) return result;

            match(input, Token.DOWN, null); if (state.failed) return result;
            // CardBuilder.g:54:16: (i= Identifier )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==Identifier) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // CardBuilder.g:0:0: i= Identifier
                    {
                    i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_function244); if (state.failed) return result;

                    }
                    break;

            }

            // CardBuilder.g:54:30: (a= argumentDecls )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==SEQUENCE) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // CardBuilder.g:0:0: a= argumentDecls
                    {
                    pushFollow(FOLLOW_argumentDecls_in_function249);
                    a=argumentDecls();

                    state._fsp--;
                    if (state.failed) return result;

                    }
                    break;

            }

            // CardBuilder.g:54:46: ( '[' ']' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==52) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // CardBuilder.g:54:47: '[' ']'
            	    {
            	    match(input,52,FOLLOW_52_in_function253); if (state.failed) return result;
            	    match(input,53,FOLLOW_53_in_function255); if (state.failed) return result;

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            pushFollow(FOLLOW_block_in_function261);
            b=block();

            state._fsp--;
            if (state.failed) return result;

            match(input, Token.UP, null); if (state.failed) return result;
            if ( state.backtracking==0 ) {
               result = new Function((i!=null?i.getText():null), a, b); 
            }

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "function"


    // $ANTLR start "variableDeclarators"
    // CardBuilder.g:58:1: variableDeclarators returns [Sequence result] : ^( SEQUENCE (v= variableDeclarator )+ ) ;
    public final Sequence variableDeclarators() throws RecognitionException {
        Sequence result = null;

        Variable v = null;


         result = new Sequence(); 
        try {
            // CardBuilder.g:60:5: ( ^( SEQUENCE (v= variableDeclarator )+ ) )
            // CardBuilder.g:60:9: ^( SEQUENCE (v= variableDeclarator )+ )
            {
            match(input,SEQUENCE,FOLLOW_SEQUENCE_in_variableDeclarators291); if (state.failed) return result;

            match(input, Token.DOWN, null); if (state.failed) return result;
            // CardBuilder.g:60:20: (v= variableDeclarator )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==VAR) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // CardBuilder.g:60:21: v= variableDeclarator
            	    {
            	    pushFollow(FOLLOW_variableDeclarator_in_variableDeclarators296);
            	    v=variableDeclarator();

            	    state._fsp--;
            	    if (state.failed) return result;
            	    if ( state.backtracking==0 ) {
            	      result.addChild(v);
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
            	    if (state.backtracking>0) {state.failed=true; return result;}
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);


            match(input, Token.UP, null); if (state.failed) return result;

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "variableDeclarators"


    // $ANTLR start "variableDeclarator"
    // CardBuilder.g:63:1: variableDeclarator returns [Variable result] : ^( VAR i= Identifier (e= expression )? ) ;
    public final Variable variableDeclarator() throws RecognitionException {
        Variable result = null;

        CommonTree i=null;
        Expression e = null;


        try {
            // CardBuilder.g:64:5: ( ^( VAR i= Identifier (e= expression )? ) )
            // CardBuilder.g:64:9: ^( VAR i= Identifier (e= expression )? )
            {
            match(input,VAR,FOLLOW_VAR_in_variableDeclarator324); if (state.failed) return result;

            match(input, Token.DOWN, null); if (state.failed) return result;
            i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_variableDeclarator328); if (state.failed) return result;
            // CardBuilder.g:64:29: (e= expression )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>=STRING && LA9_0<=NUMBER)||LA9_0==UNARY||LA9_0==PAR||LA9_0==CALL||(LA9_0>=TRUE && LA9_0<=NULL)||LA9_0==58||(LA9_0>=73 && LA9_0<=91)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // CardBuilder.g:0:0: e= expression
                    {
                    pushFollow(FOLLOW_expression_in_variableDeclarator332);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    }
                    break;

            }


            match(input, Token.UP, null); if (state.failed) return result;
            if ( state.backtracking==0 ) {
              result=new Variable((i!=null?i.getText():null),e);
            }

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "variableDeclarator"


    // $ANTLR start "argumentDecls"
    // CardBuilder.g:67:1: argumentDecls returns [Sequence result] : ^( SEQUENCE (i= Identifier )+ ) ;
    public final Sequence argumentDecls() throws RecognitionException {
        Sequence result = null;

        CommonTree i=null;

         result = new Sequence(); 
        try {
            // CardBuilder.g:69:5: ( ^( SEQUENCE (i= Identifier )+ ) )
            // CardBuilder.g:69:9: ^( SEQUENCE (i= Identifier )+ )
            {
            match(input,SEQUENCE,FOLLOW_SEQUENCE_in_argumentDecls365); if (state.failed) return result;

            match(input, Token.DOWN, null); if (state.failed) return result;
            // CardBuilder.g:69:20: (i= Identifier )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==Identifier) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // CardBuilder.g:69:21: i= Identifier
            	    {
            	    i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_argumentDecls370); if (state.failed) return result;
            	    if ( state.backtracking==0 ) {
            	      result.addChild(new Variable((i!=null?i.getText():null)));
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
            	    if (state.backtracking>0) {state.failed=true; return result;}
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            match(input, Token.UP, null); if (state.failed) return result;

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "argumentDecls"


    // $ANTLR start "block"
    // CardBuilder.g:73:1: block returns [Block result] : ^( BLOCK (s= statement )* ) ;
    public final Block block() throws RecognitionException {
        Block result = null;

        Node s = null;


         result = new Block();
        try {
            // CardBuilder.g:75:5: ( ^( BLOCK (s= statement )* ) )
            // CardBuilder.g:75:9: ^( BLOCK (s= statement )* )
            {
            match(input,BLOCK,FOLLOW_BLOCK_in_block403); if (state.failed) return result;

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); if (state.failed) return result;
                // CardBuilder.g:75:17: (s= statement )*
                loop11:
                do {
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( ((LA11_0>=STRING && LA11_0<=NUMBER)||LA11_0==UNARY||(LA11_0>=BLOCK && LA11_0<=CALL)||(LA11_0>=LOOP && LA11_0<=SWITCH)||(LA11_0>=TRUE && LA11_0<=NULL)||LA11_0==58||(LA11_0>=73 && LA11_0<=91)) ) {
                        alt11=1;
                    }


                    switch (alt11) {
                	case 1 :
                	    // CardBuilder.g:75:18: s= statement
                	    {
                	    pushFollow(FOLLOW_statement_in_block408);
                	    s=statement();

                	    state._fsp--;
                	    if (state.failed) return result;
                	    if ( state.backtracking==0 ) {
                	       result.addChild(s); 
                	    }

                	    }
                	    break;

                	default :
                	    break loop11;
                    }
                } while (true);


                match(input, Token.UP, null); if (state.failed) return result;
            }

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "block"


    // $ANTLR start "statement"
    // CardBuilder.g:78:1: statement returns [Node result] : (blk= block | e= expression | ^( VAR v= variableDeclarators ) | ^( IF c= parExpression t= statement (f= statement )? ) | ^( LOOP (i= Identifier )? ctrl= loopControl body= statement ) | ^( LOOP (i= Identifier )? cond= parExpression body= statement ) | ^( LOOP (i= Identifier )? body= statement cond= parExpression ) | ^( SWITCH parExpression ) | ^( RETURN (e= expression )? ) | ^( BREAK (i= Identifier )? ) | ^( CONTINUE (i= Identifier )? ) );
    public final Node statement() throws RecognitionException {
        Node result = null;

        CommonTree i=null;
        Block blk = null;

        Expression e = null;

        Sequence v = null;

        Expression c = null;

        Node t = null;

        Node f = null;

        LoopControl ctrl = null;

        Node body = null;

        Expression cond = null;


        try {
            // CardBuilder.g:79:5: (blk= block | e= expression | ^( VAR v= variableDeclarators ) | ^( IF c= parExpression t= statement (f= statement )? ) | ^( LOOP (i= Identifier )? ctrl= loopControl body= statement ) | ^( LOOP (i= Identifier )? cond= parExpression body= statement ) | ^( LOOP (i= Identifier )? body= statement cond= parExpression ) | ^( SWITCH parExpression ) | ^( RETURN (e= expression )? ) | ^( BREAK (i= Identifier )? ) | ^( CONTINUE (i= Identifier )? ) )
            int alt19=11;
            alt19 = dfa19.predict(input);
            switch (alt19) {
                case 1 :
                    // CardBuilder.g:79:9: blk= block
                    {
                    pushFollow(FOLLOW_block_in_statement437);
                    blk=block();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = blk;
                    }

                    }
                    break;
                case 2 :
                    // CardBuilder.g:80:9: e= expression
                    {
                    pushFollow(FOLLOW_expression_in_statement451);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = e; 
                    }

                    }
                    break;
                case 3 :
                    // CardBuilder.g:81:9: ^( VAR v= variableDeclarators )
                    {
                    match(input,VAR,FOLLOW_VAR_in_statement464); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_variableDeclarators_in_statement468);
                    v=variableDeclarators();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = v; 
                    }

                    }
                    break;
                case 4 :
                    // CardBuilder.g:82:9: ^( IF c= parExpression t= statement (f= statement )? )
                    {
                    match(input,IF,FOLLOW_IF_in_statement482); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_parExpression_in_statement486);
                    c=parExpression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_statement_in_statement490);
                    t=statement();

                    state._fsp--;
                    if (state.failed) return result;
                    // CardBuilder.g:82:43: (f= statement )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( ((LA12_0>=STRING && LA12_0<=NUMBER)||LA12_0==UNARY||(LA12_0>=BLOCK && LA12_0<=CALL)||(LA12_0>=LOOP && LA12_0<=SWITCH)||(LA12_0>=TRUE && LA12_0<=NULL)||LA12_0==58||(LA12_0>=73 && LA12_0<=91)) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // CardBuilder.g:0:0: f= statement
                            {
                            pushFollow(FOLLOW_statement_in_statement494);
                            f=statement();

                            state._fsp--;
                            if (state.failed) return result;

                            }
                            break;

                    }


                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new If(c,t,f); 
                    }

                    }
                    break;
                case 5 :
                    // CardBuilder.g:83:9: ^( LOOP (i= Identifier )? ctrl= loopControl body= statement )
                    {
                    match(input,LOOP,FOLLOW_LOOP_in_statement509); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    // CardBuilder.g:83:17: (i= Identifier )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==Identifier) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // CardBuilder.g:0:0: i= Identifier
                            {
                            i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_statement513); if (state.failed) return result;

                            }
                            break;

                    }

                    pushFollow(FOLLOW_loopControl_in_statement518);
                    ctrl=loopControl();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_statement_in_statement522);
                    body=statement();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Loop((i!=null?i.getText():null), ctrl, body);
                    }

                    }
                    break;
                case 6 :
                    // CardBuilder.g:84:9: ^( LOOP (i= Identifier )? cond= parExpression body= statement )
                    {
                    match(input,LOOP,FOLLOW_LOOP_in_statement536); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    // CardBuilder.g:84:17: (i= Identifier )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==Identifier) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // CardBuilder.g:0:0: i= Identifier
                            {
                            i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_statement540); if (state.failed) return result;

                            }
                            break;

                    }

                    pushFollow(FOLLOW_parExpression_in_statement545);
                    cond=parExpression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_statement_in_statement549);
                    body=statement();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Loop((i!=null?i.getText():null), new ForControl(null,cond,null),body); 
                    }

                    }
                    break;
                case 7 :
                    // CardBuilder.g:85:9: ^( LOOP (i= Identifier )? body= statement cond= parExpression )
                    {
                    match(input,LOOP,FOLLOW_LOOP_in_statement563); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    // CardBuilder.g:85:17: (i= Identifier )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==Identifier) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // CardBuilder.g:0:0: i= Identifier
                            {
                            i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_statement567); if (state.failed) return result;

                            }
                            break;

                    }

                    pushFollow(FOLLOW_statement_in_statement572);
                    body=statement();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_parExpression_in_statement576);
                    cond=parExpression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new Loop((i!=null?i.getText():null), new ForControl(body,cond,null),body); 
                    }

                    }
                    break;
                case 8 :
                    // CardBuilder.g:86:9: ^( SWITCH parExpression )
                    {
                    match(input,SWITCH,FOLLOW_SWITCH_in_statement590); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_parExpression_in_statement592);
                    parExpression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new DummyNode(); 
                    }

                    }
                    break;
                case 9 :
                    // CardBuilder.g:87:9: ^( RETURN (e= expression )? )
                    {
                    match(input,RETURN,FOLLOW_RETURN_in_statement606); if (state.failed) return result;

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); if (state.failed) return result;
                        // CardBuilder.g:87:19: (e= expression )?
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( ((LA16_0>=STRING && LA16_0<=NUMBER)||LA16_0==UNARY||LA16_0==PAR||LA16_0==CALL||(LA16_0>=TRUE && LA16_0<=NULL)||LA16_0==58||(LA16_0>=73 && LA16_0<=91)) ) {
                            alt16=1;
                        }
                        switch (alt16) {
                            case 1 :
                                // CardBuilder.g:0:0: e= expression
                                {
                                pushFollow(FOLLOW_expression_in_statement610);
                                e=expression();

                                state._fsp--;
                                if (state.failed) return result;

                                }
                                break;

                        }


                        match(input, Token.UP, null); if (state.failed) return result;
                    }
                    if ( state.backtracking==0 ) {
                       result = new Return(e); 
                    }

                    }
                    break;
                case 10 :
                    // CardBuilder.g:88:9: ^( BREAK (i= Identifier )? )
                    {
                    match(input,BREAK,FOLLOW_BREAK_in_statement625); if (state.failed) return result;

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); if (state.failed) return result;
                        // CardBuilder.g:88:18: (i= Identifier )?
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==Identifier) ) {
                            alt17=1;
                        }
                        switch (alt17) {
                            case 1 :
                                // CardBuilder.g:0:0: i= Identifier
                                {
                                i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_statement629); if (state.failed) return result;

                                }
                                break;

                        }


                        match(input, Token.UP, null); if (state.failed) return result;
                    }
                    if ( state.backtracking==0 ) {
                       result = new Break((i!=null?i.getText():null)); 
                    }

                    }
                    break;
                case 11 :
                    // CardBuilder.g:89:9: ^( CONTINUE (i= Identifier )? )
                    {
                    match(input,CONTINUE,FOLLOW_CONTINUE_in_statement644); if (state.failed) return result;

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); if (state.failed) return result;
                        // CardBuilder.g:89:21: (i= Identifier )?
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==Identifier) ) {
                            alt18=1;
                        }
                        switch (alt18) {
                            case 1 :
                                // CardBuilder.g:0:0: i= Identifier
                                {
                                i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_statement648); if (state.failed) return result;

                                }
                                break;

                        }


                        match(input, Token.UP, null); if (state.failed) return result;
                    }
                    if ( state.backtracking==0 ) {
                       result = new Continue((i!=null?i.getText():null)); 
                    }

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "statement"


    // $ANTLR start "loopControl"
    // CardBuilder.g:92:1: loopControl returns [LoopControl result] : ( ^( 'in' i= Identifier e= expression ) | ^( ';' (init= forInit )? (e= expression )? ';' (u= forUpdate )? ) );
    public final LoopControl loopControl() throws RecognitionException {
        LoopControl result = null;

        CommonTree i=null;
        Expression e = null;

        Sequence init = null;

        Sequence u = null;


        try {
            // CardBuilder.g:93:5: ( ^( 'in' i= Identifier e= expression ) | ^( ';' (init= forInit )? (e= expression )? ';' (u= forUpdate )? ) )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==70) ) {
                alt23=1;
            }
            else if ( (LA23_0==59) ) {
                alt23=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return result;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // CardBuilder.g:93:9: ^( 'in' i= Identifier e= expression )
                    {
                    match(input,70,FOLLOW_70_in_loopControl676); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_loopControl680); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_loopControl684);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new ForEachControl(new Variable((i!=null?i.getText():null)), e); 
                    }

                    }
                    break;
                case 2 :
                    // CardBuilder.g:94:9: ^( ';' (init= forInit )? (e= expression )? ';' (u= forUpdate )? )
                    {
                    match(input,59,FOLLOW_59_in_loopControl698); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    // CardBuilder.g:94:19: (init= forInit )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==SEQUENCE||LA20_0==VAR) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // CardBuilder.g:0:0: init= forInit
                            {
                            pushFollow(FOLLOW_forInit_in_loopControl702);
                            init=forInit();

                            state._fsp--;
                            if (state.failed) return result;

                            }
                            break;

                    }

                    // CardBuilder.g:94:30: (e= expression )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( ((LA21_0>=STRING && LA21_0<=NUMBER)||LA21_0==UNARY||LA21_0==PAR||LA21_0==CALL||(LA21_0>=TRUE && LA21_0<=NULL)||LA21_0==58||(LA21_0>=73 && LA21_0<=91)) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // CardBuilder.g:0:0: e= expression
                            {
                            pushFollow(FOLLOW_expression_in_loopControl707);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return result;

                            }
                            break;

                    }

                    match(input,59,FOLLOW_59_in_loopControl710); if (state.failed) return result;
                    // CardBuilder.g:94:48: (u= forUpdate )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==SEQUENCE) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // CardBuilder.g:0:0: u= forUpdate
                            {
                            pushFollow(FOLLOW_forUpdate_in_loopControl714);
                            u=forUpdate();

                            state._fsp--;
                            if (state.failed) return result;

                            }
                            break;

                    }


                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = new ForControl(init, e, u); 
                    }

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "loopControl"


    // $ANTLR start "forInit"
    // CardBuilder.g:97:1: forInit returns [Sequence result] : ( ^( VAR v= variableDeclarators ) | e= expressionList );
    public final Sequence forInit() throws RecognitionException {
        Sequence result = null;

        Sequence v = null;

        Sequence e = null;


        try {
            // CardBuilder.g:98:5: ( ^( VAR v= variableDeclarators ) | e= expressionList )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==VAR) ) {
                alt24=1;
            }
            else if ( (LA24_0==SEQUENCE) ) {
                alt24=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return result;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // CardBuilder.g:98:9: ^( VAR v= variableDeclarators )
                    {
                    match(input,VAR,FOLLOW_VAR_in_forInit742); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_variableDeclarators_in_forInit746);
                    v=variableDeclarators();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = v; 
                    }

                    }
                    break;
                case 2 :
                    // CardBuilder.g:99:9: e= expressionList
                    {
                    pushFollow(FOLLOW_expressionList_in_forInit761);
                    e=expressionList();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                       result = e; 
                    }

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "forInit"


    // $ANTLR start "forUpdate"
    // CardBuilder.g:102:1: forUpdate returns [Sequence result] : e= expressionList ;
    public final Sequence forUpdate() throws RecognitionException {
        Sequence result = null;

        Sequence e = null;


        try {
            // CardBuilder.g:103:5: (e= expressionList )
            // CardBuilder.g:103:9: e= expressionList
            {
            pushFollow(FOLLOW_expressionList_in_forUpdate788);
            e=expressionList();

            state._fsp--;
            if (state.failed) return result;
            if ( state.backtracking==0 ) {
               result = e; 
            }

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "forUpdate"


    // $ANTLR start "parExpression"
    // CardBuilder.g:107:1: parExpression returns [Expression result] : ^( PAR e= expression ) ;
    public final Expression parExpression() throws RecognitionException {
        Expression result = null;

        Expression e = null;


        try {
            // CardBuilder.g:108:5: ( ^( PAR e= expression ) )
            // CardBuilder.g:108:9: ^( PAR e= expression )
            {
            match(input,PAR,FOLLOW_PAR_in_parExpression815); if (state.failed) return result;

            match(input, Token.DOWN, null); if (state.failed) return result;
            pushFollow(FOLLOW_expression_in_parExpression819);
            e=expression();

            state._fsp--;
            if (state.failed) return result;

            match(input, Token.UP, null); if (state.failed) return result;
            if ( state.backtracking==0 ) {
               result = e; 
            }

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "parExpression"


    // $ANTLR start "expressionList"
    // CardBuilder.g:111:1: expressionList returns [Sequence result] : ^( SEQUENCE (e= expression )+ ) ;
    public final Sequence expressionList() throws RecognitionException {
        Sequence result = null;

        Expression e = null;


         result = new Sequence(); 
        try {
            // CardBuilder.g:113:5: ( ^( SEQUENCE (e= expression )+ ) )
            // CardBuilder.g:113:9: ^( SEQUENCE (e= expression )+ )
            {
            match(input,SEQUENCE,FOLLOW_SEQUENCE_in_expressionList850); if (state.failed) return result;

            match(input, Token.DOWN, null); if (state.failed) return result;
            // CardBuilder.g:113:20: (e= expression )+
            int cnt25=0;
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0>=STRING && LA25_0<=NUMBER)||LA25_0==UNARY||LA25_0==PAR||LA25_0==CALL||(LA25_0>=TRUE && LA25_0<=NULL)||LA25_0==58||(LA25_0>=73 && LA25_0<=91)) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // CardBuilder.g:113:21: e= expression
            	    {
            	    pushFollow(FOLLOW_expression_in_expressionList855);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return result;
            	    if ( state.backtracking==0 ) {
            	       result.addChild(e); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt25 >= 1 ) break loop25;
            	    if (state.backtracking>0) {state.failed=true; return result;}
                        EarlyExitException eee =
                            new EarlyExitException(25, input);
                        throw eee;
                }
                cnt25++;
            } while (true);


            match(input, Token.UP, null); if (state.failed) return result;

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "expressionList"


    // $ANTLR start "expression"
    // CardBuilder.g:116:1: expression returns [Expression result] : ( ^( '=' e1= expression e2= expression ) | ^( '+=' e1= expression e2= expression ) | ^( '-=' e1= expression e2= expression ) | ^( '*=' e1= expression e2= expression ) | ^( '/=' e1= expression e2= expression ) | ^( '?' e1= expression e2= expression e3= expression ) | ^( '||' e1= expression e2= expression ) | ^( '&&' e1= expression e2= expression ) | ^( '==' e1= expression e2= expression ) | ^( '!=' e1= expression e2= expression ) | ^( 'instanceof' e1= expression e2= expression ) | ^( '<=' e1= expression e2= expression ) | ^( '>=' e1= expression e2= expression ) | ^( '<' e1= expression e2= expression ) | ^( '>' e1= expression e2= expression ) | ^( '+' e1= expression e2= expression ) | ^( '-' e1= expression e2= expression ) | ^( '*' e1= expression e2= expression ) | ^( '/' e1= expression e2= expression ) | ^( '%' e1= expression e2= expression ) | ^( UNARY '+' e= expression ) | ^( UNARY '-' e= expression ) | ^( UNARY '++' e= expression ) | ^( UNARY '--' e= expression ) | ^( UNARY '~' e= expression ) | ^( UNARY '!' e= expression ) | ^( UNARY e= expression '++' ) | ^( UNARY e= expression '--' ) | p= parExpression | l= literal | s= sequenceCall );
    public final Expression expression() throws RecognitionException {
        Expression result = null;

        Expression e1 = null;

        Expression e2 = null;

        Expression e3 = null;

        Expression e = null;

        Expression p = null;

        Expression l = null;

        SequenceCall s = null;


        try {
            // CardBuilder.g:117:5: ( ^( '=' e1= expression e2= expression ) | ^( '+=' e1= expression e2= expression ) | ^( '-=' e1= expression e2= expression ) | ^( '*=' e1= expression e2= expression ) | ^( '/=' e1= expression e2= expression ) | ^( '?' e1= expression e2= expression e3= expression ) | ^( '||' e1= expression e2= expression ) | ^( '&&' e1= expression e2= expression ) | ^( '==' e1= expression e2= expression ) | ^( '!=' e1= expression e2= expression ) | ^( 'instanceof' e1= expression e2= expression ) | ^( '<=' e1= expression e2= expression ) | ^( '>=' e1= expression e2= expression ) | ^( '<' e1= expression e2= expression ) | ^( '>' e1= expression e2= expression ) | ^( '+' e1= expression e2= expression ) | ^( '-' e1= expression e2= expression ) | ^( '*' e1= expression e2= expression ) | ^( '/' e1= expression e2= expression ) | ^( '%' e1= expression e2= expression ) | ^( UNARY '+' e= expression ) | ^( UNARY '-' e= expression ) | ^( UNARY '++' e= expression ) | ^( UNARY '--' e= expression ) | ^( UNARY '~' e= expression ) | ^( UNARY '!' e= expression ) | ^( UNARY e= expression '++' ) | ^( UNARY e= expression '--' ) | p= parExpression | l= literal | s= sequenceCall )
            int alt26=31;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // CardBuilder.g:117:9: ^( '=' e1= expression e2= expression )
                    {
                    match(input,58,FOLLOW_58_in_expression884); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression888);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression892);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new AssignmentExpression(e1, e2);
                    }

                    }
                    break;
                case 2 :
                    // CardBuilder.g:118:9: ^( '+=' e1= expression e2= expression )
                    {
                    match(input,73,FOLLOW_73_in_expression906); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression910);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression914);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new PlusAssignmentExpression(e1, e2);
                    }

                    }
                    break;
                case 3 :
                    // CardBuilder.g:119:9: ^( '-=' e1= expression e2= expression )
                    {
                    match(input,74,FOLLOW_74_in_expression928); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression932);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression936);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new MinusAssignmentExpression(e1, e2);
                    }

                    }
                    break;
                case 4 :
                    // CardBuilder.g:120:9: ^( '*=' e1= expression e2= expression )
                    {
                    match(input,75,FOLLOW_75_in_expression950); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression954);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression958);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new MultiplyAssignmentExpression(e1, e2);
                    }

                    }
                    break;
                case 5 :
                    // CardBuilder.g:121:9: ^( '/=' e1= expression e2= expression )
                    {
                    match(input,76,FOLLOW_76_in_expression972); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression976);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression980);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new DivideAssignmentExpression(e1, e2);
                    }

                    }
                    break;
                case 6 :
                    // CardBuilder.g:122:9: ^( '?' e1= expression e2= expression e3= expression )
                    {
                    match(input,77,FOLLOW_77_in_expression994); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression998);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1002);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1006);
                    e3=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new ConditionExpression(e1, e2, e3);
                    }

                    }
                    break;
                case 7 :
                    // CardBuilder.g:123:9: ^( '||' e1= expression e2= expression )
                    {
                    match(input,78,FOLLOW_78_in_expression1020); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1024);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1028);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new ConditionalOrExpression(e1, e2);
                    }

                    }
                    break;
                case 8 :
                    // CardBuilder.g:124:9: ^( '&&' e1= expression e2= expression )
                    {
                    match(input,79,FOLLOW_79_in_expression1042); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1046);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1050);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new ConditionalAndExpression(e1, e2);
                    }

                    }
                    break;
                case 9 :
                    // CardBuilder.g:125:9: ^( '==' e1= expression e2= expression )
                    {
                    match(input,80,FOLLOW_80_in_expression1064); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1068);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1072);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new EqualExpression(e1, e2);
                    }

                    }
                    break;
                case 10 :
                    // CardBuilder.g:126:9: ^( '!=' e1= expression e2= expression )
                    {
                    match(input,81,FOLLOW_81_in_expression1086); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1090);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1094);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new NotEqualExpression(e1, e2);
                    }

                    }
                    break;
                case 11 :
                    // CardBuilder.g:127:9: ^( 'instanceof' e1= expression e2= expression )
                    {
                    match(input,82,FOLLOW_82_in_expression1108); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1112);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1116);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new InstanceOfExpression(e1, e2);
                    }

                    }
                    break;
                case 12 :
                    // CardBuilder.g:128:9: ^( '<=' e1= expression e2= expression )
                    {
                    match(input,83,FOLLOW_83_in_expression1130); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1134);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1138);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new LessThanOrEqualExpression(e1, e2);
                    }

                    }
                    break;
                case 13 :
                    // CardBuilder.g:129:9: ^( '>=' e1= expression e2= expression )
                    {
                    match(input,84,FOLLOW_84_in_expression1152); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1156);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1160);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new GreaterThanOrEqualExpression(e1, e2);
                    }

                    }
                    break;
                case 14 :
                    // CardBuilder.g:130:9: ^( '<' e1= expression e2= expression )
                    {
                    match(input,85,FOLLOW_85_in_expression1174); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1178);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1182);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new LessThanExpression(e1, e2);
                    }

                    }
                    break;
                case 15 :
                    // CardBuilder.g:131:9: ^( '>' e1= expression e2= expression )
                    {
                    match(input,86,FOLLOW_86_in_expression1196); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1200);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1204);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new GreaterThanExpression(e1, e2);
                    }

                    }
                    break;
                case 16 :
                    // CardBuilder.g:132:9: ^( '+' e1= expression e2= expression )
                    {
                    match(input,87,FOLLOW_87_in_expression1218); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1222);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1226);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new PlusExpression(e1, e2);
                    }

                    }
                    break;
                case 17 :
                    // CardBuilder.g:133:9: ^( '-' e1= expression e2= expression )
                    {
                    match(input,88,FOLLOW_88_in_expression1240); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1244);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1248);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new MinusExpression(e1, e2);
                    }

                    }
                    break;
                case 18 :
                    // CardBuilder.g:134:9: ^( '*' e1= expression e2= expression )
                    {
                    match(input,89,FOLLOW_89_in_expression1262); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1266);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1270);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new MultiplyExpression(e1, e2);
                    }

                    }
                    break;
                case 19 :
                    // CardBuilder.g:135:9: ^( '/' e1= expression e2= expression )
                    {
                    match(input,90,FOLLOW_90_in_expression1284); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1288);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1292);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new DivideExpression(e1, e2);
                    }

                    }
                    break;
                case 20 :
                    // CardBuilder.g:136:7: ^( '%' e1= expression e2= expression )
                    {
                    match(input,91,FOLLOW_91_in_expression1304); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1308);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1312);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new ModExpression(e1, e2);
                    }

                    }
                    break;
                case 21 :
                    // CardBuilder.g:137:7: ^( UNARY '+' e= expression )
                    {
                    match(input,UNARY,FOLLOW_UNARY_in_expression1324); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    match(input,87,FOLLOW_87_in_expression1326); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1330);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new UnaryPositiveExpression(e);
                    }

                    }
                    break;
                case 22 :
                    // CardBuilder.g:138:9: ^( UNARY '-' e= expression )
                    {
                    match(input,UNARY,FOLLOW_UNARY_in_expression1344); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    match(input,88,FOLLOW_88_in_expression1346); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1350);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new UnaryNegativeExpression(e);
                    }

                    }
                    break;
                case 23 :
                    // CardBuilder.g:139:9: ^( UNARY '++' e= expression )
                    {
                    match(input,UNARY,FOLLOW_UNARY_in_expression1364); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    match(input,92,FOLLOW_92_in_expression1366); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1370);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new UnaryIncreaseExpression(e);
                    }

                    }
                    break;
                case 24 :
                    // CardBuilder.g:140:9: ^( UNARY '--' e= expression )
                    {
                    match(input,UNARY,FOLLOW_UNARY_in_expression1384); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    match(input,93,FOLLOW_93_in_expression1386); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1390);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new UnaryDecreaseExpression(e);
                    }

                    }
                    break;
                case 25 :
                    // CardBuilder.g:141:9: ^( UNARY '~' e= expression )
                    {
                    match(input,UNARY,FOLLOW_UNARY_in_expression1404); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    match(input,94,FOLLOW_94_in_expression1406); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1410);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new UnaryNotExpression(e);
                    }

                    }
                    break;
                case 26 :
                    // CardBuilder.g:142:9: ^( UNARY '!' e= expression )
                    {
                    match(input,UNARY,FOLLOW_UNARY_in_expression1424); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    match(input,95,FOLLOW_95_in_expression1426); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1430);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new UnaryNotExpression(e);
                    }

                    }
                    break;
                case 27 :
                    // CardBuilder.g:143:9: ^( UNARY e= expression '++' )
                    {
                    match(input,UNARY,FOLLOW_UNARY_in_expression1444); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1448);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    match(input,92,FOLLOW_92_in_expression1450); if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new UnaryIncreaseExpression(e);
                    }

                    }
                    break;
                case 28 :
                    // CardBuilder.g:144:9: ^( UNARY e= expression '--' )
                    {
                    match(input,UNARY,FOLLOW_UNARY_in_expression1464); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression1468);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    match(input,93,FOLLOW_93_in_expression1470); if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=new UnaryDecreaseExpression(e);
                    }

                    }
                    break;
                case 29 :
                    // CardBuilder.g:145:9: p= parExpression
                    {
                    pushFollow(FOLLOW_parExpression_in_expression1485);
                    p=parExpression();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=p;
                    }

                    }
                    break;
                case 30 :
                    // CardBuilder.g:146:9: l= literal
                    {
                    pushFollow(FOLLOW_literal_in_expression1499);
                    l=literal();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=l;
                    }

                    }
                    break;
                case 31 :
                    // CardBuilder.g:147:9: s= sequenceCall
                    {
                    pushFollow(FOLLOW_sequenceCall_in_expression1513);
                    s=sequenceCall();

                    state._fsp--;
                    if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result=s;
                    }

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "expression"


    // $ANTLR start "sequenceCall"
    // CardBuilder.g:150:1: sequenceCall returns [SequenceCall result] : ^( CALL c= call ( subCall[result] )* ) ;
    public final SequenceCall sequenceCall() throws RecognitionException {
        SequenceCall result = null;

        SequenceCall c = null;


        try {
            // CardBuilder.g:151:5: ( ^( CALL c= call ( subCall[result] )* ) )
            // CardBuilder.g:151:9: ^( CALL c= call ( subCall[result] )* )
            {
            match(input,CALL,FOLLOW_CALL_in_sequenceCall1539); if (state.failed) return result;

            match(input, Token.DOWN, null); if (state.failed) return result;
            pushFollow(FOLLOW_call_in_sequenceCall1543);
            c=call();

            state._fsp--;
            if (state.failed) return result;
            if ( state.backtracking==0 ) {
              result=c;
            }
            // CardBuilder.g:151:35: ( subCall[result] )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==CALL) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // CardBuilder.g:151:36: subCall[result]
            	    {
            	    pushFollow(FOLLOW_subCall_in_sequenceCall1548);
            	    subCall(result);

            	    state._fsp--;
            	    if (state.failed) return result;

            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);


            match(input, Token.UP, null); if (state.failed) return result;

            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "sequenceCall"


    // $ANTLR start "call"
    // CardBuilder.g:154:1: call returns [SequenceCall result] : ( ^( CALL FUNCTION i= Identifier (a= expressionList )? arrayCall[result] ) | ^( CALL VAR i= Identifier arrayCall[result] ) );
    public final SequenceCall call() throws RecognitionException {
        SequenceCall result = null;

        CommonTree i=null;
        Sequence a = null;


        try {
            // CardBuilder.g:155:5: ( ^( CALL FUNCTION i= Identifier (a= expressionList )? arrayCall[result] ) | ^( CALL VAR i= Identifier arrayCall[result] ) )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==CALL) ) {
                int LA29_1 = input.LA(2);

                if ( (LA29_1==DOWN) ) {
                    int LA29_2 = input.LA(3);

                    if ( (LA29_2==FUNCTION) ) {
                        alt29=1;
                    }
                    else if ( (LA29_2==VAR) ) {
                        alt29=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return result;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 29, 2, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return result;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 29, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return result;}
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // CardBuilder.g:155:7: ^( CALL FUNCTION i= Identifier (a= expressionList )? arrayCall[result] )
                    {
                    match(input,CALL,FOLLOW_CALL_in_call1574); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    match(input,FUNCTION,FOLLOW_FUNCTION_in_call1576); if (state.failed) return result;
                    i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_call1580); if (state.failed) return result;
                    // CardBuilder.g:155:37: (a= expressionList )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==SEQUENCE) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // CardBuilder.g:0:0: a= expressionList
                            {
                            pushFollow(FOLLOW_expressionList_in_call1584);
                            a=expressionList();

                            state._fsp--;
                            if (state.failed) return result;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                      result = new SequenceCall(new GlobalFunctionCall((i!=null?i.getText():null),a));
                    }
                    pushFollow(FOLLOW_arrayCall_in_call1589);
                    arrayCall(result);

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;

                    }
                    break;
                case 2 :
                    // CardBuilder.g:156:7: ^( CALL VAR i= Identifier arrayCall[result] )
                    {
                    match(input,CALL,FOLLOW_CALL_in_call1600); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    match(input,VAR,FOLLOW_VAR_in_call1602); if (state.failed) return result;
                    i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_call1606); if (state.failed) return result;
                    if ( state.backtracking==0 ) {
                      result = new SequenceCall(new VariableCall((i!=null?i.getText():null)));
                    }
                    pushFollow(FOLLOW_arrayCall_in_call1610);
                    arrayCall(result);

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "call"


    // $ANTLR start "subCall"
    // CardBuilder.g:159:1: subCall[SequenceCall call] : ( ^( CALL FIELD FUNCTION i= Identifier (a= expressionList )? arrayCall[call] ) | ^( CALL FIELD VAR i= Identifier arrayCall[call] ) );
    public final void subCall(SequenceCall call) throws RecognitionException {
        CommonTree i=null;
        Sequence a = null;


        try {
            // CardBuilder.g:160:5: ( ^( CALL FIELD FUNCTION i= Identifier (a= expressionList )? arrayCall[call] ) | ^( CALL FIELD VAR i= Identifier arrayCall[call] ) )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==CALL) ) {
                int LA31_1 = input.LA(2);

                if ( (LA31_1==DOWN) ) {
                    int LA31_2 = input.LA(3);

                    if ( (LA31_2==FIELD) ) {
                        int LA31_3 = input.LA(4);

                        if ( (LA31_3==FUNCTION) ) {
                            alt31=1;
                        }
                        else if ( (LA31_3==VAR) ) {
                            alt31=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return ;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 31, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 31, 2, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 31, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // CardBuilder.g:160:7: ^( CALL FIELD FUNCTION i= Identifier (a= expressionList )? arrayCall[call] )
                    {
                    match(input,CALL,FOLLOW_CALL_in_subCall1632); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    match(input,FIELD,FOLLOW_FIELD_in_subCall1634); if (state.failed) return ;
                    match(input,FUNCTION,FOLLOW_FUNCTION_in_subCall1636); if (state.failed) return ;
                    i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_subCall1640); if (state.failed) return ;
                    // CardBuilder.g:160:43: (a= expressionList )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==SEQUENCE) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // CardBuilder.g:0:0: a= expressionList
                            {
                            pushFollow(FOLLOW_expressionList_in_subCall1644);
                            a=expressionList();

                            state._fsp--;
                            if (state.failed) return ;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                      call.addCall(new FunctionCall((i!=null?i.getText():null), a));
                    }
                    pushFollow(FOLLOW_arrayCall_in_subCall1649);
                    arrayCall(call);

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // CardBuilder.g:161:7: ^( CALL FIELD VAR i= Identifier arrayCall[call] )
                    {
                    match(input,CALL,FOLLOW_CALL_in_subCall1660); if (state.failed) return ;

                    match(input, Token.DOWN, null); if (state.failed) return ;
                    match(input,FIELD,FOLLOW_FIELD_in_subCall1662); if (state.failed) return ;
                    match(input,VAR,FOLLOW_VAR_in_subCall1664); if (state.failed) return ;
                    i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_subCall1668); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                      call.addCall(new FieldCall((i!=null?i.getText():null)));
                    }
                    pushFollow(FOLLOW_arrayCall_in_subCall1672);
                    arrayCall(call);

                    state._fsp--;
                    if (state.failed) return ;

                    match(input, Token.UP, null); if (state.failed) return ;

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "subCall"


    // $ANTLR start "arrayCall"
    // CardBuilder.g:164:1: arrayCall[SequenceCall call] : ARRAY (index= expression )* ;
    public final void arrayCall(SequenceCall call) throws RecognitionException {
        Expression index = null;


        try {
            // CardBuilder.g:165:5: ( ARRAY (index= expression )* )
            // CardBuilder.g:165:9: ARRAY (index= expression )*
            {
            match(input,ARRAY,FOLLOW_ARRAY_in_arrayCall1695); if (state.failed) return ;
            // CardBuilder.g:165:15: (index= expression )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( ((LA32_0>=STRING && LA32_0<=NUMBER)||LA32_0==UNARY||LA32_0==PAR||LA32_0==CALL||(LA32_0>=TRUE && LA32_0<=NULL)||LA32_0==58||(LA32_0>=73 && LA32_0<=91)) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // CardBuilder.g:165:16: index= expression
            	    {
            	    pushFollow(FOLLOW_expression_in_arrayCall1700);
            	    index=expression();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    if ( state.backtracking==0 ) {
            	      call.addCall(new ArrayCall(call.getText(),index));
            	    }

            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        reportError(re);
        throw re;
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "arrayCall"

    // $ANTLR start synpred23_CardBuilder
    public final void synpred23_CardBuilder_fragment() throws RecognitionException {   
        CommonTree i=null;
        LoopControl ctrl = null;

        Node body = null;


        // CardBuilder.g:83:9: ( ^( LOOP (i= Identifier )? ctrl= loopControl body= statement ) )
        // CardBuilder.g:83:9: ^( LOOP (i= Identifier )? ctrl= loopControl body= statement )
        {
        match(input,LOOP,FOLLOW_LOOP_in_synpred23_CardBuilder509); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        // CardBuilder.g:83:17: (i= Identifier )?
        int alt34=2;
        int LA34_0 = input.LA(1);

        if ( (LA34_0==Identifier) ) {
            alt34=1;
        }
        switch (alt34) {
            case 1 :
                // CardBuilder.g:0:0: i= Identifier
                {
                i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_synpred23_CardBuilder513); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_loopControl_in_synpred23_CardBuilder518);
        ctrl=loopControl();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_statement_in_synpred23_CardBuilder522);
        body=statement();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred23_CardBuilder

    // $ANTLR start synpred25_CardBuilder
    public final void synpred25_CardBuilder_fragment() throws RecognitionException {   
        CommonTree i=null;
        Expression cond = null;

        Node body = null;


        // CardBuilder.g:84:9: ( ^( LOOP (i= Identifier )? cond= parExpression body= statement ) )
        // CardBuilder.g:84:9: ^( LOOP (i= Identifier )? cond= parExpression body= statement )
        {
        match(input,LOOP,FOLLOW_LOOP_in_synpred25_CardBuilder536); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        // CardBuilder.g:84:17: (i= Identifier )?
        int alt35=2;
        int LA35_0 = input.LA(1);

        if ( (LA35_0==Identifier) ) {
            alt35=1;
        }
        switch (alt35) {
            case 1 :
                // CardBuilder.g:0:0: i= Identifier
                {
                i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_synpred25_CardBuilder540); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_parExpression_in_synpred25_CardBuilder545);
        cond=parExpression();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_statement_in_synpred25_CardBuilder549);
        body=statement();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred25_CardBuilder

    // $ANTLR start synpred27_CardBuilder
    public final void synpred27_CardBuilder_fragment() throws RecognitionException {   
        CommonTree i=null;
        Node body = null;

        Expression cond = null;


        // CardBuilder.g:85:9: ( ^( LOOP (i= Identifier )? body= statement cond= parExpression ) )
        // CardBuilder.g:85:9: ^( LOOP (i= Identifier )? body= statement cond= parExpression )
        {
        match(input,LOOP,FOLLOW_LOOP_in_synpred27_CardBuilder563); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        // CardBuilder.g:85:17: (i= Identifier )?
        int alt36=2;
        int LA36_0 = input.LA(1);

        if ( (LA36_0==Identifier) ) {
            alt36=1;
        }
        switch (alt36) {
            case 1 :
                // CardBuilder.g:0:0: i= Identifier
                {
                i=(CommonTree)match(input,Identifier,FOLLOW_Identifier_in_synpred27_CardBuilder567); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_statement_in_synpred27_CardBuilder572);
        body=statement();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_parExpression_in_synpred27_CardBuilder576);
        cond=parExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred27_CardBuilder

    // $ANTLR start synpred60_CardBuilder
    public final void synpred60_CardBuilder_fragment() throws RecognitionException {   
        Expression e = null;


        // CardBuilder.g:137:7: ( ^( UNARY '+' e= expression ) )
        // CardBuilder.g:137:7: ^( UNARY '+' e= expression )
        {
        match(input,UNARY,FOLLOW_UNARY_in_synpred60_CardBuilder1324); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        match(input,87,FOLLOW_87_in_synpred60_CardBuilder1326); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred60_CardBuilder1330);
        e=expression();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred60_CardBuilder

    // $ANTLR start synpred61_CardBuilder
    public final void synpred61_CardBuilder_fragment() throws RecognitionException {   
        Expression e = null;


        // CardBuilder.g:138:9: ( ^( UNARY '-' e= expression ) )
        // CardBuilder.g:138:9: ^( UNARY '-' e= expression )
        {
        match(input,UNARY,FOLLOW_UNARY_in_synpred61_CardBuilder1344); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        match(input,88,FOLLOW_88_in_synpred61_CardBuilder1346); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred61_CardBuilder1350);
        e=expression();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred61_CardBuilder

    // $ANTLR start synpred62_CardBuilder
    public final void synpred62_CardBuilder_fragment() throws RecognitionException {   
        Expression e = null;


        // CardBuilder.g:139:9: ( ^( UNARY '++' e= expression ) )
        // CardBuilder.g:139:9: ^( UNARY '++' e= expression )
        {
        match(input,UNARY,FOLLOW_UNARY_in_synpred62_CardBuilder1364); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        match(input,92,FOLLOW_92_in_synpred62_CardBuilder1366); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred62_CardBuilder1370);
        e=expression();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred62_CardBuilder

    // $ANTLR start synpred63_CardBuilder
    public final void synpred63_CardBuilder_fragment() throws RecognitionException {   
        Expression e = null;


        // CardBuilder.g:140:9: ( ^( UNARY '--' e= expression ) )
        // CardBuilder.g:140:9: ^( UNARY '--' e= expression )
        {
        match(input,UNARY,FOLLOW_UNARY_in_synpred63_CardBuilder1384); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        match(input,93,FOLLOW_93_in_synpred63_CardBuilder1386); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred63_CardBuilder1390);
        e=expression();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred63_CardBuilder

    // $ANTLR start synpred64_CardBuilder
    public final void synpred64_CardBuilder_fragment() throws RecognitionException {   
        Expression e = null;


        // CardBuilder.g:141:9: ( ^( UNARY '~' e= expression ) )
        // CardBuilder.g:141:9: ^( UNARY '~' e= expression )
        {
        match(input,UNARY,FOLLOW_UNARY_in_synpred64_CardBuilder1404); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        match(input,94,FOLLOW_94_in_synpred64_CardBuilder1406); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred64_CardBuilder1410);
        e=expression();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred64_CardBuilder

    // $ANTLR start synpred65_CardBuilder
    public final void synpred65_CardBuilder_fragment() throws RecognitionException {   
        Expression e = null;


        // CardBuilder.g:142:9: ( ^( UNARY '!' e= expression ) )
        // CardBuilder.g:142:9: ^( UNARY '!' e= expression )
        {
        match(input,UNARY,FOLLOW_UNARY_in_synpred65_CardBuilder1424); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        match(input,95,FOLLOW_95_in_synpred65_CardBuilder1426); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred65_CardBuilder1430);
        e=expression();

        state._fsp--;
        if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred65_CardBuilder

    // $ANTLR start synpred66_CardBuilder
    public final void synpred66_CardBuilder_fragment() throws RecognitionException {   
        Expression e = null;


        // CardBuilder.g:143:9: ( ^( UNARY e= expression '++' ) )
        // CardBuilder.g:143:9: ^( UNARY e= expression '++' )
        {
        match(input,UNARY,FOLLOW_UNARY_in_synpred66_CardBuilder1444); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred66_CardBuilder1448);
        e=expression();

        state._fsp--;
        if (state.failed) return ;
        match(input,92,FOLLOW_92_in_synpred66_CardBuilder1450); if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred66_CardBuilder

    // $ANTLR start synpred67_CardBuilder
    public final void synpred67_CardBuilder_fragment() throws RecognitionException {   
        Expression e = null;


        // CardBuilder.g:144:9: ( ^( UNARY e= expression '--' ) )
        // CardBuilder.g:144:9: ^( UNARY e= expression '--' )
        {
        match(input,UNARY,FOLLOW_UNARY_in_synpred67_CardBuilder1464); if (state.failed) return ;

        match(input, Token.DOWN, null); if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred67_CardBuilder1468);
        e=expression();

        state._fsp--;
        if (state.failed) return ;
        match(input,93,FOLLOW_93_in_synpred67_CardBuilder1470); if (state.failed) return ;

        match(input, Token.UP, null); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred67_CardBuilder

    // Delegated rules

    public final boolean synpred27_CardBuilder() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_CardBuilder_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred67_CardBuilder() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred67_CardBuilder_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred65_CardBuilder() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred65_CardBuilder_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred60_CardBuilder() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred60_CardBuilder_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred61_CardBuilder() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred61_CardBuilder_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred66_CardBuilder() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred66_CardBuilder_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred64_CardBuilder() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred64_CardBuilder_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred62_CardBuilder() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred62_CardBuilder_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred25_CardBuilder() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred25_CardBuilder_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred63_CardBuilder() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred63_CardBuilder_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_CardBuilder() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_CardBuilder_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA19 dfa19 = new DFA19(this);
    protected DFA26 dfa26 = new DFA26(this);
    static final String DFA19_eotS =
        "\50\uffff";
    static final String DFA19_eofS =
        "\50\uffff";
    static final String DFA19_minS =
        "\1\4\37\uffff\1\0\7\uffff";
    static final String DFA19_maxS =
        "\1\133\37\uffff\1\0\7\uffff";
    static final String DFA19_acceptS =
        "\1\uffff\1\1\1\2\33\uffff\1\3\1\4\1\uffff\1\10\1\11\1\12\1\13\1"+
        "\5\1\6\1\7";
    static final String DFA19_specialS =
        "\40\uffff\1\0\7\uffff}>";
    static final String[] DFA19_transitionS = {
            "\2\2\5\uffff\1\2\1\uffff\1\1\1\2\1\36\1\2\1\uffff\1\40\1\37"+
            "\1\44\1\43\1\42\1\41\2\uffff\3\2\35\uffff\1\2\16\uffff\23\2",
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
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "78:1: statement returns [Node result] : (blk= block | e= expression | ^( VAR v= variableDeclarators ) | ^( IF c= parExpression t= statement (f= statement )? ) | ^( LOOP (i= Identifier )? ctrl= loopControl body= statement ) | ^( LOOP (i= Identifier )? cond= parExpression body= statement ) | ^( LOOP (i= Identifier )? body= statement cond= parExpression ) | ^( SWITCH parExpression ) | ^( RETURN (e= expression )? ) | ^( BREAK (i= Identifier )? ) | ^( CONTINUE (i= Identifier )? ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TreeNodeStream input = (TreeNodeStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA19_32 = input.LA(1);

                         
                        int index19_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_CardBuilder()) ) {s = 37;}

                        else if ( (synpred25_CardBuilder()) ) {s = 38;}

                        else if ( (synpred27_CardBuilder()) ) {s = 39;}

                         
                        input.seek(index19_32);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 19, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA26_eotS =
        "\45\uffff";
    static final String DFA26_eofS =
        "\45\uffff";
    static final String DFA26_minS =
        "\1\4\24\uffff\1\0\17\uffff";
    static final String DFA26_maxS =
        "\1\133\24\uffff\1\0\17\uffff";
    static final String DFA26_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\uffff\1\35\1\36\4\uffff"+
        "\1\37\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34";
    static final String DFA26_specialS =
        "\25\uffff\1\0\17\uffff}>";
    static final String[] DFA26_transitionS = {
            "\2\27\5\uffff\1\25\2\uffff\1\26\1\uffff\1\34\11\uffff\3\27\35"+
            "\uffff\1\1\16\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1"+
            "\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24",
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
            "\1\uffff",
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
            ""
    };

    static final short[] DFA26_eot = DFA.unpackEncodedString(DFA26_eotS);
    static final short[] DFA26_eof = DFA.unpackEncodedString(DFA26_eofS);
    static final char[] DFA26_min = DFA.unpackEncodedStringToUnsignedChars(DFA26_minS);
    static final char[] DFA26_max = DFA.unpackEncodedStringToUnsignedChars(DFA26_maxS);
    static final short[] DFA26_accept = DFA.unpackEncodedString(DFA26_acceptS);
    static final short[] DFA26_special = DFA.unpackEncodedString(DFA26_specialS);
    static final short[][] DFA26_transition;

    static {
        int numStates = DFA26_transitionS.length;
        DFA26_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA26_transition[i] = DFA.unpackEncodedString(DFA26_transitionS[i]);
        }
    }

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = DFA26_eot;
            this.eof = DFA26_eof;
            this.min = DFA26_min;
            this.max = DFA26_max;
            this.accept = DFA26_accept;
            this.special = DFA26_special;
            this.transition = DFA26_transition;
        }
        public String getDescription() {
            return "116:1: expression returns [Expression result] : ( ^( '=' e1= expression e2= expression ) | ^( '+=' e1= expression e2= expression ) | ^( '-=' e1= expression e2= expression ) | ^( '*=' e1= expression e2= expression ) | ^( '/=' e1= expression e2= expression ) | ^( '?' e1= expression e2= expression e3= expression ) | ^( '||' e1= expression e2= expression ) | ^( '&&' e1= expression e2= expression ) | ^( '==' e1= expression e2= expression ) | ^( '!=' e1= expression e2= expression ) | ^( 'instanceof' e1= expression e2= expression ) | ^( '<=' e1= expression e2= expression ) | ^( '>=' e1= expression e2= expression ) | ^( '<' e1= expression e2= expression ) | ^( '>' e1= expression e2= expression ) | ^( '+' e1= expression e2= expression ) | ^( '-' e1= expression e2= expression ) | ^( '*' e1= expression e2= expression ) | ^( '/' e1= expression e2= expression ) | ^( '%' e1= expression e2= expression ) | ^( UNARY '+' e= expression ) | ^( UNARY '-' e= expression ) | ^( UNARY '++' e= expression ) | ^( UNARY '--' e= expression ) | ^( UNARY '~' e= expression ) | ^( UNARY '!' e= expression ) | ^( UNARY e= expression '++' ) | ^( UNARY e= expression '--' ) | p= parExpression | l= literal | s= sequenceCall );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TreeNodeStream input = (TreeNodeStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA26_21 = input.LA(1);

                         
                        int index26_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_CardBuilder()) ) {s = 29;}

                        else if ( (synpred61_CardBuilder()) ) {s = 30;}

                        else if ( (synpred62_CardBuilder()) ) {s = 31;}

                        else if ( (synpred63_CardBuilder()) ) {s = 32;}

                        else if ( (synpred64_CardBuilder()) ) {s = 33;}

                        else if ( (synpred65_CardBuilder()) ) {s = 34;}

                        else if ( (synpred66_CardBuilder()) ) {s = 35;}

                        else if ( (synpred67_CardBuilder()) ) {s = 36;}

                         
                        input.seek(index26_21);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 26, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_literal_in_value56 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_object_in_value65 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_array_in_value74 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_value83 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal101 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_StringLiteral_in_literal105 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMBER_in_literal114 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DecimalLiteral_in_literal118 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TRUE_in_literal126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_literal133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_literal140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARRAY_in_array162 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_value_in_array167 = new BitSet(new long[]{0x000000001C0006B8L});
    public static final BitSet FOLLOW_OBJECT_in_object194 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_pair_in_object196 = new BitSet(new long[]{0x0000000000000108L});
    public static final BitSet FOLLOW_FIELD_in_pair213 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_pair217 = new BitSet(new long[]{0x000000001C0006B8L});
    public static final BitSet FOLLOW_value_in_pair221 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_in_function240 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_function244 = new BitSet(new long[]{0x0010000000003000L});
    public static final BitSet FOLLOW_argumentDecls_in_function249 = new BitSet(new long[]{0x0010000000003000L});
    public static final BitSet FOLLOW_52_in_function253 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_function255 = new BitSet(new long[]{0x0010000000003000L});
    public static final BitSet FOLLOW_block_in_function261 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SEQUENCE_in_variableDeclarators291 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_variableDeclarator_in_variableDeclarators296 = new BitSet(new long[]{0x0000000000008008L});
    public static final BitSet FOLLOW_VAR_in_variableDeclarator324 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_variableDeclarator328 = new BitSet(new long[]{0x040000001C014838L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_variableDeclarator332 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SEQUENCE_in_argumentDecls365 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_argumentDecls370 = new BitSet(new long[]{0x0000000100000008L});
    public static final BitSet FOLLOW_BLOCK_in_block403 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_statement_in_block408 = new BitSet(new long[]{0x041000001CFDF838L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_block_in_statement437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_statement451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_statement464 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_variableDeclarators_in_statement468 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IF_in_statement482 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_parExpression_in_statement486 = new BitSet(new long[]{0x041000001CFDF838L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_statement_in_statement490 = new BitSet(new long[]{0x041000001CFDF838L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_statement_in_statement494 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LOOP_in_statement509 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_statement513 = new BitSet(new long[]{0x0800000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_loopControl_in_statement518 = new BitSet(new long[]{0x041000001CFDF838L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_statement_in_statement522 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LOOP_in_statement536 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_statement540 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_parExpression_in_statement545 = new BitSet(new long[]{0x041000001CFDF838L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_statement_in_statement549 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LOOP_in_statement563 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_statement567 = new BitSet(new long[]{0x041000001CFDF838L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_statement_in_statement572 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_parExpression_in_statement576 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SWITCH_in_statement590 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_parExpression_in_statement592 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_RETURN_in_statement606 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_statement610 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BREAK_in_statement625 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_statement629 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONTINUE_in_statement644 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_statement648 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_70_in_loopControl676 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_loopControl680 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_loopControl684 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_59_in_loopControl698 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_forInit_in_loopControl702 = new BitSet(new long[]{0x0C0000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_loopControl707 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_loopControl710 = new BitSet(new long[]{0x0000000000001008L});
    public static final BitSet FOLLOW_forUpdate_in_loopControl714 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VAR_in_forInit742 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_variableDeclarators_in_forInit746 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expressionList_in_forInit761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionList_in_forUpdate788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PAR_in_parExpression815 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_parExpression819 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SEQUENCE_in_expressionList850 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expressionList855 = new BitSet(new long[]{0x040000001C014838L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_58_in_expression884 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression888 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression892 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_73_in_expression906 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression910 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression914 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_74_in_expression928 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression932 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression936 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_75_in_expression950 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression954 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression958 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_76_in_expression972 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression976 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression980 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_77_in_expression994 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression998 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1002 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1006 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_78_in_expression1020 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1024 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1028 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_79_in_expression1042 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1046 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1050 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_80_in_expression1064 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1068 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1072 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_81_in_expression1086 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1090 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1094 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_82_in_expression1108 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1112 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1116 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_83_in_expression1130 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1134 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1138 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_84_in_expression1152 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1156 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1160 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_85_in_expression1174 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1178 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1182 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_86_in_expression1196 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1200 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1204 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_87_in_expression1218 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1222 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1226 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_88_in_expression1240 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1244 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1248 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_89_in_expression1262 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1266 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1270 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_90_in_expression1284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1288 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1292 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_91_in_expression1304 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1308 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1312 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_expression1324 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_87_in_expression1326 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1330 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_expression1344 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_88_in_expression1346 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1350 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_expression1364 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_92_in_expression1366 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1370 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_expression1384 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_93_in_expression1386 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1390 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_expression1404 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_94_in_expression1406 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1410 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_expression1424 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_95_in_expression1426 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_expression1430 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_expression1444 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1448 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_expression1450 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_expression1464 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1468 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_expression1470 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_parExpression_in_expression1485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_expression1499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sequenceCall_in_expression1513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CALL_in_sequenceCall1539 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_call_in_sequenceCall1543 = new BitSet(new long[]{0x0000000000010008L});
    public static final BitSet FOLLOW_subCall_in_sequenceCall1548 = new BitSet(new long[]{0x0000000000010008L});
    public static final BitSet FOLLOW_CALL_in_call1574 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_FUNCTION_in_call1576 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_Identifier_in_call1580 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_expressionList_in_call1584 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_arrayCall_in_call1589 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CALL_in_call1600 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_VAR_in_call1602 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_Identifier_in_call1606 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_arrayCall_in_call1610 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CALL_in_subCall1632 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_FIELD_in_subCall1634 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_FUNCTION_in_subCall1636 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_Identifier_in_subCall1640 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_expressionList_in_subCall1644 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_arrayCall_in_subCall1649 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CALL_in_subCall1660 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_FIELD_in_subCall1662 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_VAR_in_subCall1664 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_Identifier_in_subCall1668 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_arrayCall_in_subCall1672 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ARRAY_in_arrayCall1695 = new BitSet(new long[]{0x040000001C014832L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_arrayCall1700 = new BitSet(new long[]{0x040000001C014832L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_LOOP_in_synpred23_CardBuilder509 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_synpred23_CardBuilder513 = new BitSet(new long[]{0x0800000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_loopControl_in_synpred23_CardBuilder518 = new BitSet(new long[]{0x041000001CFDF838L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_statement_in_synpred23_CardBuilder522 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LOOP_in_synpred25_CardBuilder536 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_synpred25_CardBuilder540 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_parExpression_in_synpred25_CardBuilder545 = new BitSet(new long[]{0x041000001CFDF838L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_statement_in_synpred25_CardBuilder549 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LOOP_in_synpred27_CardBuilder563 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_Identifier_in_synpred27_CardBuilder567 = new BitSet(new long[]{0x041000001CFDF838L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_statement_in_synpred27_CardBuilder572 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_parExpression_in_synpred27_CardBuilder576 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_synpred60_CardBuilder1324 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_87_in_synpred60_CardBuilder1326 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_synpred60_CardBuilder1330 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_synpred61_CardBuilder1344 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_88_in_synpred61_CardBuilder1346 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_synpred61_CardBuilder1350 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_synpred62_CardBuilder1364 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_92_in_synpred62_CardBuilder1366 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_synpred62_CardBuilder1370 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_synpred63_CardBuilder1384 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_93_in_synpred63_CardBuilder1386 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_synpred63_CardBuilder1390 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_synpred64_CardBuilder1404 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_94_in_synpred64_CardBuilder1406 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_synpred64_CardBuilder1410 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_synpred65_CardBuilder1424 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_95_in_synpred65_CardBuilder1426 = new BitSet(new long[]{0x040000001C014830L,0x000000000FFFFE00L});
    public static final BitSet FOLLOW_expression_in_synpred65_CardBuilder1430 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_synpred66_CardBuilder1444 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_synpred66_CardBuilder1448 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_synpred66_CardBuilder1450 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UNARY_in_synpred67_CardBuilder1464 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_synpred67_CardBuilder1468 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_synpred67_CardBuilder1470 = new BitSet(new long[]{0x0000000000000008L});

}
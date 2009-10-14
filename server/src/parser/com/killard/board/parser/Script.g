grammar Script;
options {
	backtrack=true;
	memoize=true;
	output = AST;
}

tokens {
	STRING; NUMBER; BOOLEAN; OBJECT; FIELD; ARRAY; FUNCTION;
	UNARY; SEQUENCE; BLOCK; PAR; VAR; CALL; ARGUMENTS;
	LOOP; IF; CONTINUE; BREAK; RETURN; SWITCH; LABEL;
	COMMA = ',';
	TRUE; FALSE; NULL;
}

@header {
package com.oneboard.parser;  
}

@lexer::header {
package com.oneboard.parser;
}

value
	: literal
	| object
	| array
	| function
	;

literal 
    	: DecimalLiteral -> ^(NUMBER DecimalLiteral)
	| FloatingPointLiteral -> ^(NUMBER FloatingPointLiteral)
	| StringLiteral -> ^(STRING StringLiteral)
	| 'true' -> TRUE
    	| 'false' -> FALSE
	| 'null' -> NULL
    	;

object	: '{' members '}' 
	  -> ^(OBJECT members)
	;
array	: '[' elements ']'
	  -> ^(ARRAY elements)
	;

function
	: 'function' Identifier? '(' argumentDecls? ')' ('[' ']')* block
	  -> ^(FUNCTION Identifier? argumentDecls? ('[' ']')* block)
   	;

elements: value (COMMA! value)*
	;


members	: pair (COMMA! pair)*
	;

pair	: Identifier ':' value 
	  -> ^(FIELD Identifier value) 
	;

// Function
variableDeclarators
    :   variableDeclarator (',' variableDeclarator)* -> ^(SEQUENCE variableDeclarator+)
    ;

variableDeclarator
    :   Identifier ('=' expression)? -> ^(VAR Identifier expression?)
    ;

argumentDecls
    :   Identifier (',' Identifier)* -> ^(SEQUENCE Identifier+)
    ;

// STATEMENTS / BLOCKS
block
    :   '{' statement* '}' -> ^(BLOCK statement*)
    ;

statement
    :   block
    |   expression ';'!
    |   'var' variableDeclarators ';' -> ^(VAR variableDeclarators)
    |   'if' parExpression statement (options {k=1;}:'else' statement)? -> ^(IF parExpression statement statement?)
    |   (Identifier ':')? 'for' '(' loopControl ')' statement -> ^(LOOP Identifier? loopControl statement)
    |   (Identifier ':')? 'while' parExpression statement -> ^(LOOP Identifier? parExpression statement)
    |   (Identifier ':')? 'do' statement 'while' parExpression ';' -> ^(LOOP Identifier? statement parExpression)
    |   'switch' parExpression '{' switchBlockStatementGroups '}' -> ^(SWITCH parExpression)
    |   'return' expression? ';' -> ^(RETURN expression?)
    |   'break' Identifier? ';' -> ^(BREAK Identifier?)
    |   'continue' Identifier? ';' -> ^(CONTINUE Identifier?)
    |   ';'!
    ;

loopControl
//options {k=3;} // be efficient for common case: for (ID ID : ID) ...
    :   Identifier 'in'^ expression
    |   forInit? ';'^ expression? ';' forUpdate?
    ;

forInit
    :   'var'? variableDeclarators -> ^(VAR variableDeclarators)
    |   expressionList
    ;

forUpdate
    :   expressionList
    ;

switchBlockStatementGroups
    :   (switchBlockStatementGroup)*
    ;

switchBlockStatementGroup
    :   switchLabel+ statement*
    ;

switchLabel
    :   'case' expression ':'
    |   'default' ':'
    ;

// EXPRESSIONS
parExpression
    :   '(' expression ')' -> ^(PAR expression)
    ;

expressionList
    :   expression (',' expression)* -> ^(SEQUENCE expression+)
    ;

expression
    :   conditionalExpression (('='^ | '+='^ | '-='^ | '*='^ | '/='^) conditionalExpression)*
    ;

conditionalExpression
    :   conditionalOrExpression ('?'^ conditionalExpression ':'! conditionalExpression)?
    ;

conditionalOrExpression
    :   conditionalAndExpression ('||'^ conditionalAndExpression)*
    ;

conditionalAndExpression
    :   equalsExpression ('&&'^ equalsExpression)*
    ;

equalsExpression
    :   notEqualsExpression ('=='^ notEqualsExpression)*
    ;

notEqualsExpression
    :   instanceOfExpression ('!='^ instanceOfExpression)*
    ;

instanceOfExpression
    :   lessOrEqualsExpression ('instanceof'^ lessOrEqualsExpression)*
    ;

lessOrEqualsExpression
    :   greaterOrEqualsExpression ('<='^ greaterOrEqualsExpression)*
    ;

greaterOrEqualsExpression
    :   lessExpression ('>='^ lessExpression)*
    ;

lessExpression
    :   greaterExpression ('<'^ greaterExpression)*
    ;

greaterExpression
    :   plusExpression ('>'^ plusExpression)*
    ;

plusExpression
    :   minusExpression ('+'^ minusExpression)*
    ;

minusExpression
    :   multiplyExpression ('-'^ multiplyExpression)*
    ;

multiplyExpression
    :   divideExpression ('*'^ divideExpression)*
    ;

divideExpression
    :   modExpression ('/'^ modExpression)*
    ;

modExpression
    :	unaryExpression ('%'^ unaryExpression)*
    ;

unaryExpression
    :   '+' unaryExpression -> ^(UNARY '+' unaryExpression)
    |   '-' unaryExpression -> ^(UNARY '-' unaryExpression)
    |   '++' unaryExpression -> ^(UNARY '++' unaryExpression)
    |   '--' unaryExpression -> ^(UNARY '--' unaryExpression)
    |   '~' unaryExpression -> ^(UNARY '~' unaryExpression)
    |   '!' unaryExpression -> ^(UNARY '!' unaryExpression)
    |   atomicExpression '++' -> ^(UNARY atomicExpression '++')
    |   atomicExpression '--' -> ^(UNARY atomicExpression '--')
    |   atomicExpression
    ;

atomicExpression
    :   parExpression
    |   literal
    |   call subCall* -> ^(CALL call subCall*)
    ;

call
    :	Identifier '(' expressionList? ')' ('[' expression ']')* -> ^(CALL FUNCTION Identifier expressionList? ARRAY expression*)
    |	Identifier ('[' expression ']')* -> ^(CALL VAR Identifier ARRAY expression*)
    ;

subCall
    :	'.' Identifier '(' expressionList? ')' ('[' expression ']')* -> ^(CALL FIELD FUNCTION Identifier expressionList? ARRAY expression*)
    |	'.' Identifier ('[' expression ']')* -> ^(CALL FIELD VAR Identifier ARRAY expression*)
    ;

fragment IntegerTypeSuffix : ('l'|'L') ;

fragment Letter
    :  '\u0024' |
       '\u0041'..'\u005a' |
       '\u005f' |
       '\u0061'..'\u007a' |
       '\u00c0'..'\u00d6' |
       '\u00d8'..'\u00f6' |
       '\u00f8'..'\u00ff' |
       '\u0100'..'\u1fff' |
       '\u3040'..'\u318f' |
       '\u3300'..'\u337f' |
       '\u3400'..'\u3d2d' |
       '\u4e00'..'\u9fff' |
       '\uf900'..'\ufaff'
    ;

fragment EscapeSequence
    	:   '\\' (UnicodeEscape |'b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\'|'\/')
    	;
 
fragment UnicodeEscape
	: 'u' HexDigit HexDigit HexDigit HexDigit
	;
 
fragment HexDigit
	: '0'..'9' | 'A'..'F' | 'a'..'f'
	;
 
fragment Digit
	: '0'..'9'
	;

fragment OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

Exponent: ('e'|'E') '-'? ('1'..'9') Digit*;

FloatingPointLiteral
    :   ('0'..'9')+ '.' ('0'..'9')* Exponent? FloatTypeSuffix?
    |   '.' ('0'..'9')+ Exponent? FloatTypeSuffix?
    |   ('0'..'9')+ Exponent FloatTypeSuffix?
    |   ('0'..'9')+ FloatTypeSuffix
    ;

HexLiteral : '0' ('x'|'X') HexDigit+ IntegerTypeSuffix? ;

DecimalLiteral : ('0' | '1'..'9' '0'..'9'*) IntegerTypeSuffix? ;

OctalLiteral : '0' ('0'..'7')+ IntegerTypeSuffix? ;

StringLiteral
    :  '"' ( EscapeSequence | ~('\\'|'"') )* '"'
    |  '\'' ( EscapeSequence | ~('\''|'\\') ) '\''
    ;

Identifier
    :   Letter (Letter|Digit)*
    ;

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

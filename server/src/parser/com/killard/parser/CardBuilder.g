tree grammar CardBuilder;
options {
	backtrack=true;
	tokenVocab=Script; // reuse token types
	ASTLabelType=CommonTree; // $label will have type CommonTree
}
 
@header {
package com.oneboard.parser;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
}

@rulecatch {
catch (RecognitionException re) {
reportError(re);
throw re;
}
}

value returns [Object result]
	: l=literal { result = l; }
	| o=object { result = o; }
	| a=array { result = a; }
	| f=function { result = f; }
	;

literal returns [Expression result]
	: ^(STRING s=StringLiteral) { result = new StringLiteral($s.text.substring(1, $s.text.length() - 1)); }
	| ^(NUMBER d=DecimalLiteral) { result = new NumberLiteral($d.text); }
	| TRUE { result = new BooleanLiteral("true"); }
	| FALSE { result = new BooleanLiteral("false"); }
	| NULL { result = new Null(); }
	;

array returns [List list]
@init{ list = new LinkedList(); }
	: ^(ARRAY (v=value { list.add(v); })+ )
	;

object returns [Map result]
@init { result = new HashMap(); }
	: ^(OBJECT pair[result]+)
	;

pair [Map map]
	: ^(FIELD key=Identifier v=value) { map.put($key.text, v); }
	;

function returns [Function result]
	: ^(FUNCTION i=Identifier? a=argumentDecls? ('[' ']')* b=block) { result = new Function($i.text, a, b); }
	;

// Function
variableDeclarators returns [Sequence result]
@init { result = new Sequence(); }
    :   ^(SEQUENCE (v=variableDeclarator{result.addChild(v);})+)
    ;

variableDeclarator returns [Variable result]
    :   ^(VAR i=Identifier e=expression?) {result=new Variable($i.text,e);}
    ;

argumentDecls returns [Sequence result]
@init { result = new Sequence(); }
    :   ^(SEQUENCE (i=Identifier{result.addChild(new Variable($i.text));})+)
    ;

// STATEMENTS / BLOCKS
block returns [Block result]
@init{ result = new Block();}
    :   ^(BLOCK (s=statement{ result.addChild(s); })*)
    ;

statement returns [Node result]
    :   blk=block { result = blk;}
    |   e=expression { result = e; }
    |   ^(VAR v=variableDeclarators) { result = v; }
    |   ^(IF c=parExpression t=statement f=statement?) { result = new If(c,t,f); }
    |   ^(LOOP i=Identifier? ctrl=loopControl body=statement) { result = new Loop($i.text, ctrl, body);}
    |   ^(LOOP i=Identifier? cond=parExpression body=statement) { result = new Loop($i.text, new ForControl(null,cond,null),body); }
    |   ^(LOOP i=Identifier? body=statement cond=parExpression) { result = new Loop($i.text, new ForControl(body,cond,null),body); }
    |   ^(SWITCH parExpression) { result = new DummyNode(); }
    |   ^(RETURN e=expression?) { result = new Return(e); }
    |   ^(BREAK i=Identifier?) { result = new Break($i.text); }
    |   ^(CONTINUE i=Identifier?) { result = new Continue($i.text); }
    ;

loopControl returns [LoopControl result]
    :   ^('in' i=Identifier e=expression) { result = new ForEachControl(new Variable($i.text), e); }
    |   ^(';' init=forInit? e=expression? ';' u=forUpdate?) { result = new ForControl(init, e, u); }
    ;

forInit returns [Sequence result]
    :   ^(VAR v=variableDeclarators) { result = v; }
    |   e=expressionList { result = e; }
    ;

forUpdate returns [Sequence result]
    :   e=expressionList { result = e; }
    ;

// EXPRESSIONS
parExpression returns [Expression result]
    :   ^(PAR e=expression) { result = e; }
    ;

expressionList returns [Sequence result]
@init{ $result = new Sequence(); }
    :   ^(SEQUENCE (e=expression { result.addChild(e); })+)
    ;

expression returns [Expression result]
    :   ^('=' e1=expression e2=expression) {result=new AssignmentExpression(e1, e2);}
    |   ^('+=' e1=expression e2=expression) {result=new PlusAssignmentExpression(e1, e2);}
    |   ^('-=' e1=expression e2=expression) {result=new MinusAssignmentExpression(e1, e2);}
    |   ^('*=' e1=expression e2=expression) {result=new MultiplyAssignmentExpression(e1, e2);}
    |   ^('/=' e1=expression e2=expression) {result=new DivideAssignmentExpression(e1, e2);}
    |   ^('?' e1=expression e2=expression e3=expression) {result=new ConditionExpression(e1, e2, e3);}
    |   ^('||' e1=expression e2=expression) {result=new ConditionalOrExpression(e1, e2);}
    |   ^('&&' e1=expression e2=expression) {result=new ConditionalAndExpression(e1, e2);}
    |   ^('==' e1=expression e2=expression) {result=new EqualExpression(e1, e2);}
    |   ^('!=' e1=expression e2=expression) {result=new NotEqualExpression(e1, e2);}
    |   ^('instanceof' e1=expression e2=expression) {result=new InstanceOfExpression(e1, e2);}
    |   ^('<=' e1=expression e2=expression) {result=new LessThanOrEqualExpression(e1, e2);}
    |   ^('>=' e1=expression e2=expression) {result=new GreaterThanOrEqualExpression(e1, e2);}
    |   ^('<' e1=expression e2=expression) {result=new LessThanExpression(e1, e2);}
    |   ^('>' e1=expression e2=expression) {result=new GreaterThanExpression(e1, e2);}
    |   ^('+' e1=expression e2=expression) {result=new PlusExpression(e1, e2);}
    |   ^('-' e1=expression e2=expression) {result=new MinusExpression(e1, e2);}
    |   ^('*' e1=expression e2=expression) {result=new MultiplyExpression(e1, e2);}
    |   ^('/' e1=expression e2=expression) {result=new DivideExpression(e1, e2);}
    |	^('%' e1=expression e2=expression) {result=new ModExpression(e1, e2);}
    |	^(UNARY '+' e=expression) {result=new UnaryPositiveExpression(e);}
    |   ^(UNARY '-' e=expression) {result=new UnaryNegativeExpression(e);}
    |   ^(UNARY '++' e=expression) {result=new UnaryIncreaseExpression(e);}
    |   ^(UNARY '--' e=expression) {result=new UnaryDecreaseExpression(e);}
    |   ^(UNARY '~' e=expression) {result=new UnaryNotExpression(e);}
    |   ^(UNARY '!' e=expression) {result=new UnaryNotExpression(e);}
    |   ^(UNARY e=expression '++') {result=new UnaryIncreaseExpression(e);}
    |   ^(UNARY e=expression '--') {result=new UnaryDecreaseExpression(e);}
    |   p=parExpression {result=p;}
    |   l=literal {result=l;}
    |   s=sequenceCall {result=s;}
    ;

sequenceCall returns [SequenceCall result]
    :   ^(CALL c=call {result=c;} (subCall[result])*)
    ;

call returns [SequenceCall result]
    :	^(CALL FUNCTION i=Identifier a=expressionList? {result = new SequenceCall(new GlobalFunctionCall($i.text,a));} arrayCall[result])
    |	^(CALL VAR i=Identifier {result = new SequenceCall(new VariableCall($i.text));} arrayCall[result])
    ;

subCall [SequenceCall call]
    :	^(CALL FIELD FUNCTION i=Identifier a=expressionList? {call.addCall(new FunctionCall($i.text, a));} arrayCall[call])
    |	^(CALL FIELD VAR i=Identifier {call.addCall(new FieldCall($i.text));} arrayCall[call])
    ;

arrayCall [SequenceCall call]
    :   ARRAY (index=expression {call.addCall(new ArrayCall(call.getText(),index));})*
    ;

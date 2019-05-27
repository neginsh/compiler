// by najme_habibi

grammar jython;

program : importClass* (classDec)? ;

importClass : ('import' USER_TYPE) ;

classDec : 'class'  USER_TYPE ('(' USER_TYPE ')')? '{' classBody* '}' ;

classBody : varDec
           | methodDec
           | constructor
           | arrayDec
           ;

varDec :   type  ID  ;

arrayDec : type '[' expression? ']' ID  ;

methodDec : 'def' returnType  ID  '(' parameters? ')''{' ( statement)* '}';

returnType : type|'void'|type '['']' ;

constructor : 'def'  USER_TYPE '(' parameters* ')''{' ( statement)* '}' ;

parameter : (varDec | arrayDec);

parameters : parameter (',' parameter)* ;

statement :
          whileStatement
         | ifElseStatement
         | forStatement
         | varDec
         | assignment
         | printStatement
         | methodCall
         | returnStatement
         | arrayDec
         ;

returnStatement : 'return'  expression ;

conditionList : expression (('or'|'and')  expression)* ;

whileStatement : 'while' '(' conditionList ')' '{' statement* '}' ;

ifElseStatement :'if' '(' conditionList ')' '{' statement* '}'
                 ('elif' '(' conditionList ')' '{' statement* '}')*
                 ('else' '{' statement* '}')?  ;

printStatement : 'print' '('  expression ')' ;

forStatement : 'for' ID 'in' leftExp '{' statement* '}'
             | 'for' ID 'in' 'range''('expression (',' expression)? (',' expression)? ')' '{' statement* '}'
             ;

methodCall : 'self' '.' methodCall
            | ID args
            | leftExp '.' ID args;

assignment  : leftExp assignmentOperators  expression
            | varDec assignmentOperators  expression
            | arrayDec '='  type '('')' ('['expression']')
            | leftExp '=' type '(' ')' ('['expression']')
            ;


expression  :
              expression multModDiv expression
            | expression addSub expression
            | expression eqNeq  expression
            | expression relationOperators expression
            | rightExp
            ;

rightExp :
              'none'
            | BOOL
            | INTEGER
            | STRING
            | FLOAT
            | USER_TYPE args
            | leftExp
            ;

leftExp :    ID
          |  '(' expression')'
          |  ID args
          |  leftExp '[' expression ']'
          |  leftExp '.' ID
          |  leftExp '.' ID args
          |  'self' '.' leftExp  ;

args :  '(' (expList)? ')' ;
expList  :  expression (',' expression)*;


assignmentOperators : '=' | '+=' | '-=' | '*=' | '/=' ;
eqNeq               : '==' | '!=' ;
relationOperators   : '>' | '<' | '>=' | '<=';
addSub              : '+' | '-';
multModDiv         : '*' | '/' | '%';
type                 : jythonType | USER_TYPE ;
USER_TYPE            : UpperCaseChar (LowerCaseChar|UpperCaseChar|DIGIT|'_')*  ;
jythonType           : 'float'|'int'|'bool'|'string';
ID                   : (LowerCaseChar)(LowerCaseChar|UpperCaseChar|DIGIT|'_' )*;
INTEGER              : CDIGIT(DIGIT)* | [0] ;
STRING               : '"' ~('\r' | '\n' | '"')* '"';
BOOL                 : 'false' |'true';
FLOAT                : DIGIT*'.'(DIGIT)+;
LowerCaseChar        : [a-z];
UpperCaseChar        : [A-Z];
DIGIT                : [0-9];
CDIGIT               : [1-9];
WS                   : [ \t]+ -> skip ;
NEWLINE              : ( '\r' '\n'?| '\n') -> skip ;
BlockComment         : '#*' .*? '*#'  -> skip  ;
LineComment          : '#' ~('\r'|'\n')* -> skip ;
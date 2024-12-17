grammar Cpp;

//Parser Rules
program :   (stmt|expr ';')* EOF;

stmt    :   classdef
        |   binding
        |   vardecl
        |   fndecl ';'
        |   fndef
        |   print
        |   while
        |   if
        ;

expr    :   fncall
        |   cond
        |   comp
        |   calc
        |   BOOL
        |   CHAR
        |   INT
        |   ID
        ;

// STMT
classdef:   'class' ID (':' ID (',' ID)* )? block ';';

binding :   ((ID | fncall) '.')* ID '=' expr;

vardecl :   type ID ('=' expr)?;

fndecl  :   (type | 'void') ID '(' (type ID)? | (type ID (',' type ID)*) ')' ;

fndef   :   fndecl block;

print   :   'print_bool' '(' BOOL ')'   #pbool
        |   'print_int' '(' INT ')'     #pint
        |   'print_char' '(' CHAR ')'   #pchar
        ;

while   :   'while' '(' cond ')' block;

if      :   ('if' '(' cond ')' block) elseif* else?;
elseif  :   'else' 'if' '(' cond ')' block;
else    :   'else' block;

// EXPR
fncall  :   ((ID | fncall) '.')* (ID| fncall) '(' expr? | (expr (',' expr)*) ')';

cond    :   NOT? expr (CONN NOT? expr)*;

comp    :   e1 = expr '==' e2 = expr    #EQUALS
        |   e1 = expr '!=' e2 = expr    #NEAQUALS
        |   e1 = expr '>'  e2 = expr    #GREATER
        |   e1 = expr '<'  e2 = expr    #LESS
        |   e1 = expr '>=' e2 = expr    #GEAQUALS
        |   e1 = expr '<=' e2 = expr    #LEAQUALS
        ;

calc    :   e1 = expr '*' e2 = expr     #MUL
        |   e1 = expr '/' e2 = expr     #DIV
        |   e1 = expr '+' e2 = expr     #ADD
        |   e1 = expr '-' e2 = expr     #SUB
        ;

//HELP
block   :   '{' (stmt|expr)* '}';
type    :   TYPEBOOL | TYPECHAR | TYPEINT | ID;

// Lexer Rules
TYPEINT :   'int';
TYPECHAR:   'char';
TYPEBOOL:   'bool';
ID      :   [a-zA-Z][a-zA-Z0-9_]*;

OP      :   '+'
        |   '-'
        |   '*'
        |   '/'
        ;

COMP    :   '=='
        |   '!='
        |   '>'
        |   '<'
        |   '>='
        |   '<='
        ;

CONN    :   '&&'
        |   '||'
        ;

NOT     :   '!';

INT     :   [0-9]+;
CHAR    :   '\'' ~[\t\n\r] '\'';
BOOL    :   'true'
        |   'false'
        ;
WS      :   [ \t\n\r]+ -> skip ;
COMMENT :   '//' ~[\n\r]* -> skip;
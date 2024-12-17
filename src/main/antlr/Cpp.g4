grammar Cpp;

//Parser Rules
program :   (stmt|expr ';')* EOF;

stmt    :   classdef
        |   binding
        |   vardecl ';'
        |   fndecl ';'
        |   return ';'
        |   fndef
        |   print
        |   while
        |   if
        ;

expr    :   ID ('.' ID | '(' args? ')')+#FNCALL
        |   'new' ID  '(' args? ')'     #NEW
        |   '(' expr ')'                #COL
        |   e1 = expr '*' e2 = expr     #MUL
        |   e1 = expr '/' e2 = expr     #DIV
        |   e1 = expr '+' e2 = expr     #ADD
        |   e1 = expr '-' e2 = expr     #SUB
        |   e1 = expr '==' e2 = expr    #EQUALS
        |   e1 = expr '!=' e2 = expr    #NEAQUALS
        |   e1 = expr '>'  e2 = expr    #GREATER
        |   e1 = expr '<'  e2 = expr    #LESS
        |   e1 = expr '>=' e2 = expr    #GEAQUALS
        |   e1 = expr '<=' e2 = expr    #LEAQUALS
        |   e1 = expr '&&' e2 =expr     #AND
        |   e1 = expr '||' e2 =expr     #OR
        |   expr ('[' expr ']')+        #ARRACC
        |   '{'args'}'                  #ARRVALS
        |   NOT expr                    #NOT
        |   BOOL                        #BOOL
        |   CHAR                        #CHAR
        |   INT                         #INT
        |   ID                          #ID
        ;

// STMT
classdef:   'class' ID (':' ID (',' ID)* )? block ';';

binding :   (expr '.')* ID '=' expr;

vardecl :   type ID ('[' expr ']')* ('=' expr)?;

fndecl  :   (type | 'void')? ID '(' (type ID)? | (type ID (',' type ID)*) ')' ;

return  :   'return' expr?;

fndef   :   fndecl block;

print   :   'print_bool' '(' BOOL ')'   #pbool
        |   'print_int' '(' INT ')'     #pint
        |   'print_char' '(' CHAR ')'   #pchar
        ;

while   :   'while' '(' expr ')' block;

if      :   ('if' '(' expr ')' block) elseif* else?;
elseif  :   'else' 'if' '(' expr ')' block;
else    :   'else' block;

//HELP
args    :   expr (',' expr)*;
block   :   '{' (stmt|expr)* '}';
type    :   TYPEBOOL | TYPECHAR | TYPEINT | ID;

// Lexer Rules
TYPEINT :   'int';
TYPECHAR:   'char';
TYPEBOOL:   'bool';
ID      :   [a-zA-Z][a-zA-Z0-9_]*;

NOT     :   '!';

INT     :   [0-9]+;
CHAR    :   '\'' ~[\t\n\r] '\'';
BOOL    :   'true'
        |   'false'
        ;
WS      :   [ \t\n\r]+ -> skip ;
COMMENT :   '//' ~[\n\r]* -> skip;
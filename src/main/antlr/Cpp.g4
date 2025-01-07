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
classdef:   'class' cName=ID (':' ID )? block ';';

binding :   (expr '.')* ID '=' expr;

vardecl :   type ID ('[' expr ']')* ('=' expr)?;

fndecl  :   (type | 'void')? ID '(' (params)? ')' ;

return  :   'return' expr?;

fndef   :   fndecl block;

print   :   'print_bool' '(' expr ')'   #pbool
        |   'print_int' '(' expr ')'    #pint
        |   'print_char' '(' expr ')'   #pchar
        ;

while   :   'while' '(' expr ')' block;

if      :   ('if' '(' expr ')' block) elseif* else?;
elseif  :   'else' 'if' '(' expr ')' block;
else    :   'else' block;

//HELP
args    :   expr (',' expr)*;
params  :   param (',' param)*;
param   :   type ID;
block   :   '{' (stmt|expr ';')* '}';
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
grammar Cpp;

//Parser Rules
program
    :   (stmt | expr ';')* EOF
    ;

stmt
    :   classDef
    |   binding
    |   vardecl ';'
    |   fndecl ';'
    |   return ';'
    |   constructorCall
    |   fndef
    |   print
    |   while
    |   if
    ;

expr
    :   ID ('.' ID | '(' args? ')')+#FNCALL
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
    |   incDec                      #INCDEC
    |   NOT expr                    #NOT
    |   BOOL                        #BOOL
    |   CHAR                        #CHAR
    |   INT                         #INT
    |   ID                          #ID
    ;

// STMT
classDef
    :   'class' cName=ID (':' ID )? '{' classMember* '}' ';'
    ;

classMember
    :   fndecl 'override'?
    |   fndef
    |   vardecl
    |   destructor
    |   'virtual' ((fndecl ('=' '0')?)|fndef)
    |   overrideFndef
    ;

destructor
    :   '~' ID '(' ')' block
    ;

binding
    :   (expr '.')* ID assignop expr
    ;

vardecl
    :   type identifier ( EQUSIGN expr)?
    ;

constructorCall
    :   ID ID '(' args? ')'
    ;

fndecl
    :   (type | 'void')? ID '(' (paramlist)? ')'
    ;

return
    :   'return' expr?
    ;

fndef
    :   fndecl block
    ;

overrideFndef
    :   fndecl 'override' block
    ;

print
    :   'print_bool' '(' expr ')'   #pbool
    |   'print_int' '(' expr ')'    #pint
    |   'print_char' '(' expr ')'   #pchar
    ;

while
    :   'while' '(' expr ')' block
    ;

if
    :   ('if' '(' expr ')' block) elseif* else?
    ;

elseif
    :   'else' 'if' '(' expr ')' block
    ;

else
    :   'else' block
    ;

//HELP
identifier
    :   ID
    |   ID ('[' expr ']')+
    ;

incDec
    :   '++' ID     #PREINC
    |   '--' ID     #PREDEC
    |   ID '++'     #POSTINC
    |   ID '--'     #POSTDEC
    ;

args
    :   expr (',' expr)*
    ;

paramlist
    :   param (',' param)*
    ;

param
    :   type identifier
    ;

block
    :   '{' (stmt|expr ';')* '}'
    ;

type
    :   TYPEBOOL
    |   TYPECHAR
    |   TYPEINT
    |   ID
    ;

assignop
    :   ASSIGN
    |   EQUSIGN
    ;

// Lexer Rules
TYPEINT :   'int';
TYPECHAR:   'char';
TYPEBOOL:   'bool';

ASSIGN
    :   '+='
    |   '-='
    |   '*='
    |   '/='
    ;

EQUSIGN :   '=';
ID  :   [a-zA-Z][a-zA-Z0-9_]*;
NOT :   '!';
INT :   [0-9]+;
CHAR:   '\'' (~[\t\r] | '\n') '\'';

BOOL
    :   'true'
    |   'false'
    ;

WS  :   [ \t\n\r]+ -> skip;
COMMENT :   '//' ~[\n\r]* -> skip;
grammar Cpp;

//Parser Rules
program
    :   (stmt | expr ';')* EOF
    ;

stmt
    :   classDef ';'
    |   binding ';'
    |   vardecl ';'
    |   fndecl ';'
    |   return ';'
    |   constructorCall ';'
    |   fndef
    |   print ';'
    |   while
    |   if
    ;

expr
    :   objcall* ID '(' args? ')'   #FNCALL
    |   objcall* ID                 #OBJMEM
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
    |   expr '.'                    #OBJ
    |   NOT expr                    #NOT
    |   BOOL                        #BOOL
    |   CHAR                        #CHAR
    |   INT                         #INT
    |   ID                          #ID
    ;

// STMT
classDef
    :   'class' cName=ID (':' ID )? '{' classMember* '}'
    ;

classMember
    :   fndecl 'override'? ';'
    |   fndef
    |   destructor
    |   'virtual' ((fndecl (EQUSIGN INT)? ';')|fndef)
    |   vardecl ';'
    |   overrideFndef
    |   constructor
    ;

destructor
    :   'virtual'? '~' ID '(' ')' block
    ;

binding
    :   (objcall)* ID assignop expr
    ;

objcall
    :   (ID ('(' args ? ')')?  '.')
    ;

vardecl
    :   type identifier ( EQUSIGN expr)?
    ;

constructorCall
    :   ID ID '(' args? ')'
    ;

fndecl
    :   (type | 'void') ID '(' (paramlist)? ')'
    ;
    
constructor
    :   ID '(' (paramlist)? ')' block;

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
    :   '&'? ID
    |   '(' '&' ID ')' ('[' expr? ']')+
    |   ID ('[' expr? ']')+
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
NOT :   '!';
INT :   [0-9]+;
CHAR:   '\'' (~[\t\r] | '\\n') '\'';

BOOL
    :   'true'
    |   'false'
    ;

ID  :   [a-zA-Z][a-zA-Z0-9_]*;
WS  :   [ \t\n\r]+ -> skip;
COMMENT :   '//' ~[\n\r]* -> skip;
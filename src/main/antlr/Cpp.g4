grammar Cpp;

//Parser Rules
program
    :   (stmt)* EOF
    ;

stmt
    :   classDef ';'
    |   fndecl ';'
    |   fndef
    ;

blockStmt
    :   block
    |   binding ';'
    |   vardecl ';'
    |   return ';'
    |   constructorCall ';'
    |   print ';'
    |   while
    |   if
    |   expr ';'
    ;


expr
    :   '(' expr ')'                #COL
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
    |   e1 = expr '&&' e2 = expr    #AND
    |   e1 = expr '||' e2 = expr    #OR
    |   ID (LEFTBRACKET expr RIGHTBRACKET)+        #ARRACC
    |   '{'args'}'                  #ARRVALS
    |   incDec                      #INCDECWRAP
    |   NOT expr                    #NOT
    |   BOOL                        #BOOL
    |   CHAR                        #CHAR
    |   INT                         #INT
    |   ID                          #ID
    |   ('(''*'THIS')' ('.'))? objcall* fncall #FNCALLWRAP
    |   ('(''*'THIS')' ('.'))? objcall* ID (LEFTBRACKET expr RIGHTBRACKET)*    #OBJMEM
    ;

// STMT
classDef
    :   'class' cName=ID (':' 'public'? pName=ID )? '{' 'public:'? classMember* '}'
    ;

classMember
    :   overrideFndecl ';'
    |   virtual
    |   vardecl ';'
    |   overrideFndef
    |   constructor
    |   operator
    ;

operator
    :   returnType=ID '&' 'operator=' '(' paramType=ID '&' paramName=ID ')' block
    ;

overrideFndecl
    :   fndecl OVERRIDE?
    ;

virtual
    // TODO: abstract classes?
    :   'virtual' ((overrideFndecl ';')|overrideFndef)
    ;

binding
    :   ('(''*'THIS')' ('.'))? (objcall)* ID (LEFTBRACKET expr RIGHTBRACKET)* assignop exprR = expr
    ;

objcall
    :   (ID (LEFTBRACKET expr RIGHTBRACKET)*|fncall)  ('.')
    ;

fncall
    : ID '(' args? ')'
    ;

vardecl
    :   type identifier ( EQUSIGN expr)?
    ;

constructorCall
    :   cName = ID objName = ID '(' args ')'
    ;

fndecl
    :   (type | 'void') ID '(' (paramlist)? ')'
    ;
    
constructor
    :   ID '(' (paramlist)? ')' block;

return
    :   'return' (expr|('*'THIS))?
    ;

fndef
    :   fndecl block
    ;

overrideFndef
    :   overrideFndecl block
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
    |   '(' '&' ID ')' (LEFTBRACKET expr? RIGHTBRACKET)+
    |   ID (LEFTBRACKET expr? RIGHTBRACKET)+
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
    :   '{' (blockStmt)* '}'
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

THIS    :   'this';

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

LEFTBRACKET: '[';
RIGHTBRACKET: ']';

OVERRIDE: 'override';

ID  :   [a-zA-Z][a-zA-Z0-9_]*;
WS  :   [ \t\n\r]+ -> skip;
COMMENT :   '//' ~[\n\r]* -> skip;
MULTI_LINE_COMMENT : '/*' .*? '*/' -> skip;
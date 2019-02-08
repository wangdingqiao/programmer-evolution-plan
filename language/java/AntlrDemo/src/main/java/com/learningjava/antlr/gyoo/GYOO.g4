// example from: https://progur.com/2016/09/how-to-create-language-using-antlr4.html
grammar GYOO;
program   : 'begin' statement+ 'end';
          
statement : assign | add | print;

assign    : 'let' ID 'be' (NUMBER | ID) ;
print     : 'print' (NUMBER | ID) ;
add       : 'add' (NUMBER | ID) 'to' ID ;

ID     : [a-z]+ ;
NUMBER : [0-9]+ ;
WS     : [ \r\n\t]+ -> skip;

//runwith : antlr4 -package com.learningjava.antlr.gyoo .\gyoo\GYOO.g4
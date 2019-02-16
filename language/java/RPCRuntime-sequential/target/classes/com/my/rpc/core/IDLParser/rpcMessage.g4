grammar rpcMessage;

prog: stat+; // the start rule

stat: importDefStat
	| packageDefStat
	| messageBlockStat
	| enumDefStat     
	| interfaceDefStat
	;

// import and package define
importDefStat: 'import' packageExpr ('as' ID) ? DELIMITER;
packageDefStat: 'package'  packageExpr DELIMITER;
packageExpr:  ID ('.' ID)*;

// message block define
messageBlockStat: 'messageBlock' ID '{' messageDefStat+ '}' DELIMITER;

// message define
messageDefStat: messageHeader '{' messageBody '}' DELIMITER;
messageHeader: 'message' ID;
messageBody: messageMember+;
messageMember: messageDataType ID  DELIMITER;
messageDataType : ('float' | 'int' | 'string' | 'double' | 'bool' | 'byte' | 'binary' | 'short' | 'long' | 'date')  # builtInType
				| 'seq<' messageDataType '>' # seqType
				| 'map<' messageDataType ',' messageDataType '>' 	# mapType
				| packageExpr			  							# userDefineType
				;

// enum define
enumDefStat: enumHeader '{' enumBody '}' DELIMITER;
enumHeader: 'enum' ID;
enumBody: enumMember+;
enumMember: ID enumValue? ',';
enumValue: '=' INT;

// interface define
interfaceDefStat: 'interface' ID '{' methodDefStat+ '}' DELIMITER;

// method define
methodDefStat: messageDataType ID '(' methodParams? ')' '=' methodIndexer DELIMITER;
methodParams: methodParam (',' methodParam)*;
methodParam: messageDataType ID;
methodIndexer: INT;

// token rules
DELIMITER: ';';
ID: [a-zA-Z][a-zA-Z0-9]* ;
INT: [0-9]+;
WS:[ \t\r\n\u000C]+	-> skip ;
COMMENT:'/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;

// antlr4 -no-listener -visitor -package com.learningjava.rpc.IDL rpcMessage.g4
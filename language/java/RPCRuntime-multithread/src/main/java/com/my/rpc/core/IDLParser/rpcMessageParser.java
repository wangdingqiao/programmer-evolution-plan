// Generated from rpcMessage.g4 by ANTLR 4.7.1
package com.my.rpc.core.IDLParser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class rpcMessageParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, DELIMITER=28, ID=29, INT=30, WS=31, COMMENT=32, 
		LINE_COMMENT=33;
	public static final int
		RULE_prog = 0, RULE_stat = 1, RULE_importDefStat = 2, RULE_packageDefStat = 3, 
		RULE_packageExpr = 4, RULE_messageBlockStat = 5, RULE_messageDefStat = 6, 
		RULE_messageHeader = 7, RULE_messageBody = 8, RULE_messageMember = 9, 
		RULE_messageDataType = 10, RULE_enumDefStat = 11, RULE_enumHeader = 12, 
		RULE_enumBody = 13, RULE_enumMember = 14, RULE_enumValue = 15, RULE_interfaceDefStat = 16, 
		RULE_methodDefStat = 17, RULE_methodParams = 18, RULE_methodParam = 19, 
		RULE_methodIndexer = 20;
	public static final String[] ruleNames = {
		"prog", "stat", "importDefStat", "packageDefStat", "packageExpr", "messageBlockStat", 
		"messageDefStat", "messageHeader", "messageBody", "messageMember", "messageDataType", 
		"enumDefStat", "enumHeader", "enumBody", "enumMember", "enumValue", "interfaceDefStat", 
		"methodDefStat", "methodParams", "methodParam", "methodIndexer"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'import'", "'as'", "'package'", "'.'", "'messageBlock'", "'{'", 
		"'}'", "'message'", "'float'", "'int'", "'string'", "'double'", "'bool'", 
		"'byte'", "'binary'", "'short'", "'long'", "'date'", "'seq<'", "'>'", 
		"'map<'", "','", "'enum'", "'='", "'interface'", "'('", "')'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, "DELIMITER", "ID", "INT", "WS", "COMMENT", "LINE_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "rpcMessage.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public rpcMessageParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(42);
				stat();
				}
				}
				setState(45); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__4) | (1L << T__22) | (1L << T__24))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatContext extends ParserRuleContext {
		public ImportDefStatContext importDefStat() {
			return getRuleContext(ImportDefStatContext.class,0);
		}
		public PackageDefStatContext packageDefStat() {
			return getRuleContext(PackageDefStatContext.class,0);
		}
		public MessageBlockStatContext messageBlockStat() {
			return getRuleContext(MessageBlockStatContext.class,0);
		}
		public EnumDefStatContext enumDefStat() {
			return getRuleContext(EnumDefStatContext.class,0);
		}
		public InterfaceDefStatContext interfaceDefStat() {
			return getRuleContext(InterfaceDefStatContext.class,0);
		}
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stat);
		try {
			setState(52);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(47);
				importDefStat();
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				packageDefStat();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 3);
				{
				setState(49);
				messageBlockStat();
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 4);
				{
				setState(50);
				enumDefStat();
				}
				break;
			case T__24:
				enterOuterAlt(_localctx, 5);
				{
				setState(51);
				interfaceDefStat();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportDefStatContext extends ParserRuleContext {
		public PackageExprContext packageExpr() {
			return getRuleContext(PackageExprContext.class,0);
		}
		public TerminalNode DELIMITER() { return getToken(rpcMessageParser.DELIMITER, 0); }
		public TerminalNode ID() { return getToken(rpcMessageParser.ID, 0); }
		public ImportDefStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDefStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitImportDefStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDefStatContext importDefStat() throws RecognitionException {
		ImportDefStatContext _localctx = new ImportDefStatContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_importDefStat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(T__0);
			setState(55);
			packageExpr();
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(56);
				match(T__1);
				setState(57);
				match(ID);
				}
			}

			setState(60);
			match(DELIMITER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PackageDefStatContext extends ParserRuleContext {
		public PackageExprContext packageExpr() {
			return getRuleContext(PackageExprContext.class,0);
		}
		public TerminalNode DELIMITER() { return getToken(rpcMessageParser.DELIMITER, 0); }
		public PackageDefStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageDefStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitPackageDefStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PackageDefStatContext packageDefStat() throws RecognitionException {
		PackageDefStatContext _localctx = new PackageDefStatContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_packageDefStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(T__2);
			setState(63);
			packageExpr();
			setState(64);
			match(DELIMITER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PackageExprContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(rpcMessageParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(rpcMessageParser.ID, i);
		}
		public PackageExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitPackageExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PackageExprContext packageExpr() throws RecognitionException {
		PackageExprContext _localctx = new PackageExprContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_packageExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(ID);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(67);
				match(T__3);
				setState(68);
				match(ID);
				}
				}
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MessageBlockStatContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(rpcMessageParser.ID, 0); }
		public TerminalNode DELIMITER() { return getToken(rpcMessageParser.DELIMITER, 0); }
		public List<MessageDefStatContext> messageDefStat() {
			return getRuleContexts(MessageDefStatContext.class);
		}
		public MessageDefStatContext messageDefStat(int i) {
			return getRuleContext(MessageDefStatContext.class,i);
		}
		public MessageBlockStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_messageBlockStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitMessageBlockStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MessageBlockStatContext messageBlockStat() throws RecognitionException {
		MessageBlockStatContext _localctx = new MessageBlockStatContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_messageBlockStat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(T__4);
			setState(75);
			match(ID);
			setState(76);
			match(T__5);
			setState(78); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(77);
				messageDefStat();
				}
				}
				setState(80); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__7 );
			setState(82);
			match(T__6);
			setState(83);
			match(DELIMITER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MessageDefStatContext extends ParserRuleContext {
		public MessageHeaderContext messageHeader() {
			return getRuleContext(MessageHeaderContext.class,0);
		}
		public MessageBodyContext messageBody() {
			return getRuleContext(MessageBodyContext.class,0);
		}
		public TerminalNode DELIMITER() { return getToken(rpcMessageParser.DELIMITER, 0); }
		public MessageDefStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_messageDefStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitMessageDefStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MessageDefStatContext messageDefStat() throws RecognitionException {
		MessageDefStatContext _localctx = new MessageDefStatContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_messageDefStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			messageHeader();
			setState(86);
			match(T__5);
			setState(87);
			messageBody();
			setState(88);
			match(T__6);
			setState(89);
			match(DELIMITER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MessageHeaderContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(rpcMessageParser.ID, 0); }
		public MessageHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_messageHeader; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitMessageHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MessageHeaderContext messageHeader() throws RecognitionException {
		MessageHeaderContext _localctx = new MessageHeaderContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_messageHeader);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			match(T__7);
			setState(92);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MessageBodyContext extends ParserRuleContext {
		public List<MessageMemberContext> messageMember() {
			return getRuleContexts(MessageMemberContext.class);
		}
		public MessageMemberContext messageMember(int i) {
			return getRuleContext(MessageMemberContext.class,i);
		}
		public MessageBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_messageBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitMessageBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MessageBodyContext messageBody() throws RecognitionException {
		MessageBodyContext _localctx = new MessageBodyContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_messageBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(94);
				messageMember();
				}
				}
				setState(97); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << ID))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MessageMemberContext extends ParserRuleContext {
		public MessageDataTypeContext messageDataType() {
			return getRuleContext(MessageDataTypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(rpcMessageParser.ID, 0); }
		public TerminalNode DELIMITER() { return getToken(rpcMessageParser.DELIMITER, 0); }
		public MessageMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_messageMember; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitMessageMember(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MessageMemberContext messageMember() throws RecognitionException {
		MessageMemberContext _localctx = new MessageMemberContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_messageMember);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			messageDataType();
			setState(100);
			match(ID);
			setState(101);
			match(DELIMITER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MessageDataTypeContext extends ParserRuleContext {
		public MessageDataTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_messageDataType; }
	 
		public MessageDataTypeContext() { }
		public void copyFrom(MessageDataTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BuiltInTypeContext extends MessageDataTypeContext {
		public BuiltInTypeContext(MessageDataTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitBuiltInType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MapTypeContext extends MessageDataTypeContext {
		public List<MessageDataTypeContext> messageDataType() {
			return getRuleContexts(MessageDataTypeContext.class);
		}
		public MessageDataTypeContext messageDataType(int i) {
			return getRuleContext(MessageDataTypeContext.class,i);
		}
		public MapTypeContext(MessageDataTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitMapType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UserDefineTypeContext extends MessageDataTypeContext {
		public PackageExprContext packageExpr() {
			return getRuleContext(PackageExprContext.class,0);
		}
		public UserDefineTypeContext(MessageDataTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitUserDefineType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SeqTypeContext extends MessageDataTypeContext {
		public MessageDataTypeContext messageDataType() {
			return getRuleContext(MessageDataTypeContext.class,0);
		}
		public SeqTypeContext(MessageDataTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitSeqType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MessageDataTypeContext messageDataType() throws RecognitionException {
		MessageDataTypeContext _localctx = new MessageDataTypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_messageDataType);
		int _la;
		try {
			setState(115);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__16:
			case T__17:
				_localctx = new BuiltInTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(103);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case T__18:
				_localctx = new SeqTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(104);
				match(T__18);
				setState(105);
				messageDataType();
				setState(106);
				match(T__19);
				}
				break;
			case T__20:
				_localctx = new MapTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(108);
				match(T__20);
				setState(109);
				messageDataType();
				setState(110);
				match(T__21);
				setState(111);
				messageDataType();
				setState(112);
				match(T__19);
				}
				break;
			case ID:
				_localctx = new UserDefineTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(114);
				packageExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumDefStatContext extends ParserRuleContext {
		public EnumHeaderContext enumHeader() {
			return getRuleContext(EnumHeaderContext.class,0);
		}
		public EnumBodyContext enumBody() {
			return getRuleContext(EnumBodyContext.class,0);
		}
		public TerminalNode DELIMITER() { return getToken(rpcMessageParser.DELIMITER, 0); }
		public EnumDefStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumDefStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitEnumDefStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumDefStatContext enumDefStat() throws RecognitionException {
		EnumDefStatContext _localctx = new EnumDefStatContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_enumDefStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			enumHeader();
			setState(118);
			match(T__5);
			setState(119);
			enumBody();
			setState(120);
			match(T__6);
			setState(121);
			match(DELIMITER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumHeaderContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(rpcMessageParser.ID, 0); }
		public EnumHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumHeader; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitEnumHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumHeaderContext enumHeader() throws RecognitionException {
		EnumHeaderContext _localctx = new EnumHeaderContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_enumHeader);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(T__22);
			setState(124);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumBodyContext extends ParserRuleContext {
		public List<EnumMemberContext> enumMember() {
			return getRuleContexts(EnumMemberContext.class);
		}
		public EnumMemberContext enumMember(int i) {
			return getRuleContext(EnumMemberContext.class,i);
		}
		public EnumBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitEnumBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumBodyContext enumBody() throws RecognitionException {
		EnumBodyContext _localctx = new EnumBodyContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_enumBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(126);
				enumMember();
				}
				}
				setState(129); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumMemberContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(rpcMessageParser.ID, 0); }
		public EnumValueContext enumValue() {
			return getRuleContext(EnumValueContext.class,0);
		}
		public EnumMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumMember; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitEnumMember(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumMemberContext enumMember() throws RecognitionException {
		EnumMemberContext _localctx = new EnumMemberContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_enumMember);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(ID);
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__23) {
				{
				setState(132);
				enumValue();
				}
			}

			setState(135);
			match(T__21);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumValueContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(rpcMessageParser.INT, 0); }
		public EnumValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitEnumValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumValueContext enumValue() throws RecognitionException {
		EnumValueContext _localctx = new EnumValueContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_enumValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(T__23);
			setState(138);
			match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InterfaceDefStatContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(rpcMessageParser.ID, 0); }
		public TerminalNode DELIMITER() { return getToken(rpcMessageParser.DELIMITER, 0); }
		public List<MethodDefStatContext> methodDefStat() {
			return getRuleContexts(MethodDefStatContext.class);
		}
		public MethodDefStatContext methodDefStat(int i) {
			return getRuleContext(MethodDefStatContext.class,i);
		}
		public InterfaceDefStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interfaceDefStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitInterfaceDefStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InterfaceDefStatContext interfaceDefStat() throws RecognitionException {
		InterfaceDefStatContext _localctx = new InterfaceDefStatContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_interfaceDefStat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(T__24);
			setState(141);
			match(ID);
			setState(142);
			match(T__5);
			setState(144); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(143);
				methodDefStat();
				}
				}
				setState(146); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << ID))) != 0) );
			setState(148);
			match(T__6);
			setState(149);
			match(DELIMITER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDefStatContext extends ParserRuleContext {
		public MessageDataTypeContext messageDataType() {
			return getRuleContext(MessageDataTypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(rpcMessageParser.ID, 0); }
		public MethodIndexerContext methodIndexer() {
			return getRuleContext(MethodIndexerContext.class,0);
		}
		public TerminalNode DELIMITER() { return getToken(rpcMessageParser.DELIMITER, 0); }
		public MethodParamsContext methodParams() {
			return getRuleContext(MethodParamsContext.class,0);
		}
		public MethodDefStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDefStat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitMethodDefStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDefStatContext methodDefStat() throws RecognitionException {
		MethodDefStatContext _localctx = new MethodDefStatContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_methodDefStat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			messageDataType();
			setState(152);
			match(ID);
			setState(153);
			match(T__25);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__20) | (1L << ID))) != 0)) {
				{
				setState(154);
				methodParams();
				}
			}

			setState(157);
			match(T__26);
			setState(158);
			match(T__23);
			setState(159);
			methodIndexer();
			setState(160);
			match(DELIMITER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodParamsContext extends ParserRuleContext {
		public List<MethodParamContext> methodParam() {
			return getRuleContexts(MethodParamContext.class);
		}
		public MethodParamContext methodParam(int i) {
			return getRuleContext(MethodParamContext.class,i);
		}
		public MethodParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodParams; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitMethodParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodParamsContext methodParams() throws RecognitionException {
		MethodParamsContext _localctx = new MethodParamsContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_methodParams);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			methodParam();
			setState(167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21) {
				{
				{
				setState(163);
				match(T__21);
				setState(164);
				methodParam();
				}
				}
				setState(169);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodParamContext extends ParserRuleContext {
		public MessageDataTypeContext messageDataType() {
			return getRuleContext(MessageDataTypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(rpcMessageParser.ID, 0); }
		public MethodParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodParam; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitMethodParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodParamContext methodParam() throws RecognitionException {
		MethodParamContext _localctx = new MethodParamContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_methodParam);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			messageDataType();
			setState(171);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodIndexerContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(rpcMessageParser.INT, 0); }
		public MethodIndexerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodIndexer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof rpcMessageVisitor ) return ((rpcMessageVisitor<? extends T>)visitor).visitMethodIndexer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodIndexerContext methodIndexer() throws RecognitionException {
		MethodIndexerContext _localctx = new MethodIndexerContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_methodIndexer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3#\u00b2\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\6\2.\n\2\r\2\16\2/\3\3\3"+
		"\3\3\3\3\3\3\3\5\3\67\n\3\3\4\3\4\3\4\3\4\5\4=\n\4\3\4\3\4\3\5\3\5\3\5"+
		"\3\5\3\6\3\6\3\6\7\6H\n\6\f\6\16\6K\13\6\3\7\3\7\3\7\3\7\6\7Q\n\7\r\7"+
		"\16\7R\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\6\nb\n\n\r"+
		"\n\16\nc\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\5\fv\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\17\6\17\u0082"+
		"\n\17\r\17\16\17\u0083\3\20\3\20\5\20\u0088\n\20\3\20\3\20\3\21\3\21\3"+
		"\21\3\22\3\22\3\22\3\22\6\22\u0093\n\22\r\22\16\22\u0094\3\22\3\22\3\22"+
		"\3\23\3\23\3\23\3\23\5\23\u009e\n\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24"+
		"\3\24\7\24\u00a8\n\24\f\24\16\24\u00ab\13\24\3\25\3\25\3\25\3\26\3\26"+
		"\3\26\2\2\27\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*\2\3\3\2\13"+
		"\24\2\u00ad\2-\3\2\2\2\4\66\3\2\2\2\68\3\2\2\2\b@\3\2\2\2\nD\3\2\2\2\f"+
		"L\3\2\2\2\16W\3\2\2\2\20]\3\2\2\2\22a\3\2\2\2\24e\3\2\2\2\26u\3\2\2\2"+
		"\30w\3\2\2\2\32}\3\2\2\2\34\u0081\3\2\2\2\36\u0085\3\2\2\2 \u008b\3\2"+
		"\2\2\"\u008e\3\2\2\2$\u0099\3\2\2\2&\u00a4\3\2\2\2(\u00ac\3\2\2\2*\u00af"+
		"\3\2\2\2,.\5\4\3\2-,\3\2\2\2./\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\3\3\2"+
		"\2\2\61\67\5\6\4\2\62\67\5\b\5\2\63\67\5\f\7\2\64\67\5\30\r\2\65\67\5"+
		"\"\22\2\66\61\3\2\2\2\66\62\3\2\2\2\66\63\3\2\2\2\66\64\3\2\2\2\66\65"+
		"\3\2\2\2\67\5\3\2\2\289\7\3\2\29<\5\n\6\2:;\7\4\2\2;=\7\37\2\2<:\3\2\2"+
		"\2<=\3\2\2\2=>\3\2\2\2>?\7\36\2\2?\7\3\2\2\2@A\7\5\2\2AB\5\n\6\2BC\7\36"+
		"\2\2C\t\3\2\2\2DI\7\37\2\2EF\7\6\2\2FH\7\37\2\2GE\3\2\2\2HK\3\2\2\2IG"+
		"\3\2\2\2IJ\3\2\2\2J\13\3\2\2\2KI\3\2\2\2LM\7\7\2\2MN\7\37\2\2NP\7\b\2"+
		"\2OQ\5\16\b\2PO\3\2\2\2QR\3\2\2\2RP\3\2\2\2RS\3\2\2\2ST\3\2\2\2TU\7\t"+
		"\2\2UV\7\36\2\2V\r\3\2\2\2WX\5\20\t\2XY\7\b\2\2YZ\5\22\n\2Z[\7\t\2\2["+
		"\\\7\36\2\2\\\17\3\2\2\2]^\7\n\2\2^_\7\37\2\2_\21\3\2\2\2`b\5\24\13\2"+
		"a`\3\2\2\2bc\3\2\2\2ca\3\2\2\2cd\3\2\2\2d\23\3\2\2\2ef\5\26\f\2fg\7\37"+
		"\2\2gh\7\36\2\2h\25\3\2\2\2iv\t\2\2\2jk\7\25\2\2kl\5\26\f\2lm\7\26\2\2"+
		"mv\3\2\2\2no\7\27\2\2op\5\26\f\2pq\7\30\2\2qr\5\26\f\2rs\7\26\2\2sv\3"+
		"\2\2\2tv\5\n\6\2ui\3\2\2\2uj\3\2\2\2un\3\2\2\2ut\3\2\2\2v\27\3\2\2\2w"+
		"x\5\32\16\2xy\7\b\2\2yz\5\34\17\2z{\7\t\2\2{|\7\36\2\2|\31\3\2\2\2}~\7"+
		"\31\2\2~\177\7\37\2\2\177\33\3\2\2\2\u0080\u0082\5\36\20\2\u0081\u0080"+
		"\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084"+
		"\35\3\2\2\2\u0085\u0087\7\37\2\2\u0086\u0088\5 \21\2\u0087\u0086\3\2\2"+
		"\2\u0087\u0088\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008a\7\30\2\2\u008a"+
		"\37\3\2\2\2\u008b\u008c\7\32\2\2\u008c\u008d\7 \2\2\u008d!\3\2\2\2\u008e"+
		"\u008f\7\33\2\2\u008f\u0090\7\37\2\2\u0090\u0092\7\b\2\2\u0091\u0093\5"+
		"$\23\2\u0092\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0092\3\2\2\2\u0094"+
		"\u0095\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0097\7\t\2\2\u0097\u0098\7\36"+
		"\2\2\u0098#\3\2\2\2\u0099\u009a\5\26\f\2\u009a\u009b\7\37\2\2\u009b\u009d"+
		"\7\34\2\2\u009c\u009e\5&\24\2\u009d\u009c\3\2\2\2\u009d\u009e\3\2\2\2"+
		"\u009e\u009f\3\2\2\2\u009f\u00a0\7\35\2\2\u00a0\u00a1\7\32\2\2\u00a1\u00a2"+
		"\5*\26\2\u00a2\u00a3\7\36\2\2\u00a3%\3\2\2\2\u00a4\u00a9\5(\25\2\u00a5"+
		"\u00a6\7\30\2\2\u00a6\u00a8\5(\25\2\u00a7\u00a5\3\2\2\2\u00a8\u00ab\3"+
		"\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\'\3\2\2\2\u00ab\u00a9"+
		"\3\2\2\2\u00ac\u00ad\5\26\f\2\u00ad\u00ae\7\37\2\2\u00ae)\3\2\2\2\u00af"+
		"\u00b0\7 \2\2\u00b0+\3\2\2\2\16/\66<IRcu\u0083\u0087\u0094\u009d\u00a9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
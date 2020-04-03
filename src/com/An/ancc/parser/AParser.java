package com.An.ancc.parser;

import com.An.ancc.productions.*;
import com.An.ancc.productions.PToken.*;
import java.util.*;

public class AParser {
	private final ALexer lexer;
	private final HashMap<NonTerm,HashMap<Term,Production>> map;
	private final String start;
	private final String eof;
	private AToken token;
	public AParser(ALexer lexer, HashMap<NonTerm,HashMap<Term,Production>> map, String start, String eof) {
		this.lexer = lexer;
		this.map = map;
		this.start = start;
		this.eof = eof;
	}
	public ANode parse() {
		token=lexer.next();
		return parse(new NonTerm(start));
	}
	private ANode parse(NonTerm s){
		HashMap<Term,Production> ps=map.get(s);
		if(ps==null){
			throw new RuntimeException("Invalid nonterm");
		}
		if(token==null){
			throw new NullPointerException("token shouldn't be null");
		}
		Production p=ps.get(token.getTerm());
		if(p==null){
			throw new RuntimeException("Unexpected token");
		}
		ArrayList<Object> nodeTokens=new ArrayList<>();
		for(PToken t:p.getTokens()){
			if(t instanceof NonTerm){
				nodeTokens.add(parse((NonTerm)t));
			}else{
				if(token==null){
					throw new NullPointerException("Unexpected EOF");
				}
				if(t.equals(token.getTerm())){
					nodeTokens.add(token);
					token=lexer.next();
				}else{
					throw new RuntimeException("Unexpected token");
				}
			}
		}
		return new ANode(p.getId(),nodeTokens);
	}
}

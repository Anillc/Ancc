package com.An.ancc;
import com.An.ancc.productions.*;
import java.io.*;
import com.An.ancc.parser.*;
import java.util.*;
import com.An.ancc.productions.PToken.*;

public class Main{
	private static class Token implements AToken {
		private String name;
		public Token(String name){
			this.name=name;
		}
		@Override
		public PToken.Term getTerm() {
			return new PToken.Term(name);
		}
	}
	private static class Lexer implements ALexer {
		private ArrayList<AToken> list=new ArrayList<AToken>(){{
			add(new Token("id"));
			add(new Token("+"));
			add(new Token("id"));
			add(new Token("$"));
		}};
		private int pos=0;
		@Override
		public AToken next() {
			/*if(pos+1>=list.size()){
				return null;
			}*/
			return list.get(pos++);
		}
	}
	public static void main(String[] args) throws Exception{
		FileInputStream fis=new FileInputStream("test.ancc");
		AnccReader reader=new AnccReader(new InputStreamReader(fis));
		Production[] ps=reader.parse();
		HashMap<PToken.NonTerm,HashMap<PToken.Term,Production>> map=LLTableGenerater.gen(ps,"E","$");
		AParser parser=new AParser(new Lexer(),map,"E","$");
		ANode node=parser.parse();
	}
}

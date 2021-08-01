package com.An.ancc.productions;
import java.io.*;
import java.util.*;
import com.An.ancc.productions.PToken.Term;
import com.An.ancc.productions.PToken.NonTerm;

public class AnccReader{
	private final Reader reader;
	private final ArrayList<String> ts=new ArrayList<>();//terms
	private final ArrayList<Production> productions=new ArrayList<>();
	public AnccReader(Reader reader){
		this.reader=reader;
	}
	public Production[] parse() throws Exception{
		terms();
		boolean ctn=true;
		o:while(ctn){
			StringBuilder id=new StringBuilder();
			boolean f=true;
			while(true){
				int c=reader.read();
				if(f){
					f=false;
					if(c=='\n'){
						while(true){
							c=reader.read();
							if(c!='\n'){
								break;
							}
						}
					}
					if(c==-1)break o;
				}
				if(c!=':'){
					id.append((char)c);
				}else{
					break;
				}
			}
			StringBuilder name=new StringBuilder();
			while(true){
				int c=read();
				if(c!=':'){
					name.append((char)c);
				}else{
					break;
				}
			}
			ArrayList<PToken> tokens=new ArrayList<>();
			while(true){
				StringBuilder token=new StringBuilder();
				int c;
				boolean ft=true;
				while((c=reader.read())!=-1){
					if(c==' '|| c=='\n'){
						break;
					}
					if(ft){
						ft=false;
					}
					token.append((char)c);
				}
				if(ft && c=='\n'){
					break;
				}
				String t=token.toString();
				if(ts.contains(t)){
					tokens.add(new Term(t));
				}else{
					tokens.add(new NonTerm(t));
				}
				if(c==-1){
					ctn=false;
					break;
				}
				if(c=='\n'){
					break;
				}
			}
			productions.add(new Production(id.toString().trim(),name.toString(),tokens.toArray(new PToken[tokens.size()])));
		}
		return productions.toArray(new Production[productions.size()]);
	}
	private void err(String err) throws Exception{
		throw new Exception(err);
	}
	private char read() throws Exception{
		int c=reader.read();
		if(c==-1){
			err("EOF");
		}
		return (char)c;
	}
	private void terms() throws Exception{
		boolean ctn=true;
		while(ctn){
			StringBuilder term=new StringBuilder();
			while(true){
				char c=read();
				if(c==','){
					break;
				}
				if(c==';'){
					ctn=false;
					break;
				}
				term.append(c);
			}
			ts.add(term.toString().trim());
		}
	}
}

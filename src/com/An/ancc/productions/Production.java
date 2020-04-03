package com.An.ancc.productions;
import java.io.*;

public class Production implements Serializable{
	private final String id;
	private final String name;
	private final PToken[] tokens;

	public Production(String id,String name,PToken[] tokens){
		this.id=id;
		this.name=name;
		this.tokens=tokens;
	}

	public String getId(){
		return id;
	}
	
	public String getName() {
		return name;
	}

	public PToken[] getTokens() {
		return tokens;
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(id);
		sb.append(':');
		sb.append(name);
		sb.append(':');
		for(PToken t:tokens){
			sb.append(t.toString());
		}
		return sb.toString();
	}
}

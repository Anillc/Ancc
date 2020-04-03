package com.An.ancc;
import java.util.*;
import com.An.ancc.productions.PToken.Term;
import com.An.ancc.productions.PToken.NonTerm;
import com.An.ancc.productions.Production;
import com.An.ancc.utils.*;

public class LLTableGenerater{
	public static HashMap<NonTerm,HashMap<Term,Production>> gen(Production[] ps,String start,String eof){//确保一个不会被使用的非终结符来作为$
		HashMap<NonTerm,HashMap<Term,Production>> map=new HashMap<>();
		for(Production p:ps){
			NonTerm name=new NonTerm(p.getName());
			HashMap<Term,Production> map2=map.get(name);
			if(map2==null){
				map2=new HashMap<>();
				map.put(name,map2);
			}
			Set<Term> first=Functions.FIRST(p.getTokens(),ps,new Stack<String>());
			for(Term t:first){
				if(t!=Functions.E.INSTANCE){
					map2.put(t,p);
				}else{
					Set<Term> follow=Functions.FOLLOW(start,eof,name,ps,new Stack<String>());
					for(Term t2:follow){
						map2.put(t2,p);
					}
				}
			}
		}
		return map;
	}
}

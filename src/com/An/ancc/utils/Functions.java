package com.An.ancc.utils;

import com.An.ancc.productions.Production;
import com.An.ancc.productions.PToken;
import com.An.ancc.productions.PToken.Term;
import com.An.ancc.productions.PToken.NonTerm;
import java.util.*;

public class Functions{
	public static Set<Term> FOLLOW(String start,String eof,NonTerm nt,Production[] ps,Stack<String> followStack){
		HashSet<Term> res=new HashSet<>();
		if(followStack.contains(nt.getName())){
			return res;
		}
		followStack.push(nt.getName());
		for(Production p:ps){
			PToken[] tokens=p.getTokens();
			for(int i=0;i<tokens.length;i++){
				PToken token=tokens[i];
				if(nt.equals(token)){
					if(i==tokens.length){
						res.addAll(FOLLOW(start,eof,new NonTerm(p.getName()),ps,followStack));
					}else{
						int count=tokens.length-i-1;
						PToken[] ts=new PToken[count];
						System.arraycopy(tokens,i+1,ts,0,count);
						Set<Term> fres=FIRST(ts,ps,new Stack<String>());
						if(fres.contains(E.INSTANCE)){
							res.addAll(FOLLOW(start,eof,new NonTerm(p.getName()),ps,followStack));
							fres.remove(E.INSTANCE);
						}
						res.addAll(fres);
					}
				}
			}
		}
		followStack.pop();
		if(nt.getName().equals(start)){
			res.add(new Term(eof));
		}
		return res;
	}
	public static Set<Term> FIRST(PToken[] tokens,Production[] productions,Stack<String> firstStack){
		HashSet<Term> res=new HashSet<>();
		if(tokens.length==0){
			res.add(E.INSTANCE);
			return res;
		}
		for(int i=0;i<tokens.length;i++){
			PToken token=tokens[i];
			if(token instanceof Term){
				res.add((Term)token);
				break;
			}
			NonTerm nt=(NonTerm)token;
			if(firstStack.contains(nt.getName())){
			//此处判断是否进入了递归的循环，如果进入了一样的产生式则退出，因为上一次进入同样的产生式时已经将first加入了结果，所以这里直接返回空
				break;
			}
			List<Production> ps=findProductionByName(productions,nt.getName());
			boolean b=true;
			firstStack.push(nt.getName());
			for(Production p:ps){
				Set<Term> f=FIRST(p.getTokens(),productions,firstStack);
				if(f.contains(E.INSTANCE)){
					b=false;
					f.remove(E.INSTANCE);
				}
				res.addAll(f);
			}
			firstStack.pop();
			if(b){
				break;
			}
			if(i==tokens.length-1){
				res.add(E.INSTANCE);
			}
		}
		return res;
	}
	private static List<Production> findProductionByName(Production[] ps,String name){
		ArrayList<Production> res=new ArrayList<>();
		for(Production p:ps){
			if(p.getName().equals(name)){
				res.add(p);
			}
		}
		return res;
	}
	public static class E extends Term{//empty set
		public static final E INSTANCE=new E();
		private E(){
			super("");
		}
	}
}

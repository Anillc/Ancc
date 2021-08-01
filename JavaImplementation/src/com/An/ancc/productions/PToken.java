package com.An.ancc.productions;
import java.util.*;
import java.io.*;

public interface PToken{
	public static class Term implements PToken,Serializable{
		private final String name;
		public Term(String name){
			this.name=name;
		}
		public String getName(){
			return name;
		}
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Term)){
				return false;
			}
			return Objects.equals(name,((Term)obj).getName());
		}
		@Override
		public int hashCode() {
			return name.hashCode();
		}
		@Override
		public String toString() {
			return name;
		}
	}
	public static class NonTerm implements PToken,Serializable{
		private final String name;
		public NonTerm(String name){
			this.name=name;
		}
		public String getName(){
			return name;
		}
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof NonTerm)){
				return false;
			}
			return Objects.equals(name,((NonTerm)obj).getName());
		}
		@Override
		public int hashCode() {
			return name.hashCode();
		}
		@Override
		public String toString() {
			return name;
		}
	}
}

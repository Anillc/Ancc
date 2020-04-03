package com.An.ancc.parser;

import java.util.*;

public class ANode {
	private final String pid;
	private final List<Object> nodes;//ANode和AToken两种对象

	public ANode(String pid, List<Object> nodes) {
		this.pid = pid;
		this.nodes=nodes;
	}

	public String getPid() {
		return pid;
	}

	public List<Object> getNodes() {
		return nodes;
	}
}

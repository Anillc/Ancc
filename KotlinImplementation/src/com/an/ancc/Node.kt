package com.an.ancc

/**
 * @param nodes 此处的 nodes 中可能会有 token 和 node，顺序为产生式 tokens 的顺序
 */
data class Node(val id: String, val nodes: List<Any>)
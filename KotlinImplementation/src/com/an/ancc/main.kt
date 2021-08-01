package com.an.ancc

class MLexer(private vararg val tokens: String) : Lexer() {
    class Num(val num: String) : Grammar.Token.Term("num")

    private var position = 0
    override var peek: Grammar.Token.Term? = null

    override fun next() {
        peek = if (position >= tokens.size) {
            Grammar.Token.Term("$")
        } else {
            val token = tokens[position++]
            if (token.toIntOrNull() == null) {
                Grammar.Token.Term(token)
            } else {
                Num(token)
            }
        }
    }
}

fun main() {
    val table = Ancc("E") {
        "E1"  p "E"  n "F" n "E'"
        "E'1" p "E'" t "+" n "F" n "E'"
        "E'2" p "E'"
        "F1"  p "F"  n "N" n "F'"
        "F'1" p "F'" t "*" n "N" n "F'"
        "F'2" p "F'"
        "N1"  p "N"  t "num"
        "N2"  p "N"  t "(" n "E" t ")"
    }.genTable()
//    val t = table.values.flatMap { it.keys }.toSortedSet()
//    println("\t" + t.joinToString("\t"))
//    table.map { (nt, ps) ->
//        nt + "\t" + t.fold("") { acc, s ->
//            acc + ps.getOrDefault(s, "") + "\t"
//        }
//    }.forEach(::println)
    val lexer = MLexer(
        "(", "1", "+", "1", ")", "*", "3"
    )
    lexer.next()
    val parser = Parser(
        "E", table, lexer
    )
    val node = parser.parse()
    println(eval(node))
}

fun eval(node: Node): Int {
    val nodes = node.nodes
    return when (node.id) {
        "E1" -> {
            var f = eval(nodes[0] as Node)
            var ep = nodes[1] as Node
            while (ep.id == "E'1") {
                f += eval(ep.nodes[1] as Node)
                ep = ep.nodes[2] as Node
            }
            f
        }
        "F1" -> {
            var f = eval(nodes[0] as Node)
            var ep = nodes[1] as Node
            while (ep.id == "F'1") {
                f *= eval(ep.nodes[1] as Node)
                ep = ep.nodes[2] as Node
            }
            f
        }
        "N1" -> {
            (nodes[0] as MLexer.Num).num.toInt()
        }
        "N2" -> {
            eval(nodes[1] as Node)
        }
        else -> throw Exception()
    }
}
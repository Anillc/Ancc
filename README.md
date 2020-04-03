# Ancc
Ancc is Not a Compiler Compiler

这是一个LL文法预测分析法表的生成器  
顺便实现了一下parser，可以写好了文法直接用  

用法:  
1. 编写文法.ancc文件  
2. 实现ALexer和AToken接口  
3. 用AnccReader获得产生式数组  
4. 用LLTableGenerater.gen生成表(start是文法的开始符号，EOF是文件的结尾标志。流结束后需要返回一个name为你指定的EOF的NonTerm)  
5. 这里的表建议序列化起来，然后ancc文件就可以不要了  
6. 将lexer、表、start、EOF传入AParser然后调用AParser.parse就可以生成树啦  
7. 接下来就是愉快的SDD转换生成AST了  

可以参考一下Main.java  

然后是.ancc的语法  

> ancc的语法很严格，如果不正确的话无法正确读取  
> （甚至不会报错（逃  
> 文法分两部分  
> 第一部分是定义终结符  
> `+,-,(,),id,num;`  
> 这个只是在Ancc里起标识作用，其实只要你实现的AToken里返回的Term是这些字符串就没问题。这里的定义不会影响到实际读到的文字  
> 然后第二部分就是文法的定义  
> `1:E:T E1`  
> 首先1是产生式的id，用于SDD转换时识别作用。你也不想要你转换的时候不知道是哪个产生式对吧XD  
> 后面的就不用我说了吧。  
> 需要注意的是ancc中空格要求很严格，冒号前后不要有空格，像T、E1之间也只能有一个空格，多了少了都不能解析  
> 具体如何可以参考一下test.ancc  

代码是在手机上写的，很不方便，不怎么规范，可能会有bug，希望能谅解  

就酱XD  )

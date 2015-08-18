---
2015.8.18 16:58<br/>
1、修复json解析的错误
2、mainactivity.xml布局增加两个textview，用于打印显示json解析的结果
---
2015.8.15 17:51<br/>
主要实现以下功能：
1、读取json文件；<br/>
2、解析jsonString，但有bug，通过字节流操作jsonString节点时，字符串部分丢失不完整；<br/>
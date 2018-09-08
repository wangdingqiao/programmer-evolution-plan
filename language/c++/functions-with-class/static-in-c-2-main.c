#include <stdio.h>
int main()
{
 Func1();   

 Func2();
 return 0;
}

/*
static-in-c-2-main.obj : error LNK2019: 无法解析的外部符号 _Func1，该符号在函数 _main 中被引用
static-in-c-2-func.exe : fatal error LNK1120: 1 个无法解析的外部命令
*/
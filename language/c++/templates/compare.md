
|调用目标函数                               |	函数重载	  					 |函数模板|
|------------------------------------------|-----------------------------|------------|
|不调用函数  							   |obj文件50KB,不包括任何目标函数 | obj文件54KB，包含两个重载版本函数|
|compare(1,3)							   |obj文件142KB,包括int版本目标函数 | obj文件146KB，包含两个重载版本函数|
|compare(1,3)和compare("abcdef", "ABCDEF")  |obj文件145KB,包含两个版本目标函数 | obj文件147KB，包含两个重载版本函数|


模板函数中obj文件生成了两个版本函数:

> H:\cpp-summary\templates>dumpbin template-function-1.obj /SYMBOLS > template.txt

```
547 00000000 SECTA3 notype ()    External     | _main
548 00000000 SECTAF notype ()    External     | ??$compare@H@@YAHABH0@Z (int __cdecl compare<int>(int const &,int const &))
549 00000000 SECTB8 notype ()    External     | ??$endl@DU?$char_traits@D@std@@@std@@YAAAV?$basic_ostream@DU?$char_traits@D@std@@@0@AAV10@@Z (class std::basic_ostream<char,struct std::char_traits<char> > & __cdecl std::endl<char,struct std::char_traits<char> >(class std::basic_ostream<char,struct std::char_traits<char> > &))
54A 00000000 SECTB0 notype ()    External     | ??$compare@V?$basic_string@DU?$char_traits@D@std@@V?$allocator@D@2@@std@@@@YAHABV?$basic_string@DU?$char_traits@D@std@@V?$allocator@D@2@@std@@0@Z (int __cdecl compare<class std::basic_string<char,struct std::char_traits<char>,class std::allocator<char> > >(class std::basic_string<char,struct std::char_traits<char>,class std::allocator<char> > const &,class std::basic_string<char,struct std::char_traits<char>,class std::allocator<char> > const &))
```

重载版本中obj文件生成了两个版本函数:

```
549 00000000 SECTA3 notype ()    External     | ?compare@@YAHABH0@Z (int __cdecl compare(int const &,int const &))
54A 00000030 SECTA3 notype ()    External     | ?compare@@YAHABV?$basic_string@DU?$char_traits@D@std@@V?$allocator@D@2@@std@@0@Z (int __cdecl compare(class std::basic_string<char,struct std::char_traits<char>,class std::allocator<char> > const &,class std::basic_string<char,struct std::char_traits<char>,class std::allocator<char> > const &))
```

```cpp
std::cout << compare(1,3) << std::endl;
std::string s1("abcdef"), s2("ABCDEF");
std::cout << compare(s1, s2) << std::endl;
std::cout << compare("abcdef", "ABCDEF") << std::endl;
std::cout << compare("abcdefgh", "ABCDEFGH") << std::endl;
```

生成4个版本
```
550 00000000 SECTB1 notype ()    External     | ??$compare@H@@YAHABH0@Z (int __cdecl compare<int>(int const &,int const &))
551 00000000 SECTBA notype ()    External     | ??$endl@DU?$char_traits@D@std@@@std@@YAAAV?$basic_ostream@DU?$char_traits@D@std@@@0@AAV10@@Z (class std::basic_ostream<char,struct std::char_traits<char> > & __cdecl std::endl<char,struct std::char_traits<char> >(class std::basic_ostream<char,struct std::char_traits<char> > &))
552 00000000 SECTB2 notype ()    External     | ??$compare@V?$basic_string@DU?$char_traits@D@std@@V?$allocator@D@2@@std@@@@YAHABV?$basic_string@DU?$char_traits@D@std@@V?$allocator@D@2@@std@@0@Z (int __cdecl compare<class std::basic_string<char,struct std::char_traits<char>,class std::allocator<char> > >(class std::basic_string<char,struct std::char_traits<char>,class std::allocator<char> > const &,class std::basic_string<char,struct std::char_traits<char>,class std::allocator<char> > const &))
553 00000000 SECTAF notype ()    External     | ??$compare@$$BY06$$CBD@@YAHAAY06$$CBD0@Z (int __cdecl compare<char const [7]>(char const (&)[7],char const (&)[7]))
554 00000000 SECTB0 notype ()    External     | ??$compare@$$BY08$$CBD@@YAHAAY08$$CBD0@Z (int __cdecl compare<char const [9]>(char const (&)[9],char const (&)[9]))
```
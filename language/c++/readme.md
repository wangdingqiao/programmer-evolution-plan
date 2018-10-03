# C++语言重难点的总结

 这里主要针对C++语言中的一些重点和难点做出了整理，目的在于厘清C++中的几条主线。
 这里并未包含所有C++语言的细节，因为一旦陷入细节，就无法关注重点。
 很多主题是先形成一个主线，然后在具体使用时再去深究的过程。
 例如模板部分，真正深入的话已经有很多本专业书籍来详细讲解，但是这种大部头书籍仅在必要的时才需要去阅读，
 毕竟计算机技术广泛，但我们时间、精力和能力有限。


- [指针](https://github.com/wangdingqiao/programmer-evolution-plan/tree/master/language/c%2B%2B/pointer)
- [类中的函数](https://github.com/wangdingqiao/programmer-evolution-plan/tree/master/language/c%2B%2B/functions-with-class)
- [虚函数](https://github.com/wangdingqiao/programmer-evolution-plan/tree/master/language/c%2B%2B/virtual)
- [面向对象编程](https://github.com/wangdingqiao/programmer-evolution-plan/tree/master/language/c%2B%2B/OOP)
- [模板](https://github.com/wangdingqiao/programmer-evolution-plan/tree/master/language/c%2B%2B/templates)



# 有价值的参考资料

## virtual部分

- [C++ Classes and Objects](https://www.geeksforgeeks.org/c-classes-and-objects/)
- [Why do we need virtual functions in C++?](https://stackoverflow.com/questions/2391679/why-do-we-need-virtual-functions-in-c)
- [Early binding and late binding](http://www.learncpp.com/cpp-tutorial/124-early-binding-and-late-binding/)
- [Virtual destructors, virtual assignment, and overriding virtualization](http://www.learncpp.com/cpp-tutorial/123-virtual-destructors-virtual-assignment-and-overriding-virtualization/)
- [The virtual table](http://www.learncpp.com/cpp-tutorial/125-the-virtual-table/)
- [In C++, what is a virtual base class?](https://stackoverflow.com/questions/21558/in-c-what-is-a-virtual-base-class)
- [C++ Virtual Function](https://www.programiz.com/cpp-programming/virtual-functions)
- [Virtual functions and polymorphism](http://www.learncpp.com/cpp-tutorial/122-virtual-functions/)
- [Inheritance — Proper Inheritance and Substitutability](https://isocpp.org/wiki/faq/proper-inheritance#array-derived-vs-base)
- [Learning C++: polymorphism and slicing](https://stackoverflow.com/questions/4403726/learning-c-polymorphism-and-slicing)
- [What is object slicing?](https://stackoverflow.com/questions/274626/what-is-object-slicing#274636)
- [Multiple Inheritance in C++](https://www.geeksforgeeks.org/multiple-inheritance-in-c/)
- [c++ 深入理解虚函数](https://www.cnblogs.com/jin521/p/5602190.html)
- [How does virtual inheritance solve the “diamond” (multiple inheritance) ambiguity?](https://stackoverflow.com/questions/2659116/how-does-virtual-inheritance-solve-the-diamond-multiple-inheritance-ambiguit)
- [Virtual inheritance](https://en.wikipedia.org/wiki/Virtual_inheritance)
- [C++ vtables - Part 1 - Basics](https://shaharmike.com/cpp/vtable-part1/)
- [Inheritance — Multiple and Virtual Inheritance](https://isocpp.org/wiki/faq/multiple-inheritance)

- [Calling virtual methods in constructor/destructor in C++](https://www.geeksforgeeks.org/calling-virtual-methods-in-constructordestructor-in-cpp/)

- [dynamic_cast conversion](https://en.cppreference.com/w/cpp/language/dynamic_cast)

## 继承

- [C++ Disambiguation: subobject and subclass object](https://stackoverflow.com/questions/18451683/c-disambiguation-subobject-and-subclass-object)
-[What is object slicing?](https://stackoverflow.com/questions/274626/what-is-object-slicing) 
- [Why would the conversion between derived* to base* fails with private inheritance?](https://stackoverflow.com/questions/3674876/why-would-the-conversion-between-derived-to-base-fails-with-private-inheritanc)


## 类成员函数

### 构造函数

- [Constructors](http://www.learncpp.com/cpp-tutorial/85-constructors/)
- [Constructor member initializer lists](http://www.learncpp.com/cpp-tutorial/8-5a-constructor-member-initializer-lists/)
- [Constructors and Destructors in C++](https://www.cprogramming.com/tutorial/constructor_destructor_ordering.html)
- [Member Initializer List in C++](https://stackoverflow.com/questions/1711990/what-is-this-weird-colon-member-syntax-in-the-constructor)
- [Constructors in C++](https://www.geeksforgeeks.org/constructors-c/)
- [C++ default constructor](https://stackoverflow.com/questions/4836596/c-default-constructor)
- [Does the default constructor initialize built-in types?](https://stackoverflow.com/questions/2417065/does-the-default-constructor-initialize-built-in-types)
- [What does the explicit keyword mean?](https://stackoverflow.com/questions/121162/what-does-the-explicit-keyword-mean)

### 初始化列表
-[Why should I prefer to use member initialization list?](https://stackoverflow.com/questions/926752/why-should-i-prefer-to-use-member-initialization-list)

-[When do we use Initializer List in C++?](https://www.geeksforgeeks.org/when-do-we-use-initializer-list-in-c/)

### 初始化顺序

- [Order of construction of derived classes](https://www.learncpp.com/cpp-tutorial/113-order-of-construction-of-derived-classes/)

- [Constructors and initialization of derived classes](https://www.learncpp.com/cpp-tutorial/114-constructors-and-initialization-of-derived-classes/)

### 拷贝构造函数和赋值操作符
- [Copy constructors, assignment operators, and exception safe assignment](http://www.cplusplus.com/articles/y8hv0pDG/)
- [Copy Constructor (Deep Copy) c++](https://stackoverflow.com/questions/28756445/copy-constructor-deep-copy-c)
- [C++ Creating a copy constructor for stack class](https://stackoverflow.com/questions/40579676/c-creating-a-copy-constructor-for-stack-class)
- [Exception-Safe Class Design, Part 1: Copy Assignment ](http://www.gotw.ca/gotw/059.htm)
- [What is The Rule of Three?](https://stackoverflow.com/questions/4172722/what-is-the-rule-of-three)
- [What is the copy-and-swap idiom?](https://stackoverflow.com/questions/3279543/what-is-the-copy-and-swap-idiom)
- [Copy constructor vs assignment operator in C++](https://www.geeksforgeeks.org/copy-constructor-vs-assignment-operator-in-c/)
- [Copy Constructor in C++](https://www.geeksforgeeks.org/copy-constructor-in-cpp/)
- [Copy constructor (C++)](https://en.wikipedia.org/wiki/Copy_constructor_(C%2B%2B))
- [Shallow vs. deep copying](http://www.learncpp.com/cpp-tutorial/915-shallow-vs-deep-copying/)
- [Overloading the assignment operator](http://www.learncpp.com/cpp-tutorial/9-14-overloading-the-assignment-operator/)
- [Copy-and-swap](https://en.wikibooks.org/wiki/More_C++_Idioms/Copy-and-swap)
- [std::swap](http://www.cplusplus.com/reference/algorithm/swap/)


### 析构函数

- [Destructors in C++](https://www.geeksforgeeks.org/destructors-c/)
- [Destructors](https://en.cppreference.com/w/cpp/language/destructor)
- [Order of Constructor/ Destructor Call in C++](https://www.geeksforgeeks.org/order-constructor-destructor-call-c/)
- [What is stack unwinding?](https://stackoverflow.com/questions/2331316/what-is-stack-unwinding)
- [Resource Acquisition is Initialisation (RAII) Explained](https://www.tomdalling.com/blog/software-design/resource-acquisition-is-initialisation-raii-explained/)

### const成员函数

- [Meaning of 'const' last in a function declaration of a class?](https://stackoverflow.com/questions/751681/meaning-of-const-last-in-a-function-declaration-of-a-class)
- [What is meant with “const” at end of function declaration?](https://stackoverflow.com/questions/3141087/what-is-meant-with-const-at-end-of-function-declaration)
- [`What is the difference between const int*, const int * const, and int const *?`](https://stackoverflow.com/questions/1143262/what-is-the-difference-between-const-int-const-int-const-and-int-const)

### this pointer
- [‘this’ pointer in C++](https://www.geeksforgeeks.org/this-pointer-in-c/)

### static 

- [static functions](http://os.camden.rutgers.edu/c_resources/c_manual/C/SYNTAX/static.htm)
- [What does “static” mean in C?](https://stackoverflow.com/questions/572547/what-does-static-mean-in-c)
- [Static member functions](http://www.learncpp.com/cpp-tutorial/812-static-member-functions/)


### friend function
- [Friend class and function in C++](https://www.geeksforgeeks.org/friend-class-function-cpp/)
- [Friends ](https://isocpp.org/wiki/faq/friends#friends-and-encap)
- [friend declaration](https://en.cppreference.com/w/cpp/language/friend)


### inline function

- [What is C++ inline functions](http://www.cplusplus.com/articles/2LywvCM9/)
- [Benefits of inline functions in C++?](https://stackoverflow.com/questions/145838/benefits-of-inline-functions-in-c)


## function overloadding override hiding
- [Operator Overloading in C++](https://www.geeksforgeeks.org/operator-overloading-c/)
- [What are the basic rules and idioms for operator overloading?](https://stackoverflow.com/questions/4421706/what-are-the-basic-rules-and-idioms-for-operator-overloading)
- [Override and overload in C++](https://stackoverflow.com/questions/429125/override-and-overload-in-c)

## name hidding

- [Reason for C++ member function hiding](https://stackoverflow.com/questions/11923890/reason-for-c-member-function-hiding)
- [Name hiding in c++](https://stackoverflow.com/questions/26367216/name-hiding-in-c)
- [C++ inheritance and name hiding](https://stackoverflow.com/questions/32946364/c-inheritance-and-name-hiding)

- [Using-declaration](https://en.cppreference.com/w/cpp/language/using_declaration)

## pointer

- [Pointers](http://www.cplusplus.com/doc/tutorial/pointers/)
- [Swapping pointers in C (char, int)](https://stackoverflow.com/questions/8403447/swapping-pointers-in-c-char-int)
- [C++ Swapping Pointers](https://stackoverflow.com/questions/15672805/c-swapping-pointers)
- [C++ Pointers to Structure](https://www.programiz.com/cpp-programming/structure-pointer)
- [C++ Pointers and Structures](https://codescracker.com/cpp/cpp-pointers-structures.htm)
- [Implementing a simple smart pointer in C++](https://www.codeproject.com/articles/15351/implementing-a-simple-smart-pointer-in-c)
- [dangling pointer and memory leak](https://stackoverflow.com/questions/13132798/difference-between-dangling-pointer-and-memory-leak)
- [C++11 Smart Pointers](https://www.codeproject.com/Articles/541067/Cplusplus-Smart-Pointers)
- [Circular dependency issues](http://www.learncpp.com/cpp-tutorial/15-7-circular-dependency-issues-with-stdshared_ptr-and-stdweak_ptr/)
- [Distinguish between pointers and references in C++](http://www.cplusplus.com/articles/ENywvCM9/)
- [What are the differences between a pointer variable and a reference variable in C++?](https://stackoverflow.com/questions/57483/what-are-the-differences-between-a-pointer-variable-and-a-reference-variable-in)

## 内存分配

- [What is the difference between new/delete and malloc/free?](https://stackoverflow.com/questions/240212/what-is-the-difference-between-new-delete-and-malloc-free)
- [What is the difference between C++ new and C malloc](https://www.cppbuzz.com/What-is-difference-between-new-and-malloc)


## 访问修饰符

- [C++ Access Modifiers](http://www.tutorialdost.com/Cpp-Programming-Tutorial/48-CPP-Public-Private-Protected-Access-Modifiers.aspx)
- [Access Modifiers in C++](https://www.geeksforgeeks.org/access-modifiers-in-c/)
- [Public, Protected and Private Inheritance in C++ Programming](https://www.programiz.com/cpp-programming/public-protected-private-inheritance)

## sizeof Operator
- [sizeof Operator](https://msdn.microsoft.com/en-us/library/4s7x1k91.aspx)

## templates

- [An Idiot's Guide to C++ Templates - Part 1](https://www.codeproject.com/Articles/257589/An-Idiots-Guide-to-Cplusplus-Templates-Part)
- [An Idiot's Guide to C++ Templates - Part 2](https://www.codeproject.com/articles/268849/an-idiots-guide-to-cplusplus-templates-part)
- [C++ Tutorial - Templates - 2018](http://www.bogotobogo.com/cplusplus/templates.php)
- [C++ Templates Tutorial](http://users.cis.fiu.edu/~weiss/Deltoid/vcstl/templates)
- [When to use template vs inheritance](https://stackoverflow.com/questions/7264402/when-to-use-template-vs-inheritance)


## STL(Standard Template Library)

- [The Standard Template Library](https://www.learncpp.com/cpp-tutorial/16-1-the-standard-template-library-stl/)
- [A modest STL tutorial](http://cs.brown.edu/~jak/proglang/cpp/stltut/tut.html)
- [An Introduction to the Standard Template Library (STL)](https://cal-linux.com/tutorials/STL.html)
- [The Standard Template Library Tutorial](http://www.cmapx.polytechnique.fr/~benaych/stl-tutorial-Weidl.pdf)
- [Why std::set is an associative container](https://stackoverflow.com/questions/25071335/why-stdset-is-an-associative-container)
- [C++ Standard Template Library Part III - Algorithms](https://www.quantstart.com/articles/C-Standard-Template-Library-Part-III-Algorithms#)
- [Algorithms library](https://en.cppreference.com/w/cpp/algorithm)
- [Functors: Function Objects in C++](https://www.cprogramming.com/tutorial/functors-function-objects-in-c++.html)
- [What are C++ functors and their uses?](https://stackoverflow.com/questions/356950/what-are-c-functors-and-their-uses)

## Allocator
- [C++ Standard Allocator, An Introduction and Implementation](https://www.codeproject.com/Articles/4795/C-Standard-Allocator-An-Introduction-and-Implement)
- [The Standard Librarian: What Are Allocators Good For?](http://www.drdobbs.com/the-standard-librarian-what-are-allocato/184403759)
- [Allocators (STL)](https://www.codeguru.com/cpp/cpp/cpp_mfc/stl/article.php/c4079/Allocators-STL.htm)


## 其他问题

- [What is “Argument-Dependent Lookup” (aka ADL, or “Koenig Lookup”)?](https://stackoverflow.com/questions/8111677/what-is-argument-dependent-lookup-aka-adl-or-koenig-lookup)
- [C++Patterns](https://cpppatterns.com/)
##写在前面
    
python语言以其语法简洁([代码量比java开发的程序少3-5倍 比c++少5-10倍](https://www.python.org/doc/essays/comparisons/) )、快速原型开发(prototyping)以及庞大第三方库的支持，广泛应用于多个领域，成为了一门很流行的语言。我们这里使用Python，可以抛开其他像C++/java这些语言本身的复杂性，把精力集中在数据结构和算法的思考上。同时熟练掌握Python后，也可以将Python应用在其他专门领域的算法学习上，一举多得。
 
 本节重点概要如下图所示:
 
![入门1](http://img.blog.csdn.net/20170827161556885?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 
##1.语言特性
了解语言本身的特性，能够帮助我们从大方向上把握这门语言。

###1) 解释性语言  
解释语言的速度一般都要比编译型语言慢，语言层面上的比较很难找到权威指导，但你可以根据需要对你的特定实现进行基准测试(benchmarking)。

###2)内存自我管理
Python解释器实现了内存的自我管理，因此用户一般不用在代码中书写内存分配和回收的细节。当然，我们实现算法时仍然需要考虑程序内存开销，对于特殊要求的程序，甚至可能需要手动释放内存。

###3)动态类型
语言中的类型系统一般是静态的(static-typing)，或者动态的(dynamic-typing)。静态类型语言中，变量在使用前需要显式定义类型，例如c++/java语言；而动态类型语言中，可以在使用的时候直接使用即可，无需提前定义。静态类型语言中类型系统会在编译时执行检查，这是在编译时发生的；而动态语言中的类型检查是在运行时发生的。与此相关的概念还有一对是[强类型(Strong Typing )和弱类型( Weak Typing)](https://www.sitepoint.com/typing-versus-dynamic-typing/)。
```cpp
/* C code */ 
static int num, sum; // 静态类型的显示声明 
num = 5; // 声明后使用
sum = 10; 
```
Python中只需要在使用时声明即可:
```python
#Python code
num = 10 // 直接使用 无需提前声明
```
###4) 支持多种编程泛型
Python支持面向过程、函数式、面向对象多种编程模式( multi-paradigm programming language)。


###5)多操作系统支持
Python解释器支持包括 Windows, Linux/UNIX, Mac OS X, 等主流平台，[也支持一些其他类型系统](https://www.python.org/download/other/)。
	
### 6)与其他语言的交互性
python可以借助Cython来编写C语言拓展，Google的Grumpy支持将python翻译为Go语言程序。更多的工具可以参考[wiki](https://en.wikipedia.org/wiki/Python_%28programming_language%29#Cross-compilers_to_other_languages)。

## 2.变量和数据类型

## 1)名字和绑定

在C/C++等静态类型语言之中，我们定义一个变量
```cpp
int a=1；
```
会实际分配一个空间存储整型值1，然后变量a就像一个装着值为1的盒子一样：
(来自:[Understanding Python variables and Memory Management](http://foobarnbaz.com/2012/07/08/understanding-python-variables/))

![a1](http://img.blog.csdn.net/20170827143937314?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

如果我们改变变量值:
```cpp
a = 2;
```

这是就修改了内存中的值为2，变为:

![a2](http://img.blog.csdn.net/20170827144232716?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

将a赋值到b变量:
```cpp
int b = a;
```
则内存中分配一个新的空间，并初始化为值2：

![b2](http://img.blog.csdn.net/20170827144411144?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)![a2](http://img.blog.csdn.net/20170827144232716?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

而在python中，我们定义:
```python
a=1
```
时，将会创建一个整型对象，它的值为1, a作为一个名字(name)绑定到这个对象(binding to object)，如下图所示:

![a1 bind](http://img.blog.csdn.net/20170827144734216?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

执行语句:
```python
a = 2
```
后，a将会解除与之前对象的绑定，重新绑定到新对象:

![a2 bind](http://img.blog.csdn.net/20170827145024145?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

执行语句:

```python
b = a
```
之后，a和b两个名字都绑定到同一个对象：

![a b binding](http://img.blog.csdn.net/20170827145102838?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

在传统C/C++语言中，我们习惯用变量来表示这个指向内存单元的符号；而在python中,使用名字和绑定这一对术语(name and binding)更便于理解变量的真正含义。在python中包括基础类型像int,string这些类型在内，所有东西都是一个对象(Everything is an object)。

```
>>> foo = 10
>>> print(foo.__add__)
<method-wrapper '__add__' of int object at 0x018C7314>
```
我们看到上面的整型值10，作为一个int类型对象，其中包含了一个方法__add___用于加法运算。


### 2) 内置数据类型

内置数据类型总结为下表所示:

![data types](http://img.blog.csdn.net/20170827153109384?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

###3)对象的可修改性
**可修改**(mmutable )这个概念，表达的意思是允许在不新建对象的前提下改变对象的内容，也就是保持绑定对象的id不变。这种改变叫做**原地改变**(in place)。

对于不可修改对象，例如str类型，一旦创建后就不不能原地被修改，要获取新的字符串只能创建一个新的str对象。
```
>>> s = "hello, world"
>>> s[0]="H"
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: 'str' object does not support item assignment
```
但是允许创建一个新对象来获取改变后的字符串:

```
>>> s="hello,world"
>>> id(s)
33300864
>>> s=s.capitalize()
>>> id(s)
33300800
>>> s
'Hello,world'
```


例如int类型，当我们修改它的值时，将会创建一个新的对象:
```
>>> m = 1
>>> n = m
>>> id(m)
26178432
>>> id(n)
26178432
>>> m += 1
>>> id(m)
26178420
>>> id(n)
26178432
>>>
```
上面用到的**内置函数id**用于获取对象的唯一标志，我们看到当执行m+=1后，m指向了一个新的对象，而n仍然指向之前的那个对象。
注意，我们这里指的不能原地改变变量值，而不是说不能改变变量m,n的值。在改变变量m值的时候，我们实际上新建了一个不同的对象。

**需要注意的是**，虽然不可修改对象不能改变自身指向的对象，但是它的元素如果是可变的，则依然可以改变这个对象的内容(the "value" of an immutable object can't change, but it's constituent objects can)，例如:

```
>>> a= [1,2]
>>> b = ['a','b']
>>> c = (a,b)
>>> c
([1, 2], ['a', 'b'])
>>> c[0] = [1,2,3]
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
TypeError: 'tuple' object does not support item assignment
>>> a.append(3)
>>> c
([1, 2, 3], ['a', 'b'])
>>> c[0].append(4)
>>> c
([1, 2, 3, 4], ['a', 'b'])
```

我们直接改变元素的第一个元素，不可行；但是我们通过第一个元素自身的方法去改变则变得可行。

可修改对象，例如list类型，我们改变它的内容时，它不会创建新的对象:
```
>>> m = [1,2]
>>> n = m
>>> id(m)
33238872
>>> id(n)
33238872
>>> m.append(3)
>>> m
[1, 2, 3]
>>> id(m)
33238872
>>> id(n)
33238872
>>> n
[1, 2, 3]
```



## 参考资料
- [1]:[Guido van Rossum 1997的python与其他语言的比较](https://www.python.org/doc/essays/comparisons/)

- [2]:[Understanding Python variables and Memory Management](http://foobarnbaz.com/2012/07/08/understanding-python-variables/)
- [3][Python Programming/Data Types](https://en.wikibooks.org/wiki/Python_Programming/Data_Types)

- [4][Drastically Improve Your Python: Understanding Python's Execution Model](https://jeffknupp.com/blog/2013/02/14/drastically-improve-your-python-understanding-pythons-execution-model/)
- [5][Built-in Functions](https://docs.python.org/2/library/functions.html)

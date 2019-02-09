## 写在前面
 紧接着上一节python入门1，本节还是继续学习Python的必备知识。具体的知识结构图如下所示：
 
![入门2](http://img.blog.csdn.net/20170903161336321?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


## 函数和作用域

###1)函数定义
python中函数定义由关键字def开始，例如定义一个产生斐波拉契数列的函数如下：

```python
def fib(n):
	"""Print a Fibonacci series up to n."""
	a, b = 0, 1
	while a < n:
	print a,
	a, b = b, a+b
```
需要注意的是，python不使用括号“{”和"}"识别函数体，但是函数体的第一行开始必须缩进。这里第一行用'"""'书写的字符串，被称为**docstring**，是对函数的注释，一些文档工具利用它可以产生帮助文档。

##2)函数传入参数

在上面的函数例子中，我们传入了一个参数n，用来表示计算的斐波拉契数列最大值的上限。在python中参数可以分为，必须参数(**required argument**)和可选参数(**optional arguments**)。必须参数是调用函数时，必须传入的参数；而可选参数，由定义时指定默认参数，如果用户没有提供则使用默认值作为缺省的参数。

参数在传递的时候，一种方式是按照位置顺序传递，这种称之为位置参数(**positional arguments**)，另一种方式是使用关键字和值的key-value形式传递的关键字参数(**keyword arguments**)。位置参数传递时，要求要求函数定义顺序传递；而关键字参数则对传递顺序没有限制，因为使用了关键字来表明了参数意图。在调用一个函数时，支持两种方式的混合使用，但是关键字参数一定放在位置参数后面，关键字参数后面不能再跟着位置参数了。

例如下面定义的函数(来自[python doc](https://docs.python.org/2/tutorial/controlflow.html#keyword-arguments)):

```python
def parrot(voltage, state='a stiff', action='voom', type='Norwegian Blue'):
	    print "-- This parrot wouldn't", action,
	    print "if you put", voltage, "volts through it."
	    print "-- Lovely plumage, the", type
	    print "-- It's", state, "!"
```
参数voltage是一个必须参数，而state后面的三个参数都提供了默认值是可选参数。可以有多种方式调用这个函数:

```python
parrot(1000)         # 1 positional argument
parrot(voltage=1000) # 1 keyword argument
parrot(voltage=1000000, action='VOOOOOM') # 2 keyword arguments
parrot(action='VOOOOOM', voltage=1000000) # 2 keyword arguments
parrot('a million', 'bereft of life', 'jump')# 3 positional arguments
parrot('a thousand', state='pushing up the daisies') # 1 positional, 1 keyword
```

另外，Python函数也支持可变长参数的传递，分别使用单个星号和两个星号表示:

```python
def cheeseshop(kind, *arguments, **keywords):
	    print "-- Do you have any", kind, "?"
	    print "-- I'm sorry, we're all out of", kind
	    for arg in arguments:
	        print arg
	    print "-" * 40
	    keys = sorted(keywords.keys())
	    for kw in keys:
	        print kw, ":", keywords[kw]
```

这里*argument将接受所有kind之后的位置参数，并组合成为一个tuple；而**keywords则接受所有关键字参数，并组合成为一个dict。

这样调用函数:

```python
cheeseshop("Limburger", "It's very runny, sir.",
           "It's really very, VERY runny, sir.",
           shopkeeper='Michael Palin',
           client="John Cleese",
           sketch="Cheese Shop Sketch")
```
则输出内容为:

```
-- Do you have any Limburger ?
-- I'm sorry, we're all out of Limburger
It's very runny, sir.
It's really very, VERY runny, sir.
----------------------------------------
client : John Cleese
shopkeeper : Michael Palin
sketch : Cheese Shop Sketch
```
如果一个函数需要多个位置参数，而当前变量恰好为tuple或者dict,则需要将变量解压缩后传入调用函数，例如:

```
>>> range(3, 6)             # range函数至少需要两个位置参数调用
[3, 4, 5]
>>> args = [3, 6]
>>> range(*args)            # 使用*将tuple解压为range函数需要的两个位置参数
[3, 4, 5]
```
 dict也可以使用两个星号进行类似解压缩操作。

##3)函数返回值

python函数的返回值，默认为None，我们不必显式地书写```return None```这行代码。在python中我们既可以返回单个值，也可以返回多个值。返回多个值时，可以使用的形式包括:

- tuple
- list
- dict
- class object
- named tuple

等多种形式，如果我们直接书写为:

```python
   def myfunc():
	   return a,b
```

则将以tuple的形式返回参数，这里使用的逗号将会构造一个tuple；如果我们使用[a,b]将会返回list，如果使用{"v1":a,"v2":b}将会以dict形式返回。还有一种比较优雅的方式，是使用**[named tuple](https://docs.python.org/2/library/collections.html#collections.namedtuple)**来返回例如:

```python
>>> import collections
>>> def getPos():
...     pos = collections.namedtuple('Point',['x','y','z'])
...     return pos(1,2,3)
...
>>> a = getPos()
>>> a.x
1
>>> a
Point(x=1, y=2, z=3)
```
这里我们对返回值进行了命名，使得返回值的意义十分清楚了。

##4)函数参数的传递

和C/C++中参数传递不一样，python既不是传值方式(by-value)， 也不是传地址方式(by-adress)，而是传递对象的引用(**by-object-reference**)。例如定义一个函数:

```python
def foo(bar):
    bar.append(42)
    print(bar)
```
我们这样调用它:

```
answer_list = []
foo(answer_list)
print(answer_list)
# >> [42]
```

那么这里实际发生的过程是:在foo的**作用域(scoping)(下面详述)**里定义了一个名字bar,在调用的时候bar指向了一个可变对象(mutable object)answer_list，因此在上面的函数调用结束后，影响到了调用函数的作用域里的answer_list对象，实际输出:```42```。

如果参数指向一个不可变对象，那么在函数内部将无法改变调用这个函数的作用域中的原对象，例如:
```python
def foo(bar):
    bar = 'new value'
    print (bar)
    # >> 'new value'

answer_list = 'old value'
foo(answer_list)
print(answer_list)
# >> 'old value'
```

##5)函数的作用域

Python函数调用的时候，在函数作用域范围内引入了一个新的符号表( a new symbol table)。在函数内部的赋值语句，将会在这个局部符号表里添加名字(如果没有使用global时)。

在解引用一个名字的时候，首先在函数的局部符号表中查找，如果找不到则查找调用该函数的(enclosing functions)作用域，这种作用域也称为父作用域。如果在父作用域查找失败，则转到全局符号表(global symbol table)，如果再失败则查找内置符号表(global symbol table)。如果符号查找失败，则会抛出无法解析符号的异常。符号查找的过程是一个由本地逐级向上的过程。

python中有两个特殊的作用域关键字，**global和nonlocal**。在符号查找的时候使用global指明某个符号后，这个符号将解析为全局作用域中符号，指向的是全局作用域中的对象，例如:

```python
def method():
    # 显式地表明 使用全局作用域中的符号value
    global value
    value = 100

value = 0
method()
print(value)
# >> 100
```
如果函数内部没有使用global语句，那么将会在函数的局部作用域里定义一个符号value，函数调用完后，不会影响到全局作用域中的value。

nonlocal[用于表明符号优先在父作用域中解析，而不是本地(local)或者全局中(global)](https://www.dotnetperls.com/nonlocal-python)。例如:

```python
def method():

    def method2():
        # 在嵌套的方法中 优先解析为父作用域的符号
        # 这里value即是 method函数作用域中的value
        nonlocal value
        value = 100
    value = 10
    method2()
    print(value) # >> 100
# 调用方法
method()
```

对于符号的解析，这里从[SO](https://stackoverflow.com/questions/1261875/python-nonlocal-statement)选取了一个综合例子如下:

```python
# 例子1 不使用global和nonlocal
x = 0
def outer():
    x = 1
    def inner():
        x = 2
        print("inner:", x)

    inner()
    print("outer:", x)

outer()
print("global:", x)

# inner: 2
# outer: 1
# global: 0
```
例子二使用nonlocal，如下:

```python
#例子2 使用nonlocal
x = 0
def outer():
    x = 1
    def inner():
        nonlocal x
        x = 2
        print("inner:", x)

    inner()
    print("outer:", x)

outer()
print("global:", x)

# inner: 2
# outer: 2
# global: 0
```
例子3使用global，如下:

```python
#例子3  使用global
x = 0
def outer():
    x = 1
    def inner():
        global x
        x = 2
        print("inner:", x)

    inner()
    print("outer:", x)

outer()
print("global:", x)

# inner: 2
# outer: 1
# global: 2
```

##6)lambda和closure
lambda表达式，是一种匿名的短函数( anonymous functions )。python对这个函数的限制是，不能使用语句，只能使用表达式，表达式返回的值，即是函数返回值。例如:

```python
>>> foo = lambda x: x*x
>>> print(foo(10))
100
```
lambda表达式，一般在需要匿名的、短小的函数地方应用，例如在排序中应用得很多。例如[下面的lamba表达式，对学生成绩按照年龄排序](https://wiki.python.org/moin/HowTo/Sorting):

```
>>> student_tuples = [
        ('john', 'A', 15),
        ('jane', 'B', 12),
        ('dave', 'B', 10),
]
>>> sorted(student_tuples, key=lambda student: student[2])   # 按年龄排序
[('dave', 'B', 10), ('jane', 'B', 12), ('john', 'A', 15)]
```
这里key函数是一个用于返回排序元素的函数，对于元组中，我们这里返回第三个元素，也就是返回年龄进行排序。

closure这个概念，表达是[变量附加到代码之上的行为(data gets attached to the code )](https://www.programiz.com/python-programming/closure)。例如我们定义一个简单的嵌套函数如下:

```python
def print_msg(msg):
# 这是外围函数

    def printer():
# 这是内层嵌套的函数
        print(msg)

    printer()

print_msg("Hello") # >> Hello
```

在这里内层函数可以访问外围函数的msg变量，如果将内层函数改为返回函数呢？

```python
def print_msg(msg):
    def printer():
        print(msg)

    return printer  # 返回内层函数

another = print_msg("Hello")
another() # >> Hello
```
这里我们的print_msg函数返回的是一个函数对象，将这个函数对象绑定到another后，我们可以使用another来执行这个函数。需要注意的是，执行another的时候，尽管已经退出了print_msg的作用域，但是每次总是输出"Hello"，因为这个变量已经附加到了这个函数之上。这种函数在python中称之为闭包(closure)，其他语言也有类似概念。

闭包有它的特殊用途，例如为了避免全局变量，或者一定程度上的数据隐藏。下面提供了一个倍数的闭包实现，如下:

```python
def make_multiplier_of(n):
    def multiplier(x):
        return x * n
    return multiplier

# Multiplier of 3
times3 = make_multiplier_of(3)

# Multiplier of 5
times5 = make_multiplier_of(5)

# Output: 27
print(times3(9))

# Output: 15
print(times5(3))

# Output: 30
print(times5(times3(2)))
```
lambda和closure对于初学者来说，熟悉即可。

## 参考资料

- [More on Defining Functions](https://docs.python.org/2/tutorial/controlflow.html#more-on-defining-functions)

- [Is Python call-by-value or call-by-reference? Neither.](https://jeffknupp.com/blog/2012/11/13/is-python-callbyvalue-or-callbyreference-neither/)

- [Sorting Mini-HOW TO](https://wiki.python.org/moin/HowTo/Sorting)

- [Anonymous function](https://en.wikipedia.org/wiki/Anonymous_function#Python)

- [Python Closures](https://www.programiz.com/python-programming/closure)





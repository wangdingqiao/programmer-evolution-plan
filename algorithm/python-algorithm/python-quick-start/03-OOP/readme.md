## 写在前面
对于简单的任务，我们可以利用一些函数，按照任务处理的流程书写代码来完成需求，这种方式称之为过程式编程(**procedural programming**)；但是对于复杂的系统，如何有条理的将每个模块的代码内聚起来，如何清晰和简洁地表达各个模块之间的交互，就需要一种新的指导思想，面向对象编程(**object-oriented programming**)。OOP强调的就是为独立模块构造对象，对象之间通过消息通信来完成复杂的功能。OOP主题是复杂的，本节只对有限主题进行学习，知识结构如下图所示：

![入门3](http://img.blog.csdn.net/20170910185651190?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



## 面向对象编程(OOP)

##1）类的定义
在Python每个变量实际上都是指向对象的一个引用，这在前面已经说明过了。我们使用class关键字定义类，就像使用def关键字定义函数一样。在Python中，支持[两种定义类的写法](https://stackoverflow.com/questions/4015417/python-class-inherits-object):

```python
#python2.x
class MyClass: # old-style 
class MyClass(object): # new-style class
#python3.x
class MyClass(object): # new-style class
class MyClass: # new-style class (implicitly inherits from object)
```
上面的两种定义方式是受到历史原因引起的，在此我们不去深究。一种合理的方式是，在python2.x中总是显式使用:
```python
class MyClass(object):
	pass
```
这种方式；在python3.x中如果为了兼容2.x代码则也是用这种方式，否则没有区别。

##2)类的成员变量和方法
OOP与面向过程编程一个很大的区别在于，对象可以附加属性(包含哪些数据)和方法(支持哪些操作，也是一种函数)，通过将数据和函数内聚到对象身上，我们使对象的概念更加清晰，与系统其它部分的交互更加明确。例如下面定义一个圆形类:

```python
class Circle():
    pi = 3.141592
    def __init__(self, radius=1): #初始化函数 类似C++构造函数
        self.radius = radius
    def area(self):    # 求面积操作
        return self.radius * self.radius * Circle.pi

    def setRadius(self, radius):
        self.radius = radius

    def getRadius(self):
        return self.radius

# 创建一个对象
c = Circle()

c.setRadius(5)
print(c.getRadius())
print(c.area())
```
在上面的例子中，我们定义了一个简单的圆形类，这个圆形类包含一个属性即半径，用radius存储，这称之为类的成员变量(**member variables**)；同时包含3个方法分别用来计算圆的面积，设置和获取圆形的半径，这些称之为类的成员方法(**member methods**)。定义一个类之后，我们一般无法直接操作类，而是实例化类的一个具体对象(**instance object**)来操作，就好比你说开车，应该是开的某一辆具体车型的汽车。

在上面的例子中，我们也看到了一个self关键字，这个关键字用来表示对象本身的引用，类似于c++之中的this指针。当我们调用方法:

```python
print(c.area())   # 通过对象调用成员方法
```
的时候，实际上对象c将会作为参数传入类成员函数area，此时self就绑定到了这个具体对象，那么函数实际操作的数据就是这个对象c的数据了。实际上也可以这样调用函数:

```python
print(Circle.area(c))  # 通过显式传入对象 调用类的方法
```
这里我们实际上将c显式的传入了，而不是由解释器替我们传入。如果这样调用：

```python
print(Circle.area())  # TypeError 缺少实例对象
```
将会产生:
```
TypeError: unbound method area() must be called with Circle instance as first argument (got nothing instead)
```
的错误，原因在于，没能正确传入一个实例变量作为第一个参数调用函数。

在Python中主要包括[三种类型的成员变量和函数](https://softwareengineering.stackexchange.com/questions/306092/what-are-class-methods-and-instance-methods-in-python)，他们之间都有区别，列出如下：

### 2.1)实例级别的成员 (**instance -level members**)
实例级别的成员就是属于每个对象自身的数据和函数，例如上例中的radius和另外三个成员函数。**实例级别的成员变量，一般在__init__函数中进行初始化。**例如:

```python
class Foo(object):
    def hello(self):   # 注意这里需要传入实例对象给self
        print("hello from %s" % self.__class__.__name__)
```
调用方式:

```python
obj= Foo()
obj.hello() # 方式一
#>>  "hello from Foo"
Foo.hello(obj)  # 方式二
#>>  "hello from Foo"
```

###2.2)类级别的成员(**class-level members**)
  类级别的成员，不属于某个具体的对象，而是由类来保持的数据或者函数。例如某个类需要保持对象创建数量的计数，这个计数就定义为类变量。上面例子中的**pi**就是类成员变量，类成员变量定义在__init__函数之外。

```python
class Foo(object):
    @classmethod
    def hello(cls):  # 注意这里传入的为类对象 而不是某个实例对象
        print("hello from %s" % cls.__name__)
# 调用方式
Foo.hello()    # 对于类成员函数 使用类名字调用更清晰
#>>  "hello from Foo"
Foo().hello()        
#>> "hello from Foo"
```  
  注意上面代码中使用了"**@classmethod**"这种标记，在Python中称之为**Decorators**，感兴趣地[可以了解](http://pythoncentral.io/python-decorators-overview/)。
### 2.3)静态成员(**static members**)
静态成员是一种类里面为了某些操作的方便而包含在类里面的函数，这些函数在使用时不需要任何类或者实例的信息，实际上主要是为了便于管理，将它包含在类定义里面，实际应用得比较少。

```python
class Foo(object):
    @staticmethod
    def hello():  # 不需要传入实例或者类对象作为参数
        print("hello from FOO static")
 Foo.hello()
 #>> hello from FOO static
```

在类的成员函数中，有一类特殊函数，这类函数以双下划线开始和结尾，用来表示特定行为，例如__str__，函数用来将对象转换为字符串，这个字符串将在print等函数中用来输出对象的表示。
```python

class Book(object):

    def __init__(self, title, author, pages):

        print("A book is created")

        self.title = title
        self.author = author
        self.pages = pages

    def __str__(self):

        return "Title:{0} , author:{1}, pages:{2} ".format(
            self.title, self.author, self.pages)

    def __len__(self):

        return self.pages

    def __del__(self):

        print("A book is destroyed")

book = Book("Inside Steve's Brain", "Leander Kahney", 304)

print(book)
print(len(book))
del book

#>> A book is created
#>>Title:Inside Steve's Brain , author:Leander Kahney,pages:304
#>>304
#>> A book is destroyed
```
这种机制类似于C++的**操作符重载**，Python中可以重载的函数还有__add__() ,__sub__() 等。

## 3)面向对象三要素
面向对象编程包含三大要素，分别是封装(encapsulation )、继承(inheritance)、多态(Polymorphism)。下面从这三个角度看下Python如何处理的，因为面向对象编程本身是个复杂主题，这里只提供一个基本思路，详细可以参考其他资料。

### 3.1 封装性
封装涉及的任务是如何设计类的接口供用户使用，重点是类的访问控制(**access-control**)，避免用户有意或者无意破坏数据。需要注意的是，**Python设计上不支持private变量和方法**，通用的做法是用两个连续下划线表明这个成员应当被视为私有，不应该在类外使用。下面这个例子来自[SO How to do encapsulation in Python?](https://stackoverflow.com/questions/26216563/how-to-do-encapsulation-in-python)

```python
class C(object):
    def __init__(self):
        self.a = 123    # OK to access directly
        self._a = 123   # should be considered private
        self.__a = 123  # considered private, name mangled
>>> c = C()
>>> c.a
123
>>> c._a
123
>>> c.__a
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
AttributeError: 'C' object has no attribute '__a'
>>> c._C__a
123
```
在上面例子中，__a 方法被重写了(**name mangling** )为 _C__a，但实际上也能访问，这只是Python的一种提示方式，Python设计上不支持private。

##3.2 继承性

继承是一个类对另一个类共同行为的复用机制，被继承的类称之为父类或者基类(**base classes**)，从父类继承而来的类称之为子类或者派生类(**derived classes**)。子类不仅具有父类的成员变量和函数，同时也能修改父类的成员函数(overriding)和添加新的成员函数。

定义一个普通的银行账户，再定义一个最低额度的银行账户，如下：
```python
class BankAccount(object):
    def __init__(self):
        self.balance = 0

    def withdraw(self, amount):
        self.balance -= amount
        return self.balance

    def deposit(self, amount):
        self.balance += amount
        return self.balance


class MinimumBalanceAccount(BankAccount):
    def __init__(self, minimum_balance):
        BankAccount.__init__(self)    # call super class init method
        self.minimum_balance = minimum_balance

    def withdraw(self, amount):   # overriding
        if self.balance - amount < self.minimum_balance:
            print 'Sorry, minimum balance must be maintained.'
        else:
            return BankAccount.withdraw(self, amount)

a = BankAccount()
b = MinimumBalanceAccount(100)
a.deposit(100)
b.deposit(100)
print(a.withdraw(10)) # >> 90
print(b.withdraw(10)) # >> Sorry, minimum balance must be maintained.None
```
在上面的例子中，MinimumBalanceAccount的定义中，括号里书写了
父类类名字BankAccount，从父类继承了balance 成员和其他函数，同时子类MinimumBalanceAccount添加了一个最低额度的minimum_balance成员，同时重写了withdraw方法。

需要注意的是子类调用父类的方法有两种:
```python
super(SubClass, instance).method(args)
SuperClass.method(instance, args)
```
关于两种方式的好坏，可以参考[Python super method and calling alternatives](https://stackoverflow.com/questions/5033903/python-super-method-and-calling-alternatives)。

上面的例子中，我们使用:

```python
BankAccount.withdraw(self, amount)
```

可以替换为:
```python
super(MinimumBalanceAccount,self).withdraw(amount)
```
##3.3 多态性
多态性是指利用父类的引用类型可以指向父类和子类任意对象，在运行时根据实际指向的对象类型来动态决定如何操作的行为。例如：

```python
>>> a = "alfa"
>>> b = (1, 2, 3, 4)
>>> c = ['o', 'm', 'e', 'g', 'a']
>>>
>>> print(a[2])
f
>>> print(b[1])
2
>>> print(c[3])
g
```
这里的索引操作符，实际就是一种多态行为，对于字符串、元组、列表进行同种操作，但实际行为由对象本身决定。下面的例子利用继承定义了多个类，展示了多态行为：

```python
class Animal(object):
    
   def __init__(self, name=''):
      self.name = name

   def talk(self):
      pass

class Cat(Animal):
	
   def talk(self):
      print("Meow!")

class Dog(Animal):
    
   def talk(self):
      print("Woof!")

animals = [Cat("Missy"), Dog("Rocky")]
for a in animals:
	a.talk()
# >> Meow!
# >> Woof!
```
这里对a对象调用talk，实际执行时由对象的实际类型决定了哪种操作，这种动态运行的效果就是多态性，多态特性能很大程度上简化代码。


## 4 抽象类
Python在面向对象编程方面，没有提供定义接口的方法，但是支持抽象基类的。要定义抽象基类，需要使用python的abc库。下面给出一个python2.x版本的例子(来自[Abstract Classes in Python](https://www.zaiste.net/posts/abstract_classes_in_python/)):

```python
from abc import ABCMeta, abstractmethod

class Animal(object):
    __metaclass__ = ABCMeta

    @abstractmethod
    def say_something(self): pass # 抽象方法 非抽象子类需实现

class Cat(Animal):
    def say_something(self):
        return "Miauuu!"
a = Animal()
a.say_something()
```
如果试图实例化抽象基类,则会提示错误:
```
Traceback (most recent call last):
  File "abstactDemo.py", line 13, in <module>
    a = Animal()
TypeError: Can't instantiate abstract class Animal with abstract methods say_something
```
实例化实现了全部抽象方法的子类，则是允许的:
```python
from abc import ABCMeta, abstractmethod

class Animal(object):
    __metaclass__ = ABCMeta

    @abstractmethod
    def say_something(self):
          return "I'm an animal!"

class Cat(Animal):
    def say_something(self):
        s = super(Cat, self).say_something()
        return "%s - %s" % (s, "Miauuu")

a = Cat()
print(a.say_something())
# >> I'm an animal! - Miauuu
```
其他版本Python抽象基类实现，可以参考[Is it possible to make abstract classes in python?](https://stackoverflow.com/questions/13646245/is-it-possible-to-make-abstract-classes-in-python)。


## 参考资料

- [Python class inherits object](https://stackoverflow.com/questions/4015417/python-class-inherits-object)
- [correct way to define class variables in Python](https://stackoverflow.com/questions/9056957/correct-way-to-define-class-variables-in-python)
- [What are “class methods” and “instance methods”, in Python?](https://softwareengineering.stackexchange.com/questions/306092/what-are-class-methods-and-instance-methods-in-python)
- [Object-oriented programming in Python](http://zetcode.com/lang/python/oop/)
- [Abstract Classes in Python](https://www.zaiste.net/posts/abstract_classes_in_python/)
- [Is it possible to make abstract classes in python?](https://stackoverflow.com/questions/13646245/is-it-possible-to-make-abstract-classes-in-python)
- [Improve Your Python: Python Classes and Object Oriented Programming](https://jeffknupp.com/blog/2014/06/18/improve-your-python-python-classes-and-object-oriented-programming/)

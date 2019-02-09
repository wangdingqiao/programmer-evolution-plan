#写在前面
 
  本节对常见数据结构做一个预览，我们的目的是快速了解他们，对于它们涉及到的复杂的数据结构和算法，在这里并不全部展开，留在后期详述。

## 1.数组
数组是我们要学习的第一个**线性结构(Linear structure)**，所谓线性结构，指的是在数据有限集合中，每个数据元素都有一个确定的位置，例如$a_{0}$是第一个元素，$a_{n-1}$是最后一个元素，$a_{i}$是第i个元素；元素之间相邻，例如$a_{i}$前驱为$a_{i-1}$，$a_{i}$后继为$a_{i+1}$。其中i被称之为在线性表中的**位序**。

数组中存放的一般是同类型元素的集合，在物理存储上它们连续存放，通过索引来访问数组中的每个元素，数组可以是一维，二维和其他多维形式。
常见的一维数组形式表示为：
![数组表示](http://img.blog.csdn.net/20171015150151505?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

二维数组表示为:
![这里写图片描述](http://img.blog.csdn.net/20171209102219495?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

访问一维数组我们只需要一个索引，而访问多维数组则需要多个多个数组组合的索引。例如存储(10, 20, 30, 40, 50)数据的数组表示为：
![数组表示](http://img.blog.csdn.net/20171015150542620?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
访问第一个元素，我们通过可以通过索引0表示为：numbers[0]，它的值为10。
## 1.1 数组的特性

数组的实现可以分为固定大小的，也称之为静态数组(static array)；另外一种是大小随着需要能够动态增长的，称之为动态数组(dynamic array)。静态数组，需要提前预知数组的大小，这种预判在某些情况下可能会偏大，从而导致存储空间的浪费，同时如果预估比实际需要少了，则容易发生数据溢出。动态数组可以根据需要动态的增长空间，内存安全的语言例如Java/C#之类的会自动管理内存，但是像C++中动态分配数组则需要用户自行管理，相比较而言容易导致错误。Python语言会自行分配和回收内存，数组天生就是动态数组。

在数组中访问元素，通过索引即可找到，时间复杂度为O(1)。
插入元素时，如果是尾部插入，则时间复杂度为O(1)，如果是其他位置插入则需要移动其他元素，来保持数组的线性特性，假若数组长度为n，插入位置概率平均分布，则插入元素时移动元素的平均量为:

$\frac{\sum_{i=0}^n}{n}=\frac{n+(n-1)+...+1}{n}=\frac{1+n}{2}$
则平均时间复杂度为O(n)。同理，删除元素时，删除最后一个元素，则不需要移动元素，删除其余元素则需要移动其他元素，时间复杂为O(n)。

##1.2 Python中的数组

在Python中，使用list来表示数组，list对象[实现为一个动态数组](https://docs.python.org/2/faq/design.html#how-are-lists-implemented)，也就是长度根据需要自动调整的数组，这个数组**支持不同类型元素存储**。另外[array模块](https://docs.python.org/2/library/array.html)也提供了接近C语言数组的只支持同类型元素的数组。

在Python中使用list存储上面数字:

```python
   numbers = [10, 20, 30, 40, 50]
   print("array length:", len(numbers))
   print("first element:", numbers[0])
   print("last element:", numbers[-1])
   #('array length:', 5)
   #('first element:', 10)
   #('last element:', 50)
```
使用Array模块存储为:

```python
   import array
   # 使用l表示为int数组
    numbers = array.array("l", [10, 20, 30, 40, 50])  
    print("array length:", len(numbers))
    print("first element:", numbers[0])
    print("last element:", numbers[-1])
```
Python中list支持多种操作，例如添加元素append，合并两个表使用extend操作，感兴趣地可以查看[Python list](https://docs.python.org/2/tutorial/datastructures.html)。

## 1.3数组的应用
数组一般用来存放一组相关的数据，可以是简单的整数，也可以是复杂的对象；数组还可以同来实现其他数据结构，例如使用数组实现栈、队列等数据结构。

#2.链表

链表是我们要学习的第二种线性结构。与数组不同的时，链表中每个元素也称之为节点(Node)，它的物理位置并不一定与它的前驱或者后继相邻，可以在其他任意位置，只要有一种线索将每个元素串起来，形成这个线性结构即可。这个线索，就是每个节点的地址，习惯上称之为指针。

## 2.1 不同类型的链表
例如如下图所示为有一个指向后继节点指针的**单链表(single linked list)**：
![单链表](http://img.blog.csdn.net/20171209112158994?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
单链表中有一个头指针(Head)指向链表中第一个节点，最后一个节点没有后继节点了，因此它的指针指向空即NULL。

通过上面的观察，我们可以得出单链表对应的节点结构，可以表达为：
```python
class SingleLinkListNode(object):
    """
    单链表结点类
    """
    def __init__(self, data=None, next_node=None):
        self.data = data     # 数据域
        self.next = next_node # 指向下一个结点的指针(也即是地址)

    def __str__(self):
        return str(self.data)
```

在上述单链表中，如果我们已经找到了第i个节点，能不能找到位于i之前的节点呢？  这里需要在尾部节点上做一个修改，使它的指针指向链表头，这样构成了一个环形结构，这样的链表称之为**循环链表(Circular linked list)**。在循环链表中，可以通过任意节点找到链表中的其他节点。

另外一个问题是，如果我们已经找到了第i个节点，想知道它的前驱怎么办？我们可以在遍历过程中，用一个临时变量记住它的前驱节点。另一种方法是，在节点结构中提供两个指针，一个指向后继，一个指向前驱，这种链表称之为**双向链表(Double linked list)**，它的节点结构声明为：

```python
class DoubleLinkListNode(object):
    """
    双链表结点类
    """
    def __init__(self, data=None, prev_node=None,next_node=None):
        self.data = data     # 数据域
        self.prev = prev_node # 指向前驱节点
        self.next = next_node # 指向后继节点

    def __str__(self):
        return str(self.data)
```
当然也可以在双链表中，加入循环机制，构成循环双链表。上述3种链表，对比如下图所示:

![链表形式](http://img.blog.csdn.net/20171015160953438?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

##2.2 链表特性
以单链表为例进行说明。

在上面的链表中，我们可以看出，如果需要访问数组中第i个元素，则需要从头开始遍历链表，除此之外别无它法(花费额外空间保存第i个元素地址除外)，因此链表中访问元素时间复杂度为O(n)。

插入元素时，头部插入则仅仅需要重新调整头指针和插入节点的指针，时间复杂度为O(1)。尾部插入时，首先需要找到尾部节点，这个遍历过程时间复杂度为O(n)，插入时间复杂度为O(1)，因此总的时间复杂度为O(n)；**注意**，如果链表中保存了尾部指针位置，则尾部插入时时间复杂度也能达到O(1)。

删除元素时，我们首先需要定位到指定节点，然后删除，则时间复杂度为O(n)。

## 2.3 Python中链表
Python中并没有提供链表的实现，但是list结构本身是支持动态增长的数组，且数组元素是引用类型，因此使用list类型即可满足编程需求。这里自己动手实现一个上面描述的单链表:

```python
class LinkListNode(object):
    """
    链表结点
    """
    def __init__(self, data=None, next_node=None):
        self.data = data
        self.next = next_node

    def __str__(self):
        return str(self.data)


class LinkList(object):
    """
    单链表类
    """
    def __init__(self):
        self.head = LinkListNode()  # 表头的空结点指针 Dummy node
        self.tail = self.head

    def __init__(self, data_array):
        if type(data_array) is not list:
            raise ValueError("init with data array only.")
        self.head = LinkListNode()
        cur_node = self.head
        for x in data_array:
            cur_node.next = LinkListNode(x)
            cur_node = cur_node.next
        self.tail = cur_node

    def __str__(self):
        head = self.head.next
        output_str = "LinkList["
        while head:
            output_str += str(head.data)+", "
            head = head.next
        output_str += "]"
        return output_str

    def push_back(self, data):
        self.tail.next = LinkListNode(data)
        self.tail = self.tail.next

    def back(self):
        if self.tail:
            return self.tail.data
        else:
            return None

    def pop_back(self):
        if self.tail != self.head:
            node_to_remove = self.head.next
            prev_node = self.head
            while node_to_remove and node_to_remove.next:
                prev_node = node_to_remove
                node_to_remove = node_to_remove.next
            prev_node.next = None
            self.tail = prev_node
            del node_to_remove

    def front(self):
        if self.head.next:
            return self.head.next.data
        else:
            return None

    def push_front(self, data):
        self.head.next = LinkListNode(data, self.head.next)
        if self.tail == self.head:
            self.tail = self.head.next

    def pop_front(self):
        if self.head != self.tail:
            first_node = self.head.next
            self.head.next = first_node.next
            if not self.head.next:
                self.tail = self.head
            del first_node

    def remove_at(self, index):
        if index < 0 or self.tail == self.head:
            return False
        node_to_remove = self.head.next
        prev_node = self.head
        for i in range(index):
            prev_node = node_to_remove
            node_to_remove = node_to_remove.next
            if not node_to_remove:
                return False
        prev_node.next = node_to_remove.next
        if not prev_node.next:
            self.tail = prev_node
        del node_to_remove
        return True

    def is_empty(self):
        return self.tail == self.head

    def size(self):
        count = 0
        cur_node = self.head.next
        while cur_node:
            count += 1
            cur_node = cur_node.next
        return count

    @staticmethod
    def print_list(list_head):
        """
        打印链表
        :param list_head: 链表头结点
        :return:None
        """
        output_str = str(list_head)
        print(output_str)
```

注意上述实现的几点:

- 提供了一个head指向一个空的头结点，一个tail指针指向尾部节点。

- 提供了额外的tail指针，虽然方便了尾部插入；但使得在进行某些操作时，总是得考虑尾部指针的更新，例如删除节点时如果删除了尾部节点则应更新tail。**可见增加一个指针成员将使得编码变得复杂了**。

- 上面实现中提供了两个初始化方法，可以构建一个空的链表，也可以从一个数组构造链表。

- 上述实现中，提供了类的静态打印数据方法，这个方法重写了`__str__`方法。


为上述单链表类编写一个测试用例:

```python
if __name__ == "__main__":
    link_list = LinkList([1, 2, 3, 4])
    LinkList.print_list(link_list)
    i = 0
    while not link_list.is_empty():
        if i % 2:
            link_list.pop_back()
        else:
            link_list.pop_front()
        i += 1
        LinkList.print_list(link_list)
```

输出:
```
LinkList[1, 2, 3, 4, ]
LinkList[2, 3, 4, ]
LinkList[2, 3, ]
LinkList[3, ]
LinkList[]
```

链表实现的时候，测试用例的要考虑的情形还是挺多的，在编写的时候尤其要考虑到特殊情况，例如删除空表中元素等。双链表的实现可以自行实现，也可以参考[Linked Lists in Python](https://dbader.org/blog/python-linked-list)。

#3.数组和链表的比较

数组的实现要比链表简单，但是为什么还是要引入链表呢？通过下面这个表格的对比，我们就能回答这个问题(整理自[Linked Lists](https://www.cs.wcupa.edu/rkline/ds/linked-lists.html))。

| 比较项目    | Array     | LinkList|
|:--------|:---------:|:-------:|
|元素访问  | O(1)  随机访问    | O(n)  需要遍历  |
|头部添加/删除  | O(n)  因为需要移动其他元素| O(1) 修改指针即可|
|尾部添加/删除  | O(1)-尾部添加时摊销O(1)| O(1)-有尾指针的链表中O(1)|
|任意位置添加/删除|O(n)-移动元素开销|O(n)-遍历指针开销|
|额外空间|O(n)-数组空间预分配，浪费的空间在于没有实际使用的空间|O(n)-存储指针而消耗的空间|
|空间限制| 连续的空间，特殊情况下容易不足| 不需要连续空间|

总结一下链表的优势在于:

- 1)不用提前估算空间，空间也不需要预分配
- 2)在删除结点和添加结点时比数组快(删除元素时，数组需要移动元素，这开销远比链表遍历指针大，虽然二者复杂度都为O(n))。

链表的劣势在于:

 - 1)不能随机访问，除了头尾结点外只能顺序遍历。
 - 2) 存储指针也将带来额外开销。

数组的优势在于:支持随机访问 可以使用索引访问；劣势在于要求实现预分配空间，通常分配的空间比使用的多，而且在内存碎片情况下，可能无法继续扩大。

关于两者的比较，感兴趣地还可以参考:

- 1)[Difference between Array and Linked List](http://freefeast.info/difference-between/difference-between-array-and-linked-list-array-vs-linked-list/)
- 2)[SO](https://stackoverflow.com/questions/166884/array-versus-linked-list)
- 3)[geeksforgeeks](http://www.geeksforgeeks.org/linked-list-vs-array/)

#4. Python中list实现说明
Python中list实现为动态数组，而不是链表，可以参考其结构定义:

```cpp
typedef struct {
    PyObject_HEAD
    Py_ssize_t ob_size;

    /* Vector of pointers to list elements.  list[0] is ob_item[0], etc. */
    PyObject **ob_item;

    /* ob_item contains space for 'allocated' elements.  The number
     * currently in use is ob_size.
     * Invariants:
     *     0 <= ob_size <= allocated
     *     len(list) == ob_size
     *     ob_item == NULL implies ob_size == allocated == 0
     */
    Py_ssize_t allocated;
} PyListObject;
```

这是一个预分配的动态数组，真实大小为ob_size,分配大小为allocated，其中ob_item是一个指针数组，每个元素指向PyObject对象。感兴趣地可以参考[SO](https://stackoverflow.com/questions/15121905/is-a-python-list-a-singly-or-doubly-linked-list)。

也可以通过下面的图来理解Python中list的实现:
![Python数组和链表](http://img.blog.csdn.net/20171015172954365?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2FuZ2RpbmdxaWFvaXQ=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

本部分介绍了常见数据结构中的数组和链表，要想较好地掌握本节内容，更多的练习必不可少，这里推荐[leetcode linkedlist](https://leetcode.com/tag/linked-list/)训练的习题。



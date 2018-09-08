#include <iostream>
#include <algorithm>
#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */
#include <assert.h>     /* assert */
#include <string>

template<typename T>
struct DoubleLinkListNode
{
	T data;
	DoubleLinkListNode<T>* prev;
	DoubleLinkListNode<T>* next;
	DoubleLinkListNode(const T& data, DoubleLinkListNode<T>* prev, DoubleLinkListNode<T>* next)
		:data(data), prev(prev), next(next){}
};

template<typename T>
class DoubleQueue
{
public:
	DoubleQueue():_head(NULL),_tail(NULL),_size(0) {}
	~DoubleQueue()
	{
		clear();
	}
	DoubleQueue(const DoubleQueue<T>& other):_head(NULL),_tail(NULL),_size(0)
	{
		DoubleLinkListNode<T>* p = other._head;
		while(p)
		{
			push_back(p->data);
			p = p->next;
		}
	}
	DoubleQueue<T>& operator=(DoubleQueue<T> other)  // 注意参数类型为值传递 非引用传递
	{
		swap(*this, other);
		return *this;
	}
	friend void swap(DoubleQueue<T>& one, DoubleQueue<T>& other) // 注意参数为非常量引用类型
	{
		using std::swap;
		std::swap(one._head, other._head);
		std::swap(one._tail, other._tail);
		std::swap(one._size, other._size);
	}
	friend std::ostream& operator<<(std::ostream& stream, const DoubleQueue<T>& list)
	{
		stream << "DoubleQueue[";
		DoubleLinkListNode<T>* p = list._head;
		while(p)
		{
			stream << p->data << ",";
			p = p->next;
		}
		stream << "]";
		return stream;
	}
public:
	void push_front(const T& val)
	{
		if(!_head)
		{
			_tail = _head = new DoubleLinkListNode<T>(val,NULL, NULL);
		}
		else
		{
			DoubleLinkListNode<T>* _tmp = new DoubleLinkListNode<T>(val,NULL,_head);
			_head->prev = _tmp;
			_head = _tmp;
		}
		++_size;
	}
	void pop_front()
	{
		assert(_head);
		DoubleLinkListNode<T> *next = _head->next;
		delete _head;
		if(next)
		{
			_head = next;
			_head->prev = NULL;
		}
		else
		{
			_head = _tail = NULL;
		}
		--_size;
	}
	T& front()
	{
		assert(_head);
		return _head->data;
	}
	const T& front() const
	{
		return front();
	}
	void push_back(const T& val)
	{
		if(!_tail)
		{
			push_front(val);
		}
		else
		{
			DoubleLinkListNode<T>* _tmp = new DoubleLinkListNode<T>(val,_tail,NULL);
			_tail->next = _tmp;
			_tail = _tmp;
			++_size;
		}
	}
	void pop_back()
	{
		assert(_tail);
		DoubleLinkListNode<T>* p = _tail->prev;
		delete _tail;
		if(p)
		{
			p->next = NULL;
			_tail = p;
		}
		else
		{
			_head = _tail = NULL;
		}
		--_size;
	}
	T& back()
	{
		assert(_tail);
		return _tail->data;
	}
	const T& back() const
	{
		return back();
	}
	std::size_t size() const
	{
		assert(_size >= 0);
		return _size;
	}
	bool empty() const
	{
		return _head == NULL;
	}
	void clear()
	{
		DoubleLinkListNode<T>* p = _head;
		while(p)
		{
			DoubleLinkListNode<T>* next = p->next;
			delete p;
			p = next;
		}
		_head = _tail = NULL;
	}
private:
	DoubleLinkListNode<T> *_head, *_tail;
	std::size_t _size;
};

int main()
{
	/* initialize random seed: */
    srand (time(NULL));

    // 测试入队操作
	DoubleQueue<int> queue;
	queue.push_front(1);
	queue.push_back(3);
	queue.push_front(2);
	queue.push_front(5);
	std::cout << "queue=" << queue << std::endl;
	std::cout << "front= " << queue.front() << std::endl;
	std::cout << "back= " << queue.back() << std::endl;
	// 测试赋值操作符
	{
		DoubleQueue<int> another ;
		another.push_front(88);
		another.push_back(99);
		std::cout << "another, before assignment queue= " << another << std::endl;
		another = queue;
		std::cout << "another, after assignment queue= " << another << std::endl;
	}
	std::cout << "queue=" << queue << std::endl;
	// 测试拷贝构造函数
	{
		DoubleQueue<int> moreQueue(queue);
		std::cout << "one more queue= " << moreQueue << std::endl;
	}
	std::cout << "queue=" << queue << std::endl;

	// 测试出队操作
	while(!queue.empty())
	{
		const int randNum = rand() % 100;
		if(randNum % 2 > 0)
		{
			queue.pop_front();
		}
		else
		{
			queue.pop_back();
		}
		std::cout << "pop " << (randNum %2 > 0 ? "front" : "back") 
		<< " ,now queue= " <<  queue 
		<< " ,size= " << queue.size() << std::endl;
	}

	// 测试泛型
	DoubleQueue<std::string> strQueue;
	strQueue.push_back("hello");
	std::cout << "strQueue=" << strQueue << std::endl;
	strQueue.push_front("Say ");
	strQueue.push_back(" to you!");
	std::cout << "strQueue=" << strQueue << std::endl;
}
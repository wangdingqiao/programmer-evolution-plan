#!/usr/bin/python
# -*- coding: UTF-8 -*-


class CircularQueue(object):
    """
    循环队列
    """
    MAX_QUEUE_SIZE = 6

    def __init__(self):
        self.data = [None for _ in range(CircularQueue.MAX_QUEUE_SIZE)]  # 模拟固定大小数组
        self.front, self.rear = 0, 0

    def enter_queue(self, x):
        if self.is_full():
            raise Exception("queue is full.")
        self.data[self.rear] = x
        self.rear = (self.rear + 1) % CircularQueue.MAX_QUEUE_SIZE
        return True

    def del_queue(self):
        if self.is_empty():
            return None
        val = self.data[self.front]
        self.front = (self.front + 1) % CircularQueue.MAX_QUEUE_SIZE
        return val

    def is_empty(self):
        return self.front == self.rear

    def is_full(self):
        return (self.rear + 1) % CircularQueue.MAX_QUEUE_SIZE == self.front  # 牺牲一个单元 用来区分满 还是空

    def size(self):
        return (self.rear - self.front + CircularQueue.MAX_QUEUE_SIZE) % CircularQueue.MAX_QUEUE_SIZE

    def get_front(self):
        if not self.is_empty():
            return self.data[self.front]
        else:
            return None

    def clear(self):
        self.__init__()

    def __str__(self):
        ret_str = "queue["
        start, end = self.front, self.rear
        while start != end:
            ret_str += str(self.data[start]) + ", "
            start = (start + 1) % CircularQueue.MAX_QUEUE_SIZE
        ret_str += "]"
        return ret_str

    def __repr__(self):
        return self.__str__()


if __name__ == "__main__":
    my_queue = CircularQueue()
    my_queue.enter_queue('a')
    my_queue.enter_queue('b')
    my_queue.enter_queue('c')
    my_queue.enter_queue('d')
    my_queue.enter_queue('e')
    print('is full ?', my_queue.is_full(), my_queue)
    print('front pos: ', my_queue.front, ' rear pos:', my_queue.rear)
    print('front is: ', my_queue.get_front(), my_queue)
    print('delete head:', my_queue.del_queue(), my_queue)
    print("enter 'f'")
    my_queue.enter_queue('f')
    print('element size: ', my_queue.size(), my_queue)
    print('front pos: ', my_queue.front, ' rear pos:', my_queue.rear)
    my_queue.clear()
    print("after clear ,is empty ? ", my_queue.is_empty(), my_queue)

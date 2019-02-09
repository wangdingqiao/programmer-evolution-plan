#!/usr/bin/python
# -*- coding: UTF-8 -*-


class Queue(object):
    """
    大小固定的队列 存在空间浪费
    """
    MAX_QUEUE_SIZE = 4

    def __init__(self):
        self.data = [None for _ in range(Queue.MAX_QUEUE_SIZE)]  # 模拟固定大小数组
        self.front, self.rear = 0, 0

    def enter_queue(self, x):
        if self.is_full():
            raise Exception("queue is full.")
        self.data[self.rear] = x
        self.rear += 1
        return True

    def del_queue(self):
        if self.front == self.rear:
            return None
        val = self.data[self.front]
        self.front += 1
        return val

    def is_empty(self):
        return self.front == self.rear

    def is_full(self):
        return self.rear >= Queue.MAX_QUEUE_SIZE

    def size(self):
        return self.rear - self.front

    def get_front(self):
        if not self.is_empty():
            return self.data[self.front]
        else:
            return None

    def clear(self):
        self.__init__()

    def __str__(self):
        ret_str = "queue["
        for x in range(self.front, self.rear):
            ret_str += self.data[x] + ", "
        ret_str += "]"
        return ret_str

    def __repr__(self):
        return self.__str__()


if __name__ == "__main__":
    my_queue = Queue()
    my_queue.enter_queue('a')
    my_queue.enter_queue('b')
    my_queue.enter_queue('c')
    my_queue.enter_queue('d')
    print('front is: ', my_queue.get_front(), my_queue)
    print('delete head:', my_queue.del_queue())
    print("is full ? ", my_queue.is_full(), my_queue)
    print('element size: ', my_queue.size())
    my_queue.clear()
    print("after clear, is empty ? ", my_queue.is_empty(), my_queue)

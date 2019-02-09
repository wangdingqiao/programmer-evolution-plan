#!/usr/bin/python
# -*- coding: UTF-8 -*-


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

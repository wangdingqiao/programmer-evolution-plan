#!/usr/bin/python
# _*_ coding: utf-8 -*_

"""
使用 链地址法Chaining  解决冲突的哈希表
"""


class HashEntry(object):
    """
     哈希表存储条目
    """
    def __init__(self, key, value, next_entry=None):
        self.key = key
        self.value = value
        self.next = next_entry   # to build the chain

    def get_key(self):
        return self.key

    def set_value(self, value):
        self.value = value

    def get_value(self):
        return self.value

    def get_next(self):
        return self.next

    def set_next(self, next_entry):
        self.next = next_entry

    def __str__(self):
        return "[ key= " + str(self.key) + " value= " + str(self.value) + " ]"

    def __repr__(self):
        return self.__str__()


class HashMap(object):
    """
        哈希表 使用chaining技术解决冲突
    """

    def __init__(self, size=101):
        self.linked_hash_entry = [None for _ in range(size)]
        self.hash_func = lambda key: key % len(self.linked_hash_entry)

    def set_hash_func(self, hash_func):
        self.hash_func = hash_func

    def size(self):
        total_size = 0
        for pos, entry in enumerate(self.linked_hash_entry):
            while entry:
                total_size += 1
                entry = entry.get_next()
        return total_size

    def __get_capacity__(self):
        return len(self.linked_hash_entry)

    def __get_hash_value__(self, key):
        """
        执行哈希值计算 出错时抛出异常
        :param key: 键
        :return: 哈希值
        """
        pos = self.hash_func(key)
        if pos > self.__get_capacity__():
            raise AssertionError("entry pos exceed max size with key=", key, " capacity= ", self.__get_capacity__())
        return pos

    def get(self, key):
        pos = self.__get_hash_value__(key)
        entry = self.linked_hash_entry[pos]
        while entry and entry.key != key:
            entry = entry.get_next()
        return (entry and entry.value) or None

    def put(self, key, value):
        pos = self.__get_hash_value__(key)
        entry = self.linked_hash_entry[pos]
        prev_entry = None
        while entry and entry.key != key:
            prev_entry = entry
            entry = entry.get_next()
        if entry:
            entry.value = value  # update
        elif prev_entry:
            prev_entry.set_next(HashEntry(key, value))    # append to collision linked list
        else:
            self.linked_hash_entry[pos] = HashEntry(key, value)  # insert new entry

    def remove(self, key):
        pos = self.__get_hash_value__(key)
        entry = self.linked_hash_entry[pos]
        prev_entry = None
        while entry and entry.key != key:
            prev_entry = entry
            entry = entry.get_next()
        if not entry:
            return False
        if not prev_entry:
            self.linked_hash_entry[pos] = entry.get_next()  # remove entry at head
        else:
            prev_entry.set_next(entry.get_next())  # remove entry has predecessor
        return True

    def __str__(self):
        ret_str = "HashMap[ "
        for pos, entry in enumerate(self.linked_hash_entry):
            if not entry:
                continue
            entry_items = []
            while entry:
                entry_items.append(str(entry))
                entry = entry.get_next()
            ret_str += "\n\t[" + str(pos) + " : " + " ".join(entry_items) + "] "
        ret_str += " \n]"
        return ret_str

    def __repr__(self):
        return self.__str__()

if __name__ == "__main__":
    h_map = HashMap(11)
    entry_data = [54, 26, 93, 17, 77, 31, 44, 55, 20]
    for x in entry_data:
        h_map.put(x, x)
    print("after insert all elements: ", h_map)
    # test remove
    print("remove 99 success ? ", h_map.remove(99))
    print("remove 44 success ?", h_map.remove(44))
    print("remove 54 success ?", h_map.remove(54))
    print(" after remove elements: ", h_map)
    # test update
    print("before update key= ", 17, " value= ", h_map.get(17))
    h_map.put(17, 71)
    print("update key= ", 17, " new value = ", 71)
    print("after update key= ", 17, "value= ", h_map.get(17))
    print("after update", h_map)
    # retest insert
    h_map.put(88, 88)
    h_map.put(42, 42)
    print("after insert data size= ", h_map.size(), h_map)
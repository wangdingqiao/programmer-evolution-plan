#!/usr/bin/python
# _*_ coding: utf-8 -*_

"""
哈希表 使用开放寻址策略(Open addressing strategy)解决冲突 + rehash
"""
import random


class HashEntry(object):
    """
     哈希表存储条目
    """
    ENTRY_TYPE_ACTIVE = "active"
    ENTRY_TYPE_DELETED = "deleted"
    ENTRY_TYPE_EMPTY = "empty"

    def __init__(self, entry_type="empty", key=None, value=None):
        self.entry_type = entry_type
        self.key = key
        self.value = value

    def get_key(self):
        return self.key

    def set_value(self, value):
        self.value = value

    def get_value(self):
        return self.value

    def __str__(self):
        return "[ key= " + str(self.key) + " value= " + str(self.value) + " ]"

    def __repr__(self):
        return self.__str__()

    def get_type(self):
        return self.entry_type

    def set_type(self, entry_type):
        self.entry_type = entry_type


class HashMap(object):
    """
        哈希表 使用开放寻址策略
    """
    PROB_TYPE_LINEAR = "linear"
    PROB_TYPE_QUADRATIC = "quadratic"
    PROB_TYPE_RANDOM = "random"

    def __init__(self, size=101, prob_func_type="linear"):
        self.hash_entries = [HashEntry(HashEntry.ENTRY_TYPE_EMPTY) for _ in range(size)]
        self.hash_func = lambda key: key % self.__get_capacity__()
        self.probing_func_type = prob_func_type
        self.threshold = 0.75
        self.prob_random_seq = []
        self.__compute_random_prob_sequence__()

    def set_hash_func(self, hash_func):
        self.hash_func = hash_func

    def size(self):
        return len(filter(lambda e: e and e.get_type() == HashEntry.ENTRY_TYPE_ACTIVE, self.hash_entries))

    def set_threshold(self, threshold):
        self.threshold = threshold

    def __get_capacity__(self):
        return len(self.hash_entries)

    def __get_prob_offset__(self, try_times):
        if self.probing_func_type == HashMap.PROB_TYPE_LINEAR:  # 线性探测
            return try_times
        elif self.probing_func_type == HashMap.PROB_TYPE_QUADRATIC:  # 二次探测
            return try_times * try_times
        elif self.probing_func_type == HashMap.PROB_TYPE_RANDOM:  # 随机探测
            return self.prob_random_seq[try_times]

    def __compute_random_prob_sequence__(self):
        self.prob_random_seq = random.sample(xrange(self.__get_capacity__()), self.__get_capacity__())

    def __get_hash_value__(self, key, try_times):
        """
        执行哈希值计算 出错时抛出异常
        :param key: 键
        :return: 哈希值
        """
        origin_pos = self.hash_func(key)
        return (origin_pos + self.__get_prob_offset__(try_times)) % self.__get_capacity__()  # Hi = (H(key)+di) mode m

    def find_pos(self, key):
        try_time = 0
        while True:
            pos = self.__get_hash_value__(key, try_times=try_time)
            entry = self.hash_entries[pos]
            if entry.get_type() == HashEntry.ENTRY_TYPE_EMPTY:  # not found
                # print('OK search key= ', key, " empty pos = ", pos)
                return pos
            if entry.get_type() == HashEntry.ENTRY_TYPE_ACTIVE and entry.get_key() == key:  # found
                # print('OK search key= ', key, " active pos = ", pos)
                return pos
            # if entry.get_type() == HashEntry.ENTRY_TYPE_ACTIVE:
            #     print('Conflict search key= ', key, " active pos = ", pos)
            try_time += 1
            if try_time > self.__get_capacity__():  # 搜索次数过多应停止搜索
                break
        raise None

    def re_hash(self):
        print("before rehash: ", self)
        old_array = self.hash_entries
        self.hash_entries = [HashEntry(HashEntry.ENTRY_TYPE_EMPTY) for _ in range(2 * len(old_array))]
        self.__compute_random_prob_sequence__()
        for pos, entry in enumerate(old_array):
            if entry and entry.get_type() == HashEntry.ENTRY_TYPE_ACTIVE:
                self.put(entry.get_key(), entry.get_value())
        print("after rehash, now capacity= ", self.__get_capacity__(), " size= ", self.size())

    def get(self, key):
        pos = self.find_pos(key)
        if pos is None:
            return None
        entry = self.hash_entries[pos]
        if entry.get_type() == HashEntry.ENTRY_TYPE_ACTIVE:
            return entry.get_value()
        else:
            return None

    def put(self, key, value):
        pos = self.find_pos(key)
        if pos is None:
            raise AssertionError(" table is full while insert key= ", key,
                                 " with size= ", self.size(), " capacity= ", self.__get_capacity__())
        entry = self.hash_entries[pos]
        if entry.get_type() == HashEntry.ENTRY_TYPE_ACTIVE:
            entry.set_value(value)   # update value
        else:
            self.hash_entries[pos] = HashEntry(HashEntry.ENTRY_TYPE_ACTIVE, key, value)  # insert as new

        empty_slot_size = len(filter(lambda e: e and e.get_type() == HashEntry.ENTRY_TYPE_EMPTY, self.hash_entries))

        load_factor = float(self.__get_capacity__() - empty_slot_size) / self.__get_capacity__()
        if load_factor > self.threshold:
            self.re_hash()

    def remove(self, key):
        pos = self.find_pos(key)
        if pos is None:
            return False
        entry = self.hash_entries[pos]
        if entry.get_type() == HashEntry.ENTRY_TYPE_ACTIVE:
            entry.set_type(HashEntry.ENTRY_TYPE_DELETED)  # do lazy delete
            return True
        else:
            return False

    def __str__(self):
        ret_str = "HashMap[ "
        for pos, entry in enumerate(self.hash_entries):
            if not entry or entry.get_type() != HashEntry.ENTRY_TYPE_ACTIVE:
                continue
            ret_str += "\n\t" + str(pos) + ":" + str(entry)
        ret_str += " \n]"
        return ret_str

    def __repr__(self):
        return self.__str__()


def test_case_1():
    h_map = HashMap(11)
    h_map.set_threshold(2.0)  # disable rehash
    entry_data = [54, 26, 93, 17, 77, 31, 44, 55, 20]
    for x in entry_data:
        h_map.put(x, x)
    print("after insert all elements: ", h_map)
    # test get
    print("get key=99 value= ? ", h_map.get(99))
    print("get key=44 value= ?", h_map.get(44))
    print("get key=54 value= ?", h_map.get(54))
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
    insert_data = [88, 42]
    for x in insert_data:
        h_map.put(x, x)
    print("after insert data:", insert_data, " size= ", h_map.size(), h_map)


def test_case_2():
    h_map = HashMap(11)   # test rehash
    entry_data = [54, 26, 93, 17, 77, 31, 44, 55, 20]
    for x in entry_data:
        h_map.put(x, x)
    print("after insert all elements: ", h_map)
    # test get
    print("get key=99 value= ? ", h_map.get(99))
    print("get key=44 value= ?", h_map.get(44))
    print("get key=54 value= ?", h_map.get(54))
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
    insert_data = [88, 42]
    for x in insert_data:
        h_map.put(x, x)
    print("after insert data:", insert_data, " size= ", h_map.size(), h_map)


if __name__ == "__main__":
    test_case_1()
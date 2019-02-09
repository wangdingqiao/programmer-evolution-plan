#!/usr/bin/python
# -*- coding: UTF-8 -*-

import Queue as Q


def test_case_1():
    q = Q.PriorityQueue()
    q.put((10, 'ten', 'p'))
    q.put((1, 'one', 'h'))
    q.put((5, 'five', 'j'))
    while not q.empty():
        print q.get(),


def test_case_2():
    class Skill(object):
        def __init__(self, priority, description):
            self.priority = priority
            self.description = description
            print 'New Level:', description
            return

        def __cmp__(self, other):
            return cmp(self.priority, other.priority)

    q = Q.PriorityQueue()

    q.put(Skill(5, 'Proficient'))
    q.put(Skill(10, 'Expert'))
    q.put(Skill(1, 'Novice'))

    while not q.empty():
        next_level = q.get()
        print '\n Processing level:', next_level.description


if __name__ == "__main__":
    test_case_1()
    test_case_2()
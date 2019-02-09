#!/usr/bin/python
# -*- coding: UTF-8 -*-

import collections


def is_palindrome_str(s):
    """
    :type s: str
    :rtype: bool
    """
    de_queue = collections.deque([x.lower() for x in s if x.isdigit() or x.isalpha() ])
    while len(de_queue) >= 2:
        head, tail = de_queue.popleft(), de_queue.pop()
        if head != tail:
            return False
    return True


if __name__ == "__main__":
    test_str = "A man, a plan, a canal: Panama"
    print('input str= ', test_str, " is palindrome ? ", is_palindrome_str(test_str))
    test_str = "race a car"
    print('input str= ', test_str, " is palindrome ? ", is_palindrome_str(test_str))
    test_str = "abcecba"
    print('input str= ', test_str, " is palindrome ? ", is_palindrome_str(test_str))
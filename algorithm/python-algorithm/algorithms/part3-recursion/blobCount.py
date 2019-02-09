#!/usr/env/python
# _*_ coding: utf-8 _*_

"""
Problem: you have a 2-dimensional grid of cells, each of which may be filled or empty.  Filled cells
that are connected form a “blob” (for lack of a better word).
Write a recursive method that returns the size of the blob containing a specified cell (i,j)

For example:
    0    1     2    3     4
0                   x     x
1                   x
2   x    x
3   x    x    x           x
4   x    x                x

BlobCount(0,3) = 3
BlobCount(0,4) = 3
BlobCount(3,4) = 2
BlobCount(4,0) = 7
"""


class Solution(object):

    @staticmethod
    def blob_count(matrix, row, col):
        if not Solution.is_valid_cell_pos(matrix, row, col):
            return 0
        if not matrix[row][col]:
            return 0
        visited_mark = {(row, col): True}
        return 1 + Solution.count_neighbor(matrix, row, col, visited_mark)

    @staticmethod
    def is_valid_cell_pos(matrix, row, col):
        if not matrix or not matrix[0]:
            return False
        rows, cols = len(matrix), len(matrix[0])
        if row < 0 or row >= rows or col < 0 or col >= cols:
            return False
        return True

    @staticmethod
    def count_neighbor(matrix, row, col, visited_mark):
        # print('count neighbor:', row, col)
        neighbor_pos = [(row, col + 1), (row - 1, col), (row, col - 1), (row + 1, col)]
        total_count = 0
        for n_row, n_col in neighbor_pos:
            if (n_row, n_col) in visited_mark:
                continue
            if Solution.is_valid_cell_pos(matrix, n_row, n_col) and matrix[n_row][n_col]:
                visited_mark[(n_row, n_col)] = True
                total_count += 1 + Solution.count_neighbor(matrix, n_row, n_col, visited_mark)
        return total_count


if __name__ == "__main__":
    input_matrix = [[0, 0, 0, 1, 1],
                    [0, 0, 0, 1, 0],
                    [1, 1, 0, 0, 0],
                    [1, 1, 1, 0, 1],
                    [1, 1, 0, 0, 1]]
    pos_array = [(0, 3), (0, 4), (3, 4), (4, 0), (0, 1)]
    for _row, _col in pos_array:
        print(("pos: (%i, %i)" % (_row, _col)), "blob size= ", Solution.blob_count(input_matrix, _row, _col))
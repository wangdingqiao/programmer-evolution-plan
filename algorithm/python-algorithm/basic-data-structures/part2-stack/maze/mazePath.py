#!/usr/bin/python
# -*- coding: UTF-8 -*-

import turtle
import sys
import collections

"""
迷宫路径问题
Author: wangdingqiao    http://blog.csdn.net/wangdingqiaoit
Date:2017-12-10
"""


class Point(collections.namedtuple('Point', 'x y')):
    __slots__ = ()

    def __str__(self):
        return "(" + str(self.x) + "," + str(self.y) + ")"

    def __repr__(self):
        return self.__str__()


class MazeCellNode(object):
    MAX_TRY_DIR = 3
    MIN_TRY_DIR = 0

    def __init__(self, pos, content):
        self.pos = pos
        self.content = content
        self.try_dir = MazeCellNode.MAX_TRY_DIR

    def __str__(self):
        return str(self.pos)

    def __repr__(self):
        return self.__str__()

    def is_block_cell(self):
        return self.content == "1"

    def is_exit_cell(self):
        return self.content == "O"

    def is_enter_cell(self):
        return self.content == "I"

    def get_cell_color(self):
        if self.is_block_cell():
            return "black"
        elif self.is_enter_cell():
            return "blue"
        elif self.is_exit_cell():
            return "red"
        else:
            return "white"


class MazeGraphics(object):
    CELL_WIDTH = 50
    CELL_HEIGHT = 50
    MAX_ROW = 12
    MAX_COLUMN = 12

    def __init__(self):
        """Turtle Constructor"""
        self.mazeData = []
        self.path = []
        self.enter_pos = None
        self.dimension = None

        self.screen = turtle.Screen()
        self.screen.title("Maze Program")
        self.screen.screensize(MazeGraphics.MAX_COLUMN * MazeGraphics.CELL_WIDTH,
                               MazeGraphics.MAX_ROW * MazeGraphics.CELL_HEIGHT)
        self.screen.setup(1000, 700, startx=0, starty=0)
        self.draw_start_pos = Point(0, 0)
        self.screen.bgcolor("orange")
        self.turtle = turtle.Turtle(shape="turtle")
        self.turtle.pensize(3)
        self.turtle.shapesize(2, 2)

    def read_from_file(self, file_name):
        with open(file_name, "r") as f:
            for row, line in enumerate(f.read().splitlines()):
                cell_node_list = [ MazeCellNode(Point(row, col), content) for col, content in enumerate(line)]
                self.mazeData.append(cell_node_list)
                if 'I' in line:
                    self.enter_pos = Point(row, line.index('I'))
        if self.mazeData:
            self.dimension = Point(len(self.mazeData), len(self.mazeData[0]))
            self.draw_start_pos = Point(-self.dimension.y / 2 * MazeGraphics.CELL_WIDTH,
                                        self.dimension.x / 2 * MazeGraphics.CELL_HEIGHT)
            print('load maze file= ', file_name, " success dimension= ", self.dimension)

    def get_path_using_stack(self):
        if not self.mazeData or not self.dimension:
            return None
        self.path = [self.enter_pos]
        while self.path:
            cell_node = self.__get_cell_node__(self.path[-1])
            if cell_node.is_exit_cell():
                return self.path
            else:
                neighbor_pos = self.__try_next_pos__(cell_node)
                if not neighbor_pos:   # 相邻位置已经都尝试过了 则从路径中移除
                    self.path.pop()
                else:
                    self.path.append(neighbor_pos)    # 未访问过且有效则加入路径
        return None

    def get_path_using_recursion(self):
        self.path = []
        self.__get_path_recursion__(self.__get_cell_node__(self.enter_pos))
        return self.path

    def __get_path_recursion__(self, cur_node):
        self.path.append(cur_node.pos)
        if cur_node.is_exit_cell():
            return True
        while cur_node.try_dir >= MazeCellNode.MIN_TRY_DIR:
            neighbor_pos = self.__try_next_pos__(cur_node)
            if not neighbor_pos:
                break
            elif self.__get_path_recursion__(self.__get_cell_node__(neighbor_pos)):
                return True
        self.path.pop()
        return False

    def solve_maze(self):
        if not self.mazeData or not self.dimension:
            return
        self.__draw_maze__()
        self.__draw_text__(self.draw_start_pos.x, - self.draw_start_pos.y - 60, "Searching solution ...")
        self.path = [self.enter_pos]
        while self.path:
            cell_node = self.__get_cell_node__(self.path[-1])
            if cell_node.is_exit_cell():
                self.turtle.hideturtle()
                self.__draw_text__(self.draw_start_pos.x, - self.draw_start_pos.y - 80, "Path has Found!")
                self.turtle.penup()
                x, y = self.__get_cell_pos__(cell_node.pos)
                self.turtle.setpos(x + MazeGraphics.CELL_WIDTH / 2, y - MazeGraphics.CELL_HEIGHT / 2)
                self.turtle.showturtle()
                return self.path
            else:
                neighbor_pos = self.__try_next_pos__(cell_node)
                if not neighbor_pos:   # 相邻位置已经都尝试过了 则从路径中移除
                    self.path.pop()
                    if self.path:
                        from_pos, to_pos = cell_node.pos, self.path[-1]
                        self.__fill_cell__(from_pos, to_pos)
                    else:
                        x, y = self.__get_cell_pos__(cell_node.pos)
                        cell_color = self.__get_cell_color__(cell_node.pos)
                        self.turtle.hideturtle()
                        self.__draw_square__(x, y, MazeGraphics.CELL_WIDTH, MazeGraphics.CELL_HEIGHT, cell_color)
                        self.__draw_text__(self.draw_start_pos[0], - self.draw_start_pos[1] - 80, "No Available Path!")
                        self.turtle.penup()
                        self.turtle.setpos(x + MazeGraphics.CELL_WIDTH / 2, y - MazeGraphics.CELL_HEIGHT / 2)
                        self.turtle.showturtle()
                        return None
                else:
                    self.path.append(neighbor_pos)    # 未访问过且有效则加入路径
                    from_pos, to_pos = cell_node.pos, neighbor_pos
                    self.__fill_cell__(from_pos, to_pos)
        return None

    def __try_next_pos__(self, maze_node):
        the_neighbor_pos, the_neighbor_dir = None, None
        offsets = [(-1, 0), (0, -1), (1, 0), (0, 1)]  # offset of one step toward [north, west, south, east]
        while not the_neighbor_pos and maze_node.try_dir >= MazeCellNode.MIN_TRY_DIR:
            offset_row, offset_col = offsets[maze_node.try_dir][0], offsets[maze_node.try_dir][1]
            maze_node.try_dir -= 1
            neighbor_pos = Point(maze_node.pos.x + offset_row, maze_node.pos.y + offset_col)
            neighbor_cell_node = self.__get_cell_node__(neighbor_pos)
            if neighbor_pos.x < 0 or neighbor_pos.y < 0 \
                    or neighbor_pos.x > self.dimension.x or neighbor_pos.y > self.dimension.y:
                continue
            if neighbor_cell_node.try_dir != MazeCellNode.MAX_TRY_DIR:  # has visited
                continue
            if neighbor_cell_node.is_block_cell():
                continue
            the_neighbor_pos = neighbor_pos
            break
        return the_neighbor_pos

    def __draw_maze__(self):
        if not self.dimension:
            return
        self.turtle.hideturtle()
        self.__draw_text__(self.draw_start_pos.x, - self.draw_start_pos.y - 20, "Maze Path Program (By Wangdingqiao 2017)")
        self.__draw_text__(self.draw_start_pos.x, - self.draw_start_pos.y - 40, "Initializing Maze ...")
        rows, cols = self.dimension
        self.turtle.speed(0)
        self.turtle.begin_fill()
        self.__draw_square__(self.draw_start_pos.x, self.draw_start_pos.y,
                             cols * MazeGraphics.CELL_WIDTH,
                             rows * MazeGraphics.CELL_HEIGHT, "pink")
        self.turtle.end_fill()
        for row in range(rows):
            self.turtle.penup()
            self.turtle.setpos(self.draw_start_pos.x, self.draw_start_pos.y - row * MazeGraphics.CELL_HEIGHT)
            self.turtle.pendown()
            for col in range(cols):
                x, y = self.__get_cell_pos__(Point(row, col))
                cell_color = self.__get_cell_color__(Point(row, col))
                self.__draw_square__(x, y, MazeGraphics.CELL_WIDTH, MazeGraphics.CELL_HEIGHT, cell_color)
        self.turtle.showturtle()

    def __get_cell_pos__(self, row_col):
        row, col = row_col
        start_x, start_y = self.draw_start_pos[0], self.draw_start_pos[1]
        x, y = start_x + col * MazeGraphics.CELL_WIDTH, start_y - row * MazeGraphics.CELL_HEIGHT
        return x, y

    def __get_cell_node__(self, row_col):
        row, col = row_col
        return self.mazeData[row][col]

    def __fill_cell__(self, from_row_col, to_row_col):
        x, y = self.__get_cell_pos__(from_row_col)
        heading_dir = [90, 180, 270, 0]
        self.turtle.hideturtle()
        self.turtle.penup()
        self.turtle.setpos(x, y)
        self.turtle.pendown()
        self.turtle.speed(10)
        self.__draw_square__(x, y,
                             MazeGraphics.CELL_WIDTH,
                             MazeGraphics.CELL_HEIGHT,
                             self.__get_cell_color__(from_row_col))
        self.turtle.penup()
        self.turtle.showturtle()
        self.turtle.color("black")
        self.turtle.setpos(x + MazeGraphics.CELL_WIDTH / 2, y - MazeGraphics.CELL_HEIGHT / 2)

        move_dir = self.get_move_dir(from_row_col, to_row_col)
        self.turtle.setheading(heading_dir[move_dir])
        self.screen.delay(40)
        self.turtle.speed(3)
        if move_dir == 0 or move_dir == 2:
            self.turtle.forward(MazeGraphics.CELL_HEIGHT)
        else:
            self.turtle.forward(MazeGraphics.CELL_WIDTH)
        self.turtle.pendown()

    @staticmethod
    def get_move_dir(from_row_col, to_row_col):
        offsets = [(-1, 0), (0, -1), (1, 0), (0, 1)]  # offset of one step toward [north, west, south, east]
        offset = (to_row_col.x - from_row_col.x, to_row_col.y - from_row_col.y)
        return offsets.index(offset)

    def __draw_text__(self, x, y, text_content):
        self.turtle.penup()
        self.turtle.setpos(x, y)
        self.turtle.color("black")
        self.turtle.write(text_content, font=("Arial", 13, "bold"))
        self.turtle.pendown()

    def __get_cell_color__(self, row_col):
        if row_col in self.path:
            return "green"
        else:
            return self.__get_cell_node__(row_col).get_cell_color()

    def __draw_square__(self, x, y, width, height, cell_color="red"):
        self.turtle.penup()
        self.turtle.setpos(x, y)
        self.turtle.setheading(0)
        self.turtle.pencolor("pink")
        self.turtle.pendown()
        # draw border
        for i in range(4):
            if i % 2 == 0:
                self.turtle.forward(width)
            else:
                self.turtle.forward(height)
            self.turtle.right(90)
        # fill inner area
        self.turtle.setpos(x+1, y+1)
        self.turtle.fillcolor(cell_color)
        self.turtle.begin_fill()
        for i in range(4):
            if i % 2 == 0:
                self.turtle.forward(width-1)
            else:
                self.turtle.forward(height-1)
            self.turtle.right(90)
        self.turtle.end_fill()

    def run_main_loop(self):
        turtle.mainloop()

if __name__ == "__main__":
    maze = MazeGraphics()
    # if len(sys.argv) > 1:
    #     file_name = sys.argv[1]
    # else:
    #     file_name = raw_input("input the path of the maze file to read:")
    file_name = "map4.txt"
    maze.read_from_file(file_name)
    path = maze.solve_maze()
    print('found path?', str(path))
    maze.run_main_loop()

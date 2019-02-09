#!/usr/bin/env python
# encoding: utf-8

import inspect
import turtle
import collections

"""
可视化程序调用堆栈程序
Author: wangdingqiao    http://blog.csdn.net/wangdingqiaoit
Date:2017-12-16
"""

class Point(collections.namedtuple('Point', 'x y')):
    __slots__ = ()

    def __str__(self):
        return "(" + str(self.x) + "," + str(self.y) + ")"


class StackFrameNode(object):
    def __init__(self, file_name, event, function_name, line_no, call_args, return_values=None):
        self.file_name = file_name
        self.event = event
        self.function_name = function_name
        self.line_no = line_no
        self.call_args = call_args
        self.return_values = return_values

    def __str__(self):
        arg_text = ",".join([(str(x) + "="+str(y)) for x, y in self.call_args.items()])
        return_value_text = ""
        if self.return_values:
            if self.return_values is list:
                return_value_text = ",".join([str(val) for val in self.return_values])
            else:
                return_value_text = str(self.return_values)
        if self.event == "call":
            return str(self.event) + "  " + str(self.file_name) + "(" + str(self.line_no) + ") " \
                + self.function_name + "(" + arg_text + ")"
        elif self.event == "return":
            return str(self.event) + " " + str(return_value_text) + "  " \
                   + str(self.file_name) + "(" + str(self.line_no) + ") " \
                   + self.function_name + "(" + arg_text + ")"

    def __repr__(self):
        return self.__str__()


class StackFrameDrawer(object):
    MAX_STACK_DEEP = 12
    CELL_WIDTH = 750
    CELL_HEIGHT = 50

    def __init__(self):
        self.screen = turtle.Screen()
        self.screen.title("Maze Program")
        self.screen.setup(1000, 700, startx=0, starty=0)
        self.draw_start_pos = Point(-400, -StackFrameDrawer.MAX_STACK_DEEP / 2 * StackFrameDrawer.CELL_HEIGHT + 10)
        self.screen.bgcolor("orange")
        self.turtle = turtle.Turtle(shape="turtle")
        self.turtle.pensize(3)
        self.turtle.shapesize(2, 2)
        self.__draw_text__(self.draw_start_pos.x,
                           self.draw_start_pos.y - 30, "Call Stack Of Program(By Wangdingqiao 2017)")

    def push_node(self, frame, index):
        if index > StackFrameDrawer.MAX_STACK_DEEP:
            print('function call stack deep exceed max deep can draw.', index)
            return
        x, y = self.draw_start_pos.x, self.draw_start_pos.y + StackFrameDrawer.CELL_HEIGHT * index
        self.__draw_node__(frame, x, y)

    def pop_node(self, index):
        x, y = self.draw_start_pos.x, self.draw_start_pos.y + StackFrameDrawer.CELL_HEIGHT * index
        self.turtle.hideturtle()
        self.__clear_square__(x, y, StackFrameDrawer.CELL_WIDTH, StackFrameDrawer.CELL_HEIGHT)
        self.turtle.showturtle()

    def __draw_node__(self, frame_node, x, y):
        self.turtle.hideturtle()
        if frame_node.event == "call":
            self.__draw_square__(x, y, StackFrameDrawer.CELL_WIDTH, StackFrameDrawer.CELL_HEIGHT, "red")
            text_color = "blue"
        else:
            self.__draw_square__(x, y, StackFrameDrawer.CELL_WIDTH, StackFrameDrawer.CELL_HEIGHT, "gray")
            text_color = "green"
        self.__draw_text__(x + 10, y + StackFrameDrawer.CELL_HEIGHT / 4, str(frame_node), text_color)
        self.turtle.showturtle()

    def __draw_text__(self, x, y, text_content, color="black"):
        self.turtle.penup()
        self.turtle.setpos(x, y)
        self.turtle.color(color)
        self.turtle.write(text_content, font=("Arial", 13, "bold"))
        self.turtle.pendown()

    def __draw_square__(self, x, y, width, height, square_color="black"):
        self.turtle.penup()
        self.turtle.setpos(x, y)
        self.turtle.setheading(90)
        self.turtle.pencolor(square_color)
        self.turtle.setpos(x, y)
        self.turtle.pendown()
        for i in range(4):
            if i % 2 == 0:
                self.turtle.forward(height)
            else:
                self.turtle.forward(width)
            self.turtle.right(90)

    def __clear_square__(self, x, y, width, height):
        self.turtle.penup()
        self.turtle.setpos(x, y)
        self.turtle.setheading(90)
        self.turtle.pencolor("black")
        self.turtle.setpos(x, y)
        self.turtle.pendown()
        self.turtle.fillcolor("orange")
        self.turtle.begin_fill()
        for i in range(4):
            if i % 2 == 0:
                self.turtle.forward(height - 1)
            else:
                self.turtle.forward(width - 1)
            self.turtle.right(90)
        self.turtle.end_fill()


class DebugHelper(object):
    FRAME_STACK = []
    FRAME_DRAWER = StackFrameDrawer()
    SHOW_MODE = "gui"

    @staticmethod
    def init():
        DebugHelper.FRAME_STACK = []

    @staticmethod
    def trace_calls(frame, event, arg):
        if not inspect:
            return
        if event == 'return' or event == "call":
            my_frame = inspect.stack()[1]
            frame_object, file_name, line_no, function_name, code_context, index = my_frame
            arg_info = inspect.getargvalues(frame_object)
            args, varargs, keywords, local_var = arg_info
            if event == "return":
                DebugHelper.pop_stack()
                DebugHelper.push_stack(StackFrameNode(
                    file_name, event, function_name, line_no, local_var, arg))
                DebugHelper.show_stack()
                DebugHelper.pop_stack()
                if not DebugHelper.FRAME_STACK:
                    DebugHelper.show_stack()
            else:
                DebugHelper.push_stack(StackFrameNode(
                    file_name, event, function_name, line_no, local_var))
                DebugHelper.show_stack()
        return DebugHelper.trace_calls

    @staticmethod
    def push_stack(frame_node):
        push_index = len(DebugHelper.FRAME_STACK)
        DebugHelper.FRAME_STACK.append(frame_node)
        if DebugHelper.SHOW_MODE == "gui":
            DebugHelper.FRAME_DRAWER.push_node(frame_node, push_index)

    @staticmethod
    def pop_stack():
        pop_index = len(DebugHelper.FRAME_STACK) - 1
        DebugHelper.FRAME_STACK.pop()
        if DebugHelper.SHOW_MODE == "gui":
            DebugHelper.FRAME_DRAWER.pop_node(pop_index)

    @staticmethod
    def show_stack():
        if not DebugHelper.SHOW_MODE == "gui":
            DebugHelper.print_stack()

    @staticmethod
    def print_stack():
        print('[\n')
        for x in reversed(DebugHelper.FRAME_STACK):
            print(str(x) + "\n")
        print("]\n")
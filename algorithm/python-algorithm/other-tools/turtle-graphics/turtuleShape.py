#!/usr/bin/python
# -*- coding: UTF-8 -*-

import turtle


def draw_square(width, height):
    win = turtle.Screen()
    win.bgcolor("blue")
    obj = turtle.Turtle()
    obj.pencolor("white")
    obj.pensize(10)
    obj.down()
    obj.forward(width)
    obj.right(90)
    obj.forward(height)
    obj.right(90)
    obj.forward(width)
    obj.right(90)
    obj.forward(height)
    win.exitonclick()


def draw_square_2(width, height):
    win = turtle.Screen()
    win.bgcolor("blue")
    obj = turtle.Turtle(shape="turtle")
    obj.pencolor("white")
    obj.pensize(10)
    for i in range(4):
        if i % 2:
            obj.forward(height)
        else:
            obj.forward(width)
        obj.right(90)
    win.exitonclick()


def draw_circle(radius, degree=360):
    win = turtle.Screen()
    win.bgcolor("blue")
    obj = turtle.Turtle(shape="turtle")
    obj.pencolor("white")
    obj.pensize(10)
    obj.penup()
    obj.hideturtle()
    obj.speed(1)
    obj.setpos(0, -radius)
    obj.showturtle()
    obj.pendown()
    obj.circle(radius, degree, 100)
    print(obj.position())
    win.exitonclick()


if __name__ == "__main__":
    draw_circle(100)

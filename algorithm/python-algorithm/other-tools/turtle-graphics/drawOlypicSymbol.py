#!/usr/bin/python
# -*- coding: UTF-8 -*-

"""
来自: http://www.blog.pythonlibrary.org/2012/08/06/python-using-turtles-for-drawing/
代码有调整
"""

import turtle


class MyTurtle(turtle.Turtle):
    """"""

    def __init__(self):
        """Turtle Constructor"""
        self.screen = turtle.Screen()
        self.screen.bgcolor("lightgrey")
        self.turtle = turtle.Turtle(shape="turtle")
        self.turtle.pensize(3)

    def draw_circle(self, x, y, color, radius=50):
        """
        Moves the turtle to the correct position and draws a circle
        """
        self.turtle.penup()
        self.turtle.setposition(x, y)
        self.turtle.pendown()
        self.turtle.color(color)
        self.turtle.pensize(10)
        self.turtle.circle(radius)

    def draw_olympic_symbol(self):
        """
        Iterates over a set of positions to draw the Olympics logo
        """
        positions = [(0, 0, "blue"), (-120, 0, "purple"), (60, 60, "red"),
                     (-60, 60, "yellow"), (-180, 60, "green")]
        for x, y, color in positions:
            self.draw_circle(x, y, color)

        self.draw_text()

    def draw_text(self):
        """
        Draw text to the screen
        """
        self.turtle.penup()
        self.turtle.setposition(-150, -30)
        self.turtle.setheading(0)
        self.turtle.pendown()
        self.turtle.color("black")
        self.turtle.write("Games of the Tokyo Olympic 2020", font=("Arial", 16, "bold"))


if __name__ == "__main__":
    t = MyTurtle()
    t.draw_olympic_symbol()
    turtle.mainloop()

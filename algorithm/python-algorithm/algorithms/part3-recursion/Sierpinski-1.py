#!/usr/env/python
# _*_ coding: utf-8 _*_

"""
sierpinski triangle
ref-link:
[1] https://en.wikipedia.org/wiki/Sierpinski_triangle
[2] http://interactivepython.org/runestone/static/pythonds/Recursion/pythondsSierpinskiTriangle.html

The procedure for drawing a Sierpinski triangle by hand is simple.
1.Start with a single large triangle.
2.Divide this large triangle into four new triangles by connecting the midpoint of each side.
3.Ignoring the middle triangle that you just created, apply the same procedure to each of the three corner triangles.
Each time you create a new set of triangles, you recursively apply this procedure to the three smaller corner triangles.
"""

import turtle
import math
import time


class SierpinskiTriangle(object):
    def __init__(self, points):
        self.points = points

    def get_sub_triangles(self):
        sub_triangles = []
        point_index = [0, 1, 2]
        for i in range(3):
            points = [self.points[i]]
            for x in point_index:
                if x != i:
                    points.append(self.get_mid_point(self.points[i], self.points[x]))
            sub_triangles.append(SierpinskiTriangle(points))
        return sub_triangles

    @staticmethod
    def get_mid_point(pt1, pt2):
        return (pt1[0] + pt2[0]) / 2.0, (pt1[1] + pt2[1]) / 2.0

    def get_points(self):
        return self.points


class DrawSierpinski(object):

    def __init__(self, split_time):
        self.split_time = split_time

        self.screen = turtle.Screen()
        self.screen.bgcolor("gray")
        self.screen.setup(width=1.0, height=1.0)
        self.turtle = turtle.Turtle(shape="turtle")
        self.turtle.pensize(3)
        self.turtle.speed(10)
        self.init_triangle = SierpinskiTriangle([(-400, -200 * math.sqrt(3)), (400, - 200 * math.sqrt(3)), (0, 200 * math.sqrt(3))])

    def draw(self):
        start = time.time()
        triangles = self.prepare_triangles()
        for triangle in triangles:
            self.draw_triangle(triangle.get_points())
        end = time.time()
        print("draw elapsed time= ", end - start)
        self.screen.exitonclick()

    def draw_triangle(self, points):
        self.turtle.hideturtle()
        self.turtle.penup()
        self.turtle.setpos(points[0][0], points[0][1])
        self.turtle.showturtle()
        self.turtle.pendown()
        self.turtle.goto(points[1][0], points[1][1])
        self.turtle.goto(points[2][0], points[2][1])
        self.turtle.goto(points[0][0], points[0][1])

    def prepare_triangles(self):
        triangles = [self.init_triangle]
        self.get_sub_triangle(self.init_triangle, 1, triangles)
        return triangles

    def get_sub_triangle(self, triangle, split_time, triangle_list):
        if split_time >= self.split_time:
            return
        sub_triangles = triangle.get_sub_triangles()
        triangle_list.extend(sub_triangles)
        for _triangle in sub_triangles:
            self.get_sub_triangle(_triangle, split_time + 1, triangle_list)


if __name__ == "__main__":
    DrawSierpinski(8).draw()


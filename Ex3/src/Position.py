from random import random, uniform


class Position:
    def __init__(self, x=None, z=None, y = None):
        self.x = x if x else uniform(0, 100)
        self.y = y if y else uniform(0, 100)
        self.z = 0.0

    def __str__(self) -> str:
        return f"{self.x},{self.y},{self.z}"

    def pos_x(self):
        return self.x

    def pos_y(self):
        return self.y

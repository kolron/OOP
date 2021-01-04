from random import random, uniform

u = uniform(1, 100)
print(u)
class Position:
    def __init__(self, x, z, y = None):
        self.x = x if x else uniform(0, 60)
        self.y = y if y else uniform(0, 60)
        self.z = z if z else uniform(0, 60)

    def __str__(self) -> str:
        return f"{self.x},{self.y},{self.z}"
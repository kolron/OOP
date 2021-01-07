from Position import Position


class NodeData:
    def __init__(self, key, tag=0, w=0, info="", pos: Position = None):
        self.key = key
        self.tag = tag
        self.w = w
        self.info = info
        self.pos = pos
        if self.pos is None:
            self.pos = Position()

    def get_x(self) -> float:
        ret = self.pos.pos_x()
        print (ret)
        return ret

    def get_y(self) -> float:
        ret = self.pos.pos_y()
        print(ret)
        return ret

    def get_key(self):
        return self.key

    def get_weight(self):
        return self.w

    def get_info(self):
        return self.info

    def get_tag(self):
        return self.tag

    def set_weight(self, w):
        self.w = w

    def set_info(self, info):
        self.info = info

    def set_tag(self, tag):
        self.tag = tag

    def __str__(self):
        return "Node[" + str(self.get_key()) + "]"


node = NodeData(1,0,0,"", pos = Position(1.0, 1.0, 0.0))
print("1st line:", node.pos)
print("2nd line:")
node.get_x()
print("3rd line:")
node.get_y()






class NodeData:
    def __init__(self, key, tag=0, w=0, info=""):
        self.key = key
        self.tag = tag
        self.w = w
        self.info = info

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




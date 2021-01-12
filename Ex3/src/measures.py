import shutil

from GraphAlgo import GraphAlgo
import time


ag1 = GraphAlgo()
ag1.load_from_json("C:\\Users\\kolsk\\PycharmProjects\\OOP\\Ex3\\data\\G_10_80_0.json")
start = time.time()
ag1.connected_components()
end = time.time()
total = end - start
print(total)





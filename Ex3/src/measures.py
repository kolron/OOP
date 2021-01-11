from GraphAlgo import GraphAlgo
import time


ag1 = GraphAlgo()
# ag2 = GraphAlgo()
# ag3 = GraphAlgo()
# ag4 = GraphAlgo()
# ag5 = GraphAlgo()
# ag6 = GraphAlgo()
#
# ag2.load_from_json("C:/Users/Ron/PycharmProjects/OOP/Ex3/data/G_100_800_0.json")
# ag3.load_from_json("C:/Users/Ron/PycharmProjects/OOP/Ex3/data/G_1000_8000_0.json")
# ag4.load_from_json("C:/Users/Ron/PycharmProjects/OOP/Ex3/data/G_10000_80000_0.json")
# ag5.load_from_json("C:/Users/Ron/PycharmProjects/OOP/Ex3/data/G_20000_160000_0.json")
# ag6.load_from_json("C:/Users/Ron/PycharmProjects/OOP/Ex3/data/G_30000_240000_0.json")
#
#
# ag2.save_to_json("J_G_100_800_0")
# ag3.save_to_json("J_G_1000_8000_0")
# ag4.save_to_json("J_G_10000_80000_0")
# ag5.save_to_json("J_G_20000_160000_0")
# ag6.save_to_json("J_G_30000_240000_0")
ag1.load_from_json("C:/Users/Ron/PycharmProjects/OOP/Ex3/data/G_10_80_0.json")
ag1.plot_graph()
import random
NAME = ['Aizawa', 'Hanzawa', 'Boob', 'Chuujie', 'Manual', 'Cherlmegne', 'test1', 'xuan', 'Paris', 'lalala', 'hahaha',
        'boomboom', 'test2', 'rice', 'sushi', 'testuser', 'admin', 'rrot', 'riot', 'root', 'yedsfjslfj', 'zhengliang']

if __name__ == '__main__':
    _length = len(NAME)
    for _ in range(10):
        select1 = NAME[random.randint(0, _length-1)]
        select2 = NAME[random.randint(0, _length-1)]
        select3 = NAME[random.randint(0, _length-1)]
        print('{},{},{}@sjtu.edu.cn'.format(select1, select1 + select2, select3))

import matplotlib.pyplot as plt
import numpy as np
import random

if __name__ == '__main__':
    x = np.array([70, 120, 50, 60, 1, 100, 90, 130, 105, 108])
    y = np.array([6, 8, 5, 1, 2, 10, 14, 14, 9, 10])

    std_x = (x - x.mean()) / x.std()
    std_y = (y - y.mean()) / y.std()
    print('x:', std_x)
    print('y:', std_y)

    sse_count = 0
    for i in range(10):
        a = random.random()
        b = random.random()
        y_p = a + b * std_x
        sse = np.square(y_p - std_y).sum()
        print('Trial {} SSE: {}'.format(i, sse))
        sse_count += sse
    print('Avg: {}'.format(sse_count / 10))

import matplotlib.pyplot as plt
import numpy as np

if __name__ == '__main__':
    x = [70, 120, 50, 60, 1, 100, 90, 130, 105, 108]
    y = [6, 8, 5, 1, 2, 10, 14, 14, 9, 10]
    slope, intercept = np.polyfit(x, y, 1)
    print('y={}m + {}'.format(slope, intercept))

    xn = np.linspace(0, 200)
    yn = np.polyval([slope, intercept], xn)

    plt.plot(x, y, 'or')
    plt.xlim(0)
    plt.ylim(0)
    plt.plot(xn, yn)
    plt.xlabel("Protein(g)")
    plt.ylabel("Sugar(g)")
    plt.savefig('../hw6.assets/ex1.1.png')

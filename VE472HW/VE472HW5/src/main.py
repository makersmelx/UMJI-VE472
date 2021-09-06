# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

import numpy as np
from sklearn.decomposition import PCA
from sklearn.linear_model import LinearRegression

if __name__ == '__main__':
    # sensor_mat = np.mat(np.loadtxt(open("cinema_sensors/sensors2.csv", "rb"), delimiter=','))[:, :1000]
    # pca = PCA()
    # pca.fit(sensor_mat)
    #
    # target = 0.9 * sum(pca.explained_variance_ratio_)
    # for N in range(1, 1001):
    #     pca = PCA(n_components=N)
    #     pca.fit(sensor_mat)
    #     ret = sum(pca.explained_variance_ratio_)
    #     if ret > target:
    #         print(N)
    #         break

    sensor_mat = np.mat(np.loadtxt(open("cinema_sensors/sensors2.csv", "rb"), delimiter=','))
    y = sensor_mat[:, -1]
    pca = PCA(n_components=3)
    x = pca.fit_transform(sensor_mat[:, :1000])
    print("data shape:", x.shape, y.shape)
    reg = LinearRegression().fit(x, y)
    print("Rˆ2:", reg.score(x, y))
    print("coefficient:", reg.coef_.flatten())
    print("intercept:", reg.intercept_.flatten())

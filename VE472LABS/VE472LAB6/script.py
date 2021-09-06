from pyspark.sql import SparkSession
from pyspark.ml.regression import LinearRegression
def main():
    maxIter = 10
    regParam = 0.3
    elasticNetParam = 0.8

    spark = SparkSession \
        .builder \
        .appName('Lab 6') \
        .getOrCreate()

    df = spark.read.load("1987.csv", format="csv", sep=",", inferSchema="true", header="true")

    lr = LinearRegression(maxIter=maxIter, regParam=regParam, 
        elasticNetParam=elasticNetParam)

    lrModel = lr.fit(df) # df is a dataframe 

    print("Coefficients: %s" % str(lrModel.coefficients))
    print("Intercept: %s" % str(lrModel.intercept))

    trainingSummary = lrModel.summary
    print("numIterations: %d" % trainingSummary.totalIterations)
    print("objectiveHistory: %s" % str(trainingSummary.objectiveHistory))
    trainingSummary.residuals.show()
    print("RMSE: %f" % trainingSummary.rootMeanSquaredError)
    print("r2: %f" % trainingSummary.r2)
main()
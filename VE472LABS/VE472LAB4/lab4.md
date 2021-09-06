VE472 G2 Lab4

## Ex. 1. Drill and Spark installation 

### Drill on Yarn

We established a cluster of 3 nodes (we don't have enough physical machine resources).

![img](https://sjtu.feishu.cn/space/api/box/stream/download/asynccode/?code=MmExNTEwY2YwMTkzOGJjYTg2MjdlYjg0ODRhMjU3OTVfdFd2WFo2NXhROFZRTVJTaWl1S2RNdlo1NzZ1WGVYUWxfVG9rZW46Ym94Y25JZlZaNm5qRnJhbkpNRHBWeEp0ajJiXzE2MjUyODI4MjY6MTYyNTI4NjQyNl9WNA)

![img](https://sjtu.feishu.cn/space/api/box/stream/download/asynccode/?code=MjIyMTFjNjVkYTViYmI2NGJhNTQ1ZTlkZjYwMzE4NGRfMWJuVzlYaURJNEt5bDZPN2RzUlppTXRnT0VlN1V6YjdfVG9rZW46Ym94Y241bEFTdmFUeTVhdkZHVWxKbWZjdTdkXzE2MjUyODI4MjY6MTYyNTI4NjQyNl9WNA)

As for Spark, no extra configuration is needed. Just download Spark and unzip it. We are going to use PySpark in the following questions as we are not familiar with Scala.

## Ex. 2. Simple Drill queries

### 2.1

We generate a file of more than 5GB and upload it to HDFS.

![img](https://sjtu.feishu.cn/space/api/box/stream/download/asynccode/?code=M2UyN2RlZWU0ZDJhNDQ2MDRmOGMzYmMyY2NiNDhjNThfZHNoWTBSeE5LSWxPWjZzN21mdldudnBnZmw5ZkU0YTRfVG9rZW46Ym94Y25QQjk0QVhNV1NiSHZ2NEN5dW41bmloXzE2MjUyODI4MjY6MTYyNTI4NjQyNl9WNA)

### 2.2 (a)

Run the query below to get the **student with the lowest score** 

```
select data.name,cast(data.score as int) as score

from (

    select name,score

    from (

        select

            columns[0] as name,

            columns[1] as id,

            columns[2] as score

        from hdfs.root.`0.csv` 

    )

) as data

order by score

limit 1
```

The answer is:

![img](https://sjtu.feishu.cn/space/api/box/stream/download/asynccode/?code=MzcxOTE1NzdlOGFhMWRkNTAwYjJkYWMzMWM5MTZhY2FfWjJHNDMweWw0MTE1YzZOQTU5NHQ5bkNPaFhLYXZ6NG9fVG9rZW46Ym94Y255SERQTEhHNDN2SENGQ3ZWOFIyd0lmXzE2MjUyODI4MjY6MTYyNTI4NjQyNl9WNA)

![img](https://sjtu.feishu.cn/space/api/box/stream/download/asynccode/?code=MmQ1NWNlZWIwYmYwMWFhYWI5NWQxOGI5NDNkM2JiMjZfejFoT0ZoNXRpZDBmSE9QRDBzSGdOYjdMR3p4WWdtdE9fVG9rZW46Ym94Y25VV3RDaUw4OExsVXIyMjl5c2twdjllXzE2MjUyODI4MjY6MTYyNTI4NjQyNl9WNA)

### 2.2 (b)

Run the query below to get the **student with the highest average score**

```
select data.name,avg(cast(data.score as int)) as avgScore

from (

    select name,score

    from (

        select

            columns[0] as name,

            columns[1] as id,

            columns[2] as score

        from hdfs.root.`0.csv` 

    )

) as data

group by data.name

order by avgScore desc

limit 1
```

The answer is

![img](https://sjtu.feishu.cn/space/api/box/stream/download/asynccode/?code=ZTc0OTQ2N2U0Y2U3ODljNzZiZjkwOTQzZmU4NjY5MmZfMGhYcko0OFRBVU0wVnY1aXJaOUZOYkFkc1lnVGU5emRfVG9rZW46Ym94Y25mVEFORDluOG9FYmh5ZmVQWExiQmFjXzE2MjUyODI4MjY6MTYyNTI4NjQyNl9WNA)

![img](https://sjtu.feishu.cn/space/api/box/stream/download/asynccode/?code=YThkNjgyNGRhYzYxMzZlZGRlMTM3NDBjMzUyNzI2MTlfRkgyUnFKTkN6d0p6NEk3RHVpTUFuOWZROTJVTDlKMDlfVG9rZW46Ym94Y25JTmRlM1dHS2ZiTUdleTBJSVpIVG9mXzE2MjUyODI4MjY6MTYyNTI4NjQyNl9WNA)

### 2.3

Run the query below to get the **median of all the scores**  

```
select score, idAsc

from (

    select columns[2] as score, 

    row_number() over (order by cast(columns[2] as int)) as idAsc,

    (select count(*) from hdfs.root.`0.csv`) as cnt

    from hdfs.root.`0.csv` 

)

where (mod(cnt, 2) = 1 and idAsc = trunc((cnt + 1) / 2)) or (mod(cnt, 2) = 0 and idAsc in (trunc(cnt / 2), trunc(cnt / 2) - 1))
```

The answer is:

![img](https://sjtu.feishu.cn/space/api/box/stream/download/asynccode/?code=NzY2OWI4YWRhNDFiMDRmNDNmYTI3MTk0ZGRjOThhODVfNThaTUJyTnQyeU55cUdIM0dqNlp3NzlYNzdWMEcydE5fVG9rZW46Ym94Y25GMk1lWUltUkdUSkIxckF4NjY4U3liXzE2MjUyODI4MjY6MTYyNTI4NjQyNl9WNA)

![img](https://sjtu.feishu.cn/space/api/box/stream/download/asynccode/?code=ZTVjNGVjY2I2NDgzZDNhNjA1ZTgxNDBhYWE3NjRmMDhfcERaY2xsWTI1WWdjMmhtSzlFTXZrUGJPTkQyTU9ZM25fVG9rZW46Ym94Y252bW9VVU1QY0VTN1pWWmdCckNyM29oXzE2MjUyODI4MjY6MTYyNTI4NjQyNl9WNA)

## Ex. 3. Simple Spark

### 3.1-3.3

We write a python file and name it `ex3.py`:

```
import pyspark.sql as sql



spark = sql.session.SparkSession.builder.master("yarn").appName("test").getOrCreate()

grade_rdd = spark.read.text("hdfs://hadoop-master:9000/user/hadoop/0.csv").rdd

grade_pairs = grade_rdd.flatMap(lambda row: [tuple(row.value.split(",")[1:3])])

max_grade = grade_pairs.reduceByKey(lambda x, y: max([x, y]))

max_grade.collect()
```

We then submit this file to spark in cluster mode:

```
spark-submit --master yarn --deploy-mode cluster --driver-memory 2g --executor-memory 2g --executor-cores 1 /home/hadoop/ex3.py
```

The job is then submitted to YARN and start as an application:

![img](https://sjtu.feishu.cn/space/api/box/stream/download/asynccode/?code=OTA0NmU4NTJjODlmMmFkOTkyNGY1YzMzMzg3ZDQ3Y2RfcGJhU21PV3dWdzIwOHRIaDVPMnZTQ0VsZ1dwbkxYbXpfVG9rZW46Ym94Y25BT2tnN0pTWnFRT0hhWlREVTVMa0ZmXzE2MjUyODI4MjY6MTYyNTI4NjQyNl9WNA)

This job takes 9 minutes and 3 seconds to complete.

### 3.4

![img](https://sjtu.feishu.cn/space/api/box/stream/download/asynccode/?code=YTQ4OWVmZTk2MGVmM2JlMTFmODljMGZhODU3MzViODlfNjFTeHRUUzRoOG1ObmExWUdlbWRWeWRGYzhvMmhGUUlfVG9rZW46Ym94Y25aaTliTm56VFZyd3YzdnBOS1JINkRjXzE2MjUyODI4MjY6MTYyNTI4NjQyNl9WNA)



| Row count | MapReduce(s) | Spark(s) |
| --------- | ------------ | -------- |
| 100       | 3.41         | 13       |
| 1000      | 3.71         | 13       |
| 10000     | 4.07         | 15       |
| 100000    | 4.69         | 12       |
| 1000000   | 10.39        | 20       |
| 10000000  | 70.94        | 46       |
| 187505350 | 1373.96      | 543      |

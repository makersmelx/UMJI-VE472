#!/usr/bin/env python3
import csv
import sys

##f = open('score.csv', 'rt')
csv_reader = csv.reader(sys.stdin)

for line in csv_reader:
    print(line[1]+'\t'+line[2])

#f.close()

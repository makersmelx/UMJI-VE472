#!/usr/bin/env python3
import sys

line = sys.stdin.readline()
current_ID = None
ID = None
list = []
while line:
    sline = line.split()
    ID = sline[0]
    if current_ID == ID:
        list.append(int(sline[1]))
    else:
        current_ID =ID
        if list:
            print(max(list))
        list.clear()
        list.append(int(sline[1]))
    line=sys.stdin.readline()

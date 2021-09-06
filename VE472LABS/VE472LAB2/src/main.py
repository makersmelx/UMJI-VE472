# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

import csv
import random

def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+F8 to toggle the breakpoint.


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    f=open("/home/hadoop/Desktop/VE472/l2-names/firstnames.txt", 'r')
    l=open("/home/hadoop/Desktop/VE472/l2-names/lastnames.txt", 'r')
    ##headers = ['name', 'ID', 'grade']




    first = f.readline()
    first = first[:-1]
    last = l.readline()
    last = last[:-1]
    row = []
    rows = []
    while first and last:
        row.append(first+" "+last)
        row.append(''.join(str(random.choice(range(10))) for _ in range(10)))
        for i in range(random.randint(1, 10000)):
            r = list(row)
            r.append(str(random.randint(0, 100)))
            rows.append(r)

        row.clear()
        first = f.readline()
        first = first[:-1]
        last = l.readline()
        last = last[:-1]

    random.shuffle(rows)
    print(rows)
    w = open('score-10000.csv', 'w', encoding='utf8', newline='')
    writer = csv.writer(w)
    writer.writerows(rows)

    f.close()
    l.close()
    w.close()
    print_hi('PyCharm')

# See PyCharm help at https://www.jetbrains.com/help/pycharm/

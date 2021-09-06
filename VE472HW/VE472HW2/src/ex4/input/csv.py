import sys
import os
import random
import shutil

if __name__ == '__main__':
    current_path = os.getcwd()
    ret_dir = os.path.join(current_path, 'results')
    if os.path.exists(ret_dir):
        shutil.rmtree(ret_dir)
    os.mkdir(ret_dir)
    pagination = int(sys.argv[1])
    students_number = int(sys.argv[2])
    grade_count = int(sys.argv[3])
    firstnames = []
    lastnames = []
    with open('l2-names/firstnames.txt', 'r') as file:
        buffer = file.read().splitlines()
        for line in buffer:
            firstnames.append(line)
    with open('l2-names/lastnames.txt', 'r') as file:
        buffer = file.read().splitlines()
        for line in buffer:
            lastnames.append(line)

    students = {}
    for _ in range(students_number):
        id = random.randint(1000000000, 9999999999)
        while id in students:
            id = random.randint(1000000000, 9999999999)
        students[id] = '{} {}'.format(random.choice(
            firstnames), random.choice(lastnames))

    ids = list(students.keys())
    for count in range(pagination):
        with open(os.path.join(ret_dir, "{}.csv".format(count)), 'w+') as file:
            for i in range(int(grade_count/pagination)):
                id = random.choice(ids)
                file.write('{},{},{}\n'.format(
                    students[id], id, random.randint(0, 100)))

hadoop jar $PYTHON \
-mapper  /home/hadoop/Desktop/VE472/l1/mapper.py \
-reducer  /home/hadoop/Desktop/VE472/l1/reducer.py \
-input /lab2/score-5000.csv \
 -output /lab2/output

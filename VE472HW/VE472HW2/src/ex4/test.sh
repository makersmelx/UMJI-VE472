#/usr/local/zsh
csv_number=10
for((i=0;i<${csv_number};i++));do
    diff input/results/${i}.csv output/newResults/${i}.csv
done
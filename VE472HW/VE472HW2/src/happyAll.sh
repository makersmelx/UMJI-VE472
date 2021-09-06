#/usr/local/bin/zsh
#/bin/bash
root_dir=$(pwd)
cd "${root_dir}/ex4/input"
echo "========================"
echo "csv generate time"
echo "========================"
python3 csv.py 10 1000 10000
echo "========================"
echo "Java time"
echo "========================"

cd "${root_dir}/ex4"
mvn compile
mvn exec:java -Dexec.mainClass="Main"
echo "========================"
echo "Diff time"
echo "========================"
"${root_dir}/ex4/test.sh"
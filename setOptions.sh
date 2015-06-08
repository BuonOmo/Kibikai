rm options.txt
touch options.txt
for line in $*
do
echo "$line" >> options.txt
done

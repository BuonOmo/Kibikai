cat options.txt | awk 'NR == 1 {print;}' 
cat options.txt | awk 'NR == 2 {print;}' 

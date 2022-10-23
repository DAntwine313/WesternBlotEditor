# Monochrome
# define variables
# VAR1 = input file path
# VAR2 = output file path

convert "$VAR1" -monochrome "$VAR2"
printf "\nconvert %s -monochrome %s" "$VAR1" "$VAR2" >> output.sh
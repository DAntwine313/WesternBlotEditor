# Monochrome
# define variables
# VAR1 = file path

convert "$VAR1" -monochrome "$VAR1"
printf "\nconvert %s -monochrome %s" "$VAR1" "$VAR1" >> output.sh
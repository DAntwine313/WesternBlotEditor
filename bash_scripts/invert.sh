# Invert
# define variables
# VAR1 = file path

convert "$VAR1" +negate "$VAR1"
printf "\nconvert %s -negate %s" "$VAR1" "$VAR1" >> output.sh
# Sigmoidal contrast
# define variables
# VAR1 = input file path
# VAR2 = center/ mid-point
# VAR3 = factor/ contrast
# VAR4 = output file path

convert "$VAR1" -sigmoidal-contrast "$VAR3"x"$VAR2" "$VAR4"
printf "\nconvert %s -sigmoidal-contrast %sx%s %s" "$VAR1" "$VAR3" "$VAR2" "$VAR4" >> output.sh
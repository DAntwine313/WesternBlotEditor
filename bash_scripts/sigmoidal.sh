# Sigmoidal contrast
# define variables
# VAR1 = file path
# VAR2 = center/ mid-point
# VAR3 = factor/ contrast

convert "$VAR1" -sigmoidal-contrast "$VAR3"x"$VAR2" "$VAR1"
printf "\nconvert %s -sigmoidal-contrast %sx%s %s" "$VAR1" "$VAR3" "$VAR2" "$VAR1" >> output.sh
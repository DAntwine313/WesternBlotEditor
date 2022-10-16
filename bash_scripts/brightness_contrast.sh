# Brightness_contrast
# define variables
# VAR1 = file path
# VAR2 = brightness level
# VAR3 = contrast level

convert "$VAR1" -brightness-contrast "$VAR2" "$VAR3" "$VAR1"
printf "\nconvert %s -brightness-contrast %s,%s %s" "$VAR1" "$VAR2" "$VAR3" "$VAR1" >> output.sh



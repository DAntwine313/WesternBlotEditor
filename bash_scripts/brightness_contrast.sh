# Brightness/ contrast
# define variables
# VAR1 = input file path
# VAR2 = brightness
# VAR3 = contrast
# VAR4 = output file path

magick "$VAR1" -brightness-contrast "$VAR2" "$VAR3" "$VAR4"
printf "\nconvert %s -brightness-contrast %s,%s %s" "$VAR1" "$VAR2" "$VAR3" "$VAR4">> output.sh

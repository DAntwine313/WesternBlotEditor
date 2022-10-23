# Invert
# define variables
# VAR1 = input file path
# VAR1 = output file path

magick "$VAR1" +negate "$VAR2"
printf "\nconvert %s -negate %s" "$VAR1" "$VAR2" >> output.sh

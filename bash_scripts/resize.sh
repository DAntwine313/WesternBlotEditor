# resize
# define variables
# VAR1 = file path
# VAR2 = width
# VAR3 = height

magick "$VAR1" -resize "$VAR2"x"$VAR3" "$VAR4"
printf "\nmagick %s -resize %sx%s %s" "$VAR1" "$VAR2" "$VAR3" "$VAR4" >> output.sh
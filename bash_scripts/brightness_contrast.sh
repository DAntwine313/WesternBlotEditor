magick "$VAR1" -brightness-contrast "$VAR2", "$VAR3" copy.png
printf "\nconvert %s -brightness-contrast %s%s copy.png", "$VAR1", "$VAR2", "$VAR3" >> output.sh

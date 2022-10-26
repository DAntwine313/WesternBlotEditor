
magick cat.jpg -resize 300x300 cat_1.jpg
convert cat_1.jpg -monochrome cat_1_2.jpg
convert cat_1_2.jpg -brightness-contrast -26,23 cat_1_2_3.jpg
convert cat_1_2_3.jpg -sigmoidal-contrast 4x25 cat_1_2_3_4.jpg
convert cat_1_2_3_4.jpg -negate cat_1_2_3_4_5.jpg
#!/bin/bash

#Removes any temporary files
rm test_* filenames.txt testdata_final.mvg test1.txt test2.txt pleasework2.sh pleasework.txt verbosdata.txt output.txt houghexec.sh

#Canny edge detection algorithm
#Need to provide the user with different options regarding the xsigma+x%+y% so to accomadate gels of different sizes
magick badbandstest.png \
	-canny 0x0.5+5%+20% -write testimage_canny.png \
	-define connected-components:verbose=true \
	-define connected-components:mean-color=true \
	-connected-components 8 -auto-level -depth 8 verboseimage.png > testdata2.txt

#Removes the first line of the verbosedata and then appends the area and position to respective files
awk '{print $2}' testdata2.txt| awk "NR>2" > verbosedata.txt 
grep -Eo "[0-9]+x[0-9]+" verbosedata.txt > verbosedata_geometry.txt
grep -Eo "\+[0-9]+\+[0-9]+" verbosedata.txt > verbosedata_resize.txt

#Extracts bands detected with the canny edge detection algorithm
n=0;
touch testdata_final.mvg
while read line;do
	n=$(($n+1))
	filename=`echo test_"$n"`
	filepng=`echo "$filename".png`
	bounding_box=`echo "$line"`

	magick -size 518x136 -depth 8 -extract $bounding_box \
		badbandstest.png $filepng
	echo "\(" "$filepng" >> filenames.txt
done < verbosedata.txt

#creates a file containing the hough-line algorithm
n=0;
while read line;do
	n=$(($n+1))
	filename=`echo test_"$n"`
	filepng=`echo "$filename".png`
	echo "magick" "$filepng" "\\( +clone -background none -fill red -strokewidth 1 -hough-lines" "$line""+30 -write" "$filename""_line.png \\) -composite" "$filename""_hough.png" >> houghexec.sh
	echo "magick" "$filename""_hough.png -hough-lines" "$line""+30" "$filename""_hough.mvg" >> houghexec.sh 
	echo "cat" "$filename""_hough.mvg >> output.txt" >> houghexec.sh
done < verbosedata_geometry.txt

#Makes the hough-line algorithm file executable
chmod +x houghexec.sh
#Runs the file
./houghexec.sh

#Build the reconstruction algorithm

#Extracts the contents of verbose_geometry.txt and verbose_resize.txt while incorpoating the contents into the algorithm
for a in $(cat verbosedata_geometry.txt);do
    echo "-resize" "$a" "\) -geometry" >> test1.txt 
done

for b in $(cat verbosedata_resize.txt);do   
	echo "$b" "-composite" "\\" >> test2.txt
done

#merges the files together, makes the file executable, and run the executable
paste filenames.txt test1.txt test2.txt > pleasework.txt
sed '1 s/^/magick -size 518x136 xc:black \\\n/' pleasework.txt > pleasework2.sh 
echo "composite.png" >> pleasework2.sh 
chmod +x pleasework2.sh
./pleasework2.sh

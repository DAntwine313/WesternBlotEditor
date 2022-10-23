
#Moves into the directory of the image
# shellcheck disable=SC2164
cd "$VAR1"

#Removes any temporary files or appended files
rm *.tmp
rm test*
rm houghexec*
rm imageReconstruction*

#makes sure bands are white and the background is black.
magick "$VAR2" -channel RGB -negate badbandstest.png

#Canny edge detection algorithm

magick badbandstest.png \
  -canny "$VAR3" -write testimage_canny.png \
  -define connected-components:verbose=true \
  -define connected-components:mean-color=true \
  -connected-components 8 -auto-level -depth 8 verboseimage.png > testdata2.tmp

#Removes the first line of the verbosedata and then appends the area and position to respective files
awk '{print $2}' testdata2.tmp | awk 'NR >2' > verbosedata.tmp
grep -Eo "[0-9]+x[0-9]+" verbosedata.tmp > verbosedata_geometry.tmp
grep -Eo "\+[0-9]+\+[0-9]+" verbosedata.tmp > verbosedata_resize.tmp

#Extracts bands detected with the canny edge detection algorithm
n=0;

while read -r line;do
    n=$((n+1))
    filename=$(echo test_"$n")
    filepng=$(echo "$filename".png)
    bounding_box=$(echo "$line")

    magick -size 518x136 -depth 8 -extract "$bounding_box" \
        badbandstest.png "$filepng"
    echo "\(" "$filepng" >> filenames.tmp
done < verbosedata.tmp

#creates a file containing the hough-line algorithm
n=0;
while read -r line;do
    n=$((n+1))
    filename=$(echo test_"$n")
    filepng=$(echo "$filename".png)
    echo "magick" "$filepng" "\\( +clone -background none -fill red -strokewidth 1 -hough-lines" "$line""+30 -write" "$filename""_line.png \\) -composite" "$filename""_hough.png" >> houghexec.sh
    echo "magick" "$filename""_hough.png -hough-lines" "$line""+30" "$filename""_hough.mvg" >> houghexec.sh
    echo "$filename""_hough.mvg" >> hough_filenames.tmp
done < verbosedata_geometry.tmp

#Makes the hough-line algorithm file executable
chmod +x houghexec.sh
#Runs the file
./houghexec.sh


#Takes the average of the angles generate for each extracted image
while read line; do
    awk '{sum+=$6;n++}END{
        if(sum>0)
        {
            print sum/(n-3)
        }
        else
        {
            print 0
        }
    }' $line >> output2.tmp
done < hough_filenames.tmp

while read line; do
    printf "%.0f\n" "$line" >> output3.tmp
done < output2.tmp


#Outputs the angle need to correct each output to 90
while read -r line;do
    if (($line!=0));then
        y="90"
        x="$(($y-$line))"
        echo "$x" >> rotate.tmp
    else
        echo "0" >> rotate.tmp
    fi
done < output3.tmp



#Build the reconstruction algorithm

#Extracts the contents of verbose_geometry.tmp and verbose_resize.tmp while incorpoating the contents into the algorithm
while read -r a;do
    echo "-resize" "$a" >> test1.tmp
done < verbosedata_geometry.tmp

while read -r b; do
    echo "-rotate" "$b" "-background none \) -geometry" >> test2.tmp
done < rotate.tmp

while read -r c;do
    echo "$c" "-composite" "\\" >> test3.tmp
done < verbosedata_resize.tmp

#merges the files together, makes the file executable, and run the executable
paste filenames.tmp test1.tmp test2.tmp test3.tmp > imageReconstruction.tmp
sed '1 s/^/magick -size 518x136 xc:black \\\n/' imageReconstruction.tmp > imageReconstruction_real.sh
echo "composite.png" >> imageReconstruction_real.sh
chmod +x imageReconstruction_real.sh
./imageReconstruction_real.sh

#removes temporary files
rm *.tmp
rm test_*

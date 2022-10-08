magick testimage.png \
	-canny 0x1+20%+30% -write testimage_canny.png \
	-define connected-components:verbose=true \
	-define connected-components:mean-color=true \
	-connected-components 8 -auto-level -depth 8 verboseimage.png > testdata2.txt
awk '{print $2}' testdata2.txt | awk 'NR>2' > verbosedata.txt 
sed s'/+.*//' verbosedata.txt > verbosedata_geometry.txt
sed -r s'/.([0-9]+)?\x([0-9]+)?//' verbosedata.txt > verbosedata_resize.txt


n=0;
touch testdata_final.mvg
while read line;do
	n=$(($n+1))
	filename=`echo test_"$n"`
	filepng=`echo "$filename".png`
	bounding_box=`echo "$line"`

	magick -size 474x242 -depth 8 -extract $bounding_box \
		testimage.png $filepng
	echo "\(" "$filepng" >> filenames.txt

	magick $filepng \
	\( +clone -background none -fill red -stroke red -strokewidth 1 \
		-hough-lines 9x9+30 -write "$filename"_line.png \) -composite "$filename"_hough.png
	magick "$filename"_hough.png -hough-lines 9x9+30 "$filename"_hough.mvg 
	awk '{print $6}' "$filename"_hough.mvg >> testdata_final.mvg 

done < verbosedata.txt

for a in $(cat verbosedata_geometry.txt);do
    echo "-resize" "$a" "\) -geometry" >> test1.txt 
done

for b in $(cat verbosedata_resize.txt);do   
	echo "$b" "-composite" "\\" >> test2.txt
done


paste filenames.txt test1.txt test2.txt > pleasework.txt
sed '1 s/^/magick -size 474x242 xc:black \\\n/' pleasework.txt > pleasework2.sh 
echo "composite.png" >> pleasework2.sh 
chmod +x pleasework2.sh
./pleasework2.sh

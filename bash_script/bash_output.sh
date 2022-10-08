## most of this pulled from a response on Unix Stack Exchange
## https://unix.stackexchange.com/questions/31414/how-can-i-pass-a-command-line-argument-into-a-shell-script
## essentially just allows us to define however many variables we want
## and then pass them as arguments to the appropriate spot in the script

while getopts "a:b:c:d:e" opt
do
   case "$opt" in
      a ) parameterA="$OPTARG" ;; # level adjustment, black_point
      b ) parameterB="$OPTARG" ;; # level adjustment, white_point
      c ) parameterC="$OPTARG" ;; # gamma adjustment, black_point
      d ) parameterD="$OPTARG" ;; # gamma adjustment, white_point
      e ) parameterE="$OPTARG" ;; # gamma adjustment, gamma
      ? ) helpFunction ;; # Print helpFunction in case parameter is non-existent
   esac
done

## creation of bash output file

exec &> >(tee executable_output.sh)

## turn on debug stream
set -x

echo "## Script created on: $(date)" >> executable_output.sh

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

#turn off stream
set +x
exec 19>&-

## after running, parse the debugging script to reformat final output as shell script
## because sed -i can cause portability options, I used 2 lines to make the changes
## remove first instance of + on each line and space, then remove + again (echo lines)
sed -e 's/^+ //g' -e 's/^++ //g' "executable_output.sh">"executable_output.new"
mv -- "executable_output.new" "executable_output.sh"

## remove first two lines of file
sed 1,2d "executable_output.sh">"executable_output.new"
mv -- "executable_output.new" "executable_output.sh"

## escape out line if starts with "n=", "file", "bounding" (or "read line" and the 7 lines following)
## can execute multiple sed arguments with -e (allows script to be built in many parts) between each find and replace phrase
sed -e 's/^n=.*/## &/' -e 's/^file.*/## &/' -e 's/^bounding.*/## &/' "executable_output.sh">"executable_output.new"
mv -- "executable_output.new" "executable_output.sh"

## remove lines that start with "read" or "set"
sed -e '/^read.*/d' -e '/^set.*/d' "executable_output.sh">"executable_output.new"
mv -- "executable_output.new" "executable_output.sh"

## should be able run executable_output.sh to recreate the original image manipulation
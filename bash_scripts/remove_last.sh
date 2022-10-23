# Remove last
# removes last line from output.sh file

cp output.sh output.sh.tmp
sed '$ d' output.sh.tmp > output.sh
rm -f output.sh.tmp
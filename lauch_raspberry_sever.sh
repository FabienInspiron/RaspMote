cd RaspberryPiServer/
ant clean
ant

cd jar
ant
cd ..

ant -Dip=$1 -Dport1=$2 -Dport2=$3 -Dopt=$4 run

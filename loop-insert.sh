#!/bin/sh

while [ 1 ]
do
	TIME=`date +%s`
	RAND=`od -An -N2 -i /dev/random`
	RANDVAL=`echo "scale=6; $RAND / 100.1" | bc`
	RAND=`od -An -N2 -i /dev/random`
	RANDX=`echo "scale=6; $RAND / 65536" | bc`
	RAND=`od -An -N2 -i /dev/random`
	RANDY=`echo "scale=6; $RAND / 65536" | bc`
	RAND=`od -An -N2 -i /dev/random`
	RANDZ=`echo "scale=6; $RAND / 65536" | bc`
	INSERT_STRING="INSERT INTO demo VALUES ($TIME, $RANDVAL, $RANDX, $RANDY, $RANDZ)"
	echo $INSERT_STRING
	mysql -u user sensor_readings -e "$INSERT_STRING"
	sleep 2
done

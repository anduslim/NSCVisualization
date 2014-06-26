#!/bin/sh

while [ 1 ]
do
	TIME=`date +%s`
	RAND=`od -An -N2 -i /dev/random`
	RANDVAL=`echo "scale=6; $RAND / 100.1" | bc`
	INSERT_STRING="INSERT INTO demo (TimeStamp, SensorReading1) VALUES ($TIME, $RANDVAL)"
	echo $INSERT_STRING
	mysql -u user sensor_readings -e "$INSERT_STRING"
	sleep 2
done

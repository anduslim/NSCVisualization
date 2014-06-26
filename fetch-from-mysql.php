<?php
	// Set the JSON header
	header("Content-type: text/json");

	// number of records
	$numRecords = 1;

	// conection: 
	$link = mysqli_connect("localhost","user",'',"sensor_readings") or die("Error " . mysqli_error($link)); 

	// consultation: 
	$query = "SELECT * FROM demo ORDER BY TimeStamp DESC LIMIT " . $numRecords or die("Error in the consult.." . mysqli_error($link)); 

	// execute the query. 
	$result = $link->query($query); 

	// return variable
	//$ret = array();

	// display information: 
	while($row = mysqli_fetch_array($result)) { 
		//echo $row["TimeStamp"] . " " . $row["SensorReading1"] . "<br>";
		$ret =  array((int)$row["TimeStamp"] * 1000, (float)$row["SensorReading1"], (float)$row["SensorAccX"], (float)$row["SensorAccY"], (float)$row["SensorAccZ"]);
	}

	//echo count($ret) . "<br>";
	
	echo json_encode($ret);
?>

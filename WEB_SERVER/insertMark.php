<?php
	setlocale (LC_ALL, "ru_RU.UTF-8");
	$f = fopen('php://input', 'r');
	$jsondata = stream_get_contents($f);
	echo $jsondata;
	echo "<br>";
	if ($jsondata) {
	    echo "string";
	    $data = json_decode($jsondata, true);
	} 
	echo "<br>";
	echo $data;
	echo "<br>";
	$host = '127.0.0.1:D:\\Programm\\DB\\BASE_COLLEGE.fdb';
	$username = 'sysdba';
	$password = 'masterkey';
	$row = array();
	$dbh = ibase_connect($host, $username, $password, 'utf8');
	$sql = "INSERT into MARKS (Mar_ad,stu_id,lpr_id,mrt_id) values('".date('Y-m-d')."',".$data['STU_ID'].",".$data['LPR_ID'].",".$data['MRT_ID'].")";
		echo $sql;   
	$rc = ibase_query($dbh, $sql);
	unset($row);
	ibase_close($dbh);
?>

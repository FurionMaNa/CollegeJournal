<?php
	setlocale (LC_ALL, "ru_RU.UTF-8");
	$host = '127.0.0.1:D:\\Programm\\DB\\BASE_COLLEGE.fdb';
	$username = 'sysdba';
	$password = 'masterkey';
	$dbh = ibase_connect($host, $username, $password, 'utf8');
	$sql = 'SELECT mr.mrt_id,mr.name from marks_type mr';
	$rc = ibase_query($dbh, $sql);
	$data = array();
	while($row = ibase_fetch_object($rc)){
    	$data[] = $row; 
	}
	echo json_encode($data);
	ibase_free_result($rc);
	ibase_close($dbh);
?>
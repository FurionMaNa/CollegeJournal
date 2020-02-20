<?php
	setlocale (LC_ALL, "ru_RU.UTF-8");
	$host = '127.0.0.1:D:\\Programm\\DB\\BASE_COLLEGE.fdb';
	$username = 'sysdba';
	$password = 'masterkey';
	$dbh = ibase_connect($host, $username, $password, 'utf8');
	$sql = 'SELECT distinct grph.name,grp.gro_id from group_hist grph             inner join groups grp on (grp.gro_id=grph.gro_id)
        inner join group_edu_comps gec on (gec.gro_id=grp.gro_id)
        inner join workers wor on (wor.wor_id=GEC.wor_id)';
	$rc = ibase_query($dbh, $sql);
	$data = array();
	while($row = ibase_fetch_object($rc)){
    	$data[] = $row; 
	}
	echo json_encode($data);
	ibase_free_result($rc);
	ibase_close($dbh);
?>
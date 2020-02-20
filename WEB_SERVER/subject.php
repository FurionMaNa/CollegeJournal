<?php
	setlocale (LC_ALL, "ru_RU.UTF-8");
	$host = '127.0.0.1:D:\\Programm\\DB\\BASE_COLLEGE.fdb';
	$username = 'sysdba';
	$password = 'masterkey';
	$ID=$_GET["id"];
	$dbh = ibase_connect($host, $username, $password, 'utf8');
	$sql = 'SELECT sub.name,sub.sub_id
            from subjects sub
            inner join GROUP_EDU_COMPS GEC on(GEC.sub_id=SUB.sub_id)
            inner join workers wor on (wor.wor_id=GEC.wor_id)
            inner join groups grp on (grp.gro_id=gec.gro_id)
            where(grp.gro_id='.$ID.')and(gec.gec_id in (select gec_id from learning_processes))';
	$rc = ibase_query($dbh, $sql);
	$data = array();
	while($row = ibase_fetch_object($rc)){
    	$data[] = $row; 
	}
	echo json_encode($data);
	ibase_free_result($rc);
	ibase_close($dbh);
?>
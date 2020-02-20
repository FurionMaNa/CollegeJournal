<?php
	setlocale (LC_ALL, "ru_RU.UTF-8");
	$host = '127.0.0.1:D:\\Programm\\DB\\BASE_COLLEGE.fdb';
	$username = 'sysdba';
	$password = 'masterkey';
	$IDG=$_GET["idg"];
	$IDS=$_GET["ids"];
	$dbh = ibase_connect($host, $username, $password, 'utf8');
	$sql = 'SELECT lp.lpr_id,lp.num,lpp.lesson_date
            from learning_processes lp
            inner join learning_processes_parts lpp on (lpp.lpr_id=lp.lpr_id)
            inner join group_edu_comps gec on (gec.gec_id=lp.gec_id)
            inner join Subjects sub on (sub.sub_id=gec.sub_id)
            inner join Groups grp on (grp.gro_id=gec.gro_id)
            where((sub.sub_id='.$IDS.')and(grp.gro_id='.$IDG.'))';
	$rc = ibase_query($dbh, $sql);
	$data = array();
	while($row = ibase_fetch_object($rc)){
    	$data[] = $row; 
	}
	echo json_encode($data);
	ibase_free_result($rc);
	ibase_close($dbh);
?>
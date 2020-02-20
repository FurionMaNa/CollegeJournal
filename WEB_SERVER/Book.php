<?php
	setlocale (LC_ALL, "ru_RU.UTF-8");
	$host = '127.0.0.1:D:\\Programm\\DB\\BASE_COLLEGE.fdb';
	$username = 'sysdba';
	$password = 'masterkey';
	$IDG=$_GET["idg"];
	$LPR=$_GET["lpr"];
	$dbh = ibase_connect($host, $username, $password, 'utf8');
	$sql = 'SELECT distinct m.mar_id,s.stu_id,ph.name,mt.name as "marName",attt.sign,att.ate_id
            from piople_hist ph
            inner join people p on (ph.peo_id=p.peo_id)
            inner join students s on (p.peo_id=s.peo_id)
            inner join student_hist sh on (sh.stu_id=s.stu_id)
            left join marks m on (s.stu_id=m.stu_id)
            left join marks_type mt on (M.MRT_ID=mt.MRT_ID)
            left join attendances att on (att.stu_id=s.stu_id)
            left join learning_processes lp on(m.lpr_id=lp.lpr_id)
            left join learning_processes l on(att.lpr_id=l.lpr_id)
            left join attendaces_types attt on (attt.att_id=att.att_id)
            inner join groups gr on(sh.gro_id=gr.gro_id)
            inner join group_edu_comps gec on(gr.gro_id=gec.gro_id)
            inner join subjects sub on(sub.sub_id=gec.sub_id)
            inner join group_hist gh on(gr.gro_id=gh.gro_id)
            where (gr.gro_id='.$IDG.')and(m.lpr_id='.$LPR.')and(att.lpr_id='.$LPR.')
            union all
            select distinct null,s.stu_id,ph.name,null,attt.sign,att.ate_id
            from piople_hist ph
            inner join people p on (ph.peo_id=p.peo_id)
            inner join students s on (p.peo_id=s.peo_id)
            inner join student_hist sh on (sh.stu_id=s.stu_id)
            left join marks m on (s.stu_id=m.stu_id)
            left join attendances att on (att.stu_id=s.stu_id)
            left join learning_processes lp on(m.lpr_id=lp.lpr_id)
            left join learning_processes l on(att.lpr_id=l.lpr_id)
            left join attendaces_types attt on (attt.att_id=att.att_id)
            inner join groups gr on(sh.gro_id=gr.gro_id)
            inner join group_edu_comps gec on(gr.gro_id=gec.gro_id)
            inner join subjects sub on(sub.sub_id=gec.sub_id)
            inner join group_hist gh on(gr.gro_id=gh.gro_id)
            where (gr.gro_id='.$IDG.')and((m.lpr_id<>'.$LPR.')or(m.lpr_id is null))and(att.lpr_id='.$LPR.')
                 and(not(ph.peoh_id)in(select distinct ph.peoh_id
                                     from piople_hist ph
                                     inner join people p on (ph.peo_id=p.peo_id)
                                     inner join students s on (p.peo_id=s.peo_id)
                                     inner join student_hist sh on (sh.stu_id=s.stu_id)
                                     left join marks m on (s.stu_id=m.stu_id)
                                     left join marks_type mt on (M.MRT_ID=mt.MRT_ID)
                                     left join attendances att on (att.stu_id=s.stu_id)
                                     left join learning_processes lp on(m.lpr_id=lp.lpr_id)
                                     left join learning_processes l on(att.lpr_id=l.lpr_id)
                                     left join attendaces_types attt on (attt.att_id=att.att_id)
                                     inner join groups gr on(sh.gro_id=gr.gro_id)
                                     inner join group_edu_comps gec on(gr.gro_id=gec.gro_id)
                                     inner join subjects sub on(sub.sub_id=gec.sub_id)
                                     inner join group_hist gh on(gr.gro_id=gh.gro_id)
                                     where (gr.gro_id='.$IDG.')and((m.lpr_id='.$LPR.')and(att.lpr_id='.$LPR.'))) )
            union all
            select distinct m.mar_id,s.stu_id,ph.name,mt.name,null,null
            from piople_hist ph
            inner join people p on (ph.peo_id=p.peo_id)
            inner join students s on (p.peo_id=s.peo_id)
            inner join student_hist sh on (sh.stu_id=s.stu_id)
            left join marks m on (s.stu_id=m.stu_id)
            left join marks_type mt on (M.MRT_ID=mt.MRT_ID)
            left join attendances att on (att.stu_id=s.stu_id)
            left join learning_processes lp on(m.lpr_id=lp.lpr_id)
            left join learning_processes l on(att.lpr_id=l.lpr_id)
            left join attendaces_types attt on (attt.att_id=att.att_id)
            inner join groups gr on(sh.gro_id=gr.gro_id)
            inner join group_edu_comps gec on(gr.gro_id=gec.gro_id)
            inner join subjects sub on(sub.sub_id=gec.sub_id)
            inner join group_hist gh on(gr.gro_id=gh.gro_id)
            where (gr.gro_id='.$IDG.')and(m.lpr_id='.$LPR.')and((att.lpr_id<>'.$LPR.')or(att.lpr_id is null))
                 and(not(ph.peoh_id)in(select distinct ph.peoh_id
                                     from piople_hist ph
                                     inner join people p on (ph.peo_id=p.peo_id)
                                     inner join students s on (p.peo_id=s.peo_id)
                                     inner join student_hist sh on (sh.stu_id=s.stu_id)
                                     left join marks m on (s.stu_id=m.stu_id)
                                     left join marks_type mt on (M.MRT_ID=mt.MRT_ID)
                                     left join attendances att on (att.stu_id=s.stu_id)
                                     left join learning_processes lp on(m.lpr_id=lp.lpr_id)
                                     left join learning_processes l on(att.lpr_id=l.lpr_id)
                                     left join attendaces_types attt on (attt.att_id=att.att_id)
                                     inner join groups gr on(sh.gro_id=gr.gro_id)
                                     inner join group_edu_comps gec on(gr.gro_id=gec.gro_id)
                                     inner join subjects sub on(sub.sub_id=gec.sub_id)
                                     inner join group_hist gh on(gr.gro_id=gh.gro_id)
                                     where (gr.gro_id='.$IDG.')and((m.lpr_id='.$LPR.')and(att.lpr_id='.$LPR.'))) )
            union all
            select distinct null,s.stu_id,ph.name,null,null,null
            from piople_hist ph
            inner join people p on (ph.peo_id=p.peo_id)
            inner join students s on (p.peo_id=s.peo_id)
            inner join student_hist sh on (sh.stu_id=s.stu_id)
            left join marks m on (s.stu_id=m.stu_id)
            left join marks_type mt on (M.MRT_ID=mt.MRT_ID)
            left join attendances att on (att.stu_id=s.stu_id)
            left join learning_processes lp on(m.lpr_id=lp.lpr_id)
            left join learning_processes l on(att.lpr_id=l.lpr_id)
            left join attendaces_types attt on (attt.att_id=att.att_id)
            inner join groups gr on(sh.gro_id=gr.gro_id)
            inner join group_edu_comps gec on(gr.gro_id=gec.gro_id)
            inner join subjects sub on(sub.sub_id=gec.sub_id)
            inner join group_hist gh on(gr.gro_id=gh.gro_id)
            where (gr.gro_id='.$IDG.')and((m.lpr_id<>'.$LPR.')or(m.lpr_id is null))and((att.lpr_id<>'.$LPR.')or(att.lpr_id is null))
                 and(not(ph.peoh_id)in(select distinct ph.peoh_id
                                     from piople_hist ph
                                     inner join people p on (ph.peo_id=p.peo_id)
                                     inner join students s on (p.peo_id=s.peo_id)
                                     inner join student_hist sh on (sh.stu_id=s.stu_id)
                                     left join marks m on (s.stu_id=m.stu_id)
                                     left join marks_type mt on (M.MRT_ID=mt.MRT_ID)
                                     left join attendances att on (att.stu_id=s.stu_id)
                                     left join learning_processes lp on(m.lpr_id=lp.lpr_id)
                                     left join learning_processes l on(att.lpr_id=l.lpr_id)
                                     left join attendaces_types attt on (attt.att_id=att.att_id)
                                     inner join groups gr on(sh.gro_id=gr.gro_id)
                                     inner join group_edu_comps gec on(gr.gro_id=gec.gro_id)
                                     inner join subjects sub on(sub.sub_id=gec.sub_id)
                                     inner join group_hist gh on(gr.gro_id=gh.gro_id)
                                     where (gr.gro_id='.$IDG.')and((m.lpr_id='.$LPR.')or(att.lpr_id='.$LPR.'))) )
            order by 3';
	$rc = ibase_query($dbh, $sql);
	$data = array();
	while($row = ibase_fetch_object($rc)){
    	$data[] = $row; 
	}
	echo json_encode($data);
	ibase_free_result($rc);
	ibase_close($dbh);
?>
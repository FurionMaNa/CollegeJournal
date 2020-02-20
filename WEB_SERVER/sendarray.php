<?php
                                                                                  
                                                                                                                     
$ch = curl_init('http://192.168.0.103/WEB_SERVER/GROUPs.php');                                                                      
curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");                                                                     
curl_setopt($ch, CURLOPT_POSTFIELDS, $json_str);                                                                  
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);                                                                      
curl_setopt($ch, CURLOPT_HTTPHEADER, array(                                                                          
    'Content-Type: application/json',                                                                                
    'Content-Length: ' . strlen($json_str))                                                                       
);  
 
$result = curl_exec($ch);

echo $result;
?>
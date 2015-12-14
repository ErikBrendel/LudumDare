<?php
require_once 'const.php';

$output = '';

//connect to db
$link = mysql_connect($dbHost, $dbUser, $dbPwd)
        or die('Error: no connection');
mysql_select_db($databaseName) or die('Error: Database not found');

//maybe create table
$createQuery = 'CREATE TABLE IF NOT EXISTS Stats (name varchar(255), score int);';
mysgl_query($createQuery) or die('Error: Could not create table');

//get db scores
$query = 'SELECT name, score FROM Stats ORDER BY score DESC LIMIT 0, 100';
$result = mysql_query($query) or die('Error: invalid query');

//convert to string
while ($line = mysql_fetch_array($result, MYSQL_NUM)) {
    $output .= $line[0] . '=' . $line[1];
    $output .= ";";
}
$output = substr($output, 0, -1);

//output
echo $output;
?>
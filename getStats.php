<?php
//echo 'debug:startStats<br>'."\n";
//error_reporting(E_ALL);
//ini_set('display_errors', 1);
//echo 'debug:errorSet<br>' . "\n";

require_once 'const.php';
//echo 'debug:required<br>'."\n";

$output = '';

//connect to db
$link = mysql_connect($dbHost, $dbUser, $dbPwd);// or die('Error: no connection');
//echo 'debug:created link<br>'."\n";
mysql_select_db($databaseName) or die('Error: Database not found');
//echo 'debug:connected<br>'."\n";

//maybe create table
$createQuery = 'CREATE TABLE IF NOT EXISTS Stats (name varchar(255), mac varchar(255), score int);';
mysql_query($createQuery) or die('Error: Could not create table');
//echo 'debug:createQuery Started<br>'."\n";

//get db scores
$query = 'SELECT name, score FROM Stats ORDER BY score DESC LIMIT 0, 100';
$result = mysql_query($query) or die('Error: invalid query');
//echo 'debug:resultQuery started<br>'."\n";

//convert to string
while ($line = mysql_fetch_array($result, MYSQL_NUM)) {
    $output .= $line[0] . '=' . $line[1];
    $output .= ";";
}
//echo 'debug:converted<br>'."\n";
$output = substr($output, 0, -1);
//echo 'debug:finihed<br>'."\n";

//output
echo $output;
?>
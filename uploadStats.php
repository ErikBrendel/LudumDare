<?php
require_once 'const.php';

$name = $_GET['name'];
$score = $_GET['score'];

$score = (int) drcrypt($score);

$name = mysql_escape_string($name);
$score = mysql_escape_string($score);

//connect to db
$link = mysql_connect($dbHost, $dbUser, $dbPwd)
        or die('Error: no connection');
mysql_select_db($databaseName) or die('Error: Database not found');

//maybe create table
$createQuery = 'CREATE TABLE IF NOT EXISTS Stats (name varchar(255), score int);';
mysgl_query($createQuery) or die('Error: Could not create table');

//get old score from db
$query = 'SELECT score FROM Stats WHERE name="' . $name . '" ORDER BY score DESC';
$result = mysql_query($query) or die('Error: unbkannter user');

$oldScore = 0;
while ($line = mysql_fetch_array($result, MYSQL_NUM)) {
    $oldScore = (int) $line[0];
}

//check if better
if ($oldScore > $score) {
    //insert better score
    $query = "INSERT INTO `$databaseName`.`Stats` (`id`, `name`, `score`) VALUES (NULL, '$name', '$zeit')";
    echo 'better';
} else {
    echo 'notBetter';
}


exit;

function decrypt($rawScore) {
    return $rawScore;
}

?>
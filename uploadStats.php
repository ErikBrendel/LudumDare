<?php

//echo 'debug:startUpload<br>' . "\n";
//error_reporting(E_ALL);
//ini_set('display_errors', 1);
//echo 'debug:errorSet<br>' . "\n";

require_once 'const.php';
//echo 'debug:required<br>' . "\n";

$name = $_GET['name'];
$score = $_GET['score'];
$mac = $_GET['mac'];
//echo 'debug:get<br>' . "\n";

$score = (int) decrypt($score);
//echo 'debug:decrypt<br>' . "\n";

$name = mysql_escape_string($name);
$score = mysql_escape_string($score);
$mac = mysql_escape_string($mac);
//echo 'debug:escaped<br>' . "\n";
//connect to db
$link = mysql_connect($dbHost, $dbUser, $dbPwd) or die('Error: no connection');
//echo 'debug:linked<br>' . "\n";
mysql_select_db($databaseName) or die('Error: Database not found');
//echo 'debug:selected<br>' . "\n";
//maybe create table
$createQuery = 'CREATE TABLE IF NOT EXISTS Stats (name varchar(255), mac varchar(255), score int);';
mysql_query($createQuery) or die('Error: Could not create table');
//echo 'debug:createQuery<br>' . "\n";
//get old score from db
$query = 'SELECT score FROM Stats WHERE name="' . $name . '" AND mac="' . $mac . '" ORDER BY score DESC';
$result = mysql_query($query) or die('Error: unknow user');
//echo 'debug:selectQuery<br>' . "\n";

$oldScore = -1;
while ($line = mysql_fetch_array($result, MYSQL_NUM)) {
    $oldScore = (int) $line[0];
}
//echo 'debug:oldScore' . $oldScore . '<br>' . "\n";

if ($oldScore == -1) { //never played before
    $query = "INSERT INTO `$databaseName`.`Stats` (`name`, `mac`, `score`) VALUES ('$name', '$mac', '$score')";
    $result = mysql_query($query) or die('Error: not able to insert score');
    echo 'better';
}

//check if better
if ($score > $oldScore) {
    //insert better score
    //echo 'debug:better!<br>' . "\n";
    $query = "UPDATE `$databaseName`.`Stats` SET `score` = '$score' WHERE `name` = '$name' AND `mac` = '$mac'";
    $result = mysql_query($query) or die('Error: not able to update new score');
    echo 'better';
} else {
    echo 'notBetter';
}


exit;

function decrypt($rawScore) {
    $len = strlen($rawScore);
    $singleLen = $len / 10;
    $realValue = -1;
    for ($i = 0; $i < 10; $i++) {
        $data = substr($rawScore, $i * $singleLen, $singleLen);
        $offset = (int) substr($data, 0, 1);
        $data = substr($data, 1, $singleLen - 1);
        
        $value = '';
        for ($j = 0; $j < $singleLen - 1; $j++) {
            $digit = (int) substr($data, $j, 1);
            $digit -= $offset;
            if ($digit < 0) {
                $digit += 10;
            }
            $value .= $digit;
        }
        if ($realValue == -1) {
            $realValue = $value;
        }
        if ($realValue != $value) {
            //abort everything
            echo 'Stop trying to hack me!';
            exit;
        }
    }
    return $realValue;
}

?>
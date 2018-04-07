<?php
$con = mysql_connect("localhost","root","");
mysql_select_db("testcure",$con);
$username=$_POST['username'];
$password=$_POST['password'];
session_start();
$_SESSION["sid"]=$username;
$sid=$_SESSION['sid'];
//First lets get the username and password from the user
$result = mysql_query("SELECT * FROM takeracc");
while($row=mysql_fetch_array($result))
{
$user=$row['email']; 
$pass=$row['pass'];
}
$num=mysql_num_rows($result);echo $num; 
for($i=1;$i<=$num; $i++){

if($username == $user && $password==$pass)
{
header("Location: http://localhost/soas/start/soas/studmod9.php");
}
else if($password != $pass ||$username != $user )
{
echo("Please Enter Correct Username and Password ...");

}
}
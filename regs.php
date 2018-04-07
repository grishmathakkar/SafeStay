<?php

$servername="localhost"; 
$username="root";
$password="";
$db_name="testcure";
$tbl_name="takeracc";
$firstname = $_POST['username']; // required
$email = $_POST['username']; // required
$pass = $_POST['password']; // required
// Create connection
$conn = new mysqli($servername, $username, $password, $db_name);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql="INSERT INTO $tbl_name(firstname,email,pass)VALUES('$firstname','$email', '$pass')";
if ($conn->query($sql) == TRUE) {
    echo "New record created successfully";
    header("Location: http://localhost/soas/start/soas/studmod1.html");

} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
$result=mysql_query($sql);



	
   
       
?>
 
 
<?php

die();
?>

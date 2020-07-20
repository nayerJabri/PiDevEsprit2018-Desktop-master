<?php
if (isset($_FILES['myFile'])) {
    // Example:    
    $path = $_FILES['myFile']['name'];
	$ext = pathinfo($path, PATHINFO_EXTENSION);
	$filename=uniqid().'.'.$ext;
    move_uploaded_file($_FILES['myFile']['tmp_name'], "src/Images/" . $filename);
    echo $filename;
}
?>
<?php
	
 
/*
  Processing + PHP code to upload images and data to a web server
 
    Based on: http://wiki.processing.org/index.php?title=Saving_files_to_a_web_server&oldid=482
 
    Updated on 04.07.2013 by Abe Pazos - http://funprogramming.org
 
    Tested with Processing 2.0 and PHP 5.3.10
 
 
  This file receives files from Processing
  and saves them in a folder.
 
 
  NOTE 1: This includes NO SECURITY!
 
    If you can upload images, anyone can!!!
 
    This should NOT BE USED LIVE before adding
    some kind of authentication.
 
 
  NOTE 2: The uploads/ folder must be writable!
 
    This can be achieved in different ways:
 
    Via FTP: set the permissions of the folder to 777
 
    Via SSH when using Apache as web server:
    sudo chown www-data:www-data uploads
*/
 
  $uploaddir = '/Applications/XAMPP/xamppfiles/htdocs/sharetest/tmp/';
  $uploadfile = $uploaddir . basename($_FILES['p5uploader']['name']);
 
  if (move_uploaded_file($_FILES['p5uploader']['tmp_name'], $uploadfile)) {
      echo "File is valid, and was successfully uploaded.\n";
  } else {
      echo "Could not move file to $uploadfile\n";
  }
 
  echo "Here is some more debugging info:\n";
  print_r($_FILES);
 
  // END
?>

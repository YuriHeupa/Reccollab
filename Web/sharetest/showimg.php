<!DOCTYPE html>
<html>
<head>
<title>RecColab - Compartilhamento de imagem</title>
<style type="text/css">

body {
background:#f5f5f5;
font: 14px/150% 'century gothic',helvetica,arial;
}

#container {
margin:5px auto;
padding:25px;
width:400px;
border:1px solid #999;
border-radius:8px; -moz-border-radius:8px; -webkit-border-radius:8px;
background:#fff;
}
</style>
</head>
<body>
<img src="tmp/<?php
	$img = $_GET["img"];
	echo $img;
?>.png"/>
<br>
<img src = "share_button.png" id = "share_button">

<div id="fb-root"></div>
<script>
window.fbAsyncInit = function() {
    FB.init({appId: '1389223641318916', status: true, cookie: true,
            xfbml: true});
};
(function() {
 var e = document.createElement('script'); e.async = true;
 e.src = document.location.protocol +
 '//connect.facebook.net/en_US/all.js';
 document.getElementById('fb-root').appendChild(e);
 }());
</script>


<script src="//ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js" type="text/javascript"></script>


<script type="text/javascript">
$(document).ready(function(){
                  $('#share_button').click(function(e){
                                           e.preventDefault();
                                           FB.ui(
                                                 {
                                                 method: 'feed',
                                                 name: 'RecCollab',
                                                 link: 'http://reccollab.com',
                                                 picture: 'http://localhost:80/sharetes/tmp/<?php
                                                 $img = $_GET["img"];
                                                 echo $img;
                                                 ?>.png',
                                                 caption: 'Captura de imagem',
                                                 description: 'Captura realizada no RecCollab',
                                                 message: ''
                                                 });
                                           });
                  });
</script>


</body>
</html>
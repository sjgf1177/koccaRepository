<?
	$filename = $_REQUEST["filepath"];
	if($filename !=null && $filename != ""){
		if(file_exists($filename)){
			$fp = fopen($filename, "r");
			while(!feof($fp)){
				$fileStr .= fgets($fp)."<br>";
			}
			fclose($fp);
			$fileStr =preg_replace('/(<!--.*-->)/i', '', $fileStr);
			$fileStr =preg_replace('/(#.*\n{0,1}|\/\/.*\n{0,1}|\/\*(.|\s)*?\*\/\n{0,1}|\<script[^>]*\>[\w\W\r]*\<\/script\>|\<style[^>]*\>[\w\W\r]*\<\/style\>)/i', '', $fileStr);
			if(sizeof(explode("initCheckWebfilter.php", $fileStr)) > 1){ 
				echo "insert"; 
			}else{
				echo "delete"; 
			}
		}else{
			echo "none"; 
		}
	}else{
		echo "none"; 
	}
?>
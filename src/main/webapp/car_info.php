<?php
//    include 'database.php';
//    $pid = $_GET['pid'];
//    $car = "Select * from cardata left join carimages on carimages.pid = cardata.pid where cardata.pid = :id ";
//    $carstmt = $dbcon->prepare($car);
//    $carstmt->bindParam(':id', $pid, PDO::PARAM_INT);
//    $carstmt->execute();
//    $rs_car = $carstmt->fetchAll()[0];
$pid = $_GET['pid'];
?>

<!DOCTYPE html>
<html>
    <head>
        <title>Vending Cars - Car Buying Made Simple</title>
        <link rel="stylesheet" href="car_info.css">
        <script src = "car_info.js"></script>
    </head>
    
    <body onload="getCarServletInfo($pid)">
        <div class="banner">
            <h1>Vending Cars</h1>
            <div class="slogan">
                The lowest priced cars anywhere
            </div>
        </div>
<!--        <form method ="POST" action ="ProductDetailsServlet">
            <input type="hidden" name = "pid" value = $pid>
        </form>-->
        
          
        
            

        </div>
    </body>
</html>
<?php ?>
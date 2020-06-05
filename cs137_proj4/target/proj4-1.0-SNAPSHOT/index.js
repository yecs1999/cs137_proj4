

function getCarsMain(){
    if (window.XMLHttpRequest)
    {  // IE7+, Firefox, Chrome, Opera, Safari
        var xhr = new XMLHttpRequest();
    }
    else
    {  // IE5, IE6
        var xhr = new ActiveXObject ("Microsoft.XMLHTTP");
    }
    xhr.onreadystatechange = function ()
    { // 4 means finished, and 200 means okay.
        if (xhr.readyState == 4 && xhr.status == 200)
        {
            var result = xhr.responseText;
            document.getElementById("info").innerHTML = result;
            //tax = parseFloat(result);
            //document.getElementById("totalCost").innerText = priceFormat.format(price + price * tax);
        }
    }
    // Call the response software component
    xhr.open ("POST", "IndexServlet", true);
    xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    xhr.send (null);


}
function addCarToHistory(pid){
    if (window.XMLHttpRequest)
    {  // IE7+, Firefox, Chrome, Opera, Safari
        var xhr = new XMLHttpRequest();
    }
    else
    {  // IE5, IE6
        var xhr = new ActiveXObject ("Microsoft.XMLHTTP");
    }
    xhr.onreadystatechange = function ()
    { // 4 means finished, and 200 means okay.
        if (xhr.readyState == 4 && xhr.status == 200)
        {
            //var result = xhr.responseText;
            //document.getElementById("car_table_bod").innerHTML = result;
            //tax = parseFloat(result);
            //document.getElementById("totalCost").innerText = priceFormat.format(price + price * tax);
        }
    }
    // Call the response software component
    xhr.open ("POST", "CarHistoryServlet", true);
    xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    xhr.send ('pid=' + pid);    
    
    
    
    
    
}


getCarsMain();
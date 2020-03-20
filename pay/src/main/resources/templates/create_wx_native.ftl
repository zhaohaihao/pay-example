<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>支付</title>

        <script src="https://cdn.bootcss.com/jquery/1.5.1/jquery.min.js"></script>
        <script src="https://cdn.bootcss.com/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
    </head>
    <body>
        <div id="myQrcode"></div>

    </body>
    <script>
        jQuery('#myQrcode').qrcode({
            text: "${codeUrl}"
        });
    </script>
</html>
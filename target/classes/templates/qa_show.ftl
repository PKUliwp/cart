<!DOCTYPE html>
<html>
<head>
    <title>qa</title>
    <style type="text/css">
        a:link {color: #e74c3c} /* 未访问的链接 */
        a:visited {color: #f1c40f} /* 已访问的链接 */
        a:hover {color: #2ecc71} /* 鼠标移动到链接上 */
        a:active {color: #9b59b6} /* 选定的链接 */
    </style>
</head>
<body bgcolor="white">
<#assign sel = "${sel}">
<#if (sel="qa")>
    ${value}
</#if>
<script>
    if("${sel}".length > 5)
        window.location.href="${sel}";
</script>

</body>
</html>

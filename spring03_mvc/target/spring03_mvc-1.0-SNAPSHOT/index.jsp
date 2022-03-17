<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>

<script src="js/jquery-3.3.1.js"></script>
<script src="js/axios-0.18.0.js"></script>
<script>
   var userList=new Array();
   userList.push({username:"zhangsan",age:18});
   userList.push({username:"lisi",age:19});

   // $.ajax({
   //     type:"POST",
   //     url:"quick14",
   //     data:JSON.stringify(userList),
   //     contentType:"application/json;charset=utf-8"
   // });


axios({
    method:"post",
    url:"http://localhost:8080/spring03_mvc/quick14",
    data:userList,
    contentType:"application/json;charset=utf-8"
})
</script>
<body>
llll




</body>

</html>
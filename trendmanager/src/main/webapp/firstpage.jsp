<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html dir="rtl">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {
  box-sizing: border-box;
}

input[type=text], select, textarea {
  width: 50%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: vertical;
}

label {
  padding: 12px 12px 12px 0;
  display: inline-block;
}

input[type=submit] {
    width: 10%;
  padding: 12px;
  background-color: #4CAF50;
  color: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;
  resize: vertical;
}

input[type=submit]:hover {
  background-color: #45a049;
}

.container {
  border-radius: 5px;
  <%--background-color: #f2f2f2;--%>
  padding: 20px;
}

.col-25 {
  float: left;
  width: 25%;
  margin-top: 6px;
}

.col-75 {
  float: left;
  width: 75%;
  margin-top: 6px;
  text-align: center;
}
.col-100 {
  float: left;
  width: 100%;
  margin-top: 6px;
  text-align: center;
}
/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .col-25, .col-75, input[type=submit], .col-100 {
    width: 100%;
    margin-top: 0;
  }
}
</style>
</head>
<body >
<div class="container">
  <form action="/action_page.php">
      <div class="row">
      <div class="col-100">
          <p style="font-size:50px ;color:blue";>روند دات آی آر</p>
          <p style="font-size:50px ;color:blue";>Ravand.ir</p>
      </div>
    </div>
    <div class="row">
      <div class="col-100">
        <input type="text" id="q" name="q" placeholder="عبارت جستجو را وارد کنید">
      </div>
    </div>
    <div class="row">
        <div class="col-100">
      <input type="submit" value="جستجو">
        </div>
    </div>
  </form>
</div>
<div class="row-old">
    <div class="col-75-old">
        <a href="mailto:newstrendir@gmail.com">تماس با ما</a>
    </div>
</div>
</body>
</html>
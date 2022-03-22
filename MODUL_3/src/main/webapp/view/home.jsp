<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>MODUL 3</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../common/css.css">
  </head>
  <body>
  <div class="container">
    <h1>All product</h1>
  </div>
  <div>
    <button class="btn" type="button" onclick="">
      <a href="/home?action=showFormAdd" style="color: lightseagreen"></a>
    </button>
  </div>

  <div class="container col-md-9">
    <table>
      <tr>
        <td>ID</td>
        <td>Name</td>
        <td>Price</td>
        <td>Quantity</td>
        <td>Color</td>
        <td>Description</td>
        <td>Category</td>
        <td colspan="2">Action</td>
      </tr>
      <tr>
        <c:forEach var="product" items="${requestScope.products}">
          <td>${product.id}</td>
          <td>${product.name}</td>
          <td>${product.price}</td>
          <td>${product.quantity}</td>
          <td>${product.color}</td>
          <td>${product.description}</td>
          <td>${product.category}</td>
          <td>
            <button class="btn btn-info" type="button" onclick="">
              <a href="/home?action=delete&id=${product.id}" style="color: red"></a>
            </button>
          </td>
          <td>
            <button class="btn btn-info" type="button" onclick="">
              <a href="/home?action=delete&id=${product.id}" style="color: red"></a>
            </button>
          </td>
        </c:forEach>
      </tr>
    </table>
  </div>


  </body>
</html>

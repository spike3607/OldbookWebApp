<%-- 
    Document   : deleteAuthor
    Created on : Oct 5, 2015, 8:22:41 PM
    Author     : mschoenauer1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Author</title>
    </head>
    <body>
        <h1>Delete Author</h1>
        <form name="deleteAuthor" id="newAuthor" method="POST" action="AuthorController?action=delete">
            Author ID:
            <input type="number" id="author_id" name="author_id" placeholder="ID #">
            <br>
            <input type="submit" id="submit" name="submit" value="Delete Author">
        </form>
    </body>
</html>

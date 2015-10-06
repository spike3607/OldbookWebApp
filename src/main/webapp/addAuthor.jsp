<%-- 
    Document   : addAuthor
    Created on : Oct 5, 2015, 7:38:10 PM
    Author     : mschoenauer1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Author</title>
    </head>
    <body>
        <h1>Add New Author</h1>
        <form name="newAuthor" id="newAuthor" method="POST" action="AuthorController?action=add">
            Author Name: 
            <input type="text" id="newName" name="newName" form="newAuthor" placeholder="Enter New Author's Name">
            <br>
            Date Created: 
            <input type="date" id="newDate" name="newDate" form="newAuthor" placeholder ="Enter New Author's Date Created">
            <br>
            <input type="submit" id="submit" name="submit" value="Add New Author">
        </form>
    </body>
</html>

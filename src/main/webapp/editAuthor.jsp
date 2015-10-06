<%-- 
    Document   : editAuthor
    Created on : Oct 5, 2015, 8:14:43 PM
    Author     : mschoenauer1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Author</title>
    </head>
    <body>
        <h1>Edit Author</h1>
        <form name="editAuthor" id="newAuthor" method="POST" action="AuthorController?action=update">
            Author ID:
            <input type="number" id="author_id" name="author_id" placeholder="ID #">
            <br>
            Author Name: 
            <input type="text" id="new_name" name="new_name" placeholder="Author's New Name">
            <br>
            Date Created: 
            <input type="date" id="new_date" name="new_date" placeholder ="Author's New Date">
            <br>
            <input type="submit" id="submit" name="submit" value="Edit Author">
        </form>
    </body>
</html>

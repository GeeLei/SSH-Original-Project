<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
    <h>上传文件</h>
    <form name="userForm" action="/serviceplatform/grzy" method="post"
        enctype="multipart/form-data">
        id:<input type="text" name="id"> <input type="submit"
            value="进入个人主页">
    </form>
</body>
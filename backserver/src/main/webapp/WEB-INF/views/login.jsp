<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/13
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/work.ico" />
    <link rel="bookmark" href="<%=request.getContextPath()%>/images/work.ico" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.validation.css" type="text/css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/resources/jquery-1.12.0.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/resources/jquery.validation.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/resources/utilwork.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/login.js"></script>
    <title>登录</title>
</head>
<body>
<div class="header">
    <div style="margin-left: 5%">
        <span class="title">后台系统</span>
    </div>
</div>
<div class="member-wrap">
    <div class="col-md-7"></div>
    <div class="member-div col-lg-4">
        <div class="member-con">
            <div class="member-hd clearfix">
                <span class="member-hd-tab cur">系统登录</span>
            </div>
            <div class="member-bd">
                <div class="login-con member-bd-tab">
                    <form id="login-form">
                        <fieldset>
                            <p>
                                <div class="work_password" style="overflow: hidden;">
                                    <div class="work_group" style="height:70px;">
                                        <div class="input-group-addon" style="width:10%;float:left;height:40px;  line-height: 25px;">
                                            <i class="fa fa-user"></i>
                                        </div>
                                        <div style="width:88%;float:left;">
                                            <input style="height:40px;border-radius: 0 4px 4px 0;" type="text" id="loginName" class="form-control loginName" name="loginName" placeholder="请输入用户名">
                                        </div>
                                    </div>
                                </div>
                            </p>
                            <p>
                                <div class="work_password" style="height:70px;">
                                    <div class="work_group">
                                        <div class="input-group-addon" style="width:10%;float:left;height:40px;  line-height: 25px;">
                                            <i class="fa fa-lock"></i>
                                        </div>
                                        <div style="width:88%;float:left;">
                                            <input style="height:40px;border-radius: 0 4px 4px 0;" type="password" id="password" class="form-control" name="password" placeholder="请输入密码">
                                        </div>
                                    </div>
                                </div>
                            </p>
                            <div style="color:red" id="loginErrorMsg"></div>
                            <p>
                                <input style="margin-top:10px" class="btn btn-warning" id="login-btn" type="button" value="登录">
                            </p>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

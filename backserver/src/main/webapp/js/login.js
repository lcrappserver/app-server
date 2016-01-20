/**
 * Created by Administrator on 2016/1/13.
 */
var localObj = window.location;
var contextPath = localObj.pathname.split("/")[1];
var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
$(document).ready(function() {
    var validator = $("#login-form").validate({
        onblur: true,
        rules: {
            loginName: {
                required: true
            },
            password: {
                required: true
            },
        },
        messages: {
            loginName: {
                required: "不能为空"
            },
            password: {
                required: "密码不能为空"
            },
        },
        submitHandler: function() {

        },
        invalidHandler: function(event, validator) {

        }
    });
    //jQuery.validator.addMethod("password", function(value, element) {
    //    return this.optional(element) || /^[\.%-]*([0-9]*[a-zA-Z]+[0-9]+[a-zA-Z]*)|([a-zA-Z]*[0-9]+[a-zA-Z]+[0-9]*)[\.%-]*$/.test(value);
    //}, "密码需包含字母和数字,必须大于3个字符");
    //jQuery.validator.addMethod("loginName", function(value, element) {
    //    return this.optional(element) || /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(value) || /(?!.*\.co$)^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$/.test(value) || /(?!.*\.git$)^[a-zA-Z]([a-zA-Z0-9._-]){7,29}$/.test(value);
    //}, "账号格式不正确");

    $('#login-btn').click(function() {
        var param = utils.getForm('#login-form');
        var form = $('#login-form');
        if (form.valid()) {
            $('#login-btn').attr('disabled', 'true');
            utils.ajaxOper(basePath + '/back/token.do', param, 'post', {
                'SUCCESS': function(param, data) {
                    utils.setCookie('userName', param.loginName);
                    window.location.href = basePath + "/menu";
                },
            }, "#login_btn").fail(function(status, msg) {
                $('#login-btn').removeAttr('disabled')
                validator.showErrors({
                    'loginName': msg.message,
                    'password': msg.message
                });
            });
            return true;
        } else return false;
    });
});

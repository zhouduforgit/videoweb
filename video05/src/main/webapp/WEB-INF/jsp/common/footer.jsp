<%--
  Created by IntelliJ IDEA.
  User: 周都
  Date: 2021/1/29
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="background-color: #E9EBE8;box-shadow: 0px 0px 1px 1px #ccc">
    <div class="container">
        <div class="pt-5">
            <a href="#"  class="text-secondary">帮助中心</a>
            <a href="#"  class="text-secondary ml-3">联系我们</a>
            <a href="#"  class="text-secondary ml-3">关于我们</a>
            <a href="#"  class="text-secondary ml-3">客服</a>
            <a href="#"  class="text-secondary ml-3">官方粉丝群</a>
        </div>
        <br>
        <div class="pb-5">
            <a href="#"  class="text-secondary">官方粉丝群</a>
            <a href="#" class="text-secondary ml-3" >Copyright @ 2019 上海XXXX公司</a>
            <a href="#" class="text-secondary ml-3">Java课堂</a>
            <a href="#" class="text-secondary ml-3">沪ICP备XXX</a>
        </div>
    </div>
</div>

<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<!--bootstraps框架模板-->
<%--
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
--%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
<!--登录模态框-->
<div class="modal fade " id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">登录</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="/login" method="post" onsubmit="return loginSubmit()">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="loginInputEmail">邮箱</label>
                        <input name="email" type="email" class="form-control" id="loginInputEmail"
                               aria-describedby="emailHelpLon">
                        <small id="emailHelpLogin" class="form-text text-muted"></small>
                    </div>
                    <div class="form-group">
                        <label for="loginInputPwd">密码</label>
                        <input name="password" type="password" class="form-control" id="loginInputPwd">
                    </div>
                    <div class="form-group form-check">
                        <input name="autoLogin" type="checkbox" class="form-check-input" id="exampleCheck1" value="1">
                        <label class="form-check-label" for="exampleCheck1">自动登录</label>

                        <a href="/forgetPage" class="float-right">忘记密码</a>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">登录</button>
                </div>
            </form>

            <a href="#" class="ml-3 mb-3" data-toggle="modal" data-dismiss="modal"
               data-target="#registModal">还没有账号？点我注册</a>
        </div>

    </div>
</div>


<!--注册模态框-->
<div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="registerLabel">注册</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="/register" method="post" onsubmit="return registerSubmit()">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="RegisterEmailInput">邮箱</label>
                        <!--常用正则表达示-->
                        <input name="email" type="email" class="form-control" id="RegisterEmailInput" onblur="checkEmail(this)"
                               aria-describedby="emailHelpRegister" required>
                        <small id="emailHelpRegister" class="form-text text-muted"></small>
                    </div>
                    <div class="form-group">
                        <label for="RegisterPasswordInput">密码</label>
                        <input name="password" type="password" class="form-control" id="RegisterPasswordInput" onblur="checkPassword(this)"
                               required>
                        <small id="passwordHelpRegister" class="form-text text-muted"></small>
                    </div>
                    <div class="form-group">
                        <label for="RegisterVcodeInput">验证码</label>
                        <div class="row">
                            <input name="vcode" type="vcode" class="form-control col-md-6 ml-3" id="RegisterVcodeInput" maxlength="4">
                            <img src="/vcode" class="col-md-4" onclick="changeVcode(this)">
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">注册</button>
                </div>
            </form>
            <a href="#" class="ml-3 mb-3" data-toggle="modal"  data-dismiss="modal" data-target="#loginModal">已有账号，点我登录</a>
        </div>
    </div>
</div>

    <script type="application/javascript">

        function loginSubmit() {
            var submitFlag = false;
            var email = $("#loginInputEmail").val();
            var pwd = $("#loginInputPwd").val();
            $.ajax({
                url: "/checkLogin",
                type: "POST",
                data: {email: email, password: pwd},
                async: false,
                success: function (result) {
                    // 正确 直接登录提交表单
                    if (result.code == 1) {
                        submitFlag = true;
                    } else {
                        // 不正确 提示错误，不提交表单
                        console.log(email);console.log(pwd);
                        console.log("lalalaalala");
                        $("#emailHelpLogin").text("邮箱或密码不正确");
                        $("#emailHelpLogin").attr("class", "invalid-feedback");
                        $("#emailHelpLogin").css("display","block");
                        submitFlag = false;
                    }
                }
            });
            return submitFlag;
        }


        //<!--刷新验证码的前端js脚本-->
        function changeVcode(imageNode) {
            imageNode.src="/vcode?ram"+ new Date().getTime();
        }
        var registerEmailFlag=false;
        var registerPasswordFlag=false;

        function checkPassword(pawNode) {
            var pwd = pawNode.value;
            var pwdLength=pwd.length;
            console.log(pwdLength);
            if(pwdLength<6){
                $("#RegisterPasswordInput").removeClass("is-valid");
                $("#RegisterPasswordInput").addClass("is-invalid");
                $("#passwordHelpRegister").text("密码小于6位");
                $("#passwordHelpRegister").attr("class","invalid-feedback");
                registerPasswordFlag=false;
            }else{
                $("#RegisterPasswordInput").removeClass("is-invalid");
                $("#passwordHelpRegister").attr("class","valid-feedback");
                $("#RegisterPasswordInput").addClass("is-valid");
                $("#passwordHelpRegister").text("");
                registerPasswordFlag=true;
            }
        }
        //<!--判断邮箱是否存在 AJAX-->
        function checkEmail(emailNode){
            var email=emailNode.value;
            var patt = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            var suc=patt.test(email);
            if(suc==false){
                //邮箱格式不正确
                $("#RegisterEmailInput").removeClass("is-valid");
                $("#RegisterEmailInput").addClass("is-invalid");
                $("#emailHelpRegister").text("email格式不对");
                $("#emailHelpRegister").attr("class","invalid-feedback");
                registerEmailFlag=false;
                return;
            }
                $("#RegisterEmailInput").removeClass("is-invalid");
                $("#emailHelpRegister").attr("class","valid-feedback");
                $("#RegisterEmailInput").addClass("is-valid");
                $("#emailHelpRegister").text("");
            $.ajax({
                url:"/checkEmail?email="+email,
                async: true,
                success: function (result){
                    console.log(result);
                    if (result.code==1){  //该邮箱可以使用
                        console.log("ok");
                        $("#RegisterEmailInput").removeClass("is-invalid");
                        $("#emailHelpRegister").attr("class","valid-feedback");
                        $("#RegisterEmailInput").addClass("is-valid");
                        $("#emailHelpRegister").text("");
                        registerEmailFlag=true;
                    }else {
                        console.log("no");
                        $("#RegisterEmailInput").removeClass("is-valid");
                        $("#RegisterEmailInput").addClass("is-invalid");
                        $("#emailHelpRegister").text("email已被注册");
                        $("#emailHelpRegister").attr("class","invalid-feedback");
                        registerEmailFlag=false;
                    }
                }
            });
        }
        function registerSubmit() {
            if(registerEmailFlag==true && registerPasswordFlag==true){
                return true;
            }
            return false;
        }
    </script>









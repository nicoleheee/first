
$(function()
  {
      var str='';
      var reuUrl=window.location.pathname+window.location.search;
      reqUrl=encodeURI(encodeURI(reuUrl));
      str=str+'<div class="modal fade" id="modal_myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
                +'<div class="modal-dialog"> '
                    +'<div class="modal-content col-lg-8" >'
          +'<div class="modal-body" >'
          +'<div class="row auth-header-container">'
      +'<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">'
      +'<a class="auth-header auth-header-active" >账号密码登录</a>'
      +'</div>'
      +'</div>'

          +'<form id="form" >'
          +'<div class="form-group">'
          +'<label class="hidden" data-tip="请输入手机号或邮箱">输入手机号或邮箱</label>'
          +'<input type="text" class="form-control input-lg input-interactive" placeholder="请输入手机号或邮箱" id="username" name="userName">'
          +'</div>'

          +'<div class="form-group">'
          +'<label class="hidden" data-tip="请输入密码">请输入密码</label>'
          +'<input type="password" class="form-control input-lg input-interactive" placeholder="请输入密码" id="password" name="passWord">'
          +'</div>'

          +'<div class="row hidden" id="outcode" >'
          +'<div class="col-lg-6  col-md-6 col-sm-6 col-xs-6">'
          +'<div class="form-group">'
          +'<label class="hidden" data-tip="输入验证码">输入验证码</label>'
          +'<input type="text" class="form-control input-lg input-interactive" placeholder="输入验证码" id="inCode" name="inCode">'
          +'</div>'
          +'</div>'
          +'<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">'
          +'<img src="/auth/getimage?date=" id="codeimg" class="login-codeimg">'
          +'</div>'
          +'<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">'
          +'<a title="刷新验证码" href="javascript:void(0);" class="refresh-codeimg" id="flash">刷新</a>'
          +'</div>'
          +'</div>'

          +'<div class="row">'
          +'<div class="col-lg-12  col-md-12 col-sm-12 col-xs-12 text-right ">'
          +'<a href="/auth/retrievepage" class="refresh-codeimg">忘记密码?</a>'
          +'</div>'
          +'</div>'

          +'<div class="form-group">'
          +'<button type="button" class="btn btn-lg btn-default btn-block btn-orange" id="button">登&nbsp;录</button>'
          +'</div>'

          +'<div class="form-group">'
          +'<p class="form-control-static">'
          +'<a href="/auth/wxlogin?reqUrl='+reqUrl+'"><i class="fa fa-fw fa-wechat"></i>微信登录</a>'

          +'<a href="/auth/jaccount?reqUrl='+reqUrl+'">上海交大账号登录</a>'
          +'</p>'
          +'</div>'
          +'</form>'
                       +'</div> '
                    +'</div> '
                +'</div> '
               + '</div>';
      $.ajaxSetup({
          error: function(response){
              var obj=new Object();
              obj.success=false;
              switch (response.status){
                  case(500):
                      obj.errorMsg='服务器系统内部错误';
                      break;
                  case(401):
                      obj.errorMsg='未登录';
                      break;
                  case(404):
                      obj.errorMsg='请求的资源不存在';
                      break;
                  case(403):
                      obj.errorMsg='无权限执行此操作';
                      break;
                  case(408):
                      obj.errorMsg='请求超时';
                      break;
                  //针对ajax登录的情况,请求出错返回还是200,只是修改了请求头
                  case(200):
                      obj.errorMsg='登录过期';
                      break;
                  default:
                      obj.errorMsg='未知错误';
              }
          },
          complete: function (response)
          {
              if(response.getResponseHeader){
                  var sessionStatus = response.getResponseHeader("sessionstatus");
                  if (sessionStatus == 'timeout') {
                      //发送一个请求，求上次登录的用户名
                      $('body').append(str);
                      $('#codeimg').attr('src','/auth/getimage?date='+new Date().getTime());
                      $('#flash').click(function(){
                          $('#codeimg').attr('src','/auth/getimage?date='+new Date().getTime());
                      });
                      $('#form')[0].reset();
                      //验证次数校验
                      if($.cookie('check_password_failure_times')!=null&&$.cookie('check_password_failure_times')>3){
                          $('#outcode').removeClass('hidden');
                      }
                      $('#modal_myModal').modal('toggle');
                      var $username=$("#username");
                      var $password=$("#password");
                      var $inCode=$("#inCode");
                      var setTipMsg = function(msg, $source){
                          var $container=$($source).parent();
                          $container.addClass("tip-relative");
                          var $span=$("<span>").html(msg).addClass("abs-tip-msg-right error-msg");
                          $container.find("span").remove();
                          $span.appendTo($container);
                          setTimeout(function(){
                              $span.remove();
                          },2000);
                      };

                      /***** 登入按钮 */
                      $('#button').bind("click",function (){
                          var f=checkUsername()&&checkPassword()&&checkCode();
                          if (f) {

                              $.ajax({
                                  url: '/auth/ajaxlogin',
                                  type: 'post',
                                  dataType: 'json',
                                  data: $('#form').serialize(),
                                  success: function (data) {
                                      if (!data.success)
                                      {
                                          setTipMsg(data.errorMsg, $username);
                                          $('#flash').trigger('click');
                                          $('#inCode').val('');
                                          if (data.extendData > 3) {
                                              $('#outcode').removeClass('hidden');
                                          }
                                      }else
                                          location.reload(true);
                                  },
                                  error:function(data){
                                      $('#content').isLoading('hide');
                                      setTipMsg(data.errorMsg, $username);
                                  }
                              });
                          }
                      });

                      /****输入框验证***/
                      $("input.form-control").bind("blur",function(){
                          if($(this).attr("id")=="username")
                              checkUsername();
                          else if($(this).attr("id")=="password")
                              checkPassword();
                          else if($(this).attr("id")=="inCode")
                              checkCode();
                      });

                      function checkUsername(){
                          var username=$username.val().trim();
                          if(""==username){
                              setTipMsg("用户名不能为空", $username);
                              return false;
                          }else
                              return true;
                      }
                      function checkPassword(){
                          var password=$password.val().trim();
                          if(""==password){
                              setTipMsg("密码不能为空", $password);
                              return false;
                          }else if(password.length<6){
                              setTipMsg("密码长度不小于6", $password);
                              return false;
                          }else
                              return true;
                      }
                      function checkCode(){
                          if($.cookie('check_password_failure_times')!=null&&$.cookie('check_password_failure_times')>3){
                              $('#outcode').removeClass('hidden');
                              //存在验证码
                              var code=$inCode.val().trim();
                              if(""==code){
                                  setTipMsg("验证码不能为空", $inCode);
                                  return false;
                              }else if(code.length<4){
                                  setTipMsg("请输入正确的", $inCode);
                                  return false;
                              }else
                                  return true;

                          }else
                              return true;
                      }

                      function isIE() { //ie?
                          var userAgent = navigator.userAgent;
                          if (userAgent.indexOf("Firefox") > -1 ||userAgent.indexOf("Chrome") > -1||(userAgent.indexOf("Safari") > -1)){
                              return false;
                          }
                          if (!!window.ActiveXObject || "ActiveXObject" in window)
                              return true;
                          else
                              return false;
                      }
                      /*var num ;
                      $.ajax({
                          url:'/auth/gethistoryusername',
                          success:function(data){
                              //错误可能保存的信息有误或者没有登录记录,直接重新登录，不在自动填写用户名
                              if(!data.success){
                                  window.location.href='/auth/logout';
                              }
                              var $modalflash=$('#modal_flash');
                              var $modalclose=$('#modal_close');
                              var userName=$('#modal_username');
                              userName.val(data.extendData);
                              userName.attr('disabled',true);

                              $('#modal_codeimg').attr('src','/auth/getimage?date='+new Date().getTime());

                              $modalflash.unbind('click');
                              $modalflash.click(function(){
                                  $('#modal_codeimg').attr('src','/auth/getimage?date='+new Date().getTime());
                              });

                              $modalclose.unbind('click');
                              $modalclose.click(function()
                              {
                                  $('#modal_myModal').remove();
                                  $('.modal-backdrop:last').remove();
                                  $('body').removeClass('modal-open');
                              });

                              $.ajax({
                                  url:'/auth/getchecknum',
                                  type:'post',
                                  success:function(data)
                                  {
                                      if(data.success)
                                      {
                                          num=data.extendData;
                                          if(data.extendData<4)
                                          {
                                              $('#modal_outcode').css('display', 'none');
                                              $('#modal_myModal').modal('toggle');
                                              return
                                          }
                                          $('#modal_myModal').modal('toggle');
                                          return;
                                      }
                                      $('#modal_meg').text(data.errorMsg);
                                  }
                              });
                          }
                      });
                      //添加点击事件
                      var $modalbutton=$('#modal_button');
                      var $modal_messageBox=$('#modal_messageBox');
                      var $modalmeg=$('#modal_meg');

                      $modalbutton.unbind('click');
                      //登录
                      $modalbutton.click(function () {
                          var passvalue=$('#modal_password').val();
                          if(!/^[a-zA-Z0-9\.]{6,20}$/.test(passvalue))
                          {
                              $modal_messageBox.css('display', 'block');
                              $modalmeg.text('密码格式不正确');
                              return;
                          }
                          $modal_messageBox.css('display', 'none');
                          $modalmeg.text('');

                          $.ajax({
                              url: '/auth/ajaxlogin',
                              type: 'post',
                              dataType: 'json',
                              data: {userName:$('#modal_username').val(),passWord:passvalue,inCode:$('#modal_incode').val()},
                              success: function (data) {
                                  if (!data.success) {
                                      //数据异常即本地保存的从cookie被更改或者与保存的用户信息不一致，返回登录页面重新登录
                                      $modal_messageBox.css('display', 'block');
                                      $('#modal_meg').text(data.errorMsg);
                                      $('#modal_flash').trigger('click');
                                      $('#modal_incode').val('');
                                      //判断是否显示验证码
                                      num=data.extendData;
                                      if (num > 3) {
                                          $('#modal_outcode').css('display', 'block');
                                      }
                                      return;
                                  }
                                  //关闭modal
                                  $('#modal_myModal').modal('hide');
                                  $('.modal-backdrop:last').remove();
                                  $('body').removeClass('modal-open');
                                  location.reload(true);
                              }
                          });
                          $modal_messageBox.css('display', 'none');
                      });*/
                  }
              }
          }
      });

  });

/**
 * Created by User on 2016/7/27.
 */
jQuery.validator.addMethod("checkuser",function(value,element){
        return this.optional(element)||/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test(value)||
                             /^0?(13|14|15|17|18)[0-9]{9}$/.test(value);
},"请输入正确的用户名");
jQuery.validator.addMethod("checkphone",function(value,element){
    return this.optional(element)|| /^0?(13|14|15|17|18)[0-9]{9}$/.test(value);
},"请输入正确的手机号");
jQuery.validator.addMethod("checkemail",function(value,element){
    return this.optional(element)||/^[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?$/.test(value);
},"请输入正确的邮箱");
jQuery.validator.addMethod("checkpassword",function(value,element){
    return this.optional(element)||/^[a-zA-Z0-9\.]{6,20}$/.test(value);
},"请输入正确的密码");

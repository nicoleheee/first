$(function(){
    $("#addStudent").click(function () {
        $("#student_name").val("");
        $("#student_age").val("");
        $("input[name='sex']").attr("checked",false);
        $("#myModal").modal("show");
    });
    //jquery code

    var student={
        id:0,
        name:'',
        age:0,
        sex:''
    };

    $("#sub").click(function () {
        var name=$("#student_name").val();
        var age=$("#student_age").val();
        var sex=$("input[name='sex']:checked").val();
        student.name=name;
        student.age=age;
        student.sex=sex;
        if (student.id==0){
            $.ajax({
                url:'/add_ajax',
                data:student,
                type:"post",
                success:function (data) {
                    if (data.success){
                        var stu=data.extendData; //model
                        $("#student_table").append("<tr><td>"
                            +stu.id+"</td><td>"
                            +stu.name+"</td><td>"
                            +stu.age+"</td><td>"
                            +stu.sex+"</td><td><a editId='"+stu.id+"'>编辑</a><a stuId='"+stu.id+"'>删除</a></td></tr>")
                    }
                    $("#myModal").modal("hide");
                }
            })
        }else {
            $.ajax({
                url:'/update_ajax',
                data:student,
                type:"post",
                success:function (data) {
                    if (data.success){
                        alert("编辑成功");
                        var stu=data.extendData;
                        $("#stu_"+stu.id).html("<td>"
                            +stu.id+"</td><td>"
                            +stu.name+"</td><td>"
                            +stu.age+"</td><td>"
                            +stu.sex+"</td><td><a editId='"+stu.id+"'>编辑</a><a stuId='"+stu.id+"'>删除</a></td>")
                        $("#myModal").modal("hide");
                        }
                }
            })
        }
    })

    $(document).on("click","[stuId]",function () {
        var stuId=$(this).attr("stuId");
        var $a=$(this);
        $.ajax({
            url:'/delete_ajax',
            data:{id:stuId},
            success:function (data) {
                if (data.success){
                    alert("删除成功");
                    //1
                    $a.parent().parent().remove();
                    //2 $("#stu_"+stuID).remove();
                }

            }
        })

    })

    $(document).on("click","[editId]",function () {
        var id=$(this).attr("editId");
        $.ajax({
            url:'/find_ajax',
            data:{id:id},
            success:function (data) {
                var stu=data.extendData;
                $("#student_name").val(stu.name);
                $("#student_age").val(stu.age);
                $("#input[value='"+stu.sex+"']").prop("checked",true);
                $("#myModal").modal("show");
                student.id=stu.id;
            }
        })
    })
})
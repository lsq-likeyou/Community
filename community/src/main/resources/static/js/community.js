
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    if (!content){
        alert("不能回复空内容!!!")
        return;
    }

    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success:function (response) {
            if (response.code == 200){
                window.location.reload();//刷新页面
                //$("#comment_section").hide();
            }else{
                if (response.code == 2003){     //未登录
                    var isAccepted = confirm(response.message);//弹窗口提示
                    if (isAccepted){//点击确定跳登录页面
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.617e7402874b11d6&redirect_uri=http://localhost:8787/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);//成功后，传closable去index主页面
                    }
                }else{
                    alert(response.message);
                }
            }
        },
        dataType:"json"
    });
    //console.log(questionId);//输出语句
    //console.log(content);
}
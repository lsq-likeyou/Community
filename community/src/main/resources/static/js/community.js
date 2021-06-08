function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容!!!")
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();//刷新页面
                //$("#comment_section").hide();
            } else {
                if (response.code == 2003) {     //未登录
                    var isAccepted = confirm(response.message);//弹窗口提示
                    if (isAccepted) {//点击确定跳登录页面
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.617e7402874b11d6&redirect_uri=http://localhost:8787/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);//成功后，传closable去index主页面
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

//提交一级回复
function post() {
    var questionId = $("#question_id").val();//拿question的id
    var content = $("#comment_content").val();//拿到文本内容
    comment2target(questionId, 1, content);
    //console.log(questionId);//输出语句
    //console.log(content);
}

//提交二级回复
function comment(e) {
    var commentId = e.getAttribute("data-id");               //拿到评论的id
    var content = $("#input-" + commentId).val();                           //拿到文本内容

    comment2target(commentId, 2, content);
}

//展开二级评论
function collapseComments(e) {
    //debugger;
    var id = e.getAttribute("data-id");               //拿到评论的id
    var comments = $("#comment-" + id);                            //获取评论内容id
    //获取评论展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {                                             //返回不为空，点击就折叠
        comments.removeClass("in");                         //完成折叠
        e.removeAttribute("data-collapse");      //移除状态（移除值）
        e.classList.remove("active");                  //移除样式颜色
    } else {                                                      //返回空，点击就展开,并且给值
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {     //有评论直接展示，不重新拉取
            //展开二级评论
            comments.addClass("in");                           //完成展开
            e.setAttribute("data-collapse", "in");//标记评论状态
            e.classList.add("active");                              //添加颜色样式
        } else {   //重新获取
            $.getJSON("/comment/" + id, function (data) {       //获取到评论信息
                $.each(data.data.reverse(), function (index, comment) {               //for-each遍历所有子元素
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        //"style": "line-height: 45px",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");                           //完成展开
                e.setAttribute("data-collapse", "in");//标记评论状态
                e.classList.add("active");                              //添加颜色样式
            });
        }
    }
}

//标签写入
function selectTag(e) {     //将value直接写入标签栏
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();

    if (previous.indexOf(value) == -1) {        //说明标签存在，不再添加
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}
//展示标签导航
function showSelectTag(){          //默认展示，点击标签编辑框展示
    $("#select-tag").show();
}
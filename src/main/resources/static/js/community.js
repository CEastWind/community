/**
 * 提交评论
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

/**
 * 提交回复
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            console.log(response);
            if (response.code == 200) {
                /*这tm这个地方直接刷新重新请求页面了，那这个异步有屁的意义？*/
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=16510a15cdf7de8002be&redirect_url=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

/**
 * 获取二级评论内容并展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    if (comments.hasClass("in")) {
        //关闭二级评论
        comments.toggleClass("in");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //进入页面后点击过评论按钮，会缓存内容。直接展开
            comments.toggleClass("in");
            e.classList.add("active");
        } else {
            //没有点击过评论按钮，只有输入框一个元素，加载请求到的数据
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
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
            });
            comments.toggleClass("in");
            e.classList.add("active");
        }
    }
}

/**
 * 回写选中标签
 * @param value
 */
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previou = $("#tag").val().split(",");
    if (previou.indexOf(value) == -1) {
        if (previou[0] != "") {
            $("#tag").val(previou + ',' + value);
        }else {
            $("#tag").val(value);
        }
    }
}

/**
 * 弹出/关闭tags
 */
function showSelectTag() {
    $("#select-tag").show();
}
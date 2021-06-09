package life.majiang.community.exception;

//拦截question有关的客户端异常
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"问题跑路了,换个打开方式吧!!!"),
    TARGET_PARAM_NOT_FOUND(2002,"没有选中回复目标!!!"),
    NO_LOGIN(2003,"请先登录再回复!!!"),
    SYS_ERROR(2004,"服务端出错,请稍等!!!"),
    TYPE_PARAM_WRONG(2005,"回复类型错误或不存在!!!"),
    COMMENT_NOT_FOUND(2006,"回复不存在!!!"),
    CONTENT_IS_EMPTY(2007,"不能回复空!!!"),
    READ_NOTIFICATION_FAIL(2008,"无法读取他人信息!!!"),
    NOTIFICATION_NOT_FOUND(2009,"通知未找到!!!"),
    Q_AND_C_NOT_FOUND(2010,"既不是问题也不是评论!!!"),

    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer gtecode() {
        return code;
    }

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }
}

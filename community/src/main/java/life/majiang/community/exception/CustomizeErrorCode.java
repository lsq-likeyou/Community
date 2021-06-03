package life.majiang.community.exception;

//拦截question有关的客户端异常
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND("问题跑路了,换个打开方式吧!!!");

    @Override
    public String getMessage() {
        return message;
    }

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }


}

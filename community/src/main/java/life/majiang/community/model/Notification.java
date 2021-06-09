package life.majiang.community.model;

import lombok.Data;

@Data
public class Notification {
    private Long id;
    private Long notifier;//回复人id
    private Long receiver;//回复的id
    private Long outerId;//被回复问题id
    private Integer type;
    private Long gmtCreate;
    private Integer status;//状态
    private String notifierName;
    private String outerTitle;//显示的文本

}

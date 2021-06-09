package life.majiang.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long notifier;//通知人id
    private Long receiver;//回复的id
    private Long outerId;//被回复问题id
    private Integer type;
    private Long gmtCreate;
    private Integer status;//状态
    private String notifierName;//通知的人
    private String outerTitle;//显示的文本

    private String typeName;
}

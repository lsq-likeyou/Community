package life.majiang.community.model;

import lombok.Data;

//自动生成ToString(),Getter(),Setter()等方法
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}

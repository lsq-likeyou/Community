package life.majiang.community.cache;


import life.majiang.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//查询所有标签
public class TagCache {
    public static List<TagDTO> getTag() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        //第一个标签
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("JS", "PHP", "CSS", "HTML", "HTML5", "C", "Java", "Node", "Python", "C++"));
        tagDTOS.add(program);
        //第二个标签
        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("Spring", "Flask", "Laravel", "Struts", "Express", "Django", "YII", "Koa"));
        tagDTOS.add(framework);
        //第三个标签
        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("Mysql", "Redis", "SQL", "Mongodb", "Oracle", "SQLite", "SQLServer", "NOSQL"));
        tagDTOS.add(db);
        //第四个标签
        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Linux", "Docker", "Apache", "Ubuntu", "Unix", "Hadoop", "Windows-server"));
        tagDTOS.add(server);
        //第五个标签
        TagDTO messages = new TagDTO();
        messages.setCategoryName("Music");
        messages.setTags(Arrays.asList("Singer", "Art", "Enjoy", "Listen", "Relax"));
        tagDTOS.add(messages);

        return tagDTOS;
    }

    //获取非法标签
    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");//拆
        List<TagDTO> tagDTOS = getTag();//拿所有list,有两层
        //转换成一层
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        //拿到不存在的标签
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }

}

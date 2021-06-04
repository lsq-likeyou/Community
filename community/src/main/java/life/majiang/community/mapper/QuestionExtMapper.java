package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface QuestionExtMapper {
    @Update("update question set view_count = view_count + #{viewCount,jdbcType=INTEGER} where id = #{id}")
    void incView(Question question);//阅读数

    @Update("update question set comment_count = comment_count + #{commentCount,jdbcType=INTEGER} where id = #{id}")
    int inCommentCount(Question question);//评论数

}



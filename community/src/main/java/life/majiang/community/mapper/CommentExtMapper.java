package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CommentExtMapper {
    @Update("update comment set comment_count = comment_count + #{commentCount,jdbcType=INTEGER} where id = #{id}")
    int inCommentCount(Comment comment);//二级评论数

}



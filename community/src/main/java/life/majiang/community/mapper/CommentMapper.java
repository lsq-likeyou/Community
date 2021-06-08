package life.majiang.community.mapper;


import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(id,parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values (#{id},#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment ThanParentIdAndId(@Param("id") Long id);

    @Select("select * from comment where parent_id = #{id} and type = #{type}")
    List<Comment> listCommentator(@Param("id") Long id,@Param("type") Integer type);

}

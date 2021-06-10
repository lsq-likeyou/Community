package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (id,title,description,gmt_create,gmt_modified,creator,tag) values (#{id},#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);


    @Select("select * from question order by gmt_create DESC limit #{offset}, #{size}")
    List<Question> MapperList1(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    @Select("select * from question where title regexp #{search} order by gmt_create DESC limit #{offset}, #{size}")
    List<Question> MapperList2(@Param("search") String search, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(*) from question")
    Integer count1();      //计算总页码公式
    @Select("select count(*) from question where title regexp #{search}")
    Integer count2(@Param("search") String search);      //计算总页码公式



    @Select("select * from question where creator = #{userId} order by gmt_create DESC limit #{offset}, #{size}")
    List<Question> listByUserId(@Param("userId") Long userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(*) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Long userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Long id);

    @Update("update question set title = #{title},description = #{description},gmt_modified = #{gmtModified},tag = #{tag} where id = #{id}")
    void update(Question question);

    @Select("select * from question where id != #{id} and tag regexp #{tag}")
    List<Question> selectRelated(Question question);
}
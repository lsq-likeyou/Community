package life.majiang.community.mapper;

import life.majiang.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (id,notifier,type,receiver,gmt_create,outer_id,status,notifier_name,outer_title) values (#{id},#{notifier},#{type},#{receiver},#{gmtCreate},#{outerId},#{status},#{notifierName},#{outerTitle})")
    void insert(Notification notification);

    @Select("select count(*) from notification where notifier = #{userId}")
    Integer countByUserId(@Param("userId") Long userId);

    @Select("select * from notification where notifier = #{id} order by gmt_create DESC limit #{offset}, #{size}")
    List<Notification> listByUserId(@Param("id") Long id, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(*) from notification where notifier = #{userId} and status = #{status}")
    Long notificationCount(@Param("userId") Long userId, @Param("status") Integer status);

    @Select("select * from notification where id = #{id}")
    Notification selectByKey(@Param("id") Long id);

    @Update("update notification set status = #{status} where id = #{id}")
    void updateNotification(Notification notification);


}

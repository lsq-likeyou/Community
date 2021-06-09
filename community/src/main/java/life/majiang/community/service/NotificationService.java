package life.majiang.community.service;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.model.Notification;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<NotificationDTO>();

        Integer totaPage;//最后一页页码
        Integer totaCount = notificationMapper.countByUserId(userId);//拿到总页码
        if (totaCount % size == 0) {
            totaPage = totaCount / size;
        } else {
            totaPage = totaCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        } else if (page > totaPage) {
            page = totaPage;
        }
        paginationDTO.setPagination(totaPage, page);

        Integer offset = size * (page - 1);

        List<Notification> notifications = notificationMapper.listByUserId(userId, offset, size);

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        int status = NotificationStatusEnum.UNREAD.getStatus();

        return notificationMapper.notificationCount(userId, status);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
//更新status
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateNotification(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

        return notificationDTO;//拿到问题页面
    }
}

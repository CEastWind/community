package com.liudl.community.service;

import com.liudl.community.dto.NotificationDTO;
import com.liudl.community.dto.PaginationDTO;
import com.liudl.community.enums.NotificationStatusEnum;
import com.liudl.community.enums.NotificationTypeEnum;
import com.liudl.community.exception.CustomizeErrorCode;
import com.liudl.community.exception.CustomizeException;
import com.liudl.community.mapper.NotificationMapper;
import com.liudl.community.mapper.UserMapper;
import com.liudl.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by TwistedFate on 2020/2/3 18:03
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalcount = (int) notificationMapper.countByExample(notificationExample);
        //没有相关数据
        if (totalcount == 0) {
        }
        //算出导航页码条的信息
        paginationDTO.setPagination(totalcount, page, size);

        //offset代表偏移量
        Integer offset = size * (paginationDTO.getPage() - 1);
        NotificationExample notificationExample1 = new NotificationExample();
        notificationExample1.createCriteria().andReceiverEqualTo(userId);
        notificationExample1.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample1, new RowBounds(offset, size));
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
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.NOT_YOUR_NOTIFICATION);
        }


        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);


        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}

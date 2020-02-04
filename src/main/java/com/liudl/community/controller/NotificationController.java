package com.liudl.community.controller;

import com.liudl.community.dto.NotificationDTO;
import com.liudl.community.enums.NotificationTypeEnum;
import com.liudl.community.model.Notification;
import com.liudl.community.model.User;
import com.liudl.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by TwistedFate on 2020/2/4 11:05
 */
@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String read(HttpServletRequest request,
                       @PathVariable(name = "id") Long id) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }
    }
}

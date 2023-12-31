package com.xjob.api;

import com.xjob.persistence.Notification;
import com.xjob.response.NotificationResponse;
import com.xjob.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class NotificationApi {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private NotificationResponse notificationResponse;
	
	@GetMapping("/notification-list")
	public ResponseEntity<Object> getNotifications(@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(name = "page", required = false) Integer page){
		String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			List<Notification> notifications = notificationService.getByReceiverId(uid, limit, page);
			List<Map<String, Object>> notificationMaps = notificationResponse.responseNotificationList(notifications);
			Map<String, Object> data = new HashMap<>();
			data.put("notifications", notificationMaps);
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}

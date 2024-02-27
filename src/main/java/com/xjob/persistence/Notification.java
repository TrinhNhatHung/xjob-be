package com.xjob.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "notification")
@Table(name = "notification")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notification implements Serializable {
	private static final long serialVersionUID = 1234567L;
	@Id
	@Column(name = "notification_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer notificationId;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "uid_from")
	private String uidFrom;
	
	@Column(name = "uid_to")
	private String uidTo;
	
	@Column(name = "send_at")
	@CreationTimestamp
	private LocalDateTime sendAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid_from", referencedColumnName = "uid",insertable = false, updatable = false)
	private User userFrom;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid_to", referencedColumnName = "uid",insertable = false, updatable = false)
	private User userTo;
	
}

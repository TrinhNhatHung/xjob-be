package com.xjob.persistence;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

@Entity(name = "user_history")
@Table(name = "user_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserHistory implements Serializable {
	private static final long serialVersionUID = 1234567L;
	@Id
	@Column(name = "user_history_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userHistoryId;
	
	@Column(name = "keyword")
	private String keyword;
	
	@Column(name = "kind")
	private String kind;
	
	@Column(name = "uid")
	private String uid;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "uid", referencedColumnName = "uid", insertable = false, updatable = false)
	 @JsonIgnore
	private User user;
}

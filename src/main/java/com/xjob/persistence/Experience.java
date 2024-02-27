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

@Table(name = "experience")
@Entity(name = "experience")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Experience implements Serializable {

	private static final long serialVersionUID = 1234567L;

	@Id
	@Column(name = "experience_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer experienceId;

	@Column(name = "skill_name")
	private String skillName;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "detail")
	private String detail;

	@Column(name = "experience_from")
	private Integer experienceFrom;

	@Column(name = "experience_to")
	private Integer experienceTo;

	@Column(name = "uid")
	private String uid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid", referencedColumnName = "uid", insertable = false, updatable = false)
	@JsonIgnore
	private User user;
}

package com.xjob.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "job")
@Entity(name = "job")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Job implements Serializable {

	private static final long serialVersionUID = 1234567L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_id")
	private Integer jobId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "detail")
	private String detail;
	
	@Column(name = "hire_amount")
	private Integer hireAmount;
	
	@Column(name = "hired_amount")
	private Integer hiredAmount;
	
	@Column(name = "payment_kind")
	private String paymentKind;
	
	@Column(name = "term_class")
	private String termClass;
	
	@Column(name = "term_from")
	private Integer termFrom;
	
	@Column(name = "term_to")
	private Integer termTo;
	
	
	@Column(name = "hour_per_week")
	private Integer hourPerWeek;
	
	@Column(name = "price")
	private Integer price;
	
	@CreationTimestamp
	@Column(name = "create_at")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime createAt;
	
	@UpdateTimestamp
	@Column(name = "update_at")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime updateAt;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", referencedColumnName = "uid")
	private User authorId;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "job")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<JobSkill> jobSkills;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "job")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Proposal> proposals;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "job")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<JobStatus> jobStatus;

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
	    if (!(o instanceof Job)) {
	    	return false;
	    }
	    
	    Job that = (Job)o;
	    return this.getJobId().equals(that.getJobId());
	}
	
	@Override
	public int hashCode() {
		return this.jobId.hashCode();
	}

	@Override
	public String toString() {
		return "Job [jobId=" + jobId + ", title=" + title + ", detail=" + detail + ", hireAmount=" + hireAmount
				+ ", hiredAmount=" + hiredAmount + ", paymentKind=" + paymentKind + ", termClass=" + termClass
				+ ", termFrom=" + termFrom + ", termTo=" + termTo + ", hourPerWeek=" + hourPerWeek + ", price=" + price
				+ ", createAt=" + createAt + ", updateAt=" + updateAt + "]";
	}
	
	
}

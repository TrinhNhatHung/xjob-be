package com.xjob.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Table(name = "job_status")
@Entity(name = "job_status")
@Setter
@Getter
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobStatus implements Serializable{
	private static final long serialVersionUID = 1234567L;
	@EmbeddedId
	private Id jobStatusId;
	
	@Column(name = "create_at")
	@CreationTimestamp
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime createAt;
	
	@Column(name = "update_at")
	@UpdateTimestamp
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime updateAt;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("jobId")
	@JoinColumn(name = "job_id",referencedColumnName = "job_id")
	private Job job;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("statusId")
	@JoinColumn(name = "status_id",referencedColumnName = "status_id")
	private Status status;

	public static class Id implements Serializable {

		private static final long serialVersionUID = 1832919694139080251L;

		@Column(name = "job_id")
		private Integer jobId;

		@Column(name = "status_id")
		private Integer statusId;

		public Id() {
			super();
		}

		public Integer getJobId() {
			return jobId;
		}

		public void setJobId(Integer jobId) {
			this.jobId = jobId;
		}

		public Integer getStatusId() {
			return statusId;
		}

		public void setStatusId(Integer statusId) {
			this.statusId = statusId;
		}

	}
}

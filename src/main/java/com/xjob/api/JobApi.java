package com.xjob.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjob.constant.BusinessConst;
import com.xjob.persistence.Job;
import com.xjob.persistence.Skill;
import com.xjob.persistence.User;
import com.xjob.response.JobResponse;
import com.xjob.response.UserResponse;
import com.xjob.service.JobService;
import com.xjob.service.UserService;
import com.xjob.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/job")
public class JobApi {
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JobResponse jobResponse;
	
	@Autowired
	private UserResponse userResponse;
	
	@GetMapping("/job-by-author")
	public ResponseEntity<Object> getJobListByAuthor(@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(name = "page", required = false) Integer page){
		String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			List<Job> jobs = jobService.getByAuthor(uid, limit, page);
			Map<String, Object> data = new HashMap<>();
			Map<String, Object> result;
			data.put("jobs", jobResponse.responseJobList(jobs));
			result = ResponseUtil.createResponse(true, data, null);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/jobs")
	public ResponseEntity<Object> getJobList(@RequestParam(name = "limit", required = false) Integer limit,
											 @RequestParam(name = "page", required = false) Integer page,
											 HttpSession httpSession){
		try {
			httpSession.setAttribute("name", Math.random() * 100);
			List<Job> jobs = jobService.get(limit, page);
			Map<String, Object> data = new HashMap<>();
			Map<String, Object> result;
			data.put("jobs", jobResponse.responseJobList(jobs));
			result = ResponseUtil.createResponse(true, data, null);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@GetMapping("/best-matchs")
	public ResponseEntity<Object> getBestMatchJobList(@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(name = "page", required = false) Integer page){
		
		if (page == null) {
			page = 1;
		}
		
		if (limit == null) {
			limit = 3;
		}
		String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			List<Job> jobs = jobService.getBestMatch(uid, page, limit);
			Map<String, Object> data = new HashMap<>();
			Map<String, Object> result;
			data.put("jobs", jobResponse.responseJobList(jobs));
			result = ResponseUtil.createResponse(true, data, null);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity<Object> getJobBySearch (@RequestParam(name = "search") String search,
										@RequestParam(name = "limit", required = false) Integer limit,
										@RequestParam(name = "page", required = false) Integer page){
		if (page == null) {
			page = 1;
		}
		
		if (limit == null) {
			limit = 10;
		}
		
		try {
			List<Job> jobs = jobService.getBySearch(search, page, limit);
			Map<String, Object> data = new HashMap<>();
			Map<String, Object> result;
			data.put("jobs", jobResponse.responseJobList(jobs));
			result = ResponseUtil.createResponse(true, data, null);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/job-detail")
	public ResponseEntity<Object> getJobDetail(@RequestParam(name = "jobId") Integer jobId){
		String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			Job job = jobService.getByIdAndUid(jobId, uid);
			if (job == null) {
				Map<String, Object> result = new HashMap<>();
				result.put("job", null);
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				Map<String, Object> data = jobResponse.responseJob(job);
				Map<String, Object> result = new HashMap<>();
				result.put("job", data);
				return new ResponseEntity<>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/post-job")
	public ResponseEntity<Object> postJob(@RequestParam(name = "title") String title,
			@RequestParam(name = "detail") String detail,
			@RequestParam(name = "skills") String skillsJson,
			@RequestParam(name = "paymentKind") String paymentKind,
			@RequestParam(name = "price") Integer price,
			@RequestParam(name = "termClass", required = false) String termClass,
			@RequestParam(name = "termFrom", required = false) Integer termFrom,
			@RequestParam(name = "termTo", required = false) Integer termTo,
			@RequestParam(name = "hourPerWeek", required = false) Integer hourPerWeek){
		String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Skill> skills = mapper.readValue(skillsJson, new TypeReference<List<Skill>>() {});
			Job job = new Job();
			job.setTitle(title);
			job.setDetail(detail);
			job.setPaymentKind(paymentKind);
			job.setPrice(price);
			job.setTermClass(termClass);
			job.setTermFrom(termFrom);
			job.setTermTo(termTo);
			job.setHourPerWeek(hourPerWeek);
			job.setHireAmount(0);
			job.setHiredAmount(0);
			User user = new User();
			user.setUid(uid);
			job.setAuthorId(user);
			Integer insertedJobId = jobService.postjob(job, skills);
			Map<String, Object> result = new HashMap<>();
			result.put("jobId", insertedJobId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/update-job")
	public ResponseEntity<Object> updateJob(@RequestParam(name = "jobId") Integer jobId,
			@RequestParam(name = "title",required = false) String title,
			@RequestParam(name = "detail",required = false) String detail,
			@RequestParam(name = "skills",required = false) String skillsJson,
			@RequestParam(name = "paymentKind",required = false) String paymentKind,
			@RequestParam(name = "price",required = false) Integer price,
			@RequestParam(name = "termClass", required = false) String termClass,
			@RequestParam(name = "termFrom", required = false) Integer termFrom,
			@RequestParam(name = "termTo", required = false) Integer termTo,
			@RequestParam(name = "hourPerWeek", required = false) Integer hourPerWeek){
		String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String uid = "f4dd0efd-b136-4718-b6b9-29e837d8cc25";
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Skill> skills = mapper.readValue(skillsJson, new TypeReference<List<Skill>>() {});
			Job job = new Job();
			job.setJobId(jobId);
			job.setTitle(title);
			job.setDetail(detail);
			job.setPaymentKind(paymentKind);
			job.setPrice(price);
			if (paymentKind.equals(BusinessConst.PAYMENT_KIND_HOURLY)) {
				job.setTermFrom(termFrom);
				job.setTermTo(termTo);
				job.setHourPerWeek(hourPerWeek);
				job.setTermClass(termClass);
			}
			User user = new User();
			user.setUid(uid);
			job.setAuthorId(user);
			jobService.updateJob(job, skills);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/delete/{jobId}")
	public ResponseEntity<Object> deleteJob(@PathVariable(name="jobId") String jobId){
		
		try {
			Integer parsedJobId = Integer.parseInt(jobId);
			jobService.closeJob(parsedJobId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/complete/{jobId}")
	public ResponseEntity<Object> completeJob(@PathVariable(name="jobId") String jobId){
		
		try {
			Integer parsedJobId = Integer.parseInt(jobId);
			jobService.completeJob(parsedJobId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/hirings")
	public ResponseEntity<Object> getHiringByJob(@RequestParam(name = "jobId") Integer jobId){
		try {
			List<User> users = userService.getHiredUserByJob(jobId);
			List<Map<String, Object>>data = userResponse.responseFreelancerInfoList(users);
			Map<String, Object> result = new HashMap<>();
			result.put("users", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/my-job")
	public ResponseEntity<Object> getMyJob(){
		String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			List<Job> jobs = jobService.getFreelancerJob(uid);
			List<Map<String, Object>> data = jobResponse.responseMyJobList(jobs, uid);
			Map<String, Object> result = new HashMap<>();
			result.put("jobs", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {      
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

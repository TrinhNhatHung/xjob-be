package com.xjob.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xjob.service.CommonService;
import com.xjob.service.SkillService;

@RestController
@CrossOrigin
public class CommonApi {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SkillService skillService;
	
	@GetMapping("/")
	public String home() {
		return "OK";
	}
	
	@GetMapping("/common/dashboard")
	public ResponseEntity<Object> getDashboard(){
		try {
			Map<String,Object> map = commonService.getDashboard();
			List<Map<String, Object>> mostSkill = skillService.getMostSkil();
			map.put("mostSkill", mostSkill);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

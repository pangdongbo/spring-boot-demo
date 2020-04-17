package com.powersmart.study.mvc;

import com.powersmart.study.jdbc.DemoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@Autowired
	private DemoDAO dao;

	@RequestMapping("/query")
	public String query() {
		return "hello" + dao.query();
	}

}

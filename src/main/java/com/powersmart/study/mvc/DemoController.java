package com.powersmart.study.mvc;

import com.powersmart.study.model.UserVO;
import com.powersmart.study.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class DemoController {

	@Autowired
	private IDemoService service;

	@RequestMapping("/queryall4map")
	public List<Map<String, Object>> queryAll4map() {
		return service.queryAll4Map();
	}

	@RequestMapping("/queryall4vo")
	public List<UserVO> queryAll4vo() {
		return service.queryAll4VO();
	}

	@RequestMapping("/querybyotherds")
	public List<UserVO> queryByOtherDS() {
		return service.queryByOtherDS();
	}

	@RequestMapping("/query")
	public List<UserVO> query(HttpServletRequest request) {
		UserVO vo = new UserVO();
		vo.setAccount(request.getParameter("account"));
		vo.setName(request.getParameter("name"));
		return service.query(vo);
	}

	@RequestMapping("/getuserbyid/{id}")
	public UserVO getUserById(@PathVariable int id) {
		return service.getUserById(id);
	}

	@RequestMapping("/insert")
	public UserVO insert() throws Exception {
		return service.insert(this.crteateUser());
		//return dao.insertByNameParameter(this.crteateUser());
	}

	@RequestMapping("/batchinsert")
	public int[] batchInsert() {
		List<UserVO> lst = new LinkedList<UserVO>();
		for (int i = 0; i < 10; i++) {
			lst.add(this.crteateUser());
		}
		return service.batchInsert(lst);
	}

	/**
	 * 创建一个用户对象
	 * @return
	 */
	private UserVO crteateUser() {
		int random = Math.abs(new Random().nextInt(100000000));
		UserVO vo = new UserVO();
		vo.setName("name " + random);
		vo.setAccount("account " + random);
		vo.setAdress("adress " + random);
		vo.setBirthday(new Date());
		vo.setPassword("pwd " + random);
		vo.setSex(1);
		vo.setPhone("" + random);
		return vo;
	}

}

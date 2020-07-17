package com.powersmart.study.mvc;

import com.powersmart.study.model.UserVO;
import com.powersmart.study.redis.service.StringCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

/**
 * Redis的demo
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

	/**
	 * String 缓存
	 */
	@Autowired
	private StringCacheService<UserVO> string;

	/**
	 * set
	 * @return
	 */
	@RequestMapping("/set")
	public UserVO set() {
		UserVO value = this.crteateUser();
		string.set("1", value);
		return value;
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

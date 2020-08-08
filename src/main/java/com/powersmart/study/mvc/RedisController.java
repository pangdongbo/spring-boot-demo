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
	 * set
	 * @return
	 */
	@RequestMapping("/get")
	public UserVO get() {
		return string.get("1");
	}

	/**
	 * set
	 * @return
	 */
	@RequestMapping("/setnx")
	public String setnx() {
		UserVO obj = new UserVO();
		obj.setId(System.nanoTime());
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					boolean lock = string.setNX("lock", obj, 30000);
					if (lock) {
						long tag = obj.getId();
						try {
							System.err.println(Thread.currentThread().getName() + "获取到锁，搞事情去了。");
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							// 防止任务的执行时间大于key的缓存超时时间而把别人的锁给解了；
							if (tag == obj.getId()) {
								string.del("lock");
							}
						}
					} else {
						System.err.println(Thread.currentThread().getName() + "没有获取到锁。");
					}

				}
			}, "thread-" + i).start();
		}
		return "请看控制台。";
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

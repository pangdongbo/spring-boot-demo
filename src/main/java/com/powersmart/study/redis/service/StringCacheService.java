package com.powersmart.study.redis.service;

import com.powersmart.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 冰飞江南
 * @Title:
 * @history 2020年07月13日 冰飞江南 新建
 * @since JDK1.8
 */
@Service
public class StringCacheService<T> {

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 操作类
	 */
	private volatile ValueOperations<String, T> operations;

	/**
	 * 设置缓存
	 * @param key
	 * @param value
	 */
	public void set(final String key, final T value) {
		getOperations().set(this.getKey(key), value);
	}

	/**
	 * 设置缓存
	 * @param key
	 * @param value
	 */
	public void set(final String key, final T value, long timeout) {
		getOperations().set(this.getKey(key), value, timeout);
	}

	/**
	 * 批量设置值
	 * @param values
	 */
	public void hmSet(final Map<String, T> values) {
		Map<String, T> map = new LinkedHashMap<>();
		for (String key : values.keySet()) {
			map.put(this.getKey(key), values.get(key));
		}
		getOperations().multiSet(map);
	}

	/**
	 * 设置缓存，如果设置成功，则返回true，否则放回false
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public boolean setNX(final String key, final T value, long timeout) {
		return getOperations().setIfAbsent(this.getKey(key), value, timeout, TimeUnit.MILLISECONDS);
	}

	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	public T get(final String key) {
		return getOperations().get(this.getKey(key));
	}

	/**
	 * 根据多个key获取缓存
	 * @param keys
	 * @return
	 */
	public List<T> hmGet(final String... keys) {
		Set<String> keySet = new HashSet<String>();
		for (String key : keys) {
			keySet.add(this.getKey(key));
		}
		return getOperations().multiGet(keySet);
	}

	/**
	 * 根据key删除缓存
	 * @param keys
	 * @return
	 */
	public void del(final String... keys) {
		for (String key : keys) {
			getOperations().getOperations().delete(this.getKey(key));
		}
	}

	/**
	 * 最近字符串
	 * @param key
	 * @return
	 */
	public Integer append(final String key, final String value) {
		return getOperations().append(this.getKey(key), value);
	}

	/**
	 * 先增加1,然后返回增。如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * @param key
	 * @return
	 */
	public Long incr(final String key) {
		return getOperations().increment(this.getKey(key));
	}

	/**
	 * 先增加指定的步长，然后返回。如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * @param key
	 * @return
	 */
	public Long incrBy(final String key, final Long step) {
		return getOperations().increment(this.getKey(key), step);
	}

	/**
	 * 先减少1,然后返回后。如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作
	 * @param key
	 * @return
	 */
	public Long decr(final String key) {
		return getOperations().decrement(this.getKey(key));
	}

	/**
	 * 先减少指定的步长。然后返回，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作
	 * @param key
	 * @return
	 */
	public Long decrBy(final String key, final Long step) {
		return getOperations().decrement(this.getKey(key), step);
	}

	/**
	 * 获取redis操作类
	 * @return
	 */
	private ValueOperations<String, T> getOperations() {
		if (null == operations) {
			synchronized (this) {
				if (null == operations) {
					operations = redisTemplate.opsForValue();
				}
			}
		}
		return operations;
	}

	/**
	 * 获取应用key
	 * @param key
	 * @return
	 */
	private String getKey(String key) {
		return RedisConfig.NAME_SPACE + key;
	}

}

package com.zml.app.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import java.io.Serializable;


@Repository
public class BaseRedisDao<K extends Serializable, V extends Serializable> implements IBaseRedisDao<K, V> {

    @Autowired
    @Qualifier("redisTemplate")
    protected RedisTemplate<K, V> redisTemplate;


    private RedisSerializer<Object> defaultSerializer = new JdkSerializationRedisSerializer();

    private RedisSerializer<Integer> integerSerializer = new GenericToStringSerializer<Integer>(Integer.class);

    /**
     * 向redis里面添加key-value格式的数据
     *
     * @param key   key
     * @param value value
     */
    public void set(final K key, final V value, final long expireTime) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {
                    byte[] rawKey = defaultSerializer.serialize(key);
                    byte[] rawValue = defaultSerializer.serialize(value);
                    connection.set(rawKey, rawValue);
                    connection.expire(rawKey, expireTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    /**
     * increase value of key with 1
     *
     * @param key key
     */
    public void incr(final K key, final long expireTime) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {

                    byte[] rawKey = defaultSerializer.serialize(key);
                    connection.incr(rawKey);
                    connection.expire(rawKey, expireTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    /**
     * 删除指定key内容
     *
     * @param key key
     */
    public void del(final K key) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {
                    byte[] rawKey = defaultSerializer.serialize(key);
                    connection.del(rawKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    /**
     * 根据key从redis里面取出value
     *
     * @param key key
     */
    public V get(final K key) {
        return redisTemplate.execute(new RedisCallback<V>() {
            @SuppressWarnings("unchecked")
            public V doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {
                    byte[] rawKey = defaultSerializer.serialize(key);
                    byte[] rawValue = connection.get(rawKey);
                    return (V) defaultSerializer.deserialize(rawValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }


    public Integer getInteger(final K key) {
        return redisTemplate.execute(new RedisCallback<Integer>() {
            public Integer doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {
                    byte[] rawKey = defaultSerializer.serialize(key);
                    byte[] rawValue = connection.get(rawKey);
                    return integerSerializer.deserialize(rawValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    /**
     * 判读redis里面是否有此key
     *
     * @param key key
     */
    public boolean exists(final K key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {
                    byte[] rawKey = defaultSerializer.serialize(key);
                    return connection.exists(rawKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }


}
package com.example.barter_good.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.util.annotation.Nullable;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.Objects.hash;

/**
 * @author chengke
 * @version 1.0
 * @className RedisBloomFilter
 * @description 布隆过滤器
 * @date 2021/12/11 14:54
 */
@ConfigurationProperties("bloom.filter")
@Component
public class RedisBloomFilter {

    private long expectedInsertions;

    private double fpp;

    @Autowired
    private RedisTemplate redisTemplate;

    private long numBits;

    private int numHashFunctions;

    public long getExpectedInsertions() {
        return expectedInsertions;
    }

    public void setExpectedInsertions(long expectedInsertions) {
        this.expectedInsertions = expectedInsertions;
    }

    public double getFpp() {
        return fpp;
    }

    public void setFpp(double fpp) {
        this.fpp = fpp;
    }

    /**
     * 创建类时调用该方法对属性赋值
     */
    @PostConstruct
    public void init(){
        this.numBits = optimalNumOfBits(expectedInsertions, fpp);
        this.numHashFunctions = optimalNumOfHashFunctions(expectedInsertions,numBits);
    }

    /**
     * 判断key是否存在于集合，是返回true，否则返回false
     * @param key 要判断的数据
     * @return 是否存在
     */
    public boolean isExist(String key){
        long[] indexs = getIndexs(key);
        List list = redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Nullable
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (long index : indexs) {
                    connection.getBit(key.getBytes(),index);
                }
                connection.close();
                return null;
            }
        });
        return !list.contains(false);
    }

    /**
     * 将key存入redis中的bitmap
     * @param key 要存入的数据
     */
    public void put(String key){
        long[] indexs = getIndexs(key);
        redisTemplate.executePipelined(new RedisCallback<>() {

            @Nullable
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (long index : indexs) {
                    connection.setBit(key.getBytes(), index, true);
                }
                connection.close();
                return null;
            }
        });
    }

    /**
     * 根据key获取bitmap下标
     * @param key 要存入的数据
     * @return bitmap下标数组
     */
    private long[] getIndexs(String key){
        long hash1 = hash(key);
        long hash2 = hash1 >>> 16;
        long[] res = new long[numHashFunctions];

        for (int i = 0; i < numHashFunctions; i++) {
            long combineHash = hash1 + i*hash2;
            if(combineHash<0){
                combineHash = ~combineHash;
            }

            res[i] = combineHash%numBits;
        }

        return res;
    }

    /**
     * 计算hash函数个数
     * @param n n
     * @param m m
     * @return int
     */
    private int optimalNumOfHashFunctions(long n,long m){
        return Math.max(1,(int) Math.round((double) m / n*Math.log(2)));
    }

    /**
     * 计算bit数组长度
     * @param n n
     * @param p p
     * @return long
     */
    private long optimalNumOfBits(long n,double p){
        if(p == 0){
            p = Double.MIN_VALUE;
        }

        return (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }
}
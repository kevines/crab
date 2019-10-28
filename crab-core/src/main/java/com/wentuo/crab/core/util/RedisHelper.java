
package com.wentuo.crab.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Description: 通用Redis帮助类
 * User: zhouzhou
 * Date: 2018-09-05
 * Time: 15:39
 */
@Component
public class RedisHelper {

    public static final String LOCK_PREFIX = "redis_lock";
    public static final int LOCK_EXPIRE = 300; // ms

    @Autowired
    RedisTemplate redisTemplate;


    /**
     *  最终加强分布式锁
     *
     * @param key key值
     * @return 是否获取到
     */
    public boolean lock(String key){
        String lock = LOCK_PREFIX + key;
        // 利用lambda表达式
        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {

            long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
            Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());


            if (acquire) {
                return true;
            } else {

                byte[] value = connection.get(lock.getBytes());

                if (Objects.nonNull(value) && value.length > 0) {

                    long expireTime = Long.parseLong(new String(value));

                    if (expireTime < System.currentTimeMillis()) {
                        // 如果锁已经过期
                        byte[] oldValue = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
                        // 防止死锁
                        return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }

//   public void test(){
//       boolean lock = redisHelper.lock(key);
//       String tradeNo;
//       if (lock) {
//           // 执行逻辑操作
//           tradeNo =  this.orderService.add(orderParam);
//           redisHelper.delete(key);
//       } else {
//           // 设置失败次数计数器, 当到达5次时, 返回失败
//           int failCount = 1;
//           while(failCount <= 10){
//               // 等待100ms重试
//               try {
//                   Thread.sleep(300l);
//               } catch (InterruptedException e) {
//                   e.printStackTrace();
//               }
//               if (redisHelper.lock(key)){
//                   // 执行逻辑操作
//                   tradeNo =  this.orderService.add(orderParam);
//                   redisHelper.delete(key);
//                   return WTResponse.success(tradeNo);
//               }else{
//                   failCount ++;
//               }
//           }
//           throw new BizException(BizExceptionEnum.ERROR.getCode(),"现在创建的人太多了, 请稍等再试");
//       }
//   }

    /**
     * 删除锁
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

}

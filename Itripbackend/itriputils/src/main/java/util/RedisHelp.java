package util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RedisHelp {
    public static void main(String[] args) {
        RedisHelp obj=new RedisHelp();
        obj.SetData("cat","hua");
    }
    public void SetData(String key,String Value)
    {
        Jedis jedis=new Jedis("127.0.0.1");
        jedis.auth("123456");
        jedis.setex(key,60*60*2,Value);
    }

    public String Getdata(String key)
    {
        Jedis jedis=new Jedis("127.0.0.1");
        jedis.auth("123456");
        return jedis.get(key);
    }
}

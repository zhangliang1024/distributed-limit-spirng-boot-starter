# Distributed Limit 
> 基于Guava RateLimiter令牌桶算法实现 和 Reids实现的分布式限流组件。本地使用RateLimiter，分布式环境使用Redis

---

### 一、说明：
```markdown
1. 保护高并发系统的三把利器：缓存、降级、限流。限流在很多场景中被用来控制并发和请求量，保护自身和下游系统不被流量冲垮。典型如：秒杀系统

```

### 二、使用
> pom.xml
```xml
<dependency>
    <groupId>com.zhliang.pzy</groupId>
    <artifactId>distributed-limit-spirng-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

> application.yaml
```yaml
server:
  port: 8090
  redis:
    host: localhost
    port: 6379
    database: 0
pzy:
  redis:
    limit:
      enabled: true
      # 指定类型：local redis,默认：local
      type: redis
```

> 测试接口
```java
@RestController
public class LimitController {

    @GetMapping("limit")
    @RedisLimit(prefix = "name",expire = 1,count = 10)
    public String limit(@RedisParam String name,@RedisParam int age){
        return "hello " + name;
    }
}
```

### 三、支持扩展限流类型
> 实现限流类型接口和具体的执行接口
```java
/**
 * 限流类型接口
 */
@Service
public interface LimitTypeService {
    String getType();
}

/**
 * 限流执行接口
 */
public interface LimitHandler {

    boolean tryAcquire(String key, long limitCount, String description, long expire, TimeUnit timeUnit);
}

```

> 通过注入自定义实现，代替默认实现
```java
@Bean
public LimitHandler limitHandler(LimitTypeService limitTypeService){
}

@Bean
public LimitTypeService limitTypeService(){
}
```

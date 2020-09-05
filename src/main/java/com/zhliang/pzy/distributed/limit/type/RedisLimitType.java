package com.zhliang.pzy.distributed.limit.type;

/**
 */
public class RedisLimitType extends LimitTypeAbstractService {

    public RedisLimitType(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return LimitType.REDIS.getName();
    }

}




package com.zhliang.pzy.distributed.limit.handler;

import java.util.concurrent.TimeUnit;

public interface LimitHandler {

    boolean tryAcquire(String key, long limitCount, String description, long expire, TimeUnit timeUnit);
}

package com.zhliang.pzy.distributed.limit.type;

/**
 */
public class LocalLimitType extends LimitTypeAbstractService{

    public LocalLimitType(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return LimitType.LOCAL.getName();
    }
}

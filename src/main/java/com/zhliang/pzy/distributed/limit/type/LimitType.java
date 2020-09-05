package com.zhliang.pzy.distributed.limit.type;

/**
 * 支持类型枚举
 */
public enum LimitType implements LimitTypeService{

    LOCAL("local"),
    REDIS("redis")
    ;
    private String name;

    LimitType(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

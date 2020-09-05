package com.zhliang.pzy.distributed.limit.type;

/**
 */
public abstract class LimitTypeAbstractService implements LimitTypeService{

    private String type;

    LimitTypeAbstractService(String type){
        this.type = type;
    }

    @Override
    public abstract String getType();
}

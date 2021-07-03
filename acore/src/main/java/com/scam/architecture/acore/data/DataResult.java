package com.scam.architecture.acore.data;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2021/4/16 19:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/16 19:50
 * @Warn: 更新说明
 * @Version: 1.0
 */
public abstract class DataResult<T> {

    protected final T data;
    protected final DataSource dataSource;

    public DataResult(T data, DataSource dataSource){
        this.data = data;
        this.dataSource = dataSource;
    }

    public interface Result<T>{

        void onComplete(DataResult<T> data);

    }
}

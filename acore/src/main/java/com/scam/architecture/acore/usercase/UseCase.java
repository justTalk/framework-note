package com.scam.architecture.acore.usercase;

/**
 * @Description: 逻辑单元
 * @Author: Andy
 * @CreateDate: 2021/4/16 19:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/16 19:36
 * @Warn: 更新说明
 * @Version: 1.0
 */
public abstract class UseCase<R extends UseCase.RequestValue, O extends UseCase.Response> {

    protected Callback<O> callback;
    protected R requestValue;

    public abstract void executeUseCase(R requestValue);

    final void run() {
        executeUseCase(requestValue);
    }

    public void setRequestValue(R requestValue) {
        this.requestValue = requestValue;
    }

    public R getRequestValue() {
        return requestValue;
    }

    public void setCallback(Callback<O> callback){
        this.callback = callback;
    }

    public Callback<O> getCallback() {
        return callback;
    }


    public interface RequestValue{

    }

    public interface Response{

    }

    public interface Callback<O extends UseCase.Response>{

        void onCompleted(O o);

        void onError();
    }
}

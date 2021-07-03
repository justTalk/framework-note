package com.scam.architecture.acore.usercase;

import androidx.annotation.NonNull;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2021/4/16 20:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/16 20:10
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class UseCaseHandler {

    private UserCaseScheduler scheduler;

    public UseCaseHandler(@NonNull UserCaseScheduler userCaseScheduler){
        this.scheduler = userCaseScheduler;
    }

    public <T extends UseCase.RequestValue, R extends UseCase.Response> void execute(
            final UseCase<T, R> useCase, T values, UseCase.Callback<R> callback) {
        useCase.setRequestValue(values);
        useCase.setCallback(new UICallbackWrapper<R>(callback, this));
        scheduler.execute(useCase::run);
    }

    private <V extends UseCase.Response> void notifyResponse(final V response,
                                                                  final UseCase.Callback<V> useCaseCallback) {
        scheduler.notifyResponse(response, useCaseCallback);
    }

    private <V extends UseCase.Response> void notifyError(
            final UseCase.Callback<V> useCaseCallback) {
        scheduler.onError(useCaseCallback);
    }


    private class UICallbackWrapper<O extends UseCase.Response> implements UseCase.Callback<O>{

        private final UseCase.Callback<O> callback;
        private final UseCaseHandler useCaseHandler;

        private UICallbackWrapper(UseCase.Callback<O> callback, UseCaseHandler useCaseHandler){
            this.callback = callback;
            this.useCaseHandler = useCaseHandler;
        }

        @Override
        public void onCompleted(O o) {
            useCaseHandler.notifyResponse(o, callback);
        }

        @Override
        public void onError() {
            useCaseHandler.notifyError(callback);
        }
    }
}

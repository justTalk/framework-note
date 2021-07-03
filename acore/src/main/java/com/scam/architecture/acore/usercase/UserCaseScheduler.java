package com.scam.architecture.acore.usercase;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2021/4/16 20:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/16 20:17
 * @Warn: 更新说明
 * @Version: 1.0
 */
interface UserCaseScheduler {

    void execute(Runnable runnable);

    <V extends UseCase.Response> void notifyResponse(final V response,
                                                          final UseCase.Callback<V> useCaseCallback);

    <V extends UseCase.Response> void onError(
            final UseCase.Callback<V> useCaseCallback);

}

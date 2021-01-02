package com.example.framework_note.databinding;

import android.view.View;

import androidx.databinding.ObservableField;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2020/12/2 20:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/2 20:31
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class User {
    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();

    public void onClick(View view){
        firstName.set("adadadad");
    }
}

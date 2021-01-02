package com.example.framework_note.databinding;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.framework_note.BR;
import com.example.framework_note.R;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2020/12/2 20:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/2 20:34
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class DataBinddingAct extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding_layout);
        User user = new User();
        viewDataBinding.setVariable(BR.user, user);
        user.firstName.set("ming");
        user.lastName.set("aaaa");
    }
}

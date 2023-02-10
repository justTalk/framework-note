package com.example;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2021/9/22 16:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/9/22 16:01
 * @Warn: 更新说明
 * @Version: 1.0
 */
@GlideExtension
public class TestGlideType {

  private TestGlideType(){

  }

  @GlideOption
  public static void miniThumb(RequestOptions options) {
    Log.d("LMM", "miniThumb: ");
  }

  @GlideType(GifDrawable.class)
  public static void asMyGif(RequestBuilder<GifDrawable> requestBuilder) {
    Log.d("LMM", "asMyGif: ");
  }

  @GlideType(Drawable.class)
  public static void asMyDrawable(RequestBuilder<Drawable> requestBuilder) {
    Log.d("LMM", "asMyDrawable: ");
  }

  @GlideType(BitmapDrawable.class)
  public static void asBitmapTest(RequestBuilder<BitmapDrawable> requestBuilder){
    Log.d("LMM", "asBitmap: ");
  }

}

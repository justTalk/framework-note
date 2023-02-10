package com.example.framework_note

import android.util.Log
import java.io.File

/**
 * <pre>
 *     author : scam
 *     e-mail : mingming.liu@quvideo.com
 *     time   : 2022/05/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class TestKt {

  fun testSplit(){
    val originStr =
      "/data/user/0/com.videoeditorpro.android/files/demovvc/5071F39630BF0BE6101E017F7184AB59/media/0Más borracho de….m4a"
    val split = "/var/mobile/Containers/Data/Application/55382166-192A-457D-892D-5227F3F03543/tmp/VVCExport/20220502003720/media/0Más borracho de….m4a".split(File.separator)
      val name = split.last()
    Log.d("LMM", "kt is " + originStr.endsWith(split[split.size - 1]))
    Log.d("LMM", "kt is " + originStr.endsWith(name))
  }
}
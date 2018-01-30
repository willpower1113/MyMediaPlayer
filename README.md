# MyMediaPlayer 简单的android视频播放器
### GitHub地址：https://github.com/willpower1113/MyMediaPlayer
### JitPack地址：https://jitpack.io/#willpower1113/MyMediaPlayer
### 使用方法：
< allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
} />
PlayVideoActivity.start(context,url,title);
#### @params 
##### context:当前Activity的Context
##### url:文件Url或者path
##### title:文件名称（可选）


# Firebase notification with android
![](https://github.com/yc86209/FirebaseNotification/blob/master/image/notification.jpg) 
Firebase Cloud Messaging[ (FCM)](https://firebase.google.com/docs/cloud-messaging/) is a cross-platform messaging solution that lets you reliably deliver messages at no cost.

因版本變動過於頻繁，故紀錄使用方法及相關資訊。

# Firebase
首先，你需要在 [firebase](https://console.firebase.google.com/) 新增專案並註冊。

> 註冊應用程式 `'com.company.mynotificantion'`

> 下載設定檔 `'google-services.json'`

>新增 Firebase SDK

`'專案層級的 build.gradle (<專案>/build.gradle)：'`

     buildscript {
	     dependencies {
		     // Add this line
		     classpath ‘com.google.gms:google-services:4.0.1’ 
	     } 
     }

`'應用程式層級的 build.gradle (<專案>/<應用程式模組>/build.gradle)：'`

    dependencies {
	    // Add this line
        implementation 'com.google.firebase:firebase-messaging:17.0.0' 
    } 
    … 
    // Add to the bottom of the file
    apply plugin: ‘com.google.gms.google-services

> 執行應用程式以驗證是否安裝成功

# Android studio
>新增 FirebaseInstanceIdService

    public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {  
        @Override  
      public void onTokenRefresh() {  
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();  
            Log.e("FCM", "refresh token:"+refreshedToken);  
        }  
    }
    
常用在單一裝置使用，只有初次安裝或者token過期才會被呼叫。
>新增 FirebaseInstanceIdService

    public class MyFirebaseMessagingService extends FirebaseMessagingService {  
        @Override  
      public void onMessageReceived(RemoteMessage remoteMessage) {  
            super.onMessageReceived(remoteMessage);  
            Log.e("message"
            , Objects.requireNonNull(remoteMessage.getNotification()).getBody());  
            Set<String> keys = remoteMessage.getData().keySet();  
            for(String s:keys) {  
                Log.e("fcm data:", remoteMessage.getData().get(s));  
            }  
        }  
    }
App有分為前景及背景。

|App state  | Notification | Data | Both |
|--|--|--|--|
| Foreground |onMessageReceived  | onMessageReceived | onMessageReceived |
| Background| System tray | onMessageReceived  | Notification: system tray Data: in extras of the intent.|
> Edit the app manifest `'AndroidManifest.xml'`
  

    <service android:name=".MyFirebaseInstanceIdService">  
        <intent-filter>  
            <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />  
        </intent-filter>  
    </service>  
    
    <service android:name=".MyFirebaseMessagingService">  
        <intent-filter>  
            <action android:name="com.google.firebase.MESSAGING_EVENT" />  
        </intent-filter>  
    </service>
    
    <meta-data  
    android:name="com.google.firebase.messaging.default_notification_icon"  
    android:resource="@drawable/ic_stat_ic_notification" />  

    <meta-data  
    android:name="com.google.firebase.messaging.default_notification_color"  
    android:resource="@color/colorAccent"  />
# 結論
其實不難，
大概不到半小時就可以弄完，
主要還是看需求是什麼，
在經由Server發送相關Data，
其中icon除了api版本會不同之外，
還會因不同廠牌及解析度不同而異。

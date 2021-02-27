package com.solutzoid.codefestshopadminapp.FCMHelper;


import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class FcmClient extends AsyncTask<String,String,String> {

    public void sendFCMMessage(String token,String riderDetails,String title){

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{ \r\n\"to\":\""+token+"\", \r\n \"notification\" : {\r\n \"sound\":\"default\",\r\n \"body\" : \""+riderDetails+"\",\r\n \"title\":\""+title+"\",\r\n \"content_available\" : true,\r\n \"priority\" : \"high\"\r\n }\r\n}");
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .method("POST", body)
                .addHeader("Authorization", "key=AAAApRSVxVA:APA91bGsKFz1SVyTFadcSF-rzeG_Xu8U1xUmRDqnQ5PlAYxgieBBoSKgPPeYspKLDFY5-DKh6zUcro98T6vP-31USx6_L6_TI3RMdpiTa4Zsar7tItGBAeA6zKN3ej9JR-RhcN4gRute")
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    protected String doInBackground(String... s) {
        Log.d("FCMTAG","FCM : "+s[0]);
        Log.d("FCMTAG","FCM : "+s[1]);
        sendFCMMessage(s[0],s[1],s[2]);
        return null;
    }
}

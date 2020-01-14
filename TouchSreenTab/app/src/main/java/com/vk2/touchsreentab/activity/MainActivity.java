package com.vk2.touchsreentab.activity;import android.content.Intent;import android.net.Uri;import android.os.Build;import android.os.Bundle;import android.util.DisplayMetrics;import android.util.Log;import android.view.View;import android.widget.ImageView;import android.widget.Toast;import com.bumptech.glide.Glide;import com.vk2.touchsreentab.R;import com.vk2.touchsreentab.api.ApiController;import com.vk2.touchsreentab.download.FileLoaderTask;import com.vk2.touchsreentab.download.ImageLoaderTask;import com.vk2.touchsreentab.utils.OnDoubleClickListener;import com.vk2.touchsreentab.utils.YtExtractor;import com.vk2.touchsreentab.view.DialogBanner;public class MainActivity extends BaseActivity {    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();        int height = displayMetrics.heightPixels;        int width = displayMetrics.widthPixels;        float density = displayMetrics.density;        Log.d("TAG", "Density: " + density + " Height: " + height + " Width: " + width);        //new ImageLoaderTask(this).execute("http://vksinger.oss-ap-southeast-1.aliyuncs.com//1823.png?Expires=1572921580&OSSAccessKeyId=LTAI4FehsPqAwTu18gBVbiMB&Signature=pAyTvoOK3WBKRzcXVxssrrrzA6k%3D");        //new FileLoaderTask().execute("https://firebasestorage.googleapis.com/v0/b/kingpes-download.appspot.com/o/intro%2Fintro_0024_Layer%201.jpg?alt=media&token=a3655aae-cdd5-4c70-a9d0-85e5e331e2b9");        //new FileLoaderTask().execute("http://storage.fshare.vn/ktvcloud/10/101037.ts");        //new DialogBanner.Builder(this).setUrl("https://firebasestorage.googleapis.com/v0/b/kingpes-download.appspot.com/o/intro%2Fintro_0024_Layer%201.jpg?alt=media&token=a3655aae-cdd5-4c70-a9d0-85e5e331e2b9").build();        //ApiController.getInstance().getLinkYoutube(this,"https://www.youtube.com/watch?v=t-wBwTEp9TY");        ImageView img = findViewById(R.id.img);//        Uri u = ImageLoaderTask.getImageUri("182d3");//        String s = u.toString();//        Glide.with(this).load(ImageLoaderTask.getImageUri("182d3")).into(img);    }    public void single(View view) {        startActivity(new Intent(this, SingleMode.class));        finish();    }    public void dual(View view) {        startActivity(new Intent(this, DualMode.class));        finish();    }}
package sheridan.ca.android_camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAPTURE = 1;
    private int MY_REQUEST_CODE = 1;

    ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgPhoto = (ImageView) findViewById(R.id.imgView);
        Button btnClick = (Button) findViewById(R.id.button);

        if (!checkCamera()) {
            btnClick.setEnabled(false);
        }


        btnClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
    }

    boolean checkCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY); //this will return any camera if exist
    }
    //right click on the white space then generate then override method. This event handler will


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, REQUEST_CAPTURE);

            }
        }
    }

    public void takePicture(){
        if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.CAMERA}, MY_REQUEST_CODE);
        }
        else{
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, REQUEST_CAPTURE);
        }

    }
}
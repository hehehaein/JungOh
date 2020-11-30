package com.example.jung_oh;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddClothesActivity extends AppCompatActivity {

    //카메라 권한 체크 후 권한 결정
    final private static String TAG = "Hyein";

    //썸네일 사진 요청변수
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    Button btn_photo;
    ImageView iv_photo;
    Button btn_assign;
    Button btn_Outer;
    Button btn_Bottom;
    Button btn_Top;
    Button btn_Accessary;

    //옷 종류 선택 리스트
    static final String[] CLOSET_MENU = {"Outer", "Top", "Bottom", "Accessary"};

    //옷 종류 선택시 세부항목 리스트
    static final String[] OUTER_LIST = {"cardigan", "leaderJacket", "lightweightPadding", "longPadding", "mustang", "windBreaker", "bbogul", "bbogulVest",
            "shirt", "yasang", "woolenCoat", "jacket", "jersey", "denimJacket", "coat", "trenchCoat", "padding", "paddingVest",
            "paddingCoat", "flightJacket", "hoodZipup"};

    static final String[] TOP_LIST = {"longSleve", "sleeveless", "mentomen", "Tshirt", "bluse", "shirt", "anorak", "onePiece", "hoodT"};
    static final String[] BOTTOM_LIST = {"trouser", "longSkirt", "pants", "slacks", "widePants", "jeans", "skirt", "trainingPants", "trainingSuit"};
    static final String[] ACCESSARY_LIST = {"muffler", "strawHat", "packpack", "bucketHat", "beret", "cap", "beanie", "sunglasses", "scarf", "glasses", "parasol",
            "ecobag", "rainCoat", "umbrella", "gloves", "rainBoots", "crossBag", "bigBag", "woolenHat"};

    private Uri filePath;

    final static int TAKE_PICTURE = 1;

    //경로변수와 사진쵤영 요청변수 생성
    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);

        iv_photo = findViewById(R.id.iv_photo);
        btn_photo = findViewById(R.id.btn_photo);
        btn_assign = findViewById(R.id.btn_assign);
        btn_Top = findViewById(R.id.btn_Top);
        btn_Bottom = findViewById(R.id.btn_Bottom);
        btn_Outer = findViewById(R.id.btn_Outer);
        btn_Accessary = findViewById(R.id.btn_Accessary);

        // ArrayAdapter 생성. 아이템 View를 선택(multiple choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, CLOSET_MENU);
        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.list_closet);
        listview.setAdapter(adapter);
        //리스트 선택 가능하게 만들어줌
        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position);

                // TODO : use strText
            }
        });*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(AddClothesActivity.this, new String[]
                        {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        //버튼 누르면 촬영하는 부분을 dispatchTakePictureIntent를 불러오게함
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_photo:
                        dispatchTakePictureIntent();
                        break;
                }
            }
        });

        btn_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        btn_Outer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), closet_card_view.class);
                intent.putExtra(Intent.EXTRA_TEXT, OUTER_LIST);
                startActivity(intent);
            }
        });

        btn_Top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), closet_card_view.class);
                intent.putExtra(Intent.EXTRA_TEXT, TOP_LIST);
                startActivity(intent);
            }
        });

        btn_Bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), closet_card_view.class);
                intent.putExtra(Intent.EXTRA_TEXT, BOTTOM_LIST);
                startActivity(intent);
            }
        });

        btn_Accessary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), closet_card_view.class);
                intent.putExtra(Intent.EXTRA_TEXT, ACCESSARY_LIST);
                startActivity(intent);
            }
        });
    }
    //옷 종류 세부사항 고르는 화면 전환
    private void showAlertDialog(final String[] text){
        Intent intent = new Intent(getApplicationContext(), closet_card_view.class);
        intent.putExtra("list", text);
        startActivity(intent);
    }
    // 사진 촬영 후 썸네일만 띄워줌. 이미지를 파일로 저장해야 함
    //촬영한 사진을 이미지 파일로 저장하는 함수
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile( imageFileName,".jpg", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // 카메라 인텐트 실행하는 부분
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            try { photoFile = createImageFile(); }
            catch (IOException ex) { }
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.jung_oh.FileProvider", photoFile);
                //사진을 firebase에 저장하기위한 경로 저장
                filePath = photoURI;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    // 카메라로 촬영한 영상을 가져오는 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        //썸네일을 저장후 화면에 사진 띄우기
                        File file = new File(mCurrentPhotoPath);
                        //filePath = Uri.fromFile(file);
                        Bitmap bitmap;
                        if (Build.VERSION.SDK_INT >= 29) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                            try {
                                bitmap = ImageDecoder.decodeBitmap(source);
                                if (bitmap != null) { iv_photo.setImageBitmap(bitmap); }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                                if (bitmap != null) { iv_photo.setImageBitmap(bitmap); }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    //upload the file
    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //storage
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";
            StorageReference storageRef;
            //storage 주소와 폴더 파일명을 지정해 준다.
            String uid = user.getEmail();
            String cloth_type = closet_card_view.strText;
            Toast.makeText(getApplicationContext(), cloth_type, Toast.LENGTH_SHORT).show();
            if(cloth_type.equals("muffler") || cloth_type.equals("strawHat") || cloth_type.equals("packpack") || cloth_type.equals("bucketHat") || cloth_type.equals("beret") ||
                    cloth_type.equals("cap") || cloth_type.equals("beanie") || cloth_type.equals("sunglasses") || cloth_type.equals("scarf" ) || cloth_type.equals("glasses")
                    || cloth_type.equals("parasol") || cloth_type.equals("ecobag") || cloth_type.equals("rainCoat" )|| cloth_type.equals("umbrella") || cloth_type.equals("gloves")
                    || cloth_type.equals("rainBoots") || cloth_type.equals("crossBag") || cloth_type.equals("bigBag") || cloth_type.equals("woolenHat"))
                    {storageRef = storage.getReference( uid + "/Accessary/" + filename);}
            else if(cloth_type.equals("longSleve") || cloth_type.equals("sleeveless") || cloth_type.equals("mentomen") || cloth_type.equals("Tshirt") ||
                    cloth_type.equals("bluse") || cloth_type.equals("shirt") || cloth_type.equals("anorak") || cloth_type.equals("onePiece") || cloth_type.equals("hoodT"))
                    {storageRef = storage.getReference( uid + "/Top/" + filename);}
            else if(cloth_type.equals("trouser") || cloth_type.equals("longSkirt") || cloth_type.equals("pants") || cloth_type.equals("slacks") || cloth_type.equals("widePants")||
                    cloth_type.equals("jeans") || cloth_type.equals("skirt") || cloth_type.equals("trainingPants") || cloth_type.equals("trainingSuit"))
                    {storageRef = storage.getReference( uid + "/Bottom/" + filename);}
            else {storageRef = storage.getReference( uid + "/Outer/" + filename);}
            //StorageReference imagesRef = storageRef.child("images");
            //올라가거라...
            UploadTask uploadTask = storageRef.putFile(filePath);
                    //성공시
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                        Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                    }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }


}
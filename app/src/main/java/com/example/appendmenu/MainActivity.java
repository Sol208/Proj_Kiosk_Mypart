package com.example.appendmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appendmenu.model.MenuList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button mainBtn, sideBtn, etcBtn, insertBtn, modifyPageBtn;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    List<MenuList> mainList;
    List<MenuList> sideList;
    List<MenuList> etcList;
    MainFragMent mainFrag;
    static MenuList menuList;
    private final int GALLERY_CODE = 1112;
    static String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBtn = (Button) findViewById(R.id.mainBtn);
        sideBtn = (Button) findViewById(R.id.sideBtn);
        etcBtn = (Button) findViewById(R.id.etcBtn);
        insertBtn = (Button) findViewById(R.id.insertBtn);
        modifyPageBtn = (Button) findViewById(R.id.modifyPageBtn);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AppendFragment appendFrag = new AppendFragment(MainActivity.this, menuList);
        transaction.replace(R.id.fragmentBoard02, appendFrag);
        transaction.commitAllowingStateLoss();

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppendFragment appendFrag = new AppendFragment(MainActivity.this, menuList);
                RViewChange(appendFrag, null);
            }
        });

        modifyPageBtn.setOnClickListener(   new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuList == null) {
                    Toast.makeText(MainActivity.this, "????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    ModifyFragment modifyFrag = new ModifyFragment(MainActivity.this, menuList);
                    RViewChange(null, modifyFrag);
                }

            }
        });

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAll("main");
            }
        });

        sideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAll("side");
            }
        });

        etcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAll("??????");
            }
        });

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        ????????? ????????? ?????? ?????? ????????? ?????? ????????? Activity??? ?????? ?????????
    }

    public void selectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            sendPicture(data.getData()); //??????????????? ????????????
        }
    }

    private void sendPicture(Uri imgUri) {
        imgPath = getRealPathFromURI(imgUri); // path ??????
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);//????????? ?????? ??????????????? ??????
        try {
            if ((AppendFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentBoard02) != null) {
                AppendFragment appendFrag = (AppendFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentBoard02);
                appendFrag.changeAppendFragImg(bitmap, exifDegree);
                appendFrag.changeAppendFragImgText(imgPath);
                appendFrag.appendFragState = false;
            }
        } catch (ClassCastException c) {
            c.printStackTrace();
        }
        try {
            if ((ModifyFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentBoard02) != null) {
                ModifyFragment modifyFrag = (ModifyFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentBoard02);
                modifyFrag.changeModifyFragImg(bitmap, exifDegree);
                modifyFrag.changeModifyFragImgText(imgPath);
                modifyFrag.modifyFragState = false;
            }
        } catch (ClassCastException c) {
            c.printStackTrace();
        }


    }

    //????????? ?????? ????????? ????????? ???????????? ??????
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //????????? ?????? ?????? ??????
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // ??????
                    Log.d("MainActivity", "?????? ?????? : " + permissions[i]);
                }
            }
        }
    }

    public void checkSelfPermission() {
        String temp = "";

        // ?????? ?????? ?????? ??????
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }

        // ?????? ?????? ?????? ??????
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }

        if (TextUtils.isEmpty(temp) == false) {
            //?????? ??????
            ActivityCompat.requestPermissions(this, temp.trim().split(" "), 1);
        } else {
            //?????? ?????? ??????
            Toast.makeText(this, "????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
        }
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public Bitmap rotate(Bitmap src, float degree) {

        // Matrix ?????? ??????
        Matrix matrix = new Matrix();
        // ?????? ?????? ??????
        matrix.postRotate(degree);
        // ???????????? Matrix ??? ???????????? Bitmap ?????? ??????
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    public void selectAll(String btnType) {
        List<MenuList> menuLists = new ArrayList<MenuList>();
        firestore.collection("Enterprise_Users").document("a").collection("MenuList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            menuLists.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Long menuNum = (Long) document.getData().get("MenuNum");
                                String menuName = (String) document.getData().get("MenuName");
                                String menuPrice = (String) document.getData().get("MenuPrice");
                                String menuDetail = (String) document.getData().get("MenuDetail");
                                String menuCG = (String) document.getData().get("MenuCG");
                                Boolean stockState = (Boolean) document.getData().get("StockState");
                                String optSize01 = (String) document.getData().get("OptSize01");
                                String optPrice01 = (String) document.getData().get("OptPrice01");
                                String optSize02 = (String) document.getData().get("OptSize02");
                                String optPrice02 = (String) document.getData().get("OptPrice02");
                                String optSize03 = (String) document.getData().get("OptSize03");
                                String optPrice03 = (String) document.getData().get("OptPrice03");
                                String optKind01 = (String) document.getData().get("OptKind01");
                                String optPrice04 = (String) document.getData().get("OptPrice04");
                                String optKind02 = (String) document.getData().get("OptKind02");
                                String optPrice05 = (String) document.getData().get("OptPrice05");
                                String imgPath = (String) document.getData().get("ImagePath");

                                menuLists.add(new MenuList(menuNum, menuName, menuPrice, menuDetail, menuCG, stockState, optSize01, optPrice01, optSize02, optPrice02, optSize03, optPrice03, optKind01, optPrice04, optKind02, optPrice05, imgPath));
                            }
                            System.out.println(menuLists);
                            mainList = new ArrayList<>();
                            sideList = new ArrayList<>();
                            etcList = new ArrayList<>();
                            for (int i = 0; i < menuLists.size(); i++) {
                                if (btnType.equals("main")) {   // ????????? ?????? ??????????????? ?????? ????????? ??????????????? btnType??? ?????????.
                                    if (menuLists.get(i).getMenuCG().equals("??????")) {
                                        mainList.add(menuLists.get(i));
                                        mainFrag = new MainFragMent(MainActivity.this, mainList);
                                    }

                                } else if (btnType.equals("side")) {
                                    if (menuLists.get(i).getMenuCG().equals("?????????")) {
                                        sideList.add(menuLists.get(i));
                                        mainFrag = new MainFragMent(MainActivity.this, sideList);
                                    }

                                } else {
                                    if (menuLists.get(i).getMenuCG().equals("??????")) {
                                        etcList.add(menuLists.get(i));
                                        mainFrag = new MainFragMent(MainActivity.this, etcList);
                                    }
                                }
                            }
                            System.out.println("mainList ====>>>> " + mainList);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragmentBoard01, mainFrag);
                            transaction.commitAllowingStateLoss();
                        } else {
                            Log.d("DocSnippets", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void SelectItemGet(MenuList dto) {
        menuList = new MenuList(dto.getMenuNum(), dto.getMenuName(), dto.getMenuPrice(), dto.getMenuDetail(), dto.getMenuCG(), dto.getStockState(), dto.getOptSize01(),
                dto.getOptPrice01(), dto.getOptSize02(), dto.getOptPrice02(), dto.getOptSize03(), dto.getOptPrice03(), dto.getOptKind01(), dto.getOptPrice04(), dto.getOptKind02(), dto.getOptPrice05(), dto.getImgPath());
    }

    public void RViewChange(AppendFragment appendFrag, ModifyFragment modifyFrag) {

        if (appendFrag == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentBoard02, modifyFrag);
            transaction.commitAllowingStateLoss();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentBoard02, appendFrag);
            transaction.commitAllowingStateLoss();
        }

    }
}
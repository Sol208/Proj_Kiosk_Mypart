package com.example.appendmenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.appendmenu.model.MenuList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AppendFragment extends Fragment  {
    Spinner categorySpinner;
    Context context;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Button appendBtn, upLoadBtn;
    TextView imgPathTxt;
    EditText insertMenuNum, insertMenuN, insertMenuP, insertMenuD, optEditSize01_1, optEditPrice01_2, optEditSize02_1, optEditPrice02_2,
            optEditSize03_1, optEditPrice03_2, optEditKind04_1, optEditPrice04_2, optEditKind05_1, optEditPrice05_2;
    Switch optSwitch01, optSwitch02, optSwitch03, optSwitch04, optSwitch05, stateSwitch;
    LinearLayout optLayout01_01, optLayout01_02, optLayout02_01, optLayout02_02, optLayout03_01, optLayout03_02, optLayout04_01,
            optLayout04_02, optLayout05_01, optLayout05_02;
    View view;
    ImageView imageView;
    MainActivity mainActivity;
    static MenuList menuList;
    Boolean appendFragState = false;


    public AppendFragment(Context context, MenuList menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    public AppendFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menuappend_page, container, false);
        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        appendBtn = (Button) view.findViewById(R.id.appendBtn);
        upLoadBtn = (Button) view.findViewById(R.id.upLoadBtn);
        imgPathTxt = (TextView) view.findViewById(R.id.imgPathTxt);
        insertMenuNum = (EditText) view.findViewById(R.id.insertMenuNum);
        insertMenuN = (EditText) view.findViewById(R.id.insertNewMenuN);
        insertMenuP = (EditText) view.findViewById(R.id.insertNewMenuP);
        insertMenuD = (EditText) view.findViewById(R.id.insertNewDetail);
        optEditSize01_1 = (EditText) view.findViewById(R.id.optEditSize01_1);
        optEditPrice01_2 = (EditText) view.findViewById(R.id.optEditPrice01_2);
        optEditSize02_1 = (EditText) view.findViewById(R.id.optEditSize02_1);
        optEditPrice02_2 = (EditText) view.findViewById(R.id.optEditPrice02_2);
        optEditSize03_1 = (EditText) view.findViewById(R.id.optEditSize03_1);
        optEditPrice03_2 = (EditText) view.findViewById(R.id.optEditPrice03_2);
        optEditKind04_1 = (EditText) view.findViewById(R.id.optEditKind04_1);
        optEditPrice04_2 = (EditText) view.findViewById(R.id.optEditPrice04_2);
        optEditKind05_1 = (EditText) view.findViewById(R.id.optEditKind05_1);
        optEditPrice05_2 = (EditText) view.findViewById(R.id.optEditPrice05_2);
        stateSwitch = (Switch) view.findViewById(R.id.stateSwitch);
        optSwitch01 = (Switch) view.findViewById(R.id.optSwitch01);
        optSwitch02 = (Switch) view.findViewById(R.id.optSwitch02);
        optSwitch03 = (Switch) view.findViewById(R.id.optSwitch03);
        optSwitch04 = (Switch) view.findViewById(R.id.optSwitch04);
        optSwitch05 = (Switch) view.findViewById(R.id.optSwitch05);
        optLayout01_01 = (LinearLayout) view.findViewById(R.id.optLayout01_01);
        optLayout01_02 = (LinearLayout) view.findViewById(R.id.optLayout01_02);
        optLayout02_01 = (LinearLayout) view.findViewById(R.id.optLayout02_01);
        optLayout02_02 = (LinearLayout) view.findViewById(R.id.optLayout02_02);
        optLayout03_01 = (LinearLayout) view.findViewById(R.id.optLayout03_01);
        optLayout03_02 = (LinearLayout) view.findViewById(R.id.optLayout03_02);
        optLayout04_01 = (LinearLayout) view.findViewById(R.id.optLayout04_01);
        optLayout04_02 = (LinearLayout) view.findViewById(R.id.optLayout04_02);
        optLayout05_01 = (LinearLayout) view.findViewById(R.id.optLayout05_01);
        optLayout05_02 = (LinearLayout) view.findViewById(R.id.optLayout05_02);
        imageView = (ImageView) view.findViewById(R.id.imageView);

        mainActivity = new MainActivity();

        final String[] element = {"-선택-", "메인", "사이드", "음료"};
        ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, element);
        categorySpinner.setAdapter(adapter);

        upLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    appendFragState = true;
                    ((MainActivity)context).checkSelfPermission();
                    ((MainActivity)context).selectGallery();
            }
        });

        appendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> menu = new HashMap<>();
                if (insertMenuNum.getText().toString().equals("") | insertMenuN.getText().toString().equals("") |
                        insertMenuP.getText().toString().equals("") | categorySpinner.getSelectedItem().toString().equals("-선택-")){
                    Toast.makeText(context, "필수항목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Long menuNum = Long.parseLong(insertMenuNum.getText().toString());
                    String menuName = insertMenuN.getText().toString();
                    String menuPrice = insertMenuP.getText().toString();
                    String menuDetail = insertMenuD.getText().toString();
                    String menuCG = categorySpinner.getSelectedItem().toString();
                    Boolean stockState = stateSwitch.isChecked();
                    String optSize01 = optEditSize01_1.getText().toString();
                    String optPrice01 = optEditPrice01_2.getText().toString();
                    String optSize02 = optEditSize02_1.getText().toString();
                    String optPrice02 = optEditPrice02_2.getText().toString();
                    String optSize03 = optEditSize03_1.getText().toString();
                    String optPrice03 = optEditPrice03_2.getText().toString();
                    String optKind01 = optEditKind04_1.getText().toString();
                    String optPrice04 = optEditPrice04_2.getText().toString();
                    String optKind02 = optEditKind05_1.getText().toString();
                    String optPrice05 = optEditPrice05_2.getText().toString();
                    String imgPath = imgPathTxt.getText().toString();

                    menu.put("MenuNum", menuNum);
                    menu.put("MenuName", menuName);
                    menu.put("MenuPrice", menuPrice);
                    menu.put("MenuDetail", menuDetail);
                    menu.put("MenuCG", menuCG);
                    menu.put("StockState", stockState);
                    menu.put("OptSize01", optSize01);
                    menu.put("OptPrice01", optPrice01);
                    menu.put("OptSize02", optSize02);
                    menu.put("OptPrice02", optPrice02);
                    menu.put("OptSize03", optSize03);
                    menu.put("OptPrice03", optPrice03);
                    menu.put("OptKind01", optKind01);
                    menu.put("OptPrice04", optPrice04);
                    menu.put("OptKind02", optKind02);
                    menu.put("OptPrice05", optPrice05);
                    menu.put("ImagePath", imgPath);

                    firestore.collection("Enterprise_Users").document("a")
                            .collection("MenuList").document(menuNum.toString()).set(menu).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "추가완료", Toast.LENGTH_SHORT).show();
                            if (menuCG.equals("메인")) {
                                ((MainActivity)context).selectAll("main");
                            } else if (menuCG.equals("사이드")) {
                                ((MainActivity)context).selectAll("side");
                            } else if (menuCG.equals("음료")) {
                                ((MainActivity)context).selectAll("음료");
                            }
                        }
                    });
                }
            }
        });
        optSwitch01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (optSwitch01.isChecked() == true) {
                    optLayout01_01.setVisibility(View.VISIBLE);
                    optLayout01_02.setVisibility(View.VISIBLE);
                } else {
                    optLayout01_01.setVisibility(View.GONE);
                    optLayout01_02.setVisibility(View.GONE);
                }
            }
        });

        optSwitch02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (optSwitch02.isChecked() == true) {
                    optLayout02_01.setVisibility(View.VISIBLE);
                    optLayout02_02.setVisibility(View.VISIBLE);
                } else {
                    optLayout02_01.setVisibility(View.GONE);
                    optLayout02_02.setVisibility(View.GONE);
                }
            }
        });

        optSwitch03.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (optSwitch03.isChecked() == true) {
                    optLayout03_01.setVisibility(View.VISIBLE);
                    optLayout03_02.setVisibility(View.VISIBLE);
                } else {
                    optLayout03_01.setVisibility(View.GONE);
                    optLayout03_02.setVisibility(View.GONE);
                }
            }
        });

        optSwitch04.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (optSwitch04.isChecked() == true) {
                    optLayout04_01.setVisibility(View.VISIBLE);
                    optLayout04_02.setVisibility(View.VISIBLE);
                } else {
                    optLayout04_01.setVisibility(View.GONE);
                    optLayout04_02.setVisibility(View.GONE);
                }
            }
        });

        optSwitch05.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (optSwitch05.isChecked() == true) {
                    optLayout05_01.setVisibility(View.VISIBLE);
                    optLayout05_02.setVisibility(View.VISIBLE);
                } else {
                    optLayout05_01.setVisibility(View.GONE);
                    optLayout05_02.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    public void changeAppendFragImg(Bitmap img, int exifDegree){
        imageView.setImageBitmap(((MainActivity)context).rotate(img, exifDegree));
    }

    public void changeAppendFragImgText(String path){
        imgPathTxt.setText(path);
    }
}

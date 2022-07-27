package com.example.appendmenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appendmenu.model.MenuList;


public class MenuItemLayout extends LinearLayout {
    ImageView menu_Img, soldOut_Img;
    TextView menu_N, menu_P;
    LinearLayout menuLayout;
    MenuList menuList;
    static Bitmap resourceImg, upLoadImg;
    static Context context;
    public MenuItemLayout(Context context) {
        super(context);
    }

    public MenuItemLayout(Context context, MenuList menuList) {
        super(context);
        this.context = context;
        soldOut_Img = new ImageView(context);
        menu_Img = new ImageView(context);
        menu_N = new TextView(context,null, Typeface.BOLD);
        menu_P = new TextView(context,null, Typeface.BOLD);
        menuLayout = new LinearLayout(context);
        this.menuList = menuList;
        setLayout();
    }

    public void setLayout(){
        if (menuList.getImgPath().equals("")){
            upLoadImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_img);
        }else{
            upLoadImg = BitmapFactory.decodeFile(menuList.getImgPath());
        }

        LinearLayout.LayoutParams layoutMargin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutMargin.setMargins(45, 20, 0, 0);
        layoutMargin.width = 360;
        layoutMargin.height = 440;
        LinearLayout.LayoutParams imgMargin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgMargin.setMargins(20, 20, 20, 20);
        imgMargin.width = 320;
        imgMargin.height = 280;
        LinearLayout.LayoutParams txtMargin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txtMargin.setMargins(20, 2, 20, 0);
        txtMargin.width = 320;
        txtMargin.height = 60;


        menu_N.setText(menuList.getMenuName());
        menu_N.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        menu_P.setText(menuList.getMenuPrice());
        menu_P.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        this.addView(menuLayout);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundResource(R.drawable.itembutton_f);
        this.setLayoutParams(layoutMargin);

        if (menuList.getStockState() == true){
            Bitmap resultBtm = overlayBitmap(upLoadImg, context);
            this.addView(menu_Img);
            menu_Img.setImageBitmap(resultBtm);
            menu_Img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            menu_Img.setLayoutParams(imgMargin);
        } else {
            this.addView(menu_Img);
            if (upLoadImg == null)
                menu_Img.setImageResource(R.drawable.basic_img);
            else
                menu_Img.setImageBitmap(upLoadImg);
            menu_Img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            menu_Img.setLayoutParams(imgMargin);
        }

        this.addView(menu_N);
        menu_N.setLayoutParams(txtMargin);
        menu_N.setTypeface(Typeface.DEFAULT_BOLD);
        menu_N.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        menu_N.setTextSize(18);

        this.addView(menu_P);
        menu_P.setLayoutParams(txtMargin);
        menu_P.setTypeface(Typeface.DEFAULT_BOLD);
        menu_P.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        menu_P.setTextSize(18);
    }

    public static Bitmap getOverlayBitmap(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.soldout_img);
    }

    static public Bitmap overlayBitmap(Bitmap original, Context context) {

        double aspectRatio = (double) original.getHeight() / (double) original.getWidth();

        int MAX_LENGTH = 120;

        int targetWidth, targetHeight;
        int startW = 0;
        int startH = 0;
        Bitmap originalResizeBmp;

        if (aspectRatio >= 1) { //세로가 긴 경우
            targetWidth = MAX_LENGTH;
            targetHeight = (int) (targetWidth * aspectRatio);
            startH = (targetHeight - targetWidth) / 2;

        } else { //가로가 긴 경우
            targetHeight = MAX_LENGTH;
            targetWidth = (int) (targetHeight / aspectRatio);
            startW = (targetWidth - targetHeight) / 2;
        }

        //하단 비트맵
        originalResizeBmp = Bitmap.createScaledBitmap(original, targetWidth, targetHeight, false);
        originalResizeBmp = originalResizeBmp.createBitmap(originalResizeBmp, startW, startH
                , (targetWidth > targetHeight ? targetHeight : targetWidth)
                , (targetWidth > targetHeight ? targetHeight : targetWidth));

        //상단 비트맵
        Bitmap overlayBmp = Bitmap.createScaledBitmap(getOverlayBitmap(context)
                , (targetWidth > targetHeight ? targetHeight : targetWidth)
                , (targetWidth > targetHeight ? targetHeight : targetWidth)
                , false);

        //결과값 저장을 위한 Bitmap
        Bitmap resultOverlayBmp = Bitmap.createBitmap(originalResizeBmp.getWidth()
                , originalResizeBmp.getHeight()
                , originalResizeBmp.getConfig());


        //상단 비트맵에 알파값을 적용하기 위한 Paint
        Paint alphaPaint = new Paint();
        alphaPaint.setAlpha(125);

        //캔버스를 통해 비트맵을 겹치기한다.
        Canvas canvas = new Canvas(resultOverlayBmp);
        canvas.drawBitmap(originalResizeBmp, new Matrix(), null);
        canvas.drawBitmap(overlayBmp, new Matrix(), alphaPaint);

        if (originalResizeBmp != original) {
            original.recycle();
        }
        if (originalResizeBmp != resultOverlayBmp) {
            originalResizeBmp.recycle();
        }
        if (overlayBmp != resultOverlayBmp) {
            overlayBmp.recycle();
        }

        return resultOverlayBmp;
    }
}

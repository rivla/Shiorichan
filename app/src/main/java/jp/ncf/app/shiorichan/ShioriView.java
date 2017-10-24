package jp.ncf.app.shiorichan;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * Created by ideally on 2017/10/10.
 * 担当 : fukui
 */

// ここに、しおり用のビューを作成する
public class ShioriView extends Activity {

    private ViewFlipper viewFlipper;
    //フリックのＸY位置
    private float X,firstX;
    private float Y,firstY;
    // フリックの遊び部分（最低限移動しないといけない距離）
    private float adjust = 150;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewFlipperでつかうlayoutの設定
        setContentView(R.layout.shiori_main);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);

        // ViewFlipper にページレイアウトを作る //
        // === 基本的にはxmlを呼び出して, テキストを上書きして, 格納していく === //

        // === first page === //
        View v1 = this.getLayoutInflater().inflate(R.layout.first_page, null);
        TextView textView18 = (TextView)v1.findViewById(R.id.textView18);
        textView18.setText("テーマ:"+Value.input_text); // もし変更するならココで
        if (Value.itineraryPlaceList.size() > 1) { // もし受け取ったルートの長さが2以上なら...
            ImageView imageView5 = (ImageView)v1.findViewById(R.id.imageView5); // 表紙の中央の画像

            if(Value.itineraryPlaceList.get(1).image!=null) {
                imageView5.setImageBitmap(Value.itineraryPlaceList.get(1).getImage()); //
            }
        } else{
            Log.d("from ShioriView.java : ","受け取ったスケジュールの場所の数が"+Value.itineraryPlaceList.size()+"個です");
        }
        viewFlipper.addView(v1); // ViewFlipperにv1のレイアウトを追加

        // === Schedule page === //
        // 5つごとに1ページ作成,

        View v2 = null;
        for (int i = 0; i < Value.itineraryPlaceList.size(); i++) {
            switch (i % 5) {
                case 0:
                    // 1つ目の行き先
                    v2 = getLayoutInflater().inflate(R.layout.schedule_page, null);
                    if (Value.itineraryPlaceList.get(i).getName() != null) {
                        TextView textView4 = (TextView) v2.findViewById(R.id.textView4);
                        textView4.setText(DepArrStringMaker(Value.itineraryPlaceList.get(i))); //
                        TextView textView5 = (TextView) v2.findViewById(R.id.textView5); // v2.findViewByIdで指定する
                        textView5.setText(Value.itineraryPlaceList.get(i).getName());
                    }
                    break;
                case 1:
                    // 2つ目の行き先
                    if (Value.itineraryPlaceList.get(i).getName() != null) {
                        ImageView imageView6 = (ImageView) v2.findViewById(R.id.imageView6);
                        imageView6.setImageResource(R.mipmap.car_keijidousya);
                        TextView textView6 = (TextView) v2.findViewById(R.id.textView6);
                        textView6.setText(DepArrStringMaker(Value.itineraryPlaceList.get(i))); //
                        TextView textView7 = (TextView) v2.findViewById(R.id.textView7);
                        textView7.setText(Value.itineraryPlaceList.get(i).getName());
                    }
                    break;
                case 2:
                    // 3つ目の行き先
                    if (Value.itineraryPlaceList.get(i).getName() != null) {
                        ImageView imageView7 = (ImageView) v2.findViewById(R.id.imageView7);
                        imageView7.setImageResource(R.mipmap.car_keijidousya);
                        TextView textView8 = (TextView) v2.findViewById(R.id.textView8);
                        textView8.setText(DepArrStringMaker(Value.itineraryPlaceList.get(i))); //
                        TextView textView9 = (TextView) v2.findViewById(R.id.textView9);
                        textView9.setText(Value.itineraryPlaceList.get(i).getName());
                    }
                    break;
                case 3:
                    // 4つ目の行き先
                    if (Value.itineraryPlaceList.get(i).getName() != null) {
                        ImageView imageView8 = (ImageView) v2.findViewById(R.id.imageView8);
                        imageView8.setImageResource(R.mipmap.car_keijidousya);
                        TextView textView10 = (TextView) v2.findViewById(R.id.textView10);
                        textView10.setText(DepArrStringMaker(Value.itineraryPlaceList.get(i))); //
                        TextView textView11 = (TextView) v2.findViewById(R.id.textView11);
                        textView11.setText(Value.itineraryPlaceList.get(i).getName());
                    }
                    break;
                case 4:
                    // 5つ目の行き先
                    if (Value.itineraryPlaceList.get(i).getName() != null) {
                        ImageView imageView9 = (ImageView) v2.findViewById(R.id.imageView9);
                        imageView9.setImageResource(R.mipmap.car_keijidousya);
                        TextView textView12 = (TextView) v2.findViewById(R.id.textView12);
                        textView12.setText(DepArrStringMaker(Value.itineraryPlaceList.get(i))); //
                        TextView textView13 = (TextView) v2.findViewById(R.id.textView13);
                        textView13.setText(Value.itineraryPlaceList.get(i).getName());
                    }
                    // viewFlipperに追加
                    viewFlipper.addView(v2);
                    // === 2ページ目終了 === //
                    v2 = null;
                    break;
                default: // まずありえない
                    Log.d("from ShioriView.java : ","i%5 が0から4以外の数値を返しました");
                    break;
                }
            }
            if (v2 != null) {
                // viewFlipperに追加
                viewFlipper.addView(v2);
            }

        // === 観光地の周辺地図のページ === //
        // Mapをつくる
        // Value.itineraryPlaceList.get(0).mapImage // これでgoogle のマップが取ってこれる
        // ImageViewをつくってそこに画像を投げる

        View v4 = this.getLayoutInflater().inflate(R.layout.map_page, null);
        // 周辺地図をセットする。
        if (Value.itineraryPlaceList.size() > 0) {
            //出発地→第一目的地間の地図。
            ImageView mapImageView1 = (ImageView) v4.findViewById(R.id.mapImage1);
            mapImageView1.setImageBitmap(Value.itineraryPlaceList.get(0).mapImage);
            TextView mapImageText1=(TextView)v4.findViewById(R.id.mapImageText1);
            mapImageText1.setText("S:"+Value.itineraryPlaceList.get(0).name+"\n↓\n"+"1:"+Value.itineraryPlaceList.get(1).name);

            ImageView mapImageView2 = (ImageView) v4.findViewById(R.id.mapImage2);
            mapImageView2.setImageBitmap(Value.itineraryPlaceList.get(Value.itineraryPlaceList.size()-1).mapImage);
            TextView mapImageText2=(TextView)v4.findViewById(R.id.mapImageText2);
            mapImageText2.setText("1:"+Value.itineraryPlaceList.get(1).name+"\n↓\n"+String.valueOf(Value.itineraryPlaceList.size()-2)+":"+Value.itineraryPlaceList.get(Value.itineraryPlaceList.size()-2).name);

            ImageView mapImageView3 = (ImageView) v4.findViewById(R.id.mapImage3);
            mapImageView3.setImageBitmap(Value.itineraryPlaceList.get(Value.itineraryPlaceList.size()-2).mapImage);
            TextView mapImageText3=(TextView)v4.findViewById(R.id.mapImageText3);
            mapImageText3.setText(String.valueOf(Value.itineraryPlaceList.size()-2)+":"+Value.itineraryPlaceList.get(Value.itineraryPlaceList.size()-2).name+"\n↓\n"+"G:"+Value.itineraryPlaceList.get(Value.itineraryPlaceList.size()-1).name);

            //地図の下に表示する、観光地リストの表示
            TextView mapPlaceText=(TextView)v4.findViewById(R.id.mapPlaceText);
            String tempString="";
            for(int i=0;i<Value.itineraryPlaceList.size();i++){
                if(i==0){
                    tempString=tempString+"S:"+Value.itineraryPlaceList.get(i).name+"\n";
                }else if(i==Value.itineraryPlaceList.size()-1){
                    tempString=tempString+"G:"+Value.itineraryPlaceList.get(i).name+"\n";
                }else{
                    tempString=tempString+String.valueOf(i)+":"+Value.itineraryPlaceList.get(i).name+"\n";
                }
            }
            mapPlaceText.setText(tempString);
        }
        else{
            Log.d("from ShioriView.java : ","地図を作成出来ませんでした\nValue.itineraryPlaceList.get(0).mapImage を確認して下さい");
        }
        // viewFlipperに追加
        viewFlipper.addView(v4);


        // === 3ページ目 観光地の情報=== //
        View v3 = this.getLayoutInflater().inflate(R.layout.place_infomation, null);
        for (int i = 1; i < Value.itineraryPlaceList.size() - 1; i++) { // 出発地点(0)と到着地(最後)は飛ばす
            // 観光地の画像
            if (Value.itineraryPlaceList.get(i).getImage() != null) {
                ImageView imageView3 = (ImageView) v3.findViewById(R.id.imageView3);
                imageView3.setImageBitmap(Value.itineraryPlaceList.get(i).getImage()); //
            }
            // 観光地の名称
            TextView textView15 = (TextView) v3.findViewById(R.id.textView15);
            textView15.setText(Value.itineraryPlaceList.get(i).prefecture+"\n"+Value.itineraryPlaceList.get(i).getName());
            // 観光地の説明文
            TextView textView16 = (TextView) v3.findViewById(R.id.textView16);
            textView16.setText(Value.itineraryPlaceList.get(i).getExplainText());
            Linkify.addLinks(textView16,Linkify.ALL);

            // 評価値の☆をつける

//            RatingBar ratingBar = (RatingBar) v3.findViewById(R.id.ratingBar);
//            ratingBar.setRating((float) Value.itineraryPlaceList.get(i).getRate());
            final ImageView rateWhiteImage=(ImageView)v3.findViewById(R.id.starWhiteImage);
            final ImageView rate5Image=(ImageView)v3.findViewById(R.id.star5Image);
            ViewTreeObserver vto = rate5Image.getViewTreeObserver();
            final int finalI = i;
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewGroup.LayoutParams params=rateWhiteImage.getLayoutParams();
                    Log.d("test",String.valueOf((int)(rate5Image.getWidth()*(1-(Value.itineraryPlaceList.get(finalI).rate/5)))));
                    Log.d("test",String.valueOf(rate5Image.getWidth()));
                    params.width=(int)(rate5Image.getWidth()*(1-(Value.itineraryPlaceList.get(finalI).rate/5)));
                    rateWhiteImage.setLayoutParams(params);
                }
            });







            // viewFlipperに追加
            viewFlipper.addView(v3);

            v3 = this.getLayoutInflater().inflate(R.layout.place_infomation, null); // 2週目以降
        }

        // === 最後のページ Have a nice trip!! === //
        View v5 = this.getLayoutInflater().inflate(R.layout.last_page, null);
        viewFlipper.addView(v5);
    }


    //onTouchEventの代わりにこちらを使ってください。
    //ScrollView(説明文のスクロール)が画面タッチ時のイベントを優先して吸い取ってしまいonTouchEventが実行されないため、
    //ScrollViewよりも優先度の高いdispatchTouchEventを使うことで無理やりスワイプを検知しています。
    public final boolean dispatchTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstX = touchevent.getX();
                firstY = touchevent.getY();
                return super.dispatchTouchEvent(touchevent);
            case MotionEvent.ACTION_UP:
                X = touchevent.getX();
                Y = touchevent.getY();

                // Handling left to right screen swap.
                if (X - firstX > adjust) {
                    if (Y - firstY  < X - firstX && firstY - Y < X - firstX) { // X方向の移動のほうがおおきかったら...

                        Log.d("from ShioriView.java : ", "右方向のスワイプを検知しました -> 先のページに移動します");
                        // Next screen comes in from left.
                        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left));
                        // Current screen goes out from right.
                        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_to_right));

                        // Display previous screen.
                        viewFlipper.showPrevious();

                        return false;
                    }
                    else{
                        Log.d("from ShioriView.java : ", "上下方向のスワイプと判断しました");
                    }
                }

                // Handling right to left screen swap.
                else if (firstX - X > adjust) {
                    if (Y - firstY < firstX - X && firstY - Y < firstX - X) {
                        Log.d("from ShioriView.java : ", "左方向のスワイプを検知しました -> 前のページに移動します");
                        // Next screen comes in from right.
                        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_from_right));
                        // Current screen goes out from left.
                        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_to_left));

                        // Display next screen.
                        viewFlipper.showNext();

                        return false;
                    }
                    else{
                            Log.d("from ShioriView.java : ", "上下方向のスワイプと判断しました");
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(touchevent);
    }

    //着:13:00
    //発:15:00
    //上記のような文字列を生成する関数
    public String DepArrStringMaker(SpotStructure spot) {
        String tempTimeString="";//この変数に文字列を加算していくことで目的の変数を作成する
        if(spot.arriveTime!=null){
            tempTimeString=tempTimeString+String.format("着:%02d:%02d", spot.arriveTime.getHours(), spot.arriveTime.getMinutes());
        }
        if(spot.arriveTime!=null && spot.departTime!=null){
            tempTimeString=tempTimeString+"\n";
        }
        if(spot.departTime!=null){
            tempTimeString=tempTimeString+String.format("出:%02d:%02d", spot.departTime.getHours(), spot.departTime.getMinutes());
        }
        return tempTimeString;
    }

}

class localScrollView extends android.widget.ScrollView {
    public localScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("test","interruput");
        ((Activity)(this.getContext())).onTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }
}
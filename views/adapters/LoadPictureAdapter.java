
package com.seoul.hanokmania.views.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.seoul.hanokmania.R;
import com.suwonsmartapp.abl.AsyncBitmapLoader;

/**
 * Created by junsuk on 15. 9. 25..
 */
public class LoadPictureAdapter extends CursorAdapter implements
        AsyncBitmapLoader.BitmapLoadListener {

    private final LayoutInflater mLayoutInflater;

    // 다이나믹 비트맵 로더
    private AsyncBitmapLoader mAsyncBitmapLoader;

    public LoadPictureAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        mLayoutInflater = LayoutInflater.from(context);

        // 다이나믹 비트맵 로더 생성 및 이벤트 연결
        mAsyncBitmapLoader = new AsyncBitmapLoader(context);
        mAsyncBitmapLoader.setBitmapLoadListener(this);
    }

    // 맨 처음 레이아웃 만드는 부분
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // 레이아웃 가져오기
        View view = mLayoutInflater.inflate(R.layout.item_picture, parent, false);

        // 홀더에 저장
        ViewHolder holder = new ViewHolder();
        holder.imageView = (ImageView) view.findViewById(R.id.imageView);
        view.setTag(holder);

        return view;
    }

    // 실제 데이타 셋팅
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        // 이미지 셋팅
        mAsyncBitmapLoader.loadBitmap(cursor.getPosition(), holder.imageView);
    }

    @Override
    public Bitmap getBitmap(int position) {
        // id 가져오기
        long id = getItemId(position);

        // Bitmap 샘플링
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1; // 2의 배수

        // id 로부터 bitmap 생성
        return MediaStore.Images.Thumbnails.getThumbnail(mContext.getContentResolver(),
                id,
                MediaStore.Images.Thumbnails.MINI_KIND,
                options);
    }

    static class ViewHolder {
        ImageView imageView;
    }

}

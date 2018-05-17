package com.thn.erp.view;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thn.basemodule.tools.GlideUtil;
import com.thn.erp.Constants;
import com.thn.erp.MainActivity;
import com.thn.erp.UserGuideActivity;
import com.thn.erp.user.LoginRegisterActivity;
import com.thn.erp.utils.SPUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.util.List;

public class ViewPagerAdapter<T> extends RecyclingPagerAdapter {
    private final String TAG = getClass().getSimpleName();
    private Activity activity;
    private List<T> list;
    private int size;
    private boolean isInfiniteLoop;
    private String code;
    private WaitingDialog dialog;

    public int getSize() {
        return size;
    }

    public ViewPagerAdapter(final Activity activity, List<T> list) {
        this.activity = activity;
        this.list = list;
        this.size = (list == null ? 0 : list.size());
        isInfiniteLoop = false;
        if (activity instanceof UserGuideActivity) {
            dialog = new WaitingDialog(activity);
        }
    }

    @Override
    public int getCount() {
        if (size == 0) {
            return 0;
        }
        return isInfiniteLoop ? Integer.MAX_VALUE : size;
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(activity);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final T content = list.get(getPosition(position));

        if (content instanceof Integer) {
            holder.imageView.setImageResource((Integer) content);
        }

        if (content instanceof String) {
            if (TextUtils.isEmpty((String) content)) {
                ToastUtils.showError("图片链接为空");
            } else {
                GlideUtil.loadImage(content, holder.imageView);
            }
        }


        if (activity instanceof UserGuideActivity) {
            if (position == size - 1) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(UserGuideActivity.fromPage)) {
                            String token = SPUtil.read(Constants.TOKEN);
                            if (TextUtils.isEmpty(token)){
                                activity.startActivity(new Intent(activity, LoginRegisterActivity.class));
                            }else {
                                activity.startActivity(new Intent(activity, MainActivity.class));
                            }
                            activity.finish();
                        } else {
                            UserGuideActivity.fromPage = null;
                            activity.finish();
                        }
                    }
                });
            }
        }

        return view;
    }


    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ViewPagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
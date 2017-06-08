package com.kuaimeizhuang.fashionmix.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.ShareAdapter;
import com.kuaimeizhuang.fashionmix.bean.ShareBean;

import java.util.List;

/**
 * <p>分享Dialog</p>
 * Created on 17/5/23.
 *
 * @author Shi GuangXiang.
 */

public class ShareDialog extends Dialog {


    public ShareDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public ShareDialog(@NonNull Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private String title;
        private View contenView;
        private List<ShareBean> shareBeanList;
        private OnItemClickListener onItemClickListener;


        public Builder(Context context) {
            this.context = context;
        }

        //设置标题
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置数据
         *
         * @param shareBeanList 数据
         * @return this
         */
        public Builder setShareData(List<ShareBean> shareBeanList) {
            this.shareBeanList = shareBeanList;
            return this;
        }


        //设置布局
        public Builder setContentView(View contentView) {
            this.contenView = contentView;
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public ShareDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ShareDialog dialog = new ShareDialog(context, R.style.ActionSheetDialogStyle);

            View view = inflater.inflate(R.layout.dialog_share, null);
            dialog.setContentView(view);
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            lp.x = 0; // 新位置X坐标
            lp.y = -20; // 新位置Y坐标
            lp.width = context.getResources().getDisplayMetrics().widthPixels; // 宽度
            view.measure(0, 0);
            lp.height = view.getMeasuredHeight();
            lp.alpha = 9f; // 透明度
            dialogWindow.setAttributes(lp);
            TextView title = (TextView) view.findViewById(R.id.tv_share_title);
            title.setText(this.title);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_share);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 5));
            ShareAdapter adapter = new ShareAdapter();
            adapter.addData(this.shareBeanList);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(title1 -> {
                        onItemClickListener.onItemClick(title1);
                        dialog.dismiss();
                    }
            );
            return dialog;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String title);
    }
}

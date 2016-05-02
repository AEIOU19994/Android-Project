package com.example.ui;

import com.example.project02.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyDialog extends Dialog {

	public MyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyDialog(Context context,int theme){
		super(context,theme);
	}
	
	public static class Builder{
		private Context context;
		private String title;										// 标题
		private String message;										// 信息
		private String cancelBtnText;								// 取消按钮文字
		private String confirmBtnText;								// 确定按钮文字
		private View contentView;
		private DialogInterface.OnClickListener cancelBtnListener;	// 取消按钮监听器
		private DialogInterface.OnClickListener confirmBtnListener;	// 确定按钮监听器
		
		public Builder(Context context){
			this.context = context;
		}


		/**
		 * @param cancelBtnText the cancelBtnText to set
		 */
		public Builder setCancelBtn(String cancelBtnText,
				DialogInterface.OnClickListener listener) {
			this.cancelBtnText = cancelBtnText;
			this.cancelBtnListener = listener;
			return this;
		}

		/**
		 * @param title the title to set
		 */
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		/**
		 * @param message the message to set
		 */
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * @param confirmBtnText the confirmBtnText to set
		 */
		public Builder setConfirmBtn(String confirmBtnText,
				DialogInterface.OnClickListener listener) {
			this.confirmBtnText = confirmBtnText;
			this.confirmBtnListener = listener;
			return this;
		}

		/**
		 * @param contentView the contentView to set
		 */
		public Builder setcontentView(View contentView) {
			this.contentView = contentView;
			return this;
		}
		
		/*
		 * 创建 Dialog
		 */
		public MyDialog create(){
			LayoutInflater inflater = (LayoutInflater)context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final MyDialog dialog = new MyDialog(context,R.style.Dialog);
			View layout = inflater.inflate(R.layout.view_dialog_layout, null);
			dialog.setContentView(layout,new LayoutParams(
					 LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));  
			
			((TextView) layout.findViewById(R.id.title)).setText(title);  
			
			// 确定按钮
			if(confirmBtnText != null){
				((Button) layout.findViewById(R.id.confirmButton))  
                .setText(confirmBtnText);
				if(confirmBtnListener != null){
					((Button) layout.findViewById(R.id.confirmButton))  
                    .setOnClickListener(new View.OnClickListener() {  
                        public void onClick(View v) {  
                            confirmBtnListener.onClick(dialog,  
                                    DialogInterface.BUTTON_POSITIVE);  
                        }  
                    });  
				}
			}
			else{
				layout.findViewById(R.id.confirmButton).setVisibility(  
                        View.GONE);  
			}
			
			// 取消按钮
			if (cancelBtnText != null) {  
                ((Button) layout.findViewById(R.id.cancelButton))  
                        .setText(cancelBtnText);  
                if (cancelBtnListener != null) {  
                    ((Button) layout.findViewById(R.id.cancelButton))  
                            .setOnClickListener(new View.OnClickListener() {  
                                public void onClick(View v) {  
                                    cancelBtnListener.onClick(dialog,  
                                            DialogInterface.BUTTON_NEGATIVE);  
                                }  
                            });  
                }  
            } else {  
                // if no confirm button just set the visibility to GONE  
                layout.findViewById(R.id.cancelButton).setVisibility(  
                        View.GONE);  
            } 
			
			// 设置显示文字
			if (message != null) {  
                ((TextView) layout.findViewById(R.id.message)).setText(message);  
            } else if (contentView != null) {  
                // if no message set  
                // add the contentView to the dialog body  
                ((LinearLayout) layout.findViewById(R.id.content))  
                        .removeAllViews();  
                ((LinearLayout) layout.findViewById(R.id.content))  
                        .addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));  
            }  
            dialog.setContentView(layout);  
			
			return dialog;
		}
		
	}
}

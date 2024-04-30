package com.example.clicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

public class MyDialog {
    protected static TextView Dialog_TV_Name,Dialog_TV_Hint;
    protected static Button Dialog_Btn_One,Dialog_Btn_Two,Dialog_Btn_Cancel;
    public static void showDialogHelp(Context context, String title, String message) {
        Dialog dialog = SetUpDialog(context,title,message);
        Dialog_Btn_Cancel.setOnClickListener(v-> dialog.dismiss());
        Dialog_Btn_One.setOnClickListener(v-> dialog.dismiss());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public static void showDialogMenu(Context context, View view, String title, String message) {
        Dialog dialog = SetUpDialog(context,title,message);
        Dialog_Btn_One.setVisibility(View.VISIBLE);
        Dialog_Btn_Cancel.setOnClickListener(v-> dialog.dismiss());
        Dialog_Btn_One.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_helpFragment);
            dialog.dismiss();
        });
        Dialog_Btn_One.setText("Подсказки");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private static Dialog SetUpDialog(Context context , String title, String message){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_alert_dialog);
        Dialog_TV_Name = dialog.findViewById(R.id.Dialog_Name);
        Dialog_TV_Hint = dialog.findViewById(R.id.Dialog_Hint);
        Dialog_Btn_One = dialog.findViewById(R.id.Dialog_Btn_One);
        Dialog_Btn_Cancel = dialog.findViewById(R.id.Dialog_Cancel);
        Dialog_Btn_Two = dialog.findViewById(R.id.Dialog_Btn_Two);
        Dialog_TV_Name.setText(title);
        Dialog_TV_Hint.setText(message);
        return dialog;
    }
}

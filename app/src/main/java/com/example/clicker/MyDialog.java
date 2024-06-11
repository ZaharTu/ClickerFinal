package com.example.clicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MyDialog {
    protected  TextView Dialog_TV_Name,Dialog_TV_Hint,Dialog_TV_Cost;
    protected  Button Dialog_Btn_One,Dialog_Btn_Two,Dialog_Btn_Cancel;
    private AllResRepository repository;
    public void showDialogHelp(Context context, String title, String message) {
        Dialog dialog = SetUpDialog(context,title,message);
        Dialog_Btn_Cancel.setOnClickListener(v-> dialog.dismiss());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private Dialog SetUpDialog(Context context , String title, String message){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_alert_dialog);
        Dialog_TV_Name = dialog.findViewById(R.id.Dialog_Name);
        Dialog_TV_Hint = dialog.findViewById(R.id.Dialog_Hint);
        Dialog_TV_Cost=dialog.findViewById(R.id.Dialog_cost);
        Dialog_Btn_One = dialog.findViewById(R.id.Dialog_Btn_One);
        Dialog_Btn_Cancel = dialog.findViewById(R.id.Dialog_Cancel);
        Dialog_Btn_Two = dialog.findViewById(R.id.Dialog_Btn_Two);
        Dialog_TV_Name.setText(title);
        Dialog_TV_Hint.setText(message);
        return dialog;
    }
    public void showDialogResearch(Context context,int position,View parent){
        String title = context.getResources().getStringArray(R.array.ResearchName)[position];
        String about = context.getResources().getStringArray(R.array.ResearchAbout)[position];
        int Cost = 0;
        if (position>0) Cost=context.getResources().getIntArray(R.array.ResearchCost)[position-1];
        boolean researched = false;
        Dialog dialog = SetUpDialog(context,title,about);
        Dialog_Btn_One.setVisibility(View.VISIBLE);
        Dialog_TV_Cost.setVisibility(View.VISIBLE);
        repository=AllResRepository.getInstance(context);
        if ((position != 0) && (position < 4)){
            researched= repository.getResearched(position-1);
            Dialog_TV_Cost.setText("$"+Cost);
        } else if (position != 0) {
            researched= repository.getResearched(position-1);
            Dialog_TV_Cost.setText(Cost+"🥔");
        }
        if (researched){
            Dialog_Btn_One.setText("Изучено");
            Dialog_Btn_One.setBackgroundColor(context.getColor(R.color.BottomNavRipple));
        }
        else if (position==0){
            Dialog_Btn_One.setVisibility(View.GONE);
        }else{
            Dialog_Btn_One.setText("Изучить");
            Dialog_Btn_One.setOnClickListener(v -> {
                if (repository.TryResearch(position - 1)){
                    dialog.dismiss();
                    MediaPlayer.create(context,R.raw.sound_of_successful).start();
                }
                else {
                    Snackbar.make(parent,"Недостаточно средств",Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(context.getResources().getColor
                                    (R.color.ProgressBar, context.getTheme()))
                            .show();
                    MediaPlayer.create(context,R.raw.error).start();
                }
            });
        }
        Dialog_Btn_Cancel.setOnClickListener(v -> dialog.dismiss());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public void showDialogAbsence(Context context,int time, int incr){
        String title = context.getResources().getString(R.string.Offline);
        String timeAbsence = (time / 60) + ":" + ((time % 60) < 10 ? "0" : "") + (time % 60);
        repository = AllResRepository.getInstance(context);
        String about = "За время вашего отсутствия (" + timeAbsence + ") ваши соседи заработали $" + incr;
        Dialog dialog = SetUpDialog(context, title, about);
        Dialog_Btn_Cancel.setText("Собрать");
        Dialog_Btn_Cancel.setOnClickListener(v -> {
            dialog.dismiss();
            repository.setBalance(repository.getBalance()+incr);
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}

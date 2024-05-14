package com.example.clicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder>{
    private final Context mcontext;
    private final String[] Help_Name;
    private final String[] Help_Hint;
    private final MyDialog myDialog= new MyDialog();
    int[] Help_Image = {R.drawable.potatobg,R.drawable.what,
            R.drawable.money, R.drawable.shovel, R.drawable.leyka,
            R.drawable.slave, R.drawable.traktor,R.drawable.plant,
            R.drawable.barn, R.drawable.market};

    public HelpAdapter(Context mcontext) {
        this.mcontext = mcontext;
        Help_Name=mcontext.getResources().getStringArray(R.array.HelpName);
        Help_Hint=mcontext.getResources().getStringArray(R.array.HelpAbout);
    }

    @NonNull
    @Override
    public HelpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view =layoutInflater.inflate(R.layout.help_item,parent,false);
        return new HelpAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.MyViewHolder holder, int position) {
        holder.Help_Name_Adapter.setText(Help_Name[position]);
        holder.Help_Image_Adapter.setImageResource(Help_Image[position]);
        holder.itemView.setOnClickListener(v -> {
            myDialog.showDialogHelp(mcontext,Help_Name[position], Help_Hint[position]);
        });
    }

    @Override
    public int getItemCount() {
        return Help_Name.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Help_Name_Adapter;
        ImageView Help_Image_Adapter;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Help_Name_Adapter=itemView.findViewById(R.id.Help_Name);
            Help_Image_Adapter=itemView.findViewById(R.id.Help_Image);
        }
    }
}

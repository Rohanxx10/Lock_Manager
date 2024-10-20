package com.example.lock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class adapterRecycle extends RecyclerView.Adapter<adapterRecycle.ViewHolder> {


    Context context;
    ArrayList<Store> arrayList;
    dataBaseHelper dataBaseHelper;


    public adapterRecycle(Context context, ArrayList<Store> arrayList,dataBaseHelper dataBaseHelper) {
        this.context = context;
        this.arrayList = arrayList;
        this.dataBaseHelper=dataBaseHelper;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(arrayList.get(position).getName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });
        holder.llrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);

                TextView userName_show=dialog.findViewById(R.id.userName_show);
                TextView password_show=dialog.findViewById(R.id.password_show);

                String s=arrayList.get(position).getUsername();
                String j=arrayList.get(position).getPassword();
                userName_show.setText("User Name : "+s);
                password_show.setText("Password : "+j);


//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Dialog dialog_update=new Dialog(context);
//                        dialog_update.setContentView(R.layout.dialog);
//
//                        Button update=dialog_update.findViewById(R.id.addItem);
//                        update.setText("Update");
//
//                        EditText userName=dialog_update.findViewById(R.id.userName);
//                        EditText password=dialog_update.findViewById(R.id.password);
//                        EditText Name=dialog_update.findViewById(R.id.name);
//
//                        userName.setText(arrayList.get(position).getUsername());
//                        Name.setText(arrayList.get(position).getName());
//                        password.setText(arrayList.get(position).getPassword());
//
//
//                        update.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//
//                                ((MainActivity)context).show();
//                                updated(arrayList.get(position).getId(),arrayList.get(position).getName(),arrayList.get(position).getUsername(),arrayList.get(position).getPassword());
//                                dialog_update.dismiss();
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog_update.show();
//                    }
//                });
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView delete;
        ConstraintLayout llrow;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            delete=itemView.findViewById(R.id.delete);
            llrow=itemView.findViewById(R.id.llrow);


        }
    }
    public void deleteItem(int position){
        AlertDialog dialog=new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure want to delete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dataBaseHelper.storeDoe().delete(new Store(arrayList.get(position).getId(),arrayList.get(position).getName(),arrayList.get(position).getUsername(),arrayList.get(position).getPassword()));
                        ((MainActivity)context).show();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

    }
    public void updated(int id,String name,String username,String pass){
        dataBaseHelper.storeDoe().updates(id,name,username,pass);
    }


}

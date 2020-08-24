package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {

    private Context context;
    private JSONArray array;

    public AdapterRecycler(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycle,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyFunction obj = new MyFunction();
        try{
            final JSONObject objData = array.getJSONObject(position);
            holder.tvCoffee.setText(objData.getString("product"));
            final String imgUrl = objData.getString("img");
            Picasso.get().load(imgUrl)
                    .placeholder(context.getResources().getDrawable(R.drawable.koi_coffe))
                    .error(context.getResources().getDrawable(R.drawable.koi_coffe))
                    .into(holder.imageone);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obj.openActivity(context,Details.class);
            }
            });
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }

    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCoffee;
        private ImageView imageone;
        private CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCoffee = itemView.findViewById(R.id.tvCoffee);
            imageone = itemView.findViewById(R.id.imageone);
            card = itemView.findViewById(R.id.card);
        }
    }
}

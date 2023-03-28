package com.example.pantry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter  extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<Ingredient> mPantry;
    private OnItemClickListener mListener;
    private IngredientAdapter mAdapter;

    public IngredientAdapter getmAdapter() {
        return mAdapter;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView;
        public TextView mTextView2;

        ImageButton minusIngredientButton;
        ImageButton plusIngredientButton;
        TextView ingredientQuantityInPantry;


        public IngredientViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById((R.id.textView2));

            minusIngredientButton = itemView.findViewById(R.id.imageButtonMinus);
            plusIngredientButton = itemView.findViewById(R.id.imageButtonPlus);
            ingredientQuantityInPantry = itemView.findViewById(R.id.textViewQuantity);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public IngredientAdapter(List<Ingredient> pantry){
        mPantry = pantry;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient, parent,false);
        IngredientViewHolder ivh = new IngredientViewHolder(v, mListener);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        final Ingredient currentIngredient = mPantry.get(position);
        currentIngredient.loadImageIntoImageView(holder.mImageView.getContext(), holder.mImageView);
//        holder.mImageView.setImageBitmap(currentIngredient.getImage());
        holder.mTextView.setText(currentIngredient.getName());
        holder.mTextView2.setText(currentIngredient.getBarcode());
        holder.ingredientQuantityInPantry.setText(Integer.toString(currentIngredient.getQtInPantry()));
        final PantryManager[] manager = new PantryManager[1];
        holder.plusIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager[0] = new PantryManager(v.getContext());
                manager[0].addToPantry(currentIngredient, 1);
                IngredientAdapter.this.notifyItemChanged(holder.getAdapterPosition());
            }
        });

        holder.minusIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager[0] = new PantryManager(v.getContext());
                manager[0].addToPantry(currentIngredient, -1);
                IngredientAdapter.this.notifyItemChanged(holder.getAdapterPosition());            }
        });
    }

    @Override
    public int getItemCount() {
        return mPantry.size();
    }
}

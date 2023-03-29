package com.example.pantry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ViewIngredientPopUpDialog extends AppCompatDialogFragment {

    private ImageView mIngredientImageView;
    private TextView mTextViewName;
    private TextView mTextViewCarbs;
    private TextView mTextViewFats;
    private TextView mTextViewProtein;
    private TextView mTextViewSugars;
    private TextView mTextViewQuantityInPantry;
    private TextView mTextViewQuantityInDefaultPantry;
    private TextView mTextViewBrand;
    private TextView mTextViewNutritionFactsHeader;
    private TextView mTextViewProductInformationHeader;
    private View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_view_ingredient_popup_dialog, null);
        Bundle receivedBundle = getArguments();
        String barcode = receivedBundle.getString("barcode", "");

        // initialize buttons and text views
        mIngredientImageView =              (ImageView)view.findViewById(R.id.ingredientImageView);
        mTextViewName =                     (TextView) view.findViewById(R.id.textViewName);
        mTextViewCarbs =                    (TextView) view.findViewById(R.id.textViewCarbs);
        mTextViewFats =                     (TextView) view.findViewById(R.id.textViewFats);
        mTextViewProtein =                  (TextView) view.findViewById(R.id.textViewProtein);
        mTextViewSugars =                   (TextView) view.findViewById(R.id.textViewSugars);
        mTextViewQuantityInPantry =         (TextView) view.findViewById(R.id.textViewQuantityInPantry);
        mTextViewQuantityInDefaultPantry =  (TextView) view.findViewById(R.id.textViewQuantityInDefaultPantry);
        mTextViewBrand =                    (TextView) view.findViewById(R.id.textViewBrand);
        mTextViewNutritionFactsHeader =     (TextView) view.findViewById(R.id.textViewNutritionHeader);
        mTextViewProductInformationHeader = (TextView) view.findViewById(R.id.textViewProductInformationHeader);

        // Get Ingredient and update pop up information
        PantryManager pm = new PantryManager(getActivity());
        Ingredient ingredient = pm.getIngredient(barcode);

        // update ImageViews
        ingredient.loadImageIntoImageView(getActivity(), mIngredientImageView);

        // update TextViews
        mTextViewName.setText(ingredient.getName());
        mTextViewCarbs.setText("Carbs: "     + Double.toString(ingredient.getCarbs()));
        mTextViewFats.setText("Fats: "       + Double.toString(ingredient.getFats()));
        mTextViewProtein.setText("Protein: " + Double.toString(ingredient.getProtein()));
        mTextViewSugars.setText("Sugars: "   + Double.toString(ingredient.getSugars()));
        mTextViewQuantityInPantry.setText("Qty in Pantry: " + ingredient.getQtInPantry());
        mTextViewQuantityInDefaultPantry.setText("Qty in Default Pantry: " + "0");// TODO: implement quantity in default pantry
        mTextViewBrand.setText("Brand: " + ingredient.getBrand());

        // set up builder
        builder.setView(view)
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

}

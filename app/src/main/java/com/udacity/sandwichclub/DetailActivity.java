package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView originTv, alsoKnownTv, alsoKnownLabelTv, ingredientsTv, placeOfOriginTv,
            placeOfOriginLabelTv, descriptionTv;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        alsoKnownLabelTv = findViewById(R.id.also_known_label_tv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        placeOfOriginLabelTv = findViewById(R.id.place_of_origin_label_tv);
        placeOfOriginTv = findViewById(R.id.place_of_origin_tv);
        descriptionTv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        originTv.setText(sandwich.getMainName());
        checkViewVisibility(sandwich.getAlsoKnownAs(), alsoKnownLabelTv, alsoKnownTv);
        ingredientsTv.setText(sandwich.getIngredients().toString());
        checkViewVisibility(sandwich.getPlaceOfOrigin(), placeOfOriginLabelTv, placeOfOriginTv);
        descriptionTv.setText(sandwich.getDescription());
    }

    private void checkViewVisibility(List<String> list, TextView tvLabel, TextView tv) {
        if(list.size() != 0) {
            tvLabel.setVisibility(View.VISIBLE);
            tv.setVisibility(View.VISIBLE);
            tv.setText(list.toString());
        } else {
            tvLabel.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);
        }
    }

    private void checkViewVisibility(String data, TextView tvLabel, TextView tv) {
        if(data != null && !(data.equals(""))) {
            tvLabel.setVisibility(View.VISIBLE);
            tv.setVisibility(View.VISIBLE);
            tv.setText(data);
        } else {
            tvLabel.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);
        }
    }
}

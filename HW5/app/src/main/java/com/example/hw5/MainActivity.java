package com.example.hw5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraRental crPriceList = new CameraRental(getApplicationContext());

        HashMap<String, Double> cameraPriceList = crPriceList.getPriceList();
        List<String> keys = new ArrayList<>(cameraPriceList.keySet());

        Spinner cameraSpinner = findViewById(R.id.camera_spinner);

        ArrayAdapter<String> cameraSpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, keys);
        cameraSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        cameraSpinner.setAdapter(cameraSpinnerAdapter);

        NumberPicker rentalDaysPicker = findViewById(R.id.rental_length);
        rentalDaysPicker.setMinValue(3);
        rentalDaysPicker.setMaxValue(21);


    }

    public void onClick(View view) {
        TextView results = findViewById(R.id.results);
        Spinner cameraModels = findViewById(R.id.camera_spinner);
        String cameraModel = String.valueOf(cameraModels.getSelectedItem());
        NumberPicker rentalLength = findViewById(R.id.rental_length);
        int rentalDays = rentalLength.getValue();
        EditText insurance = findViewById(R.id.insurance);
        String insuranceText = insurance.getText().toString().toLowerCase();
        double insuranceSurcharge = 0.00;

        String[] brandModelSplit = cameraModel.split(" ");

        CameraRental cameraRental = new CameraRental(brandModelSplit[0], brandModelSplit[1], rentalDays);
        boolean insuranceRequired = cameraRental.getInsuranceRequirements();
        double rentalCost = cameraRental.getRentalPrice();

       if (insuranceText.isEmpty()) {
            Toast toast = Toast.makeText(this, "Do You Have Insurance? Yes or No", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        if(!insuranceText.equals("yes") && insuranceRequired) {
            Toast toast = Toast.makeText(this, "Insurance is Required. $100 surcharge applied", Toast.LENGTH_LONG);
            toast.show();
            insuranceSurcharge = 100.00;
        } else {
            Toast toast = Toast.makeText(this, "No Insurance Required", Toast.LENGTH_LONG);
            toast.show();
        }

        double rentalTotal = rentalCost + insuranceSurcharge;

        // display results !
        results.setText("Camera: " + cameraModel + "\n"
                        + "Rental Length: " + rentalDays + "\n"
                        + "Insurance Surcharge: " + insuranceSurcharge + "\n"
                        + "Rental Price: " + rentalCost + "\n"
                        + "Total Price: " + rentalTotal + "\n");

    }
}

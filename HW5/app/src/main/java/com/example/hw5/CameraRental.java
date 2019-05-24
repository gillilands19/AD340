package com.example.hw5;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class CameraRental {



    private String cameraBrand;
    private String model;
    private double msrp;
    private int rentalLength;
    private double rentalPrice;
    private boolean insuranceNeeded;

    private HashMap<String, Double> priceList;

    public CameraRental(String cameraBrand, String model, int rentalLength) {
        this.priceList = buildPriceList();
        this.cameraBrand = cameraBrand;
        this.model = model;
        this.msrp = setMSRP(cameraBrand + ' ' + model);
        this.rentalLength = rentalLength;
        this.rentalPrice = this.msrp * .025 * this.rentalLength;
        this.insuranceNeeded = insuranceRequired(this.msrp, this.rentalLength);
    }

    public CameraRental(Context context) {
       this.priceList = buildPriceList();
       this.cameraBrand = "Nikon";
       this.model = "Z6";
       this.msrp = 2299.00;
       this.rentalLength = 7;
       this.rentalPrice = this.msrp * .025 * this.rentalLength;
       this.insuranceNeeded = insuranceRequired(this.msrp, this.rentalLength);
    }

    private boolean insuranceRequired(double msrp, int rentalLength) {
        if (msrp < 2000.00 && rentalLength <= 7){
            return false;
        } else if (msrp < 2000.00 && rentalLength > 7) {
            return true;
        } else if (msrp > 2000.00 && rentalLength <= 3) {
            return false;
        } else {
            return true;
        }
    }

    private static HashMap<String, Double> buildPriceList() {
        HashMap<String, Double> priceMap = new HashMap<>();
        priceMap.put("Leica M3", 3000.00);
        priceMap.put("Nikon Z6", 2299.00);
        priceMap.put("Sony A7III", 2999.00);
        priceMap.put("Nikon SP", 3000.00);
        priceMap.put("Hasselblad C500", 1500.00);
        priceMap.put("Canon EOSRP", 1299.00);
        return priceMap;
    }

    private double setMSRP(String camera) {
        if (this.priceList.containsKey(camera)) {
            return this.priceList.get(camera);
        }
        return 0.00;
    }

    public void changeRentalPeriod(int rentalLength) {
        this.rentalLength = rentalLength;
    }

    public String getCameraBrand() {
        return this.cameraBrand;
    }

    public String getModel() {
        return this.model;
    }

    public double getMSRP() {
        return this.msrp;
    }

    public void setCameraBrand(String cameraBrand) {
        this.cameraBrand = cameraBrand;
    }

    public HashMap<String, Double> getPriceList() {
        return this.priceList;
    }

    public boolean getInsuranceRequirements() {
        return this.insuranceNeeded;
    }

    public double getRentalPrice() {
        return this.rentalPrice;
    }
    public int getRentalLength() {
        return this.rentalLength;
    }


}

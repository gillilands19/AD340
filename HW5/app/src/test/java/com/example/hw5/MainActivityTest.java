package com.example.hw5;

import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Test
    public void testRentalLengthEmpty() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        activity.findViewById(R.id.confirm_rental).performClick();

        assertEquals("Do You Have Insurance? Yes or No", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testInsuranceNotRequired() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        String insuranceAnswer = "no";
        ((EditText)activity.findViewById(R.id.insurance)).setText(insuranceAnswer);
        activity.findViewById(R.id.confirm_rental).performClick();
        assertEquals("No Insurance Required",
                ShadowToast.getTextOfLatestToast());

    }
}

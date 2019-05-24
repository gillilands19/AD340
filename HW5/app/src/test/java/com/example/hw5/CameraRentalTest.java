package com.example.hw5;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.google.common.truth.Truth.assertThat;

public class CameraRentalTest {

    @Mock
    Context mockContext;

    private CameraRental cameraRental;
    private CameraRental defaultCameraRental;
    private CameraRental nikonSpRental = new CameraRental("Nikon", "SP",
            3);

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void leicaRentalReturnsCorrectVals() {
        cameraRental = new CameraRental("Leica", "M3", 7);
        assertThat(cameraRental.getInsuranceRequirements()).isEqualTo(true);
        assertThat(cameraRental.getRentalPrice()).isEqualTo(cameraRental.getMSRP() * .025 * cameraRental.getRentalLength());
        assertThat(cameraRental.getPriceList().containsKey(cameraRental.getCameraBrand() + " " + cameraRental.getModel()))
                .isEqualTo(true);
        assertThat(cameraRental.getMSRP())
                .isEqualTo(cameraRental.getPriceList().get(cameraRental.getCameraBrand() + " " + cameraRental.getModel()));
    }

    @Test
    public void gigDefaultGigLoadsDefaultValues() {
        defaultCameraRental = new CameraRental(mockContext);

        assertThat(defaultCameraRental.getCameraBrand()).isEqualTo("Nikon");
        assertThat(defaultCameraRental.getModel()).isEqualTo("Z6");
        assertThat(defaultCameraRental.getRentalPrice()).isEqualTo(402.325);
    }

    @After
    public void tearDown() {

    }
}

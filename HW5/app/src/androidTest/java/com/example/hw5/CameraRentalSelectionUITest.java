package com.example.hw5;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@RunWith(JUnit4.class)
@LargeTest
public class CameraRentalSelectionUITest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testCameraRentalSelection() {
        onView(withId(R.id.camera_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Nikon SP"))).perform(click());
        onView(withId(R.id.insurance)).perform(typeText("yes"), closeSoftKeyboard());
        onView(withId(R.id.confirm_rental)).perform(click());
        onView(withId(R.id.results))
                .check(matches(withText("Camera: Nikon SP\nRental Length: 3\nInsurance Surcharge: 0.0\nRental Price: 225.0\nTotal Price: 225.0\n")));

    }
}

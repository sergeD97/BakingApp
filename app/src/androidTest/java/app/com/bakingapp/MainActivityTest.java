package app.com.bakingapp;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Serge Pessokho on 10/11/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public static final String RECIPE = "Nutella Pie";
    public static final String BTN_TEXT = "INGREDIENTS :";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) ;

    @Test
    public void recip_list_test(){
        onView(withText(RECIPE)).check(matches(isDisplayed()));

        onView(withId(R.id.list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // check if stepactivity were displayed
        onView(withId(R.id.ingre_but)).check(matches(isDisplayed()));

        onView(withText(BTN_TEXT)).check(matches(isDisplayed()));


        //clicks some item in the  step list
        onView(withId(R.id.step_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //check if the descrption textview is displayed
        onView(withId(R.id.description_tv)).check(matches(isDisplayed()));

    }

}

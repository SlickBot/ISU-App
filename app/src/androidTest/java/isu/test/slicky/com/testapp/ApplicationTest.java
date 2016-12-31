package isu.test.slicky.com.testapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.slicky.isu.activities.MainActivity;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ActivityTestRule<MainActivity> {

    public ApplicationTest() {
        super(MainActivity.class);
    }

    @Test
    public void customIntentToStartActivity() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        launchActivity(intent);
    }
}
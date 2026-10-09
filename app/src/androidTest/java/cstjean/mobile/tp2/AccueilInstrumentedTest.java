package cstjean.mobile.tp2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AccueilInstrumentedTest {


    /**
     * Règle à éxécuter avant le test.
     */
    @Rule
    public ActivityScenarioRule<AccueilActivity> rule = new ActivityScenarioRule<>(AccueilActivity.class);

    @Test
    public void testSaisieNoms() {

        String texte1 = "Marc";

        String texte2 = "Jean";

        onView(withId(R.id.txtNomJoueur1)).perform(typeText(texte1), closeSoftKeyboard());
        onView(withId(R.id.txtNomJoueur2)).perform(typeText(texte2), closeSoftKeyboard());

        onView(withId(R.id.txtNomJoueur1)).check(matches(withText(texte1)));
        onView(withId(R.id.txtNomJoueur2)).check(matches(withText(texte2)));

        onView(withId(R.id.btnJouer)).perform(click());

        onView(withId(R.id.changement_tour)).check(matches(withText(texte2)));
    }

    @Test
    public void testSansNom() {
        onView(withId(R.id.btnJouer)).check(matches(not(isEnabled())));
    }

    @Test
    public void testSaisie1Nom() {

        String texte1 = "Marc";

        onView(withId(R.id.txtNomJoueur1)).perform(typeText(texte1), closeSoftKeyboard());
        onView(withId(R.id.txtNomJoueur1)).check(matches(withText(texte1)));

        onView(withId(R.id.btnJouer)).check(matches(not(isEnabled())));
    }

    @Test
    public void testSaisie2Nom() {

        String texte2 = "Marc";

        onView(withId(R.id.txtNomJoueur2)).perform(typeText(texte2), closeSoftKeyboard());
        onView(withId(R.id.txtNomJoueur2)).check(matches(withText(texte2)));

        onView(withId(R.id.btnJouer)).check(matches(not(isEnabled())));
    }
}
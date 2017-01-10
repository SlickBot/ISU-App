package com.slicky.isu.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.slicky.decisiontree.*;
import com.slicky.isu.ActivityUtils;
import com.slicky.isu.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {
    private static final String TAG = ChooseActivity.class.getSimpleName();

    private ActivityUtils utils = ActivityUtils.getInstance();
    private DecisionTreeParser parser = DecisionTreeParser.INSTANCE;

    private TreeData data;
    private Decision currentDecision;
    private List<String> flags;

    private LinearLayout llAnswers;
    private LinearLayout llQuestions;
    private TextView tvQuestions;
    private ImageView ivMore;

    private TextView selectedView;
    private Answer selectedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        utils.removeActionBar(this);

        llAnswers = (LinearLayout) findViewById(R.id.llAnswers);
        llQuestions = (LinearLayout) findViewById(R.id.llQuestions);
        tvQuestions = (TextView) findViewById(R.id.tvQuestion);
        ivMore = (ImageView) findViewById(R.id.ivMore);
        flags = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int resourceID = bundle.getInt("resource_id");

        try {
            // get parsed decision tree data
            data = parser.parse(getResources().openRawResource(resourceID));
            // find root decision
            Decision root = data.findRootDecision();
            // set root decision
            setDecision(root);

        } catch (DecisionTreeException e) {
            Log.wtf(TAG, "Error while parsing data!", e);
            alertAndDie(e);
        }
    }

    public void continueClick(View view) {
        if (selectedView == null || selectedAnswer == null) {
            displaySelectionNotification();
            return;
        }

        try {
            String actionID = selectedAnswer.getActionID();
            Action selectedAction = data.findAction(actionID);

            // add flags from selected answer
            flags.addAll(selectedAnswer.getFlags());
            // add flags from selected action
            flags.addAll(selectedAction.getFlags());

            switch (selectedAction.getType()) {

                case DECISION:
                    String decisionID = selectedAction.getNextID();
                    Decision nextDecision = data.findDecision(decisionID);
                    setDecision(nextDecision);
                    return;

                case END:
                    String endID = selectedAction.getNextID();
                    End end = data.findEnd(endID);
                    Intent intent = new Intent(this, EndActivity.class);
                    intent.putExtra("end", end);
                    intent.putExtra("flags", flags.toArray());
                    startActivity(intent);
                    finish();
            }

        } catch (DecisionTreeException e) {
            Log.wtf(TAG, "Error while finding data!", e);
            alertAndDie(e);
        }
    }

    private void displaySelectionNotification() {
        Snackbar snackbar = Snackbar.make(
                llAnswers,
                R.string.choose_must_be_selected,
                Snackbar.LENGTH_SHORT
        );
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }

    private void setDecision(final Decision decision) {
        // display question
        tvQuestions.setText(decision.getText());
        // display more if not present
        if (decision.getMore() != null)
            ivMore.setVisibility(View.VISIBLE);
        else
            ivMore.setVisibility(View.GONE);

        // set decision as current
        currentDecision = decision;
        // save all flags from decision
        flags.addAll(currentDecision.getFlags());

        // hide answers
        llAnswers.setAlpha(0);
        // hide questions
        llQuestions.setAlpha(0);

        // remove old answers
        llAnswers.removeAllViews();
        // add new answers
        for (Answer answer : decision.getAnswers()) {
            llAnswers.addView(createButton(answer));
        }

        // display questions
        llQuestions.animate()
            .alpha(1.0f)
            .setDuration(1000)
            .setListener(null);

        // display answers
        llAnswers.animate()
                .alpha(1.0f)
                .setStartDelay(500)
                .setDuration(1000)
                .setListener(null);
    }

    private RelativeLayout createButton(final Answer answer) {
        final LayoutInflater inflater = getLayoutInflater();
        final RelativeLayout layout =
                (RelativeLayout) inflater.inflate(R.layout.button_layout, llAnswers, false);

        final TextView tv = (TextView) layout.findViewById(R.id.tvAnswer);
        final ImageView iv = (ImageView) layout.findViewById(R.id.ibMore);

        // set button text
        tv.setText(answer.getText());
        // set button on selected listener
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected((TextView) layout.findViewById(R.id.tvAnswer), answer);
            }
        });
        // display more icon if there is more text
        if (answer.getMore() != null) {
            iv.setVisibility(View.VISIBLE);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moreForAnswer(answer);
                }
            });
        }

        return layout;
    }

    private void setSelected(TextView view, Answer answer) {
        if (selectedAnswer == answer)
            return;

        int selectedColor = utils.getColor(this, R.color.colorSecondary);
        int unselectedColor = utils.getColor(this, R.color.colorPrimary);

        startAnimation(view, 500, unselectedColor, selectedColor);

        if (selectedView != null)
            startAnimation(selectedView, 500, selectedColor, unselectedColor);

        selectedView = view;
        selectedAnswer = answer;
    }

    private void moreForAnswer(Answer answer) {
        String questionString = currentDecision.getText();
        String answerString = answer.getText();
        String title = String.format("%s - %s", questionString, answerString);

        Intent intent = new Intent(this, MoreActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("text", answer.getMore());
        startActivity(intent);
    }

    public void moreForQuestion(View view) {
        Intent intent = new Intent(this, MoreActivity.class);
        intent.putExtra("title", currentDecision.getText());
        intent.putExtra("text", currentDecision.getMore());
        startActivity(intent);
    }

    private void startAnimation(final TextView view, int duration, int colorFrom, int colorTo) {
        final ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(colorFrom, colorTo);
        anim.setDuration(duration);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (int) valueAnimator.getAnimatedValue();
                view.setBackgroundColor(val);
            }
        });
        anim.start();
    }

    private void alertAndDie(DecisionTreeException e) {
        new AlertDialog.Builder(this)
                .setTitle("Error while parsing data")
                .setMessage(e.getMessage())
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
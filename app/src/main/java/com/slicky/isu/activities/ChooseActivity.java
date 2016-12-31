package com.slicky.isu.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.slicky.decisiontree.*;
import com.slicky.isu.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {
    private static final String TAG = ChooseActivity.class.getSimpleName();

    private ActivityUtils utils = ActivityUtils.getInstance();
    private DecisionTreeParser parser = DecisionTreeParser.INSTANCE;

    private TreeData data;
    private Decision currentDecision;
    private LinearLayout llAnswers;
    private TextView tvQuestions;
    private RadioGroup group;
    private List<String> flags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        utils.removeActionBar(this);

        llAnswers = (LinearLayout) findViewById(R.id.llAnswers);
        tvQuestions = (TextView) findViewById(R.id.tvQuestion);
        flags = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int resourceID = bundle.getInt("resource_id");

        try {
            // get parsed decision tree data
            data = parser.parse(getResources().openRawResource(resourceID));
            // set root decision as current
            currentDecision = data.getRootDecision();

            setDecision(currentDecision);

        } catch (DecisionTreeException e) {
            Log.wtf(TAG, "Error while parsing data!", e);
            alertAndDie(e);
        }
    }

    public void continueClick(View view) {
        int checked = group.getCheckedRadioButtonId();
        if (checked == -1)
            return;

        try {
            Answer selectedAnswer = currentDecision.getAnswers().get(checked);
            String actionID = selectedAnswer.getActionID();
            Action selectedAction = data.findAction(actionID);
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

    private void setDecision(Decision decision) {
        // display question
        tvQuestions.setText(decision.getText());

        // set decision as current
        currentDecision = decision;
        // save all flags from decision
        flags.addAll(currentDecision.getFlags());

        // create new radio group
        group = new RadioGroup(this);
        // set radio gravity
        group.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        // remove old group
        llAnswers.removeAllViews();
        // add new group
        llAnswers.addView(group);

        List<Answer> answers = decision.getAnswers();
        // add new answers
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            RadioButton answerButton = createAnswerButton(answer);
            answerButton.setId(i);
            group.addView(answerButton);
        }
    }

    private RadioButton createAnswerButton(Answer answer) {
        RadioButton button = new RadioButton(this);
        button.setText(answer.getText());
        button.setTextSize(20.0f);
        button.setTextColor(Color.WHITE);
        button.setPadding(0, 75, 0, 75);
        return button;
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
package com.team7.hadcontrolpanel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


// initiate variable
public class calender extends AppCompatActivity {
    CalendarView calendarView;
    TextView myDate;
    DatabaseReference databaseReference;
    private TextInputEditText inputTitle;
    private EditText inputDay;
    private EditText  inputTask;
    private FloatingActionButton viewCal;

    //button
    Button btnSave;


    //On create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set content view
        setContentView(R.layout.activity_calender);
        // button
        Button btnSave = (Button) findViewById(R.id.btnSave);

        //reference for databbase
        databaseReference= FirebaseDatabase.getInstance().getReference("Tasks");
        viewCal =findViewById(R.id.viewCal);

        viewCal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent t = new Intent(calender.this, DataRetrived.class);
                //startActivity(new Intent(calender.this, DataRetrived.class));
                startActivity(t);
            }
        });
        inputTitle = findViewById(R.id.txtTitle);
        inputDay = (EditText)findViewById(R.id.txtDay);
        inputTask = (EditText)findViewById(R.id.txtTask);


        // when the user cilcks, add a new task, and opens a new dialog.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
                addTasks();
            }
        });

        calendarView = (CalendarView) findViewById(R.id.Calenderview);
        myDate = (TextView) findViewById(R.id.myDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 + 1) + "/" + i2 + "/" + i;
                myDate.setText(date);
                inputDay.setText(date);
            }
        });
    }

    // open a new dialog.
    public void openDialog() {
        DialogBox dialogBox = new DialogBox("Information", "Your date has been saved");
        dialogBox.show(getSupportFragmentManager(), "Dialog");
    }

    // add a new task when the user click on add task Button
    public void addTasks(){
        String titleName = inputTitle.getText().toString();
        String dayName = inputDay.getText().toString();
        String taskName = inputTask.getText().toString();

        if(!TextUtils.isEmpty(titleName)&& !TextUtils.isEmpty((taskName))) {

            String id = databaseReference.push().getKey();

            CalTask tasks = new CalTask(id, taskName,  titleName,  dayName);
            databaseReference.child(id).setValue(tasks);
            inputTitle.setText(" ");
            inputDay.setText(" ");
            inputTask.setText(" ");
        }
    }
}
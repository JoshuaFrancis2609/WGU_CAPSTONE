package com.example.d308vacationplanner.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExcursionDetails  extends AppCompatActivity {

    //Variables
    int excursionId;
    String excursionTitle;
    String excursionDate;
    int vacationId;

    String vacationStartDate;
    String vacationEndDate;

    //Edits
    EditText editExcursionTitle;
    EditText editExcursionDate;

    Repository repository;

    //SimpleDateForm for notifications and validations and datePicker
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());

        //Excursion title
        excursionTitle = getIntent().getStringExtra("excursionTitle");
        editExcursionTitle = findViewById(R.id.excursionTitleText);
        editExcursionTitle.setText(excursionTitle);

        //Excursion date
        excursionDate = getIntent().getStringExtra("excursionDate");
        editExcursionDate = findViewById(R.id.excursionDateText);
        editExcursionDate.setText(excursionDate);

        //Excursion Id
        excursionId = getIntent().getIntExtra("excursionId", -1);
        vacationId = getIntent().getIntExtra("vacationId", -1);

        //Fixing back arrow bug that brings up blank vacation instead of the one the user was on
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Figuring out if excursion exsits already or is new
        if (excursionId != -1) {
            Excursion excursion = repository.getExcursionById(excursionId);

            if (excursion != null) {
                editExcursionTitle.setText(excursion.getExcursionTitle());
                editExcursionDate.setText(excursion.getExcursionDate());
            }
        }

        //datePicker for excursion date and calendar
        editExcursionDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ExcursionDetails.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date =
                                ((selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear);
                        editExcursionDate.setText(date);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //For creating a new excursion or updating an older excursion
        if (item.getItemId() == R.id.excursion_save) {
            saveOrUpdateExcursion();
            return true;
        }

        //For deleting a vacation
        else if (item.getItemId() == R.id.excursion_delete) {
            deleteExcursion();
            return true;
        }

        //For sharing an excursion
        else if (item.getItemId() == R.id.excursion_share) {
            shareExcursion();
            return true;
        }

        //For setting notifications for excursions
        else if (item.getItemId() == R.id.excursion_notification) {
            setExcursionNotification();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void saveOrUpdateExcursion() {
        Excursion excursion;

        //Validation for date formating
        if(!validateExcursionDate()) {
            return;
        }

        //For creating a new excursion
        if (excursionId == -1) {
            if (repository.getAllExcursions().isEmpty()) {
                excursionId = 1;
            } else {
                excursionId = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionId() + 1;
            }

            excursion = new Excursion(
                    excursionId,
                    editExcursionTitle.getText().toString(),
                    editExcursionDate.getText().toString(),
                    vacationId
            );

            repository.insert(excursion);
            Toast.makeText(this, excursion.getExcursionTitle() + " excursion added.", Toast.LENGTH_SHORT).show();

        } else {

            //For updating an excursion
            excursion = new Excursion(
                    excursionId,
                    editExcursionTitle.getText().toString(),
                    editExcursionDate.getText().toString(),
                    vacationId
            );

            repository.update(excursion);

            Toast.makeText(this, excursion.getExcursionTitle() + " excursion updated.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    //For deleting an excursion
    private void deleteExcursion() {

        Excursion excursion = new Excursion(
                excursionId,
                editExcursionTitle.getText().toString(),
                editExcursionDate.getText().toString(),
                vacationId
        );

        repository.delete(excursion);

        Toast.makeText(this, excursion.getExcursionTitle() + " excursion deleted", Toast.LENGTH_SHORT).show();

        finish();
    }

    //Share excursion - not needed apparently,but I've already started it, why wouldn't a user want to also share excursions?
    //I see this is sorta done in the sharing vacations but leaving it in case you wanted to share just one excursion once you're
    //alread on your vacation.

    private void shareExcursion() {

        //Check that the vacation has been saved first
        if(excursionId == -1) {
            Toast.makeText(this, "Please save the excursion before sharing.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        String excursionDetails = "Excursion: " + excursionTitle + "\nExcursion Date: " + excursionDate;

        shareIntent.putExtra(Intent.EXTRA_TEXT, excursionDetails);
        startActivity(Intent.createChooser(shareIntent, "Share Excursion Details"));
        Toast.makeText(this, excursionTitle + " shared.", Toast.LENGTH_SHORT).show();
    }

    //Notifications for the excursion
    private void setExcursionNotification() {
        //Retrieve excursion details
        Excursion excursion = new Excursion(
                excursionId,
                editExcursionTitle.getText().toString(),
                editExcursionDate.getText().toString(),
                vacationId
        );

        //Set notification for the excursion
        setNotificationForDate(excursion);
            Toast.makeText(this, "Notifications set for this excursion", Toast.LENGTH_SHORT).show();
    }

    //Set notifications for the excursion date
    private void setNotificationForDate(Excursion excursion) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager == null) {
            return;
        }
        try {
            //Set notification
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(sdf.parse(excursion.getExcursionDate()));

            Intent startIntent = new Intent(this, ExcursionNotificationReceiver.class);
            startIntent.putExtra("excursionTitle", excursion.getExcursionTitle());

            PendingIntent startPendingIntent = PendingIntent.getBroadcast(
                    this,
                    ++MainActivity.numAlert,
                    startIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    startCalendar.getTimeInMillis(),
                    startPendingIntent
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //Validator for dates inputted by the user, check the format.
    //Then checks that the excursion date is withing the dates for the vacation.
    private boolean validateExcursionDate() {

        //Get excursion date
        String excursionDate = editExcursionDate.getText().toString();

        //Get vacation dates
        String vacationStartDate = getIntent().getStringExtra("vacationStartDate");
        String vacationEndDate = getIntent().getStringExtra("vacationEndDate");

        //Empty validation check
        if (excursionDate.isEmpty()) {
            Toast.makeText(this, "Date cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (vacationStartDate == null || vacationEndDate == null ||
                vacationStartDate.isEmpty() || vacationEndDate.isEmpty()) {
            Toast.makeText(this, "Vacation start and end dates are required.", Toast.LENGTH_SHORT).show();
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        sdf.setLenient(false);

        //Checks that excursion date (and start and end date) are parsed correctly
        try {
            Date dateOfExcursion = sdf.parse(excursionDate);
            Date start = sdf.parse(vacationStartDate);
            Date end = sdf.parse(vacationEndDate);

            //Null validation check
            if(dateOfExcursion != null && start != null && end != null) {

                //Checks that excursion date is withing the vacation dates
                if (dateOfExcursion.before(start)) {
                    Toast.makeText(this,"Excursion date is before the vacation start date.", Toast.LENGTH_LONG).show();
                    Toast.makeText(this,"Excursion date must be during the vacation!",Toast.LENGTH_LONG).show();
                    return false;
                } else if (dateOfExcursion.after(end)) {
                    Toast.makeText(this,"Excursion date is after the vacation end date.", Toast.LENGTH_LONG).show();
                    Toast.makeText(this,"Excursion date must be during the vacation!", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
            return true;
        } catch (ParseException e) {

            //If the dates aren't parsed correctly
            Toast.makeText(this, "Dates must be in MM/dd/yyyy format.", Toast.LENGTH_SHORT).show();
            return false;

        }
    }
}
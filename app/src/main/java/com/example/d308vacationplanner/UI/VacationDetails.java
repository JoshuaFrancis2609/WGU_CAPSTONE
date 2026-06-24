package com.example.d308vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFormatException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    //Variabls
    int vacationID;
    String vacationTitle;
    String vacationHotel;
    String startDate;
    String endDate;

    //edits
    EditText editVacationTitle;
    EditText editVacationHotel;
    EditText editStartDate;
    EditText editEndDate;
    Repository repository;

    //SimpleDataForm for notifications and validations and datePicker
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        //Create fab
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);

        //edit text fields in vacation details
        editVacationTitle = findViewById(R.id.titleText);
        editVacationHotel = findViewById(R.id.hotelText);
        editStartDate = findViewById(R.id.startDateText);
        editEndDate = findViewById(R.id.endDateText);

        //get data from vacation edit text fields
        vacationID = getIntent().getIntExtra("vacationID", -1);
        vacationTitle = getIntent().getStringExtra("vacationTitle");
        vacationHotel = getIntent().getStringExtra("vacationHotel");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");

        //set data to vacation edit text fields
        editVacationTitle.setText(vacationTitle);
        editVacationHotel.setText(vacationHotel);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        //datePicker for vacation start date
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        VacationDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        editStartDate.setText(
                                (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear);
                    }
                }, year,month,day
                );
                datePickerDialog.show();
            }
        });

        //datePicker for vacation end date
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        VacationDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        editEndDate.setText(
                                (selectedMonth + 1) + "/" + selectedDay + "/" +selectedYear);
                    }
                }, year,month,day
                );
                datePickerDialog.show();
            }
        });

        //fab clickListner
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);

                intent.putExtra("vacationId", vacationID);
                intent.putExtra("vacationStartDate", editStartDate.getText().toString());
                intent.putExtra("vacationEndDate", editEndDate.getText().toString());

                startActivity(intent);
            }
        });

        //Create recycler view
        RecyclerView recyclerView = findViewById(R.id.VacationDetailsRecyclerView);

        //get repository
        repository = new Repository(getApplication());

        //Trying to filter excursions by vactions when a vacation is clicked on
        int vacationID = getIntent().getIntExtra("vacationID", -1);

        //Excursion adapter
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(repository.getAssociatedExcursions(vacationID));
    }

    @Override
    public void onResume() {
        super.onResume();

        //Creating the recycler view
        RecyclerView recyclerView = findViewById(R.id.VacationDetailsRecyclerView);

        //getting repository
        repository = new Repository(getApplication());

        //Excursion adapter for onResume
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(repository.getAssociatedExcursions(vacationID));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        //Updating handling the menu
        //For creating a new vacation or updating an older vacation
        else if (item.getItemId() == R.id.vacation_save) {
            saveOrUpdateVacation();
            return true;
        }

        //For deleting a vacation
        else if (item.getItemId() == R.id.vacation_delete) {
            deleteVacation();
            return true;
        }

        //For sharing a vacation
        else if (item.getItemId() == R.id.vacation_share) {
            shareVacation();
            return true;
        }

        //For setting notifications for vacations
        else if (item.getItemId() == R.id.vacation_notification) {
            setVacationNotification();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void saveOrUpdateVacation() {

        Vacation vacation;

        //Validator for date formatting and for end date being after the start date
        if(!validateDates()) {
            return;
        }

        //For creating a new vacation
        if (vacationID == -1) {
            if (repository.getAllVacations().isEmpty()) {
                vacationID = 1;
            } else {
                vacationID = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationID() + 1;
            }

            vacation = new Vacation(vacationID, editVacationTitle.getText().toString(), editVacationHotel.getText().toString(),
                    editStartDate.getText().toString(), editEndDate.getText().toString()
            );

            repository.insert(vacation);

            Toast.makeText(this, vacation.getVacationTitle() + " vacation added.", Toast.LENGTH_SHORT).show();

        } else {

            //For updating a vacation
            vacation = new Vacation(vacationID, editVacationTitle.getText().toString(), editVacationHotel.getText().toString(),
                    editStartDate.getText().toString(), editEndDate.getText().toString()
            );

            repository.update(vacation);

            Toast.makeText(this, vacation.getVacationTitle() + " vacation updated.", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    //For deleting a vacation
    private void deleteVacation() {

        List<Excursion> associatedExcursions = repository.getAssociatedExcursions(vacationID);

        //Check if vacation has any associated excursions
        if (associatedExcursions != null && !associatedExcursions.isEmpty()) {
            Toast.makeText(this, "Cannot delete this vacation. There " +
                    "are excursions associated with this vacation", Toast.LENGTH_SHORT).show();
            return;

        }

        //Delete the vacation if there are no associated excursions
        Vacation vacation = new Vacation (
                vacationID, editVacationTitle.getText().toString(), editVacationHotel.getText().toString(),
                editStartDate.getText().toString(), editEndDate.getText().toString()
        );

        repository.delete(vacation);

        Toast.makeText(this, vacation.getVacationTitle() + " vacation deleted",Toast.LENGTH_SHORT).show();

        finish();
    }

    //Share vacation
    private void shareVacation() {

        //Check that the vacation has been saved first
        if(vacationID == -1) {
            Toast.makeText(this, "Please save the vacation before sharing.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        String vacationDetails = "Vacation: " + vacationTitle + "\nHotel: " +
                vacationHotel + "\nStart Date: " + startDate + "\nEnd Date: " +
                endDate;

        //Add associated excursions
        List<Excursion> associatedExcurions = repository.getAssociatedExcursions(vacationID);

        vacationDetails += "\n\nExcursions:";

        if (associatedExcurions.isEmpty()) {
            vacationDetails += "\nNo excursions associated with this vacation.";
        } else {
            for (Excursion excursion : associatedExcurions) {
                vacationDetails += "\n" +
                        excursion.getExcursionTitle() + " - " + excursion.getExcursionDate();
            }
        }

        shareIntent.putExtra(Intent.EXTRA_TEXT, vacationDetails);
        startActivity(Intent.createChooser(shareIntent, "Share Vacation Details"));
        Toast.makeText(this, vacationTitle + " shared.", Toast.LENGTH_SHORT).show();
    }

    private void setVacationNotification() {
        //Retrieve vacation details
        Vacation vacation = new Vacation(
            vacationID,
            editVacationTitle.getText().toString(),
            editVacationHotel.getText().toString(),
            editStartDate.getText().toString(),
            editEndDate.getText().toString()
        );

        //Set notification for the vacation
        setNotificationForDates(vacation);
            Toast.makeText(this, "Notifications set for this vacation", Toast.LENGTH_SHORT).show();
    }

    //Set notification for vacation start and end dates
    private void setNotificationForDates(Vacation vacation) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager == null) {
            return;
        }
        try {
            //Set notification for start date
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(sdf.parse(vacation.getStartDate()));

            Intent startIntent = new Intent(this, VacationNotificationReceiver.class);
            startIntent.putExtra("vacationTitle", vacation.getVacationTitle());
            startIntent.putExtra("status", "starting");

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

            //Set notification for end date
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(sdf.parse(vacation.getEndDate()));

            Intent endIntent = new Intent(this, VacationNotificationReceiver.class);
            endIntent.putExtra("vacationTitle", vacation.getVacationTitle());
            endIntent.putExtra("status", "ending");

            PendingIntent endPendingIntent = PendingIntent.getBroadcast(
                    this,
                    ++MainActivity.numAlert,
                    endIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    endCalendar.getTimeInMillis(),
                    endPendingIntent
            );

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //Validator for dates inputted by the user, check that it is in the correct format
    //Then checks that the end date is not before the start date.
    private boolean validateDates() {


        String startDate = editStartDate.getText().toString();
        String endDate = editEndDate.getText().toString();

        //Empty/null validation check
        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Dates cannot be empty.", Toast.LENGTH_SHORT).show();
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        sdf.setLenient(false);

        //Checks that dates are parsed correctly
        try {

            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            //Checks that end date is after the start date
            if (start != null && end != null && end.before(start)) {
                Toast.makeText(this, "End date cannot be before start date.", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;

        } catch (ParseException e) {
            //If the dates aren't parsed correctly
            Toast.makeText(this, "Dates must be in MM/dd/yyyy format.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
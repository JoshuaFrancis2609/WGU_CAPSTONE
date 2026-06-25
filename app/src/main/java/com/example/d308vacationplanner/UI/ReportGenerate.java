package com.example.d308vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportGenerate extends AppCompatActivity {

    private Repository repository;
    private TableLayout vacationTable;
    private TableLayout excursionTable;
    private SearchView searchView;
    private TextView reportDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_generate);

        // Initialize repository and UI components
        repository = new Repository(getApplication());
        searchView = findViewById(R.id.searchView);
        vacationTable = findViewById(R.id.vacationTable);
        excursionTable = findViewById(R.id.excursionTable);
        reportDate = findViewById(R.id.reportDate);

        // Generate the report timestamp
        setReportDate();

        // Generate a report when the user submits a search value
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // Method for creating the timestamp for the report
    private void setReportDate() {
        String timestamp = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault())
                .format(new Date());

        reportDate.setText("Generated at: " + timestamp);
    }

    private void performSearch(String query) {
        setReportDate();

        vacationTable.removeAllViews();
        excursionTable.removeAllViews();

        addHeaderRow(vacationTable, "ID", "Title", "Hotel", "Start Date", "End Date");
        addHeaderRow(excursionTable, "Vacation ID", "Excursion ID", "Title", "Date");

        List<Integer> matchingVacationIds = new ArrayList<>();
        String searchText = query == null ? "" : query.toLowerCase(Locale.ROOT).trim();

        if (searchText.length() < 3) {
            addMessageRow(vacationTable, "Please enter at least 3 characters.");
            addMessageRow(excursionTable, "No excursions to display");
            return;
        }

        for (Vacation vacation : repository.getAllVacations()) {
            String vacationTitle = vacation.getVacationTitle() == null ? "" :
                    vacation.getVacationTitle().toLowerCase(Locale.ROOT);

            String vacationHotel = vacation.getVacationHotel() == null ? "":
                    vacation.getVacationHotel().toLowerCase(Locale.ROOT);

            if (!searchText.isEmpty() && (vacationTitle.contains(searchText) || vacationHotel.contains(searchText))) {
                matchingVacationIds.add(vacation.getVacationID());
                addVacationRow(vacation);
            }
        }

        if (matchingVacationIds.isEmpty()) {
            addMessageRow(vacationTable, "No vacations found.");
            addMessageRow(excursionTable, "No excursions to display.");
            return;
        }

        boolean excursionsFound = false;

        for (Excursion excursion : repository.getAllExcursions()) {
            if (matchingVacationIds.contains(excursion.getVacationId())) {
                addExcursionRow(excursion);
                excursionsFound = true;
            }
        }

        if (!excursionsFound) {
            addMessageRow(excursionTable, "No excursions associated with the matching vacation(s).");
        }
    }

    private void addVacationRow(Vacation vacation) {
        TableRow row = new TableRow(this);

        addStyledTextView(row, String.valueOf(vacation.getVacationID()));
        addStyledTextView(row, vacation.getVacationTitle());
        addStyledTextView(row, vacation.getVacationHotel());
        addStyledTextView(row, vacation.getStartDate());
        addStyledTextView(row, vacation.getEndDate());

        vacationTable.addView(row);
        addDivider(vacationTable);
    }

    private void addExcursionRow(Excursion excursion) {
        TableRow row = new TableRow(this);

        addStyledTextView(row, String.valueOf(excursion.getVacationId()));
        addStyledTextView(row, String.valueOf(excursion.getExcursionId()));
        addStyledTextView(row, excursion.getExcursionTitle());
        addStyledTextView(row, excursion.getExcursionDate());

        excursionTable.addView(row);
        addDivider(excursionTable);
    }

    private void addHeaderRow(TableLayout tableLayout, String... headers) {
        TableRow headerRow = new TableRow(this);

        for (String header : headers) {
            TextView textView = createTextView(header);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            headerRow.addView(textView);
        }

        tableLayout.addView(headerRow);
        addDivider(tableLayout);
    }

    private void addStyledTextView(TableRow row, String text) {
        row.addView(createTextView(text));
    }

    private void addMessageRow(TableLayout tableLayout, String message) {
        TableRow row = new TableRow(this);

        TextView textView = createTextView(message);
        textView.setTextColor(Color.RED);
        textView.setTypeface(null, Typeface.BOLD);

        row.addView(textView);
        tableLayout.addView(row);
        addDivider(tableLayout);
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);

        textView.setText(text == null ? "" : text);
        textView.setPadding(8, 8, 8, 8);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
        );

        textView.setLayoutParams(params);

        return textView;
    }

    private void addDivider(TableLayout tableLayout) {
        View divider = new View(this);

        divider.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                1
        ));

        divider.setBackgroundColor(Color.GRAY);
        tableLayout.addView(divider);
    }
}
package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationList extends AppCompatActivity {
    private Repository repository;
    private VacationAdapter vacationAdapter;

    //Create onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);


        //Create fab
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        //End of create fab

        //Create recycler view
        RecyclerView recyclerView = findViewById(R.id.VacationListRecyclerView);

        //get repository
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getAllVacations();

        //Vacation adapter
        vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacation(allVacations);

        //System.out.println(getIntent().getStringExtra("test"));
    }

    //menu item inflator and back button navigation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    //On resume for going back to view updates to database
    @Override
    protected void onResume() {
        super.onResume();
        List<Vacation> allVacations = repository.getAllVacations();
        vacationAdapter.setVacation(allVacations);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add_sample_data) {

            repository = new Repository(getApplication());

            List<Vacation> vacations = repository.getAllVacations();

            if (vacations != null) {
                for (Vacation v : vacations) {
                    if (v.getVacationTitle().equals("Scotland Legends")) {
                        Toast.makeText(this, "Sample data already added", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
            }

            //Inform user sample data is being added
            Toast.makeText(this, "Adding sample data...", Toast.LENGTH_SHORT).show();

            //Add sample code

            //Scotland vacation
            Vacation highlands = new Vacation(0, "Scotland Legends", "Heatherstone Castle Hotel", "08/01/2026", "08/15/2026");

            //Scotland excursions
            Excursion highlands_loch_ness = new Excursion(0,"Loch Ness Monster Boat Tour", "08/03/2026",1);
            Excursion highlands_ghost_tour = new Excursion(0, "Edinburgh Poltergeist Tour", "08/05/2026", 1);
            Excursion highlands_harry_potter = new Excursion(0, "Harry Potter Steam Engine Ride", "08/07/2026", 1);
            Excursion highlands_castle_tour = new Excursion(0, "Edinburgh Castle Guided Tour", "08/09/2026", 1);
            Excursion highlands_coo_farm = new Excursion(0, "Highland Coo Farm Experience", "08/11/2026", 1);

            //Norway vacation
            Vacation midnight_sun = new Vacation(0, "Midnight Sun Adventure", "Frostfjord Lodge","06/20/2026", "07/04/2026");

            //Norway excursions
            Excursion midnight_sun_northern_lights = new Excursion(0, "Northern Lights Viewing Tour", "06/22/2026", 2);
            Excursion midnight_sun_vikings = new Excursion(0, "Viking Museum & Ship Experience", "06/25/2026", 2);
            Excursion midnight_sun_fjord = new Excursion(0, "Fjord Kayaking Adventure", "06/29/2026", 2);
            Excursion midnight_sun_reindeer = new Excursion(0, "Reindeer Sleigh Ride", "07/01/2026", 2);
            Excursion midnight_sun_ice = new Excursion(0, "Ice Hotel Overnight Experience", "07/02/2026", 2);

            //Italy vacation
            Vacation il_bel_paese = new Vacation(0, "La Dolce Vita Getaway", "Palazzo di Firenze", "04/12/2027", "04/26/2027");

            //Italy excursions
            Excursion il_bel_paese_gondola = new Excursion(0, "Gondola Ride Through Venice", "04/13/2026", 3);
            Excursion il_bel_paese_food = new Excursion(0, "Venetian Food Tour", "04/14/2026", 3);
            Excursion il_bel_paese_colosseum = new Excursion(0, "Colosseum Guided Tour", "04/17/2026", 3);
            Excursion il_bel_paese_cooking = new Excursion(0, "Pasta & Gelato Cooking Class", "04/19/2026", 3);
            Excursion il_bel_paese_wine = new Excursion(0, "Italian Vineyard Experience", "04/23/2026", 3);

            /**Thailand vacation
            Vacation land_of_smiles = new Vacation(0,"Tropical Temples Adventure", "The Emerald Tiger Resort","11/20/2026", "12/11/2026");

            //Thailand excursions
            Excursion land_of_smiles_lanterns = new Excursion(0, "Lantern Festival Night Cruise", "11/24/2026", 4);
            Excursion land_of_smiles_market = new Excursion(0, "Floating Market Boat Tour", "11/29/2026", 4);
            Excursion land_of_smiles_elephant = new Excursion(0, "Elephant Sanctuary Visit", "12/01/2026", 4);
            Excursion land_of_smiles_temples = new Excursion(0, "The Grand Palace & Wat Phra Kaew Experience", "12/03/2026", 4);
            Excursion land_of_smiles_food = new Excursion(0, "A Chef's Tour of Thailand", "12/07/2026", 4);

            //Argentina vacations
            Vacation silver_country = new Vacation(0, "Patagonia & Tando Escape","Silver Condor Lodge", "12/28/2026","01/11/2027");

            //Argentina excursions
            Excursion silver_country_dance = new Excursion(0, "New Years Tango Dance Dinner Show", "12/31/2026", 5);
            Excursion silver_country_glacier = new Excursion(0, "Patagonia Glacier Hike", "01/03/2027", 5);
            Excursion silver_country_horseback = new Excursion(0, "Horseback on the Pampas", "01/06/2027", 5);
            Excursion silver_country_falls = new Excursion(0, "Iguazu Falls Boat Adventure", "01/08/2027", 5);
            Excursion silver_country_penguins = new Excursion(0, "Penguin Watching in Patagonia", "01/10/2027", 5);
            **/

            //Scotland vacation and excursions insert
            repository.insert(highlands);
            repository.insert(highlands_loch_ness);
            repository.insert(highlands_ghost_tour);
            repository.insert(highlands_harry_potter);
            repository.insert(highlands_castle_tour);
            repository.insert(highlands_coo_farm);

            //Norway vacation and excursions insert
            repository.insert(midnight_sun);
            repository.insert(midnight_sun_northern_lights);
            repository.insert(midnight_sun_vikings);
            repository.insert(midnight_sun_fjord);
            repository.insert(midnight_sun_reindeer);
            repository.insert(midnight_sun_ice);

            //Italy vacation and excursions insert
            repository.insert(il_bel_paese);
            repository.insert(il_bel_paese_gondola);
            repository.insert(il_bel_paese_food);
            repository.insert(il_bel_paese_colosseum);
            repository.insert(il_bel_paese_cooking);
            repository.insert(il_bel_paese_wine);

            /**
            //Thailand vacation and excursions insert
            repository.insert(land_of_smiles);
            repository.insert(land_of_smiles_lanterns);
            repository.insert(land_of_smiles_market);
            repository.insert(land_of_smiles_elephant);
            repository.insert(land_of_smiles_temples);
            repository.insert(land_of_smiles_food);

            //Argentina vacation and excursions insert
            repository.insert(silver_country);
            repository.insert(silver_country_dance);
            repository.insert(silver_country_glacier);
            repository.insert(silver_country_horseback);
            repository.insert(silver_country_falls);
            repository.insert(silver_country_penguins);
            **/

            //force refresh vacation list to show the sample data
            vacationAdapter.setVacation(repository.getAllVacations());

            //Inform user sample data has been added.
            Toast.makeText(this, "Sample data added!", Toast.LENGTH_SHORT).show();

            return true;
        }

        //generate report button
        if (id == R.id.generate_report) {
            Intent intent = new Intent(VacationList.this, ReportGenerate.class);
            startActivity(intent);

            return true;
        }

        //for back button
        if (id == android.R.id.home) {
            this.finish();
            //Intent intent=new Intent(VacationList.this, VacationDetails.class);
            //startActivity(intent);
            return true;
        }

        return true;
    }
}
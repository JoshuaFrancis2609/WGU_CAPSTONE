package com.example.d308vacationplanner.database;

import android.app.Application;

import com.example.d308vacationplanner.dao.ExcursionDAO;
import com.example.d308vacationplanner.dao.VacationDAO;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static ExcursionDAO mExcursionDAO;
    private static VacationDAO mVacationDAO;

    private List<Excursion> mAllExcursions;
    private List<Vacation> mAllVacations;

    private static final int NUMBER_OF_THREADS = 4;
            static final ExecutorService databaseWriteExecutor =
                    Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public Repository(Application application) {
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mVacationDAO = db.vacationDAO();
        mExcursionDAO = db.excursionDAO();
    }

    // VACATIONS
    //Get all vacations
    public List<Vacation> getAllVacations() {
        databaseWriteExecutor.execute(() -> {
            mAllVacations = mVacationDAO.getAllVacations();
        });

        //Sets the time for the database to complete the operation
        try{
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllVacations;
    }

    //Insert Vacations
    public void insert(Vacation vacation) {
        databaseWriteExecutor.execute(() -> {
            mVacationDAO.insert(vacation);
        });

        //Sets time for database to complete the operation
        try{
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Update Vacations
    public void update(Vacation vacation) {
        databaseWriteExecutor.execute(() -> {
            mVacationDAO.update(vacation);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Delete Vacations
    public void delete(Vacation vacation) {
        databaseWriteExecutor.execute(() -> {
            mVacationDAO.delete(vacation);
        });

        //Sets the time for database to complete the operation
        try{
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //EXCURSIONS
    //Get all excursions
    public List<Excursion> getAllExcursions() {
        databaseWriteExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });

        //Set time for the database to complete the operation
        try{
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mAllExcursions;
    }

    //Get associated excursions
    public List<Excursion> getAssociatedExcursions(int vacationId) {
        databaseWriteExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAssociatedExcursions(vacationId);
        });

        //Set time for the database to complete the operation
        try{
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mAllExcursions;
    }

    //Insert Excursions
    public void insert(Excursion excursion) {
        databaseWriteExecutor.execute(() -> {
            mExcursionDAO.insert(excursion);
        });

        //Set time for the database to complete the operation
        try{
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Update Excursions
    public void update(Excursion excursion) {
        databaseWriteExecutor.execute(() -> {
            mExcursionDAO.update(excursion);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Delete Excursions
    public void delete(Excursion excursion) {
        databaseWriteExecutor.execute(() -> {
            mExcursionDAO.delete(excursion);
        });

        //Set the time for the database to complete the operation
        try{
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Get excursion by id
    public Excursion getExcursionById(int excursionId) {
        final Excursion[] excursion = new Excursion[1];
        databaseWriteExecutor.execute(() -> {
            excursion[0] = mExcursionDAO.getExcursionById(excursionId);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return excursion[0];
    }
}

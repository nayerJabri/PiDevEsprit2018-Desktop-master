/**
 * Copyright (C) 2015, 2016 Dirk Lemmermann Software & Consulting (dlsc.com) 
 * 
 * This file is part of CalendarFX.
 */

package Controller;

import Core.Controller;
import IService.IEvenementService;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import java.sql.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import javafx.event.Event;
import javafx.event.EventHandler;

public class CalendarApp extends Controller {

    private final IEvenementService es = this.getService().getEvenementService();
    public  StackPane startCalendar() throws Exception {
        CalendarView calendarView = new CalendarView();
        Calendar accepted = new Calendar("Accepted");
        accepted.setShortName("Accepted");
        loadCalander(accepted);
        accepted.setStyle(Style.STYLE1);
        CalendarSource familyCalendarSource = new CalendarSource("Family");
        familyCalendarSource.getCalendars().addAll( accepted);
        calendarView.getCalendarSources().setAll(familyCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(calendarView);                
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });
                    try {                       
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();     
        return stackPane;
    }
    private EventHandler<CalendarEvent> foo(CalendarEvent evt) {
        return (event) -> {           
        };
    }
    public LocalDateTime toLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toLocalDate().atTime(0, 0)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    private  void loadCalander(Calendar accepted) {
                es.getEv()
                .forEach(e -> {
                    String titre = e.getTitre();
                    int id = e.getId();
                    Date date = e.getDateEvenement();                    
                    Interval i = new Interval(toLocalDateTimeViaInstant(date), toLocalDateTimeViaInstant(date));
                    Entry entry = new Entry(titre, i);
                    entry.setId(Integer.toString(id));                   
                    accepted.addEntry(entry);
                    entry.setCalendar(accepted);  
});
}
}

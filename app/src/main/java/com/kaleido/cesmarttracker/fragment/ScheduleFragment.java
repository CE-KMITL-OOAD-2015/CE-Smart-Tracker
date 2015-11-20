package com.kaleido.cesmarttracker.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kaleido.cesmarttracker.CompactCalendarView;
import com.kaleido.cesmarttracker.CalendarDayEvent;
import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;
import com.kaleido.cesmarttracker.data.Event;
import com.kaleido.cesmarttracker.data.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by monkiyes on 10/25/2015 AD.
 */
public class ScheduleFragment extends Fragment {

    private ListView notify;
    private ArrayAdapter<String> adapter2;
    //    CalendarView calendar;
    Test test = new Test();
    Student s1= test.getStudents().get(0);

    ArrayList<Event> events= s1.getEvents();
    ArrayList<String> event = new ArrayList<>();
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.main_fragment,container,false);
//        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, event);
//        notify = (ListView) view.findViewById(R.id.list);
//        calendar = (CalendarView) view.findViewById(R.id.calendarView);
//        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                //Toast.makeText(getActivity().getApplicationContext(), dayOfMonth + "/" + ++month + "/" + year, Toast.LENGTH_SHORT).show();
//                String date = dayOfMonth + "/" + (month+1) + "/" + year;
//                event.clear();
//                for (int i = 0; i < test.getStudents().get(0).getEvents().size(); i++) {
//                    if (date.contentEquals(test.getStudents().get(0).getEvents().get(i).getDueDate()))
//                        event.add(test.getStudents().get(0).getEvents().get(i).getTitle());
//                }
//                notify.setAdapter(adapter);
//            }
//        });
//        return  view;
//    }

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
    private Map<Date, List<Booking>> bookings = new HashMap<>();
    TextView text;


    public class Booking {
        private String title;
        private Date date;

        public Booking(String title, Date date) {
            this.title = title;
            this.date = date;
        }


        @Override
        public String toString() {
            return "Booking{" +
                    "title='" + title + '\'' +
                    ", date=" + date +
                    '}';
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=inflater.inflate(R.layout.main_fragment,container,false);
        // final ActionBar actionBar =getSupportActionBar();
        final List<String> mutableBookings = new ArrayList<>();
        notify = (ListView) view.findViewById(R.id.list);
        notify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO when click showDialog
                //TODO sent events.get(position)


            }
        });
        TextView button=(TextView) view.findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO start study plan

            }
        });
        adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, event);

        //   final ListView bookingsListView = (ListView) view.findViewById(R.id.list);
        final Button showPreviousMonthBut = (Button) view.findViewById(R.id.prev_button);
        final Button showNextMonthBut = (Button) view.findViewById(R.id.next_button);

        final ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mutableBookings);
        //      bookingsListView.setAdapter(adapter);
        final CompactCalendarView compactCalendarView = (CompactCalendarView) view.findViewById(R.id.calendarView);
        compactCalendarView.drawSmallIndicatorForEvents(true);

        // below allows you to configure color for the current day in the month
        compactCalendarView.setCurrentDayBackgroundColor(getResources().getColor(R.color.calendar_today));
        // below allows you to configure colors for the current day the user has selected
        compactCalendarView.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.calendar_click));

        for(Event e:events)
        {
            String[] s= e.getDueDate().split("/");
            int date=Integer.parseInt(s[0]);
            int month=Integer.parseInt(s[1]);
            int year=Integer.parseInt(s[2]);
            addEvent(compactCalendarView, date,month,year);
        }

//        addEvents(compactCalendarView, -1);
//        addEvents(compactCalendarView, Calendar.DECEMBER);
//        addEvents(compactCalendarView, Calendar.AUGUST);
//        addEvent(compactCalendarView,10,Calendar.DECEMBER);
        compactCalendarView.invalidate();

        // below line will display Sunday as the first day of the week
        // compactCalendarView.setShouldShowMondayAsFirstDay(false);
        text = (TextView)view.findViewById(R.id.textView35);
        //set initial title
        text.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        //set title on calendar scroll
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String date=df.format(dateClicked);
                String[] s= date.split("/");
                //Toast.makeText(getActivity().getApplicationContext(), s[0]+s[1], Toast.LENGTH_SHORT).show();
                event.clear();
                for (int i = 0; i < events.size(); i++) {
                    if (date.contentEquals(events.get(i).getDueDate()))
                        event.add(events.get(i).getTitle());
                }
                notify.setAdapter(adapter2);
//                List<Booking> bookingsFromMap = bookings.get(dateClicked);
//                    Log.d("MainActivity", "inside onclick " + dateClicked);
//                    if(bookingsFromMap != null){
//                        Log.d("MainActivity", bookingsFromMap.toString());
//                        mutableBookings.clear();
//                        for(Booking booking : bookingsFromMap){
//                            mutableBookings.add(booking.title);
//                        }
//                        // below will remove events
//                        // compactCalendarView.removeEvent(new CalendarDayEvent(dateClicked.getTime(), Color.argb(255, 169, 68, 65)), true);
//                        adapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                text.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        showPreviousMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showPreviousMonth();
            }
        });

        showNextMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showNextMonth();
            }
        });

        return view;
    }
    private void addEvent(CompactCalendarView compactCalendarView,int day, int month,int year) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, day);
        Date firstDayOfMonth = currentCalender.getTime();
        currentCalender.setTime(firstDayOfMonth);
        currentCalender.set(Calendar.MONTH, (month - 1));
        currentCalender.set(Calendar.YEAR,year);
        currentCalender.add(Calendar.DATE, 0);
        setToMidnight(currentCalender);
//        bookings.put(currentCalender.getTime(), createBookings());
        compactCalendarView.addEvent(new CalendarDayEvent(currentCalender.getTimeInMillis(), Color.argb(255, 169, 68, 65)), false);
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}

//package com.kaleido.cesmarttracker.fragment;
//
//import android.content.DialogInterface;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AlertDialog;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.CalendarView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.facebook.share.ShareApi;
//import com.facebook.share.model.SharePhoto;
//import com.facebook.share.model.SharePhotoContent;
//import com.facebook.share.widget.ShareButton;
//import com.kaleido.cesmarttracker.R;
//import com.kaleido.cesmarttracker.Test;
//
//import java.util.ArrayList;
//
///**
// * Created by monkiyes on 10/25/2015 AD.
// */
//public class ScheduleFragment extends Fragment {
//
//    private ListView notify;
//    private ArrayAdapter<String> adapter;
//    CalendarView calendar;
//    Test test = new Test();
//    View view;
//    ArrayList<String> event = new ArrayList<>();
//    ShareButton shareButton;
//    private int counter = 0;
//    private Bitmap image;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view=inflater.inflate(R.layout.main_fragment,container,false);
//        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, event);
//        notify = (ListView) view.findViewById(R.id.list);
//        calendar = (CalendarView) view.findViewById(R.id.calendarView);
//        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                //Toast.makeText(getActivity().getApplicationContext(), dayOfMonth + "/" + ++month + "/" + year, Toast.LENGTH_SHORT).show();
//                String date = dayOfMonth + "/" + month + "/" + year;
//                event.clear();
//                for (int i = 0; i < test.getStudents().get(0).getEvents().size(); i++) {
//                    if (date.contentEquals(test.getStudents().get(0).getEvents().get(i).getDueDate()))
//                        event.add(test.getStudents().get(0).getEvents().get(i).getTitle());
//                }
//                notify.setAdapter(adapter);
//            }
//        });
//
//        shareButton = (ShareButton) view.findViewById(R.id.share_btn);
//        shareButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                postPicture();
//            }
//        });
//
//        return  view;
//    }
//
//    private void postPicture() {
//        //check counter
//        if(counter == 0) {
//            //save the screenshot
//            View rootView = view;//findViewById(android.R.id.content).getRootView();
//            rootView.setDrawingCacheEnabled(true);
//            // creates immutable clone of image
//            image = Bitmap.createBitmap(rootView.getDrawingCache());
//            // destroy
//            rootView.destroyDrawingCache();
//
//            //share dialog
//            final AlertDialog.Builder shareDialog = new AlertDialog.Builder(getContext());
//            shareDialog.setTitle("Share Screen Shot");
//            shareDialog.setMessage("Share image to Facebook?");
//            shareDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    //share the image to Facebook
//                    SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
//                    SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
//
//                    shareButton.setShareContent(content);
//                    counter = 1;
//                    shareButton.performClick();
//                    Toast.makeText(getContext(), "Share!"
//                            , Toast.LENGTH_SHORT).show();
//                    ShareApi.share(content, null);
//                }
//            });
//            shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            shareDialog.show();
//        }
//        else {
//            counter = 0;
//            shareButton.setShareContent(null);
//        }
//    }
//}

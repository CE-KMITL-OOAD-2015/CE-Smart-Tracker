package com.kaleido.cesmarttracker.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.WelcomeButtonAction;
import com.dexafree.materialList.listeners.OnDismissCallback;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;
import com.kaleido.cesmarttracker.OnCourseSelected;
import com.kaleido.cesmarttracker.R;
import com.kaleido.cesmarttracker.Test;
import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkiyes on 10/29/2015 AD.
 */
public class ProgressTeacherFragment extends Fragment {
    MaterialListView mListView;
    Test test = new Test();
    Teacher a = test.getTeachers().get(0);
    ArrayList<Course> courses = a.getCourses();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=inflater.inflate(R.layout.activity_teacher_progress,container,false);
        mListView = (MaterialListView) view.findViewById(R.id.material_listview);
        //mListView.setItemAnimator(new SlideInLeftAnimator());
        mListView.getItemAnimator().setAddDuration(300);
        mListView.getItemAnimator().setRemoveDuration(300);

        /*
        final ImageView emptyView = (ImageView) view.findViewById(R.id.imageView);
        emptyView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mListView.setEmptyView(emptyView);
        Picasso.with(getActivity())
                .load("https://www.skyverge.com/wp-content/uploads/2012/05/github-logo.png")
                .resize(100, 100)
                .centerInside()
                .into(emptyView);
        */

        // Fill the array withProvider mock content
        fillArray();

        // Set the dismiss listener
        mListView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(@NonNull Card card, int position) {
                // Show a toast
                Toast.makeText(getActivity(), "You have dismissed a " + card.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the ItemTouchListener
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Card card, int position) {
                Log.d("CARD_TYPE", "" + card.getTag());
            }

            @Override
            public void onItemLongClick(@NonNull Card card, int position) {
                Log.d("LONG_CLICK", "" + card.getTag());
            }
        });

        return  view;
    }

    // i is Subject's amount
    private void fillArray() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            cards.add(getCard(i));
        }
        mListView.getAdapter().addAll(cards);
    }

    private Card getCard(final int position) {
        final String title = courses.get(position).getName();
        String description = "Lorem ipsum dolor sit amet";
        //ArrayList<Student> students = courses.get(position).getAllStudent();

        final CardProvider provider = new Card.Builder(getActivity())
                .setTag("WELCOME_CARD")
                .setDismissible()
                .withProvider(new CardProvider())
                .setLayout(R.layout.course_card_layout)
                .setTitle(title)
                .setTitleColor(Color.DKGRAY)
                .setDescription("I am the description")
                .setDescriptionColor(Color.DKGRAY)
                .setSubtitle("My subtitle!")
                .setSubtitleColor(Color.DKGRAY)
                .setBackgroundColor(Color.WHITE)
                .addAction(R.id.student_button, new WelcomeButtonAction(getActivity())
                        //.setText(students.size()+" Students!")
                        .setTextColor(Color.WHITE)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                //TODO how to pass object by intent ?
                                Course c = null;
                                for(int i=0;i<courses.size();i++){
                                    if(courses.get(i).getName().equals(title)){
                                        c = courses.get(i);
                                    }
                                }
                                Intent intent = new Intent(getActivity(), OnCourseSelected.class);
                                intent.putExtra("coursee",c);
                                startActivity(intent);
                                //Toast.makeText(getActivity(), "Welcome!", Toast.LENGTH_SHORT).show();
                            }
                        }));
        /*
        if (position % 2 == 0) {
            provider.setBackgroundResourceColor(android.R.color.holo_orange_dark);
        }
        */
        return provider.endConfig().build();
    }
}

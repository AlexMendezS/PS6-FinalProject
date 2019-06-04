package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ps6.R;
import com.example.ps6.WaitingListActivity;

import java.util.ArrayList;
import java.util.List;

import model.user;


public class StudentAdapter extends ArrayAdapter<user> {

    private Context context;
    private int resource;
    private ArrayList<user> students;
    private static String TAG = "StudentAdapter";

    public StudentAdapter(Context context, int resource, ArrayList<user> students) {
        super(context, resource, students);
        this.context = context;
        this.resource = resource;
        this.students = students;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(context).inflate(resource, parent, false);

        user newstudent = students.get(position);

        //Set the student informations
        int queueNumber = newstudent.getQueueNumber();
        String strStudentQueueNbr = Integer.toString(queueNumber);
        String firstName = newstudent.getFirstName();
        String name = newstudent.getName();

        //Create the student object with the information
//        user student = new user(queueNumber, firstName, name);


        TextView tvName = view.findViewById(R.id.nameView);
        TextView tvNumber = view.findViewById(R.id.numberView);
        Button tvButton = view.findViewById(R.id.deleteStudent);

//        tvButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        tvName.append(firstName + " " + name);
        tvNumber.setText(strStudentQueueNbr);

        return view;
    }
}

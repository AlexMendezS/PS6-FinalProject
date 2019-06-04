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

import java.util.List;

import model.user;


public class StudentAdapter extends ArrayAdapter<user> {

    private Context context;
    private int resource;
    private List<user> objects;
    private static String TAG = "StudentAdapter";

    public StudentAdapter(Context context, int resource, List<user> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Set the student informations
        int queueNumber = getItem(position).getQueueNumber();
        String strStudentQueueNbr = Integer.toString(queueNumber);
        String firstName = getItem(position).getFirstName();
        String name = getItem(position).getName();

        //Create the student object with the information
        user student = new user(queueNumber, firstName, name);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView tvName = convertView.findViewById(R.id.nameView);
        TextView tvNumber = convertView.findViewById(R.id.numberView);
        Button tvButton = convertView.findViewById(R.id.deleteStudent);

//        tvButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        tvName.append(firstName + " " + name);
        tvNumber.setText(strStudentQueueNbr);

        return convertView;
    }
}

package com.alger1.pfev2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alger1.pfev2.R;
import com.alger1.pfev2.model.Employe;

import java.util.List;

/**
 * Created by ISLEM-PC on 5/2/2018.
 */

public class EmployeAdapter extends ArrayAdapter {

    List list_Emp;

    public EmployeAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.list_Emp = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.employe_row, parent, false);

        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            viewHolder.id = convertView.findViewById(R.id.id_emp);
            viewHolder.username = convertView.findViewById(R.id.username_emp);
            viewHolder.firtname = convertView.findViewById(R.id.firstname_emp);
            viewHolder.lastname = convertView.findViewById(R.id.lastname_emp);
            convertView.setTag(viewHolder);
        }

        Employe employe = (Employe) list_Emp.get(position);
        viewHolder.id.setText(""+employe.getId_employe());
        viewHolder.username.setText(employe.getUsername());
        viewHolder.firtname.setText(employe.getFirstname());
        viewHolder.lastname.setText(employe.getLastname());

        return convertView;
    }

    public class ViewHolder {
        TextView id;
        TextView username;
        TextView firtname;
        TextView lastname;
    }

}

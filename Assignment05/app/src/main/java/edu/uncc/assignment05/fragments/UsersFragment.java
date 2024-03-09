package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentUsersBinding;
import edu.uncc.assignment05.models.User;


public class UsersFragment extends Fragment {


    ArrayList<User> users = new ArrayList<>();




    public UsersFragment() {
        // Required empty public constructor
    }

    public void setUsers(ArrayList<User> users)
    {
        this.users = users;
    }
    String type="";
    public void setType(String type)
    {
        this.type = type;
    }







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentUsersBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUsersBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


//    ArrayAdapter<User> adapter;
    UsersAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapter = new UsersAdapter(getActivity(),users);
        binding.listView.setAdapter(adapter);


        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddUserFragment();
            }
        });

        binding.buttonSort.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectSortFragment();
            }
        }));


        if(!type.equals("")) {
            sortList(type);
            adapter.notifyDataSetChanged();
        }

    }


    class UsersAdapter extends ArrayAdapter<User>{

        public UsersAdapter(@NonNull Context context, @NonNull List<User> objects) {
            super(context, R.layout.user_list_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.user_list_item,parent,false);
            }


            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewEmail = convertView.findViewById(R.id.textViewEmail);
            TextView textViewAge = convertView.findViewById(R.id.textViewAge);
            TextView textViewGender = convertView.findViewById(R.id.textViewGender);
            TextView textViewState = convertView.findViewById(R.id.textViewState);
            TextView textViewGroup = convertView.findViewById(R.id.textViewGroup);

            User user = getItem(position);

            textViewName.setText(user.getName());
            textViewEmail.setText(user.getEmail());
            textViewAge.setText(Integer.toString(user.getAge()));
            textViewGender.setText(user.getGender());
            textViewState.setText(user.getState());
            textViewGroup.setText(user.getGroup());



            return convertView;


        }
    }



    private void sortList(String sortType){


        if(sortType.equals("NameAsc"))
        {
            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {

                    return (o1.getName().compareTo(o2.getName()));
                }
            });

        }
        else if(sortType.equals("NameDsc")) {

            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {

                    return -1 * (o1.getName().compareTo(o2.getName()));
                }
            });

        }
    }






    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentUserListener) context;
    }

    FragmentUserListener mListener;

    public interface FragmentUserListener {
       void gotoAddUserFragment();
       void gotoSelectSortFragment();
    }

}
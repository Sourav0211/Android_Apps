package edu.uncc.giftlistapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.giftlistapp.databinding.FragmentGiftListsBinding;
import edu.uncc.giftlistapp.databinding.ListItemGiftlistBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GiftListsFragment extends Fragment {
    public GiftListsFragment() {
        // Required empty public constructor
    }

    FragmentGiftListsBinding binding;
    ArrayList<GiftList> giftLists;

    giftListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGiftListsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    String mToken;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Gift Lists");
        mToken = mListener.getAuthToken();
        Log.d("token", "onViewCreated: User Token " + mToken);
        giftLists = new ArrayList<>();
        fetchGiftList(mToken);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new giftListAdapter();
        binding.recyclerView.setAdapter(adapter);

        //token authorization
    }


    public void fetchGiftList(String mToken)
    {

        OkHttpClient client = new OkHttpClient();
        String postsUrl = "https://www.theappsdr.com/api/giftlists/lists"; // Requesting specific page

//        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        Request request = new Request.Builder()
                .url(postsUrl)
                .header("Authorization", "BEARER " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTA3OTEzODYsImV4cCI6MTc0MjMyNzM4NiwianRpIjoiNm9GMVlVMUJ1YTZtQjR4UXh3VTJJZyIsInVzZXIiOjJ9.2yK_L7Ua4MUNK5GH-i5I2zZ9HXHeYFEODY8fcm5smlY")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String body = response.body().string();
                    try {
                        JSONObject root = new JSONObject(body);
                        JSONArray giftListJson = root.getJSONArray("lists");
                        Log.d("giftListJson", "onResponse: "+giftListJson);
                        for (int i = 0; i < giftListJson.length(); i++) {

                            JSONObject list = giftListJson.getJSONObject(i);
                            String name = list.getString("name");
                            String gid = list.getString("gid");
                            JSONArray itemsJson = list.getJSONArray("items");
                            ArrayList<Product> products = new ArrayList<>();

                            for (int j = 0; j < itemsJson.length(); j++) {
                                JSONObject item = itemsJson.getJSONObject(j);
                                String pid = item.getString("pid");
                                int count = item.getInt("count");
                                String ItemName = item.getString("name");
                                String price_per_item = item.getString("price_per_item");
                                String img_url = item.getString("img_url");
                                Product product = new Product(pid ,count, ItemName, price_per_item,img_url);
                                products.add(product);
                            }

                            giftLists.add(new GiftList(name,gid,products));



                        }



                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                }else{

                }
            }
        });

    }



    class giftListAdapter extends RecyclerView.Adapter<giftListAdapter.giftListViewHolder> {
        @NonNull
        @Override
        public giftListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemGiftlistBinding binding = ListItemGiftlistBinding.inflate(getLayoutInflater(), parent, false);
            return new giftListViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull giftListViewHolder holder, int position) {
            GiftList mGiftList = giftLists.get(position);
            holder.setupUI(mGiftList);
        }

        @Override
        public int getItemCount() {
            return giftLists.size();
        }

        class giftListViewHolder extends RecyclerView.ViewHolder {

            ListItemGiftlistBinding mBinding;
            GiftList mList;

            public giftListViewHolder(ListItemGiftlistBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(GiftList giftList) {
                mList = giftList;
                mBinding.textViewName.setText(mList.getName());
                mBinding.textViewTotalItems.setText(String.valueOf(mList.getTotalCount()));
                mBinding.textViewTotalCost.setText(String.valueOf(mList.getTotalCost()));

                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItem(mList.getGid());
                    }
                });

                mBinding.IndList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoGiftListFragment(mList);
                    }
                });
            }

            private void deleteItem(String gid) {
                OkHttpClient client = new OkHttpClient();

                FormBody formBody = new FormBody.Builder()
                        .add("gid", gid)
                        .build();
                Log.d("TAG", "deleteItem: "+gid);

                Request request = new Request.Builder()
                        .url("https://www.theappsdr.com/api/giftlists/delete")
                        .header("Authorization", "BEARER " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTA3OTEzODYsImV4cCI6MTc0MjMyNzM4NiwianRpIjoiNm9GMVlVMUJ1YTZtQjR4UXh3VTJJZyIsInVzZXIiOjJ9.2yK_L7Ua4MUNK5GH-i5I2zZ9HXHeYFEODY8fcm5smlY")
                        .post(formBody)
                        .build();
//

                Log.d("TAG", "deleteItem: delete is being called");
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        // Handle failure
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Log.d("TAG", "onResponse: "+response);
                            int position = giftLists.indexOf(mList);
                            giftLists.remove(position);


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    adapter.notifyDataSetChanged();
                                    fetchGiftList("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTA3OTEzODYsImV4cCI6MTc0MjMyNzM4NiwianRpIjoiNm9GMVlVMUJ1YTZtQjR4UXh3VTJJZyIsInVzZXIiOjJ9.2yK_L7Ua4MUNK5GH-i5I2zZ9HXHeYFEODY8fcm5smlY");

                                }
                            });
                        } else {
                            // Handle unsuccessful response
                        }
                    }
                });
            }
        }
    }












    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout){
            mListener.performLogout();
            return true;
        } else if (item.getItemId() == R.id.action_add){
            mListener.gotoAddNewGiftList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    GiftListsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GiftListsListener){
            mListener = (GiftListsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GiftListsListener");
        }
    }






    interface GiftListsListener{
        String getAuthToken();
        void gotoAddNewGiftList();
        void performLogout();

        void gotoGiftListFragment(GiftList list);
    }
}
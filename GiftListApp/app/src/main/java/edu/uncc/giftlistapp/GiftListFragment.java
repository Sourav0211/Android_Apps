package edu.uncc.giftlistapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.giftlistapp.databinding.FragmentGiftListBinding;
import edu.uncc.giftlistapp.databinding.ListItemProductBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GiftListFragment extends Fragment {
    public GiftListFragment() {
        // Required empty public constructor
    }

    FragmentGiftListBinding binding;
    GiftList giftList;
    String gid;

    ArrayList<Product> mProducts;
    ProductEditAdapter adapter;

    String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGiftListBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        if(bundle!= null)
        {
            GiftList giftList = (GiftList) bundle.getSerializable("giftList");
            gid = giftList.getGid();

        }

        return binding.getRoot();
    }


    String mToken;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Gift List");
        mToken = mListener.getAuthToken();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductEditAdapter();
        binding.recyclerView.setAdapter(adapter);


        fetchList(gid,mToken);

        //token authorization
    }


    public void fetchList(String gid, String mToken)
    {
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/giftlists/lists")
                .header("Authorization" , "BEARER " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTA3OTEzODYsImV4cCI6MTc0MjMyNzM4NiwianRpIjoiNm9GMVlVMUJ1YTZtQjR4UXh3VTJJZyIsInVzZXIiOjJ9.2yK_L7Ua4MUNK5GH-i5I2zZ9HXHeYFEODY8fcm5smlY")
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {   String body = response.body().string();
                    try {
                        JSONObject root = new JSONObject(body);
                        JSONArray giftListJson = root.getJSONArray("lists");

                        for (int i = 0; i < giftListJson.length(); i++) {

                            JSONObject list = giftListJson.getJSONObject(i);
                            String name = list.getString("name");
                            String temp_gid = list.getString("gid");

                            if(gid.equals(temp_gid))
                            {

                                JSONArray itemsJson = list.getJSONArray("items");
                                ArrayList<Product> products = new ArrayList<>();

                                for (int j = 0; j < itemsJson.length(); j++) {
                                    JSONObject item = itemsJson.getJSONObject(j);
                                    String pid = item.getString("pid");
                                    int count = item.getInt("count");
                                    String ItemName = item.getString("name");
                                    String price_per_item = item.getString("price_per_item");
                                    String img_url = item.getString("img_url");
                                    Product product = new Product(pid, count, ItemName, price_per_item, img_url);
                                    products.add(product);
                                }
                                giftList = new GiftList(name,temp_gid, products);
                                mProducts = giftList.getProducts();

                            }
                            else{
                                continue;
                            }



                        }


                        } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.textViewGiftListName.setText(giftList.getName());
                            binding.textViewOverallCost.setText(Double.toString(giftList.getTotalCost()));
                            adapter.notifyDataSetChanged();

                        }
                    });

                }
            }
        });
    }















    GiftListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GiftListListener){
            mListener = (GiftListListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GiftListListener");
        }
    }


    class ProductEditAdapter extends RecyclerView.Adapter<ProductEditAdapter.ProductEditViewHolder> {
        @NonNull
        @Override
        public ProductEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemProductBinding binding = ListItemProductBinding.inflate(getLayoutInflater(), parent, false);
            return new ProductEditViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductEditViewHolder holder, int position) {

            Product product = mProducts.get(position);
            holder.setupUI(product);
        }

        @Override
        public int getItemCount() {
            if(mProducts!=null)
            return mProducts.size();
            return 0;
        }

        class ProductEditViewHolder extends RecyclerView.ViewHolder{
            ListItemProductBinding mBinding;
            public ProductEditViewHolder(ListItemProductBinding binding) {
                super(binding.getRoot());
                mBinding = binding;


            }

            void setupUI(Product product)
            {
                mBinding.textViewName.setText(product.getName());
                mBinding.textViewItemCount.setText(Integer.toString(product.getCount()));
                mBinding.textViewCostPerItem.setText(Double.toString(product.getTotalPrice()));
                Picasso.get().load(product.getImg_url()).into(mBinding.imageViewIcon);


                mBinding.imageViewPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FormBody formBody = new FormBody.Builder()
                                .add("gid",giftList.getGid())
                                .add("pid",product.getPid())
                                .build();

                        Request request = new Request.Builder()
                                .url("https://www.theappsdr.com/api/giftlists/add-item")
                                .header("Authorization" , "BEARER " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTA3OTEzODYsImV4cCI6MTc0MjMyNzM4NiwianRpIjoiNm9GMVlVMUJ1YTZtQjR4UXh3VTJJZyIsInVzZXIiOjJ9.2yK_L7Ua4MUNK5GH-i5I2zZ9HXHeYFEODY8fcm5smlY")
                                .post(formBody)
                                .build();

                        OkHttpClient client = new OkHttpClient();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                if(response.isSuccessful()){

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            fetchList(gid,mToken);

                                        }
                                    });


                                }
                            }
                        });
                    }
                });

                mBinding.imageViewMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FormBody formBody = new FormBody.Builder()
                                .add("gid",giftList.getGid())
                                .add("pid",product.getPid())
                                .build();

                        Request request = new Request.Builder()
                                .url("https://www.theappsdr.com/api/giftlists/remove-item")
                                .header("Authorization" , "BEARER " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTA3OTEzODYsImV4cCI6MTc0MjMyNzM4NiwianRpIjoiNm9GMVlVMUJ1YTZtQjR4UXh3VTJJZyIsInVzZXIiOjJ9.2yK_L7Ua4MUNK5GH-i5I2zZ9HXHeYFEODY8fcm5smlY")
                                .post(formBody)
                                .build();

                        OkHttpClient client = new OkHttpClient();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                if(response.isSuccessful()){

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            fetchList(gid,mToken);

                                        }
                                    });


                                }
                            }
                        });
                    }
                });

            }

        }

    }

    interface GiftListListener{
        String getAuthToken();
    }
}
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

import edu.uncc.giftlistapp.databinding.FragmentCreateGiftListBinding;
import edu.uncc.giftlistapp.databinding.FragmentGiftListBinding;
import edu.uncc.giftlistapp.databinding.ListItemGiftlistBinding;
import edu.uncc.giftlistapp.databinding.ListItemProductBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateGiftListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGiftListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    OkHttpClient client;
    ProductAdapter adapter;


    ArrayList<Product> ShowProducts = new ArrayList<>();

    ArrayList<String> productids = new ArrayList<>();
    public CreateGiftListFragment() {
        // Required empty public constructor
    }

    FragmentCreateGiftListBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateGiftListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    String mToken;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Gift List");
        mToken = mListener.getAuthToken(); //token authorization

        // Initialize the ArrayList here
        adapter = new ProductAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        fetchProducts(mToken);


        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextGiftListName.getText().toString();
                FormBody formbody = new FormBody.Builder()
                        .add("name", name)
                        .add("productIds", String.valueOf(productids))
                        .build();

                Request request = new Request.Builder()
                        .url("https://www.theappsdr.com/api/giftlists/new")
                        .header("Authorization" , "BEARER " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTA3OTEzODYsImV4cCI6MTc0MjMyNzM4NiwianRpIjoiNm9GMVlVMUJ1YTZtQjR4UXh3VTJJZyIsInVzZXIiOjJ9.2yK_L7Ua4MUNK5GH-i5I2zZ9HXHeYFEODY8fcm5smlY")
                        .post(formbody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful())
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mListener.gotoGiftListsFragment();
                                }
                            });
                        }
                        else{
                            Log.d("TAG", "onFailure: Add new List failed");
                        }
                    }
                });
            }
        });
    }


    private void fetchProducts(String token) {

        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/giftlists/products")
                .header("Authorization", "BEARER " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTA3OTEzODYsImV4cCI6MTc0MjMyNzM4NiwianRpIjoiNm9GMVlVMUJ1YTZtQjR4UXh3VTJJZyIsInVzZXIiOjJ9.2yK_L7Ua4MUNK5GH-i5I2zZ9HXHeYFEODY8fcm5smlY")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
//

                            JSONArray productsArray = jsonResponse.getJSONArray("products");
                            for (int i = 0; i < productsArray.length(); i++) {
                                JSONObject productObject = productsArray.getJSONObject(i);
                                String pid = productObject.getString("pid");
                                String name = productObject.getString("name");
                                String imgUrl = productObject.getString("img_url");
                                String price = productObject.getString("price");

                                Product product = new Product(pid, 0,name, price, imgUrl);
                                productids.add(pid);
                                Log.d("Product", "onResponse: "+ product);

                                ShowProducts.add(product);

                            }
                            Log.d("Product", "onResponse: "+ ShowProducts);

                            // Update UI on the main thread
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();

                                }
                            });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }



    class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemProductBinding binding =  ListItemProductBinding.inflate(getLayoutInflater(), parent, false);
            return new ProductViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product mProduct = ShowProducts.get(position);
            holder.setupUI(mProduct);

        }

        @Override
        public int getItemCount() {
            return ShowProducts.size();
        }

        class ProductViewHolder extends RecyclerView.ViewHolder{
            ListItemProductBinding mbinding;

            public ProductViewHolder(ListItemProductBinding binding) {
                super(binding.getRoot());
                mbinding = binding;

            }
            public void setupUI(Product mProduct)
            {
                mbinding.textViewName.setText(mProduct.getName());
                mbinding.textViewCostPerItem.setText(mProduct.getPrice_per_item());
                Picasso.get().load(mProduct.getImg_url()).into(mbinding.imageViewIcon);
                mbinding.textViewCostPerItem.setText(Double.toString(mProduct.getTotalPrice()));
               mbinding.textViewItemCount.setText(Integer.toString(mProduct.getCount()));


                mbinding.imageViewPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       int count = mProduct.getCount();
                        count++;
                       mProduct.setCount(count);
                       ShowProducts.set(getAdapterPosition(),mProduct);
                       notifyDataSetChanged();

                    }
                });

                mbinding.imageViewMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(mProduct.getCount() !=0) {
                            int count = mProduct.getCount();
                            count--;
                            mProduct.setCount(count);
                            ShowProducts.set(getAdapterPosition(), mProduct);
                            notifyDataSetChanged();
                        }

                    }
                });
            }
        }
    }




    CreateGiftListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (CreateGiftListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CreateGiftListListener");
        }
    }

    interface CreateGiftListListener{
        String getAuthToken();
        void gotoGiftListsFragment();
    }
}
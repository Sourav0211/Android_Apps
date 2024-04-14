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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.uncc.giftlistapp.databinding.FragmentCreateGiftListBinding;
import edu.uncc.giftlistapp.databinding.FragmentGiftListBinding;
import edu.uncc.giftlistapp.databinding.ListItemProductBinding;
import models.GiftList;
import models.Product;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateGiftListFragment extends Fragment {
    public CreateGiftListFragment() {
        // Required empty public constructor
    }

    FragmentCreateGiftListBinding binding;
    ArrayList<Product> mProducts = new ArrayList<>();
    ProductsAdapter adapter;
    ArrayList<String> selectedTags = new ArrayList<>();



    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ListenerRegistration listenerRegistration;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getProducts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateGiftListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Gift List");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProductsAdapter();
        binding.recyclerView.setAdapter(adapter);





        if(selectedTags.size() == 0){
            binding.textViewSelectedTags.setText("Tags: N/A");
        } else {
            binding.textViewSelectedTags.setText("Tags: " + String.join(", ", selectedTags));
        }

        binding.buttonSelectTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectTags();
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelCreateGiftList();
            }
        });





            binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nameList = binding.editTextGiftListName.getText().toString();

                    if (nameList.equals("")) {
                        Toast.makeText(getActivity(), "Enter Gift List Name", Toast.LENGTH_SHORT).show();
                    }else if (selectedTags.size() == 0) {
                        Toast.makeText(getActivity(), "Enter Tags", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("TAG", "onComplete: submit clikced");

                        HashMap<String, Object> data = new HashMap<>();
                        DocumentReference docRef = db.collection("giftlists").document();
                        data.put("docId", docRef.getId());
                        data.put("userName", mAuth.getCurrentUser().getDisplayName());
                        data.put("giftListName", nameList);
                        data.put("progress", "");
                        data.put("selectedTags",selectedTags);
                        data.put("totalAmount", 0.0);
                        data.put("amountPledged", 0.0);
                        data.put("numberOfItem", 0);

                        docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("TAG", "onComplete: set Data called");
                                    addProducts(docRef.getId());
                                    mListener.doneCreateGiftList();
                                }

                            }
                        });
                    }
                }
            });
        }


    public void addProducts(String docId)
    {

        for(Product product : mProducts)
        {
            if(product.isSelected() == true)
            {

                HashMap<String,Object> data = new HashMap<>();
                DocumentReference docRef = db.collection("giftlists").document(docId).collection("giftlist-poducts").document();
                data.put("docId" , docRef.getId());
                data.put("pid",product.getPid());
                data.put("name",product.getName());
                data.put("img_url",product.getImg_url());
                data.put("price",product.getPrice());
                ArrayList<String> pledgedBy = new ArrayList<>();
                data.put("pledgedBy",pledgedBy);

                docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            HashMap<String , Object> updateData = new HashMap<>();

                            updateData.put("totalAmount", FieldValue.increment(product.getPrice()));
                            updateData.put("numberOfItem",FieldValue.increment(1.0));

                            db.collection("giftlists").document(docId)
                                    .update(updateData);
                        }
                    }
                });
            }
        }



    }


    private void calculateAndDisplayTotal(){

        double total=0.0;
        for(Product product : mProducts)
        {
            if(product.isSelected()) {
                total = total + product.getPrice();
            }
        }
        binding.textViewTotalCost.setText(Double.toString(total));


    }

    public void updateSelectedTags(ArrayList<String> tags){
        selectedTags.clear();
        selectedTags.addAll(tags);
    }

    private final OkHttpClient client = new OkHttpClient();

    private void getProducts(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/giftlists/products")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Failed to get products. " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    try {
                        mProducts.clear();
                        JSONObject json = new JSONObject(body);
                        JSONArray courses = json.getJSONArray("products");
                        for (int i = 0; i < courses.length(); i++) {
                            Product product = new Product(courses.getJSONObject(i));
                            mProducts.add(product);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(adapter != null){
                                    adapter.notifyDataSetChanged();
                                    calculateAndDisplayTotal();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Failed to get products", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }



    class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemProductBinding itemBinding = ListItemProductBinding.inflate(getLayoutInflater(), parent, false);
            return new ProductViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            holder.setupUI(mProducts.get(position));
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }

        class ProductViewHolder extends RecyclerView.ViewHolder {
            ListItemProductBinding itemBinding;
            Product mProduct;
            public ProductViewHolder(ListItemProductBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(Product product){
                mProduct = product;
                itemBinding.textViewName.setText(product.getName());
                itemBinding.textViewCostPerItem.setText(String.format("$%.2f", product.getPrice()));

                if(mProduct.isSelected()){
                    itemBinding.imageViewPlusOrMinus.setImageResource(R.drawable.ic_minus);
                } else {
                    itemBinding.imageViewPlusOrMinus.setImageResource(R.drawable.ic_plus);
                }

                itemBinding.imageViewPlusOrMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mProduct.isSelected()){
                            mProduct.setSelected(false);
                        } else {
                            mProduct.setSelected(true);
                        }
                        notifyDataSetChanged();
                        calculateAndDisplayTotal();
                    }
                });
                Picasso.get().load(product.getImg_url()).into(itemBinding.imageViewIcon);
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
        void cancelCreateGiftList();
        void doneCreateGiftList();
        void gotoSelectTags();
    }
}
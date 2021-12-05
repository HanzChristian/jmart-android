package com.HanzChristianJmartMH;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.HanzChristianJmartMH.model.Product;
import com.HanzChristianJmartMH.model.ProductCategory;
import com.HanzChristianJmartMH.request.RequestFactory;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Product> PList = new ArrayList<>();
    private static final Gson gson = new Gson();
    private ListView listView;
    private Button prevbtn;
    private Button nextbtn;
    private Button gobtn;
    private EditText edtfilterproduct;
    int page = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance() {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        prevbtn = view.findViewById(R.id.prev);
        nextbtn = view.findViewById(R.id.next);
        gobtn = view.findViewById(R.id.go);
        edtfilterproduct = view.findViewById(R.id.editPage);
        listView = view.findViewById(R.id.listViewProduct);
        GetshowProductList(page, 1);

        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = Integer.valueOf(edtfilterproduct.getText().toString());
                page--;
                GetshowProductList(page, 1);
            }
        });

        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page == 0){
                    Toast.makeText(getActivity(), "Error! Already in Page 1!", Toast.LENGTH_SHORT).show();
                }
                else if (page >= 1){
                    page--;
                    GetshowProductList(page, 1);
                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                GetshowProductList(page, 1);
            }
        });
        return view;
    }

    public void GetshowProductList(int page, int pageSize){
        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    PList.clear();
                    JSONArray jsonArray = new JSONArray(response);
                    Type productlistType = new TypeToken<ArrayList<Product>>(){}.getType();
                    PList = gson.fromJson(response, productlistType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<String> productnameList = new ArrayList<>();
                for (Product product : PList) {
                    productnameList.add(product.name);
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.productlist, productnameList);
                listView.setAdapter(adapter);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(RequestFactory.getPage("product", page, pageSize, stringListener, errorListener));

    }
}
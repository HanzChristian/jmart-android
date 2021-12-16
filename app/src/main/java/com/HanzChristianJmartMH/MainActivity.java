package com.HanzChristianJmartMH;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.HanzChristianJmartMH.model.Account;
import com.HanzChristianJmartMH.model.Payment;
import com.HanzChristianJmartMH.model.Product;
import com.HanzChristianJmartMH.model.ProductCategory;
import com.HanzChristianJmartMH.request.FilterRequest;
import com.HanzChristianJmartMH.request.PaymentRequest;
import com.HanzChristianJmartMH.request.RequestFactory;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;




public class MainActivity extends AppCompatActivity {

    private String[] productCategory = {"BOOK", "KITCHEN", "ELECTRONIC", "FASHION", "GAMING", "GADGET", "MOTHERCARE",
            "COSMETICS", "HEALTHCARE", "FURNITURE", "JEWELRY", "TOYS", "FNB", "STATIONERY", "SPORTS", "AUTOMOTIVE",
            "PETCARE", "ART_CRAFT", "CARPENTRY", "MISCELLANEOUS", "PROPERTY", "TRAVEL", "WEDDING"};

    //Products
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Product> PList = new ArrayList<>();
    private static final Gson gson = new Gson();
    private ListView listView;
    private Button prevbtn;
    private Button nextbtn;
    private Button gobtn;
    private Button buynowbtn;
    private Button cancelbtn;
    private Button purchasebtn;
    private EditText edtfilterproduct;
    int page = 0;
    private Toolbar mTopToolbar;
    private CardView productcardView, filtercardView;
    private boolean SignFilter = false;
    double discount = 0;

    //Filter
    private EditText editName;
    private EditText lowestPrice;
    private EditText highestPrice;
    private CheckBox checkNew;
    private CheckBox checkUsed;
    private Button applyButton;
    private Button clearButton;
    private Spinner spinner;

    private LinearLayout linearlayoutBuyNow;
    private Payment payment;




    Account account = LoginActivity.getLoggedAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Products
        prevbtn = findViewById(R.id.prev);
        nextbtn = findViewById(R.id.next);
        gobtn = findViewById(R.id.go);
        edtfilterproduct = findViewById(R.id.editPage);
        listView = findViewById(R.id.listViewProduct);

        //Dialog for product_detail
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.product_detail);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                final TextView productdetailsName = dialog.findViewById(R.id.productdetailsName);
                final TextView productdetailsWeight = dialog.findViewById(R.id.productdetailsWeight);
                final TextView productdetailsPrice = dialog.findViewById(R.id.productdetailsPrice);
                final TextView productdetailsDiscount = dialog.findViewById(R.id.productdetailsDiscount);
                final TextView productdetailsConditionUsed = dialog.findViewById(R.id.productdetailsConditionUsed);
                final TextView productdetailsCategory = dialog.findViewById(R.id.productdetailsCategory);
                final TextView productdetailsShipmentPlan = dialog.findViewById(R.id.productdetailsShipmentPlan);

                final TextView productdetailsTotalPrice = dialog.findViewById(R.id.productdetailsTotalPrice);
                final EditText productdetailsQuantity = dialog.findViewById(R.id.productdetailsQuantity);
                final EditText productdetailsAddress = dialog.findViewById(R.id.productdetailsAddress);

                buynowbtn = dialog.findViewById(R.id.productdetailsBuyNow);
                linearlayoutBuyNow = dialog.findViewById(R.id.linearlayoutBuyNow);
                cancelbtn = dialog.findViewById(R.id.productdetailsCancel);
                purchasebtn = dialog.findViewById(R.id.productdetailsPurchase);
                Product product = PList.get(position);

                //Button Buy Now pada product detail
                buynowbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        buynowbtn.setVisibility(v.GONE);
                        linearlayoutBuyNow.setVisibility(v.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Buy Now clicked",Toast.LENGTH_SHORT).show();
                    }
                });

                //Cancel button di click
                cancelbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        buynowbtn.setVisibility(v.VISIBLE);
                        linearlayoutBuyNow.setVisibility(v.GONE);
                        Toast.makeText(getApplicationContext(),"Cancel Clicked",Toast.LENGTH_SHORT).show();
                    }
                });

                //Cancel button di click
                purchasebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isEmptyFields = false;
                        if (TextUtils.isEmpty(productdetailsAddress.getText().toString())) {
                            isEmptyFields = true;
                            productdetailsAddress.setError("Address must be filled!");
                        }
                        if (TextUtils.isEmpty(productdetailsQuantity.getText().toString())) {
                            isEmptyFields = true;
                            productdetailsQuantity.setError("Quantity must be filled!");
                        }
                        if (!isEmptyFields) {
                            double totalprice = Double.valueOf(productdetailsTotalPrice.getText().toString().substring(3));
                            if(account.balance < totalprice){
                                Toast.makeText(MainActivity.this,"Your balance is too low!",Toast.LENGTH_SHORT).show();
                            }
                            else if(account.balance >= totalprice){
                                Response.Listener<String> listenerCreatePayment = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONObject object = null;
                                        try {
                                            object = new JSONObject(response);
                                            if (object != null) {
                                                Toast.makeText(MainActivity.this, "Payment Completed!", Toast.LENGTH_SHORT).show();
                                                takeBalance();
                                                dialog.dismiss();
                                            }
                                            payment = gson.fromJson(response, Payment.class);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                Response.ErrorListener errorListenerCreatePayment = new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity.this, "Payment Failed!", Toast.LENGTH_SHORT).show();
                                        Log.d("ERROR", error.toString());
                                    }
                                };
                                PaymentRequest createPaymentRequest = new PaymentRequest(account.id, product.id, Integer.parseInt(productdetailsQuantity.getText().toString()), productdetailsAddress.getText().toString(), product.shipmentPlans, product.accountId, listenerCreatePayment, errorListenerCreatePayment);
                                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                queue.add(createPaymentRequest);
                            }

                        }
                    }
                });

                productdetailsQuantity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(productdetailsQuantity.getText().toString().equals("")){

                        }
                        else{
                            if(product.discount == 0){
                                productdetailsTotalPrice.setText("Rp. " + ((product.price * Integer.valueOf(productdetailsQuantity.getText().toString()))));
                            }
                            discount = product.price * (product.discount/(100));
                            productdetailsTotalPrice.setText("Rp. " + ((product.price - discount)* Integer.valueOf(productdetailsQuantity.getText().toString())));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                String ShipmentPlans = "REGULER";
                if (product.shipmentPlans == 1) {
                    ShipmentPlans = "INSTANT";
                } else if (product.shipmentPlans == 2) {
                    ShipmentPlans = "SAME DAY";
                } else if (product.shipmentPlans == 4) {
                    ShipmentPlans = "NEXT DAY";
                } else if (product.shipmentPlans == 8) {
                    ShipmentPlans = "REGULER";
                } else if (product.shipmentPlans == 16) {
                    ShipmentPlans = "KARGO";
                }

                String condition;
                if (product.conditionUsed) { //kondisi true
                    condition = "NEW";
                } else {
                    condition = "USED";
                }


                productdetailsName.setText(product.name);
                productdetailsWeight.setText(product.weight + " Kg");
                productdetailsPrice.setText("Rp. " + product.price);
                productdetailsDiscount.setText(product.discount + " %");
                productdetailsConditionUsed.setText(condition);
                productdetailsCategory.setText(product.category + "");
                productdetailsShipmentPlan.setText(ShipmentPlans);
                productdetailsTotalPrice.setText("Rp. " + product.price);

                dialog.show();
            }
        });


        GetshowProductList(0,3);


        //Button pada Product
        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = Integer.valueOf(edtfilterproduct.getText().toString());
                page--;
                if (SignFilter) {
                    GetshowFilterProductList(page, 3);
                } else {
                    GetshowProductList(page,3);
                }
            }
        });

        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page == 0) {
                    Toast.makeText(MainActivity.this, "Error! Already in Page 1!", Toast.LENGTH_SHORT).show();
                } else if (page >= 1) {
                    page--;
                    if (SignFilter) {
                        GetshowFilterProductList(page, 3);
                    } else {
                        GetshowProductList(page,3);
                    }
                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                if (SignFilter) {
                    GetshowFilterProductList(page, 3);
                } else {
                    GetshowProductList(page,3);
                }
            }
        });


        //Untuk menampilkan toolbar pada menu_main.xml
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        //Filter
        editName = findViewById(R.id.name);
        lowestPrice = findViewById(R.id.lowestprice);
         highestPrice = findViewById(R.id.highestprice);
         checkNew = findViewById(R.id.checknew);
         checkUsed = findViewById(R.id.checkused);
        applyButton = findViewById(R.id.appplybutton);
        clearButton = findViewById(R.id.clearbutton);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetshowFilterProductList(0, 3);
                SignFilter = true;
                edtfilterproduct.setText("" + 1);
                Toast.makeText(MainActivity.this, "Filter Success!", Toast.LENGTH_SHORT).show();

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 0;
                GetshowProductList(page,3);
                SignFilter = false;
                edtfilterproduct.setText("" + 1);
                Toast.makeText(MainActivity.this, "Filter Cleared!", Toast.LENGTH_SHORT).show();

            }
        });


        //Spinner
        spinner = (Spinner) findViewById(R.id.spinnerproduct);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, productCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Mengatur tab
        CardView productcardView = findViewById(R.id.productCardView);
        CardView filtercardView = findViewById(R.id.filterCardView);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        productcardView.setVisibility(View.VISIBLE);
                        filtercardView.setVisibility(View.GONE);
                        break;
                    case 1:
                        filtercardView.setVisibility(View.VISIBLE);
                        productcardView.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //mengambil balance ketika sudah membayar
    public void takeBalance(){
        //Ketika menerima response
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    account = gson.fromJson(response, Account.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //Ketika tidak menerima response
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(RequestFactory.getById("account", account.id, listener, errorListener));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem addMenu = menu.findItem(R.id.addbox);
        if (account.store == null) {
            addMenu.setVisible(false);
        }
        return true;
    }

    //Toolbar dipencet
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            Toast.makeText(MainActivity.this, "Search Clicked", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.addbox) {
            Toast.makeText(MainActivity.this, "Add Box Clicked", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, CreateProductActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.person) {
            Toast.makeText(MainActivity.this, "Person Clicked", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method untuk mengambil product listnya sesuai page dan pageSize
    public void GetshowProductList(int pageBefore,int pageSize) {
        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    PList.clear();
                    JSONArray jsonArray = new JSONArray(response);
                    Type productlistType = new TypeToken<ArrayList<Product>>() {
                    }.getType();
                    PList = gson.fromJson(response, productlistType);
                    if(PList.isEmpty()){
                        Toast.makeText(MainActivity.this, "Error! The page you are looking for is empty!", Toast.LENGTH_SHORT).show();
                        page--;
                    }
                    else{
                        List<String> productnameList = new ArrayList<>();
                        for (Product product : PList) {
                            productnameList.add(product.name);
                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.productlist, productnameList);
                        listView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(RequestFactory.getPage("product", pageBefore, pageSize, stringListener, errorListener));
    }


    public void GetshowFilterProductList(int page, int pageSize) {
        String filteredname = editName.getText().toString();
        Integer minP;
        Integer maxP;
        if (lowestPrice.getText().toString().equals("")) {
            minP = null;
        } else {
            minP = Integer.valueOf(lowestPrice.getText().toString());
        }

        if (highestPrice.getText().toString().equals("")) {
            maxP = null;
        } else {
            maxP = Integer.valueOf(highestPrice.getText().toString());
        }
        ProductCategory category = getProductCategory(spinner);
        String productcategory = spinner.getSelectedItem().toString();
        for (ProductCategory p : ProductCategory.values()) {
            if (p.toString().equals(productcategory)) {
                category = p;
            }
        }

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    PList.clear();
                    JSONArray jsonArray = new JSONArray(response);
                    Type PListType = new TypeToken<ArrayList<Product>>(){}.getType();
                    PList = gson.fromJson(response, PListType);
                    if(!PList.isEmpty()){
                        List<String> productnameList = new ArrayList<>();
                        for (Product product : PList) {
                            productnameList.add(product.name);
                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.productlist, productnameList);
                        listView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error! Already in the last filter!", Toast.LENGTH_SHORT).show();
            }
        };
        FilterRequest filterRequest;
        if (maxP == null && minP == null) {
            filterRequest = new FilterRequest(page, pageSize, account.id, filteredname, category, listener, errorListener);
        } else if (maxP == null) {
            filterRequest = new FilterRequest(page, pageSize, account.id, minP, filteredname, category, listener, errorListener);
        } else if (minP == null) {
            filterRequest = new FilterRequest(filteredname, page, account.id, maxP, category, listener, errorListener);
        } else {
            filterRequest = new FilterRequest(page, account.id, filteredname, minP, maxP, category, listener, errorListener);
        }
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(filterRequest);
    }


    public ProductCategory getProductCategory(Spinner spinner){
        ProductCategory category = ProductCategory.BOOK;
        String productCategory = spinner.getSelectedItem().toString();
        for(ProductCategory pc : ProductCategory.values()){
            if(pc.toString().equals(productCategory)){
                category = pc;
            }
        }
        return category;
    }

}
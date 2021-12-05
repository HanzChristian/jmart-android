package com.HanzChristianJmartMH;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.HanzChristianJmartMH.model.Account;
import com.HanzChristianJmartMH.model.Product;
import com.HanzChristianJmartMH.model.ProductCategory;
import com.HanzChristianJmartMH.request.FilterRequest;
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
    private EditText edtfilterproduct;
    int page = 0;
    private Toolbar mTopToolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CardView productcardView, filtercardView;
    private boolean SignFilter = false;

    //Filter
    private EditText editName;
    private EditText lowestPrice;
    private EditText highestPrice;
    private CheckBox checkNew;
    private CheckBox checkUsed;
    private Button applyButton;
    private Button clearButton;
    private Spinner spinner;



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
        GetshowProductList(page, 1);

        //Button pada Product
        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = Integer.valueOf(edtfilterproduct.getText().toString());
                page--;
                if (SignFilter) {
                    GetshowFilterProductList(page, 1);
                } else {
                    GetshowProductList(page, 1);
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
                        GetshowFilterProductList(page, 1);
                    } else {
                        GetshowProductList(page, 1);
                    }
                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                if (SignFilter) {
                    GetshowFilterProductList(page, 1);
                } else {
                    GetshowProductList(page, 1);
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
                GetshowFilterProductList(page, 1);
                SignFilter = true;
                edtfilterproduct.setText("" + 1);
                Toast.makeText(MainActivity.this, "Filter Success!", Toast.LENGTH_SHORT).show();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetshowFilterProductList(page, 1);
                edtfilterproduct.setText("" + 1);
                Toast.makeText(MainActivity.this, "Filter Cleared!", Toast.LENGTH_SHORT).show();

            }
        });


        //Spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerproduct);
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
    public void GetshowProductList(int page, int pageSize) {
        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    PList.clear();
                    JSONArray jsonArray = new JSONArray(response);
                    Type productlistType = new TypeToken<ArrayList<Product>>() {
                    }.getType();
                    PList = gson.fromJson(response, productlistType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<String> productnameList = new ArrayList<>();
                for (Product product : PList) {
                    productnameList.add(product.name);
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.productlist, productnameList);
                listView.setAdapter(adapter);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(RequestFactory.getPage("product", page, pageSize, stringListener, errorListener));
    }


    public void GetshowFilterProductList(int page, int pageSize) {
        String filteredname = editName.getText().toString();
        int minP;
        int maxP;
        if (lowestPrice.getText().toString().equals("")) {
            minP = 0;
        } else {
            minP = Integer.valueOf(lowestPrice.getText().toString());
        }

        if (highestPrice.getText().toString().equals("")) {
            maxP = 0;
        } else {
            maxP = Integer.valueOf(highestPrice.getText().toString());
        }

        ProductCategory category = ProductCategory.AUTOMOTIVE;
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
                    Type PListType = new TypeToken<ArrayList<Product>>() {
                    }.getType();
                    PList = gson.fromJson(response, PListType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<String> productnameList = new ArrayList<>();
                for (Product product : PList) {
                    productnameList.add(product.name);
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.productlist, productnameList);
                listView.setAdapter(adapter);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        };
        FilterRequest filterRequest;
        if (maxP == 0 && minP == 0) {
            filterRequest = new FilterRequest(page, pageSize, account.id, filteredname, category, listener, errorListener);
        } else if (maxP == 0) {
            filterRequest = new FilterRequest(page, pageSize, account.id, minP, filteredname, category, listener, errorListener);
        } else if (minP == 0) {
            filterRequest = new FilterRequest(filteredname, page, account.id, maxP, category, listener, errorListener);
        } else {
            filterRequest = new FilterRequest(page, account.id, filteredname, minP, maxP, category, listener, errorListener);
        }
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(filterRequest);

    }
}
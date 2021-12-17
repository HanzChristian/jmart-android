package com.HanzChristianJmartMH;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.HanzChristianJmartMH.model.Payment;
import com.HanzChristianJmartMH.model.Product;
import com.HanzChristianJmartMH.request.RequestFactory;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Merupakan Class yang merepresentasikan Adapter untuk invoice Account
 * @author Hanz Christian
 * @version 16 Desember 2021
 */
public class invoiceAccountAdapter extends RecyclerView.Adapter<invoiceAccountAdapter.CardViewAccountHolder> {
    private ArrayList<Payment> listP = new ArrayList<>();
    private Product product;
    private static final Gson gson = new Gson();
    double discount;

    /**
     * Merupakan method yang digunakan untuk mendapatkan list paymentnya
     * @param list
     */
    public invoiceAccountAdapter(ArrayList<Payment> list){
        this.listP = list;
    }

    /**
     * Merupakan method yang digunakan untuk merepresentasikan layout adapter yang dipakai yaitu invoice_detail
     * @param parent
     * @param viewType
     * @return berypa Holder untuk Cardview Account
     */
    @NonNull
    @Override
    public invoiceAccountAdapter.CardViewAccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_detail, parent, false);
        return new CardViewAccountHolder(view);
    }

    /**
     * Merupakan method yang digunakan untuk mengatur tiap Holdernya
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull invoiceAccountAdapter.CardViewAccountHolder holder, int position) {
        Payment payment = listP.get(position);
        Payment.Record lastRec = payment.history.get(payment.history.size() - 1);
        getProductData(holder, payment);
        holder.invoiceStatus.setText(lastRec.status.toString());
        holder.invoiceDate.setText(lastRec.date.toString());
        holder.invoiceAddress.setText(payment.shipment.address);
        holder.invoiceOrderId.setText("Order ID #" + (position+1));
    }

    @Override
    public int getItemCount() {
        return listP.size();
    }

    /**
     * Merupakan class yang digunakan untuk Holder pada Account
     * @author Hanz Christian
     * @version 16 Desember 2021
     */
    public class CardViewAccountHolder extends RecyclerView.ViewHolder {
        TextView invoiceName, invoiceStatus, invoiceDate, invoiceAddress, invoiceCost,invoiceOrderId;

        /**
         * Merupakan method yang digunakan untuk inisiasi setiap parameter berdasarkan id di xml
         * @param itemView
         */
        public CardViewAccountHolder(@NonNull View itemView) {
            super(itemView);
            invoiceName = itemView.findViewById(R.id.invoiceName);
            invoiceStatus = itemView.findViewById(R.id.invoiceStatus);
            invoiceDate = itemView.findViewById(R.id.invoiceDate);
            invoiceAddress = itemView.findViewById(R.id.invoiceAddress);
            invoiceCost = itemView.findViewById(R.id.invoiceCost);
            invoiceOrderId = itemView.findViewById(R.id.invoiceOrderId);

        }
    }

    /**
     * Merupakan method yang digunakan untuk mendapatkan data product yang sudah dibayar
     * @param holder
     * @param payment
     */
    public void getProductData (invoiceAccountAdapter.CardViewAccountHolder holder, Payment payment){
        Response.Listener<String> ListenerProduct = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    product = gson.fromJson(response, Product.class);
                    holder.invoiceName.setText(product.name);
                    holder.invoiceCost.setText("Rp " + ((product.price -(product.price * (product.discount/(100))))*payment.productCount));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(holder.invoiceName.getContext(), "ERROR!", Toast.LENGTH_SHORT).show();
            }
        };

        RequestQueue queue = Volley.newRequestQueue(holder.invoiceName.getContext());
        queue.add(RequestFactory.getById("product", payment.productId, ListenerProduct, errorListener));
    }
}

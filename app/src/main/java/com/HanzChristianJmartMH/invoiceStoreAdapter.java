package com.HanzChristianJmartMH;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.HanzChristianJmartMH.model.Account;
import com.HanzChristianJmartMH.model.Invoice;
import com.HanzChristianJmartMH.model.Payment;
import com.HanzChristianJmartMH.model.Product;
import com.HanzChristianJmartMH.request.AcceptPaymentRequest;
import com.HanzChristianJmartMH.request.CancelPaymentRequest;
import com.HanzChristianJmartMH.request.RequestFactory;
import com.HanzChristianJmartMH.request.SubmitPaymentRequest;
import com.HanzChristianJmartMH.request.TopUpRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Merupakan Class yang merepresentasikan Adapter untuk invoice Store
 *
 * @author Hanz Christian
 * @version 16 Desember 2021
 */
public class invoiceStoreAdapter extends RecyclerView.Adapter<invoiceStoreAdapter.CardViewStoreHolder> {
    private ArrayList<Payment> listP = new ArrayList<>();
    private Product product;
    private static final Gson gson = new Gson();
    double discount;
    Payment.Record lastRec;
    Dialog dialog;


    /**
     * Merupakan method yang digunakan untuk mendapatkan list paymentnya
     *
     * @param list
     */
    public invoiceStoreAdapter(ArrayList<Payment> list) {
        this.listP = list;
    }

    /**
     * Merupakan method yang digunakan untuk merepresentasikan layout adapter yang dipakai yaitu storeinvoice_detail
     *
     * @param parent
     * @param viewType
     * @return berypa Holder untuk Cardview Account
     */
    @NonNull
    @Override
    public invoiceStoreAdapter.CardViewStoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storeinvoice_detail, parent, false);
        return new CardViewStoreHolder(view);
    }

    /**
     * Merupakan method yang digunakan untuk mengatur tiap Holdernya
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull invoiceStoreAdapter.CardViewStoreHolder holder, int position) {
        Payment payment = listP.get(position);
        lastRec = payment.history.get(payment.history.size() - 1);
        getProductData(holder, payment);
        holder.invoiceStoreStatus.setText(lastRec.status.toString());
        holder.invoiceStoreDate.setText(lastRec.date.toString());
        holder.invoiceStoreAddress.setText(payment.shipment.address);
        holder.invoiceStoreOrderId.setText("Order ID #" + (position + 1));

        if (lastRec.status.equals(Invoice.Status.WAITING_CONFIRMATION)) {
            holder.storebuttonslayout.setVisibility(View.VISIBLE);
        } else {
            holder.storebuttonslayout.setVisibility(View.GONE);
        }

        if (lastRec.status.equals(Invoice.Status.ON_PROGRESS)) {
            holder.receiptButton.setVisibility(View.VISIBLE);
        } else {
            holder.receiptButton.setVisibility(View.GONE);
        }


        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listenerAcceptPayment = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Boolean isAccepted = Boolean.valueOf(response);
                        if (isAccepted) {
                            Toast.makeText(holder.invoiceStoreName.getContext(), "Payment Accepted!", Toast.LENGTH_SHORT).show();
                            payment.history.add(new Payment.Record(Invoice.Status.ON_PROGRESS, "Payment Accepted!"));
                            lastRec = payment.history.get(payment.history.size() - 1);
                            holder.invoiceStoreStatus.setText(lastRec.status.toString());
                            holder.storebuttonslayout.setVisibility(View.GONE);
                            holder.receiptButton.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(holder.invoiceStoreName.getContext(), "Payment Failed due to Accept fails!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Response.ErrorListener errorListenerAcceptPayment = new Response.ErrorListener() {      //errorListener jika tidak terkoneksi ke backend
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(holder.invoiceStoreName.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    }
                };
                AcceptPaymentRequest acceptPaymentRequest = new AcceptPaymentRequest(payment.id, listenerAcceptPayment, errorListenerAcceptPayment);
                RequestQueue queue = Volley.newRequestQueue(holder.invoiceStoreName.getContext());
                queue.add(acceptPaymentRequest);
            }
        });

        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listenerCancelPayment = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Boolean isAccepted = Boolean.valueOf(response);
                        if (isAccepted) {
                            double price = Double.valueOf(holder.invoiceStoreCost.getText().toString().trim().substring(3));
                            Response.Listener<String> listener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Boolean object = Boolean.valueOf(response);
                                    if (object) {
                                        Toast.makeText(holder.invoiceStoreName.getContext(), "Balance has been restored!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(holder.invoiceStoreName.getContext(), "Balance not returned!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            Response.ErrorListener errorListener = new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(holder.invoiceStoreName.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                                }
                            };
                            TopUpRequest topUpRequest = new TopUpRequest(payment.buyerId, price, listener, errorListener);
                            RequestQueue queue = Volley.newRequestQueue(holder.invoiceStoreName.getContext());
                            queue.add(topUpRequest);
                            Toast.makeText(holder.invoiceStoreName.getContext(), "Payment is cancelled!", Toast.LENGTH_SHORT).show();
                            payment.history.add(new Payment.Record(Invoice.Status.CANCELLED, "Payment is cancelled!"));
                            lastRec = payment.history.get(payment.history.size() - 1);
                            holder.invoiceStoreStatus.setText(lastRec.status.toString());
                            holder.storebuttonslayout.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(holder.invoiceStoreName.getContext(), "Payment cannot be cancelled!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Response.ErrorListener errorListenerCancelPayment = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(holder.invoiceStoreName.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    }
                };
                CancelPaymentRequest cancelPaymentRequest = new CancelPaymentRequest(listenerCancelPayment, payment.id, errorListenerCancelPayment);
                RequestQueue queue = Volley.newRequestQueue(holder.invoiceStoreName.getContext());
                queue.add(cancelPaymentRequest);
            }
        });

        holder.receiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(holder.acceptButton.getContext());
                dialog.setContentView(R.layout.receipt);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                EditText receiptnumber = dialog.findViewById(R.id.receiptnumber);
                Button submitButton = dialog.findViewById(R.id.submitButton);
                dialog.show();

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String noReceipt = receiptnumber.getText().toString().trim();
                        Response.Listener<String> listenerSubmit = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Boolean isSubmit = Boolean.valueOf(response);
                                if (isSubmit) {
                                    Toast.makeText(holder.invoiceStoreName.getContext(), "Payment has been submitted!", Toast.LENGTH_SHORT).show();
                                    payment.history.add(new Payment.Record(Invoice.Status.ON_DELIVERY, "Payment has been Submitted!"));
                                    lastRec = payment.history.get(payment.history.size() - 1);
                                    holder.invoiceStoreStatus.setText(lastRec.status.toString());
                                    holder.receiptButton.setVisibility(view.GONE);
                                } else {
                                    Toast.makeText(holder.invoiceStoreName.getContext(), "Payment not submitted!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        Response.ErrorListener errorListenerSubmit = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(holder.invoiceStoreName.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                            }
                        };
                        SubmitPaymentRequest submitPaymentRequest = new SubmitPaymentRequest(payment.id, noReceipt, listenerSubmit, errorListenerSubmit);
                        RequestQueue queue = Volley.newRequestQueue(holder.invoiceStoreName.getContext());
                        queue.add(submitPaymentRequest);
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return listP.size();
    }

    /**
     * Merupakan class yang digunakan untuk Holder pada Store
     *
     * @author Hanz Christian
     * @version 16 Desember 2021
     */
    public class CardViewStoreHolder extends RecyclerView.ViewHolder {
        TextView invoiceStoreName, invoiceStoreStatus, invoiceStoreDate, invoiceStoreAddress, invoiceStoreCost, invoiceStoreOrderId;
        Button acceptButton, cancelButton, receiptButton;
        LinearLayout storebuttonslayout;

        /**
         * Merupakan method yang digunakan untuk inisiasi setiap parameter berdasarkan id di xml
         *
         * @param itemView
         */
        public CardViewStoreHolder(@NonNull View itemView) {
            super(itemView);
            invoiceStoreName = itemView.findViewById(R.id.invoiceStoreName);
            invoiceStoreStatus = itemView.findViewById(R.id.invoiceStoreStatus);
            invoiceStoreDate = itemView.findViewById(R.id.invoiceStoreDate);
            invoiceStoreAddress = itemView.findViewById(R.id.invoiceStoreAddress);
            invoiceStoreCost = itemView.findViewById(R.id.invoiceStoreCost);
            invoiceStoreOrderId = itemView.findViewById(R.id.invoiceStoreOrderId);
            acceptButton = itemView.findViewById(R.id.acceptButton);
            cancelButton = itemView.findViewById(R.id.cancelButton);
            receiptButton = itemView.findViewById(R.id.receiptButton);
            storebuttonslayout = itemView.findViewById(R.id.storebuttonslayout);
        }
    }

    /**
     * Merupakan method yang digunakan untuk mendapatkan data product yang sudah dibayar
     *
     * @param holder
     * @param payment
     */
    public void getProductData(invoiceStoreAdapter.CardViewStoreHolder holder, Payment payment) {
        Response.Listener<String> ListenerProduct = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    product = gson.fromJson(response, Product.class);
                    holder.invoiceStoreName.setText(product.name);
                    holder.invoiceStoreCost.setText("Rp " + ((product.price - (product.price * (product.discount / (100)))) * payment.productCount));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(holder.invoiceStoreName.getContext(), "ERROR!", Toast.LENGTH_SHORT).show();
            }
        };

        RequestQueue queue = Volley.newRequestQueue(holder.invoiceStoreName.getContext());
        queue.add(RequestFactory.getById("product", payment.productId, ListenerProduct, errorListener));
    }

}


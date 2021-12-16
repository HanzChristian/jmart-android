package com.HanzChristianJmartMH.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Merupakan Model untuk Payment
 * @author Hanz Christian
 * @version 16 Desember 2021
 */

public class Payment extends Invoice{
        public ArrayList<Record> history = new ArrayList<>();
        public int productCount;
        public Shipment shipment;
        public int storeId;

        public static class Record
        {
            public Status status;
            public final Date date;
            public String message;

            public Record(Status status, String message)
            {
                this.status = status;
                this.message = message;
                this.date = Calendar.getInstance().getTime();
            }
        }

    }


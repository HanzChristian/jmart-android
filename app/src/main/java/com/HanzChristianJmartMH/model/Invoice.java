package com.HanzChristianJmartMH.model;

import java.util.Date;

/**
 * Merupakan Model untuk Invoice
 * @author Hanz Christian
 * @version 16 Desember 2021
 */

public abstract class Invoice extends Serializable{

    /**
     * Merupakan enum Status yang berisikan kondisi barang ketika sudah dibeli oleh Account
     */
    public enum Status
    {
        WAITING_CONFIRMATION,
        CANCELLED,
        DELIVERED,
        ON_PROGRESS,
        ON_DELIVERY,
        COMPLAINT,
        FINISHED,
        FAILED
    }

    /**
     * Merupakan enum untuk memberikan rating
     */
    enum Rating
    {
        NONE,
        BAD,
        NEUTRAL,
        GOOD
    }

    public Date date;           //harusnya final di backend
    public int buyerId;
    public int productId;
    public int complaintId;
    public Rating rating;
}

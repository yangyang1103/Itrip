package cn.itrip.pojo;

import java.util.Date;

public class StoreVo {
    private int roomId;
    private int store;
    private Date recordDate;
    private int store1;
    private int bookingDays;
    private Date checkInDate;
    private Date checkOutDate;

    public StoreVo(){

    };
    public StoreVo(int roomId, int store, Date recordDate, int store1, int bookingDays, Date checkInDate, Date checkOutDate) {
        this.roomId = roomId;
        this.store = store;
        this.recordDate = recordDate;
        this.store1 = store1;
        this.bookingDays = bookingDays;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }


    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getStore1() {
        return store1;
    }

    public void setStore1(int store1) {
        this.store1 = store1;
    }

    public int getBookingDays() {
        return bookingDays;
    }

    public void setBookingDays(int bookingDays) {
        this.bookingDays = bookingDays;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}

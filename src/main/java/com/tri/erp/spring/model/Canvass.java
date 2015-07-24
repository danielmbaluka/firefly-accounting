package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tri.erp.spring.response.CanvassDetailDto;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Personal on 5/12/2015.
 */
@Entity
public class Canvass extends Document implements Serializable {

    @NotNull(message = "Please enter voucher date.")
    @Column
    private Date voucherDate;

    @Column
    private Integer year;

    @NotNull(message = "Please select vendor.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK_vendorAccountNo")
    private SlEntity vendor;

    @Transient
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private ArrayList<CanvassDetailDto> canvassDetails = new ArrayList<>();

    public Canvass() {}

    public Canvass(Date voucherDate, Integer year, SlEntity vendor, ArrayList<CanvassDetailDto> canvassDetails) {
        this.voucherDate = voucherDate;
        this.year = year;
        this.vendor = vendor;
        this.canvassDetails = canvassDetails;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public SlEntity getVendor() {
        return vendor;
    }

    public void setVendor(SlEntity vendor) {
        this.vendor = vendor;
    }

    public ArrayList<CanvassDetailDto> getCanvassDetails() {
        return canvassDetails;
    }

    public void setCanvassDetails(ArrayList<CanvassDetailDto> canvassDetails) {
        this.canvassDetails = canvassDetails;
    }
}

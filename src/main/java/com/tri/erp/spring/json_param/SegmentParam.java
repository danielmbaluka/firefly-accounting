package com.tri.erp.spring.json_param;

import com.tri.erp.spring.model.SegmentAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TSI Admin on 10/3/2014.
 */
public class SegmentParam {

    private List<Integer> segmentAccountIds = new ArrayList<>();

    public List<Integer> getSegmentAccountIds() {
        return segmentAccountIds;
    }

    public void setSegmentAccountIds(List<Integer> segmentAccountIds) {
        this.segmentAccountIds = segmentAccountIds;
    }

}

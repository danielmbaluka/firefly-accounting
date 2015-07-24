package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.model.RvDetail;
import com.tri.erp.spring.repo.RvDetailRepo;
import com.tri.erp.spring.response.RvDetailDto;
import com.tri.erp.spring.service.interfaces.RvDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Personal on 4/29/2015.
 */
@Service
public class RvDetailServiceImpl implements RvDetailService {
    @Autowired
    RvDetailRepo rvDetailRepo;

    @Override
    public List<RvDetailDto> getRvDetails(Integer rvId) {
        List<RvDetail> rvDetails = rvDetailRepo.findByRequisitionVoucherId(rvId);

        List<RvDetailDto> rvDetailDtos = new ArrayList<>();

        if (rvDetails != null) {
            for (RvDetail line : rvDetails) {
                RvDetailDto lineDto = new RvDetailDto();
                lineDto.setQuantity(line.getQuantity());
                lineDto.setItemId(0);
                if(line.getItem() != null) {
                    lineDto.setItemCode(line.getItem().getCode());
                    lineDto.setItemDescription(line.getItem().getDescription());
                    lineDto.setItemId(line.getItem().getId());
                }
                lineDto.setUnitId(line.getUnitMeasure().getId());
                lineDto.setUnitCode(line.getUnitMeasure().getCode());
                lineDto.setRvId(line.getRequisitionVoucher().getId());
                lineDto.setJoDescription(line.getJoDescription());

                rvDetailDtos.add(lineDto);
            }
        }

        return rvDetailDtos;
    }

    @Override
    public List<RvDetailDto> getRvDetailsByStatus(Integer statusId) {
        List<RvDetail> rvDetails = rvDetailRepo.findRvDetailsByRequisitionVoucherDocumentStatusId(statusId);

        List<RvDetailDto> rvDetailDtos = new ArrayList<>();

        if (rvDetails != null) {
            for (RvDetail line : rvDetails) {
                RvDetailDto lineDto = new RvDetailDto();
                lineDto.setId(line.getId());
                lineDto.setQuantity(line.getQuantity());
                lineDto.setItemId(0);
                if(line.getItem() != null) {
                    lineDto.setItemCode(line.getItem().getCode());
                    lineDto.setItemDescription(line.getItem().getDescription());
                    lineDto.setItemId(line.getItem().getId());
                }
                lineDto.setUnitId(line.getUnitMeasure().getId());
                lineDto.setUnitCode(line.getUnitMeasure().getCode());
                lineDto.setRvId(line.getRequisitionVoucher().getId());
                lineDto.setJoDescription(line.getJoDescription());
                lineDto.setRvNumber(line.getRequisitionVoucher().getCode());
                lineDto.setRvDate(line.getRequisitionVoucher().getVoucherDate());
                lineDto.setPoQuantity(line.getPoQuantity());

                rvDetailDtos.add(lineDto);
            }
        }

        return rvDetailDtos;
    }
}

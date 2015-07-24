package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.model.PoDetail;
import com.tri.erp.spring.repo.PoDetailRepo;
import com.tri.erp.spring.response.PoDetailDto;
import com.tri.erp.spring.service.interfaces.PoDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Personal on 5/15/2015.
 */
@Service
public class PoDetailServiceImpl implements PoDetailService {
    @Autowired
    PoDetailRepo poDetailRepo;

    @Override
    public List<PoDetailDto> getPoDetails(Integer poId) {
        List<PoDetail> poDetails = poDetailRepo.findByPurchaseOrderId(poId);

        List<PoDetailDto> poDetailDtos = new ArrayList<>();

        if (poDetails != null) {
            for (PoDetail line : poDetails) {
                PoDetailDto lineDto = new PoDetailDto();
                lineDto.setId(line.getId());
                lineDto.setRvDetailId(line.getRvDetail().getId());
                lineDto.setPurchaseOrderId(line.getPurchaseOrder().getId());
                lineDto.setQuantity(line.getQuantity());
                lineDto.setItemCode(line.getRvDetail().getItem().getCode());
                lineDto.setItemDescription(line.getRvDetail().getItem().getDescription());
                lineDto.setUnitCode(line.getRvDetail().getUnitMeasure().getCode());
                lineDto.setUnitPrice(line.getUnitPrice());
                lineDto.setVat(line.getVat());
                lineDto.setDiscount(line.getDiscount());
                lineDto.setItemAmount(line.getAmount());
                lineDto.setRvdQuantity(line.getRvDetail().getQuantity());

                poDetailDtos.add(lineDto);
            }
        }

        return poDetailDtos;
    }
}

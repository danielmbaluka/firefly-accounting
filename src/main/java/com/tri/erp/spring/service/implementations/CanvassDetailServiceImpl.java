package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.model.CanvassDetail;
import com.tri.erp.spring.repo.CanvassDetailRepo;
import com.tri.erp.spring.response.CanvassDetailDto;
import com.tri.erp.spring.service.interfaces.CanvassDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Personal on 5/14/2015.
 */
@Service
public class CanvassDetailServiceImpl implements CanvassDetailService {
    @Autowired
    CanvassDetailRepo canvassDetailRepo;

    @Override
    public List<CanvassDetailDto> getCanvassDetails(Integer canvassId) {
        List<CanvassDetail> canvassDetails = canvassDetailRepo.findByCanvassId(canvassId);

        List<CanvassDetailDto> canvassDetailDtos = new ArrayList<>();

        if (canvassDetails != null) {
            for (CanvassDetail line : canvassDetails) {
                CanvassDetailDto lineDto = new CanvassDetailDto();
                lineDto.setId(line.getId());
                lineDto.setRvDetailId(line.getRvDetail().getId());
                lineDto.setCanvassId(line.getCanvass().getId());
                lineDto.setRvNumber(line.getRvDetail().getRequisitionVoucher().getCode());
                lineDto.setQuantity(line.getRvDetail().getQuantity());
                lineDto.setItemCode(line.getRvDetail().getItem().getCode());
                lineDto.setItemDescription(line.getRvDetail().getItem().getDescription());
                lineDto.setUnitCode(line.getRvDetail().getUnitMeasure().getCode());

                canvassDetailDtos.add(lineDto);
            }
        }

        return canvassDetailDtos;
    }
}

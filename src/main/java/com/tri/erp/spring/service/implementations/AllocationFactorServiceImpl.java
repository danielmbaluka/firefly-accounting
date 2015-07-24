package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.Debug;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.dtoers.AllocationFactorDtoer;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.repo.AllocationFactorRepo;
import com.tri.erp.spring.repo.DateRangeRepo;
import com.tri.erp.spring.repo.GeneralLedgerRepo;
import com.tri.erp.spring.response.AccountDto;
import com.tri.erp.spring.response.AllocationFactorDto;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.service.interfaces.AllocationFactorService;
import com.tri.erp.spring.validator.AllocationFactorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by TSI Admin on 5/19/2015.
 */
@Service
public class AllocationFactorServiceImpl implements AllocationFactorService {

    @Autowired
    AllocationFactorRepo allocationFactorRepo;

    @Autowired
    AllocationFactorDtoer factorDtoer;

    @Autowired
    DateRangeRepo dateRangeRepo;

    @Autowired
    GeneralLedgerRepo generalLedgerRepo;

    @Override
    public List<AllocationFactorDto> findAll() {
        return factorDtoer.findAll();
    }

    @Override
    public AllocationFactorDto findByAccountAndEffectivityId(Integer accountId, Integer effectId) {
        return factorDtoer.findOne(accountId, effectId);
    }

    @Override
    public List<Map> findLatestByAccount(Integer accountId) {
        return factorDtoer.findLatestOne(accountId);
    }

    @Override
    public List<Map> findAccountAndEffectDate(Integer accountId, String voucherDate) {
        List<Object[]> factors = allocationFactorRepo.findByAccountAndDate(accountId, voucherDate);
        List<Map> maps = new ArrayList<>();

        if (!Checker.collectionIsEmpty(factors)) {
            for(Object[] factorRow:factors) {
                Map segmentPercentageMap = new HashMap();
                segmentPercentageMap.put("segmentAccountId", factorRow[1]);
                segmentPercentageMap.put("segmentId", factorRow[2]);
                segmentPercentageMap.put("segmentDescription", factorRow[3]);
                segmentPercentageMap.put("percentage", factorRow[4]);

                maps.add(segmentPercentageMap);
            }
        }
        return maps;
    }


    @Override
    public PostResponse processUpdate(Object entity, BindingResult bindingResult, MessageSource messageSource) {
        return this.processCreate(entity, bindingResult, messageSource);
    }

    @Override
    public PostResponse processCreate(Object entity, BindingResult bindingResult, MessageSource messageSource) {
        AllocationFactorDto factor = (AllocationFactorDto) entity;
        PostResponse response = new PostResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        AllocationFactorValidator validator = new AllocationFactorValidator();
        validator.validate(factor, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
            response.setSuccess(false);
        } else {
            AccountDto accountDto = factor.getAccount();
            Boolean allSaved = false;

            Account account = new Account();
            account.setId(accountDto.getId());
            DateRange effectDate = factor.getEffectivity();

            if (factor.getId() != null) {
                // delete/insert stuffs
                allocationFactorRepo.deleteByAccountIdAndEffectivityDateId(accountDto.getId(), effectDate.getId());
            }

            if (!Checker.collectionIsEmpty(factor.getSegmentPercentage())) {

                int insertCount = 0;
                int totalFactors = factor.getSegmentPercentage().size();
                for (Map f:factor.getSegmentPercentage()){

                    BigDecimal percentage = new BigDecimal(f.get("value").toString());
                    if (percentage.compareTo(BigDecimal.ZERO) > 0) {
                        Integer segmentId = (Integer) f.get("id");

                        BusinessSegment segment = new BusinessSegment();
                        segment.setId(segmentId);

                        AllocationFactor allocationFactor = new AllocationFactor();
                        allocationFactor.setAccount(account);
                        allocationFactor.setBusinessSegment(segment);
                        allocationFactor.setEffectivityDate(effectDate);
                        allocationFactor.setPercentage(percentage);

                        AllocationFactor af = allocationFactorRepo.save(allocationFactor);
                        if (af == null) {
                            break;
                        } else {
                            insertCount++;
                        }
                    } else {
                        totalFactors--;
                    }

                }
                allSaved = totalFactors == insertCount;
            }

            if (allSaved) {
                response.setModelId(account.getId());
                response.setSuccessMessage("Allocation factor successfully saved!");
                response.setSuccess(true);
            }
        }
        return response;
    }

    @Override
    public List<Map> findDateRanges() {
        List<DateRange> dateRangeList = dateRangeRepo.findByOrderByEndDesc(new PageRequest(0, 5));
        if (!Checker.collectionIsEmpty(dateRangeList)) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            List<Map> rangeMap = new ArrayList<>();
            for(DateRange dr:dateRangeList){
                Map m = new HashMap();
                m.put("id", dr.getId());
                m.put("start", dr.getStart());
                m.put("end", dr.getEnd());
                m.put("range",  dateFormat.format(dr.getStart()) + " - " + dateFormat.format(dr.getEnd()));

                rangeMap.add(m);
            }
            return rangeMap;
        }
        return null;
    }

    @Override
    public Map testAutoAllocation(Map postData) {
        Map result = new HashMap();
        Boolean isDistribution = false;

        List<Map> allocations = (List<Map>) postData.get("allocations");
        List<Map> distributions = (List<Map>) postData.get("glentries");
        if (Checker.collectionIsEmpty(distributions)) {
            distributions = (List<Map>) postData.get("distribution");
            isDistribution = true;
        }

        Boolean auto = true;
        allocations:
        for (Map allocation:allocations) {
            Boolean factorInGLEntries = false;

            Integer allocationSegmentAccountId = (Integer)allocation.get("segmentAccountId");
            BigDecimal allocationAmount = new BigDecimal(allocation.get("amount").toString());

            glEntries:
            for (Map gl:distributions) {
                BigDecimal glAmount;
                if (isDistribution) {
                    try {
                        glAmount = new BigDecimal(gl.get("amount").toString());
                    } catch (Exception e) {
                        BigDecimal d = new BigDecimal(gl.get("debit").toString());
                        BigDecimal c = new BigDecimal(gl.get("credit").toString());

                        glAmount = d.add(c);
                    }
                } else {
                    BigDecimal debit = new BigDecimal(gl.get("debit").toString());
                    BigDecimal credit = new BigDecimal(gl.get("credit").toString());
                    glAmount = (debit == null || debit.compareTo(BigDecimal.ZERO) == 0) ? credit:debit;
                }
                Integer glSegmentAccountId = (Integer)gl.get("segmentAccountId");

                if (allocationSegmentAccountId.equals(glSegmentAccountId)) {
                    factorInGLEntries = true;
                    if (allocationAmount.compareTo(glAmount) != 0) {
                        // auto allocated amount is not equal to the GL amount
                        // meaning not using allocation factor automatically
                        auto = false;
                        break allocations;
                    } else {
                        break glEntries;
                    }
                }
            }
            // if there is missing factor in the GL entries
            // therefore not using allocation factors automatically
            if (!factorInGLEntries) {
                auto = false;
                break allocations;
            }
        }
        if (!auto) {
            // reconstruct allocations
            List<Map> newAllocations = new ArrayList<>();
            glEntries:
            for (Map gl:distributions) {
                Integer glSegmentAccountId = (Integer)gl.get("segmentAccountId");
                BigDecimal glAmount;
                if (isDistribution) {
                    try {
                        glAmount = new BigDecimal(gl.get("amount").toString());
                    } catch (Exception e) {
                        BigDecimal d = new BigDecimal(gl.get("debit").toString());
                        BigDecimal c = new BigDecimal(gl.get("credit").toString());

                        glAmount = d.add(c);
                    }
                } else {
                    BigDecimal debit = new BigDecimal(gl.get("debit").toString());
                    BigDecimal credit = new BigDecimal(gl.get("credit").toString());
                    glAmount = (debit == null || debit.compareTo(BigDecimal.ZERO) == 0) ? credit:debit;
                }

                allocations:
                for (Map allocation:allocations) {
                    Integer allocationSegmentAccountId = (Integer)allocation.get("segmentAccountId");
                    if (allocationSegmentAccountId.equals(glSegmentAccountId)) {
                        allocation.put("amount", glAmount);
                        newAllocations.add(allocation);
                        allocations.remove(allocation);
                        break allocations;
                    }
                }
            }
            allocations = newAllocations;
        }

        result.put("auto", auto);
        result.put("allocations", allocations);
        return result;
    }
}

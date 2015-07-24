package com.tri.erp.spring.dtoers;

import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.model.BusinessSegment;
import com.tri.erp.spring.model.DateRange;
import com.tri.erp.spring.repo.AllocationFactorRepo;
import com.tri.erp.spring.response.AccountDto;
import com.tri.erp.spring.response.AllocationFactorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by TSI Admin on 5/19/2015.
 */

@Component
public class AllocationFactorDtoerImpl implements AllocationFactorDtoer {

    @Autowired
    AllocationFactorRepo allocationFactorRepo;

    @Override
    public List<AllocationFactorDto> findAll() {
        List<AllocationFactorDto> factorDtos = new ArrayList<>();
        List<Object[]> factors = allocationFactorRepo.findAllCustom();

        if (!Checker.collectionIsEmpty(factors)) {

            Integer prevAccountId = 0;
            for(Object[] factorRow:factors) {

                Integer accountId = (Integer) factorRow[1];
                BigDecimal percentage = (BigDecimal) factorRow[11];
                Integer segmentId = (Integer) factorRow[4];
                String segmentDesc = (String) factorRow[5];

                AllocationFactorDto dto = new AllocationFactorDto();

                if (!prevAccountId.equals(accountId)) {

                    Integer factorId = (Integer) factorRow[0];
                    String accountCode = (String) factorRow[2];
                    String accountTitle = (String) factorRow[3];
                    Date rangeStart = (Date) factorRow[6];
                    Date rangeEnd = (Date) factorRow[7];
                    Date createdAt = (Date) factorRow[8];
                    Date updateAt = (Date) factorRow[9];
                    Integer effectDateId = (Integer) factorRow[10];

                    AccountDto accountDto = new AccountDto();
                    accountDto.setId(accountId);
                    accountDto.setCode(accountCode);
                    accountDto.setTitle(accountTitle);

                    DateRange dateRange = new DateRange();
                    dateRange.setId(effectDateId);
                    dateRange.setStart(rangeStart);
                    dateRange.setEnd(rangeEnd);

                    dto.setAccount(accountDto);
                    dto.setId(factorId);
                    dto.setCreated(createdAt);
                    dto.setLastUpdated(updateAt);
                    dto.setEffectivity(dateRange);

                    factorDtos.add(dto);
                    prevAccountId = accountDto.getId();
                }

                int lastFactorIdx = factorDtos.size() - 1;
                AllocationFactorDto lastFactor = factorDtos.get(lastFactorIdx);
                List<Map> lastFactorSegmentPercentage = lastFactor.getSegmentPercentage();

                BusinessSegment segment = new BusinessSegment();
                segment.setId(segmentId);
                segment.setDescription(segmentDesc);

                Map lastFactorSegmentPercentageMap = new HashMap();
                lastFactorSegmentPercentageMap.put("segment", segment);
                lastFactorSegmentPercentageMap.put("value", percentage);
                lastFactorSegmentPercentage.add(lastFactorSegmentPercentageMap);

                lastFactor.setSegmentPercentage(lastFactorSegmentPercentage);
                factorDtos.set(lastFactorIdx, lastFactor);
            }
        }
        return factorDtos;
    }

    @Override
    public AllocationFactorDto findOne(Integer accountId, Integer effectId) {
        List<Object[]> factors = allocationFactorRepo.findOneCustom(accountId, effectId);
        AllocationFactorDto allocationFactorDto = null;

        if (!Checker.collectionIsEmpty(factors)) {

            allocationFactorDto = new AllocationFactorDto();
            Object[] row = factors.get(0);

            String accountCode = (String) row[2];
            String accountTitle = (String) row[3];
            Date rangeStart = (Date) row[6];
            Date rangeEnd = (Date) row[7];
            Date createdAt = (Date) row[8];
            Date updateAt = (Date) row[9];
            Integer effectDateId = (Integer) row[10];

            AccountDto accountDto = new AccountDto();
            accountDto.setId(accountId);
            accountDto.setCode(accountCode);
            accountDto.setTitle(accountTitle);

            DateRange dateRange = new DateRange();
            dateRange.setId(effectDateId);
            dateRange.setStart(rangeStart);
            dateRange.setEnd(rangeEnd);

            allocationFactorDto.setAccount(accountDto);
            allocationFactorDto.setCreated(createdAt);
            allocationFactorDto.setLastUpdated(updateAt);
            allocationFactorDto.setEffectivity(dateRange);

            for(Object[] factorRow:factors) {

                Integer segmentId = (Integer) factorRow[4];
                String segmentDesc = (String) factorRow[5];
                BigDecimal percentage = (BigDecimal) factorRow[11];

                List<Map> lastFactorSegmentPercentage = allocationFactorDto.getSegmentPercentage();
                Map segmentPercentageMap = new HashMap();
                segmentPercentageMap.put("id", segmentId);
                segmentPercentageMap.put("description", segmentDesc);
                segmentPercentageMap.put("value", percentage);
                lastFactorSegmentPercentage.add(segmentPercentageMap);

                allocationFactorDto.setSegmentPercentage(lastFactorSegmentPercentage);
            }
        }
        return allocationFactorDto;
    }

    @Override
    public List<Map> findLatestOne(Integer accountId) {
        List<Object[]> factors = allocationFactorRepo.findLatestAccountAllocatedSegments(accountId);
        List<Map> maps = new ArrayList<>();

        if (!Checker.collectionIsEmpty(factors)) {
            for(Object[] factorRow:factors) {

                Integer segmentAccountId = (Integer) factorRow[0];
                Integer segmentId = (Integer) factorRow[1];
                String segmentDesc = (String) factorRow[2];
                BigDecimal percentage = (BigDecimal) factorRow[3];

                Map segmentPercentageMap = new HashMap();
                segmentPercentageMap.put("segmentAccountId", segmentAccountId);
                segmentPercentageMap.put("segmentId", segmentId);
                segmentPercentageMap.put("segmentDescription", segmentDesc);
                segmentPercentageMap.put("percentage", percentage);

                maps.add(segmentPercentageMap);
            }
        }
        return maps;
    }
}

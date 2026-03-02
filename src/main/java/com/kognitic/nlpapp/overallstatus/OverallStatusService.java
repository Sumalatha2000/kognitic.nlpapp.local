package com.kognitic.nlpapp.overallstatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OverallStatusService {

	@Autowired
    private OverallStatusRepository overallStatusRepository;

    @Transactional
    public void saveAll(List<OverallStatus> overallStatusList) {

        overallStatusRepository.saveAll(overallStatusList);

    }

    @Transactional
    public void deleteByNctId(String nctId) {

        overallStatusRepository.deleteByNctId(nctId);

    }


}
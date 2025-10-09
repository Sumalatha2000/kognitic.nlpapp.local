package com.kognitic.nlpapp.trialslog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrialsLogService {

	@Autowired
	private TrialsLogRepository logRepository;

	public TrialsInProcess save(TrialsInProcess entity) {
		return logRepository.save(entity);
	}

	public Iterable<TrialsInProcess> saveAll(List<TrialsInProcess> list) {
		return logRepository.saveAll(list);
	}

	public List<TrialsInProcess> findAllByNctId(String nctId) {
		return logRepository.findAllByNctId(nctId);
	}

	public void softDelete(String nctId) {
		List<TrialsInProcess> data = findAllByNctId(nctId);
		data.forEach(x -> x.setDeleted(true));
		saveAll(data);
	}

	public void trialogging(String nctId, boolean flag) {
		TrialsInProcess inProcess = new TrialsInProcess();
		inProcess.setNctId(nctId);
		inProcess.setSuccess(flag);
		softDelete(nctId);
		save(inProcess);
	}
}

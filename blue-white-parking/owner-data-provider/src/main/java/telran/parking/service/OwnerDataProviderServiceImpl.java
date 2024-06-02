package telran.parking.service;
import telran.parking.repo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.dto.OwnerDto;
import telran.parking.exceptions.OwnerNotFoundException;
import telran.parking.model.OwnerData;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly=true)
public class OwnerDataProviderServiceImpl implements OwnerDataProviderService {
	@Autowired
	OwnerDataRepo ownerDataRepo;

	@Override
	public OwnerDto getOwnerData(long ownerId) {
		OwnerData ownerData =ownerDataRepo.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException());
		return ownerData.buid();
	
		
	}
	

}

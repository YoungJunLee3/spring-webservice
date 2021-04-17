package com.june.webservice.domain.key;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface KeyRepository extends JpaRepository<Key, String>{
	
}
package com.june.webservice.domain.register;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface RegisterRepository extends JpaRepository<Register, String>{
	
}
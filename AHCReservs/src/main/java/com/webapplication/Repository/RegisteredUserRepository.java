package com.webapplication.Repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.RegisteredUser;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, String>{
	
	public List<RegisteredUser> findAll();
	
	public RegisteredUser findByEmailIdIgnoreCase(String emailid);
	
	public RegisteredUser findByUsername(String username);
	
	@Query("select user from RegisteredUser as user where user.id = ?1")
	public Optional<RegisteredUser> findOne(Long id);
	
	@Transactional
	public void deleteByUsername(String username);
	
}

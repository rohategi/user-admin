package com.springboot.model;


import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
	public void setUsername(String username2) {
		// TODO Auto-generated method stub
		
	}
	public void setPassword(String encryptedPassword) {
		// TODO Auto-generated method stub
		
	}
   
  
}

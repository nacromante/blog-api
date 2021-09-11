package com.framework.blogapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "tb_role")
public class Role /* implements GrantedAuthority*/{
	
	private static final long serialVersionUID = 1L;

//	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;

//	@Override
	public String getAuthority() {
		return this.name;
	}

}
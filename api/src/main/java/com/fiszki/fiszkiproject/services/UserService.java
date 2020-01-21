package com.fiszki.fiszkiproject.services;

import java.util.List;

import com.fiszki.fiszkiproject.dtos.UserBasicInfoDto;
import com.fiszki.fiszkiproject.dtos.UserNameChangeDto;
import com.fiszki.fiszkiproject.dtos.UserPasswordChangeDto;
import com.fiszki.fiszkiproject.exceptions.common.BusinessException;

public interface UserService {
	
	List<UserBasicInfoDto> getAllUsers();
	
	UserBasicInfoDto getUserById(Long id);
	/**
	 * Allows User to change their display name.
	 * Accepts dto that contains only id and new display name.
	 * Validates the new display name and throws UserValidatorException
	 * 
	 * @param dto - UserNameChangeDto that contains id and new display name
	 * @return true if success or false if user with given id could not be found
	 * @throws ValidatorException when new display name does not fulfill validity conditions
	 */
	boolean changeDisplayName(UserNameChangeDto dto) throws BusinessException;
	
	/**
	 * Allows User to change their password.
	 * Accepts dto that contains only id, old password and new password.
	 * Checks the old password and throws AuthValidatorException
	 * Validates the new password and throws UserValidatorException
	 * 
	 * @param dto - UserPasswordChangeDto that contains id, old and new password
	 * @return true if success or false if user with given id could not be found
	 * @throws ValidatorException
	 */
	boolean changePassword(UserPasswordChangeDto dto) throws BusinessException;

}

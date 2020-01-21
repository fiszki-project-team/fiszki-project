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
	 * @return true if success
	 * @throws UserValidatorException when new display name does not fulfill validity conditions
	 * @throws InvalidIdException when invalid user id
	 */
	boolean changeDisplayName(UserNameChangeDto dto) throws BusinessException;
	
	/**
	 * Allows User to change their password.
	 * Accepts dto that contains only id, old password and new password.
	 * Checks if user with given id exists and throws InvalidIdException.
	 * Checks the old password and throws AuthValidatorException
	 * Validates the new password and throws UserValidatorException
	 * 
	 * @param dto - UserPasswordChangeDto that contains id, old and new password
	 * @return true if success
	 * @throws AuthValidatorException when user is not authenticated
	 * @throws UserValidatorException when new password not valid
	 * @throws InvalidIdException when invalid user id
	 */
	boolean changePassword(UserPasswordChangeDto dto) throws BusinessException;

}

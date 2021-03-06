package util;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import logic.User;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		User user = (User)target;
		String group = "error.required";
		if(user.getUserid() == null || user.getUserid().length() == 0) {
			errors.rejectValue("userid", group);
		}
		if(user.getPassword() == null || user.getPassword().length() == 0) {
			errors.rejectValue("password", group);
		}
		if(user.getUsername() == null || user.getUsername().length() == 0) {
			errors.rejectValue("username", group);
		}
		if(user.getPostcode() == null || user.getPostcode().length() == 0) {
			errors.rejectValue("postcode", group);
		}
		if(user.getPhoneno() == null || user.getPhoneno().length() == 0) {
			errors.rejectValue("phoneno", group);
		}
		if(user.getAddress() == null || user.getAddress().length() == 0) {
			errors.rejectValue("address", group);
		}
		if(user.getEmail() == null || user.getEmail().length() == 0) {
			errors.rejectValue("email", group);
		}
		if(errors.hasErrors()) {
			errors.reject("error.input.user");
		}
	}

}

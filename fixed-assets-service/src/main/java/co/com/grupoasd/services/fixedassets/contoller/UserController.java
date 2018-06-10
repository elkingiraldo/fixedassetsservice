package co.com.grupoasd.services.fixedassets.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.grupoasd.services.fixedassets.dtos.UserDTO;
import co.com.grupoasd.services.fixedassets.exception.FixedAssetsServiceException;
import co.com.grupoasd.services.fixedassets.service.UserService;

@RestController
@RequestMapping("/fixedassets/user/v1.0")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<UserDTO> post(@RequestBody UserDTO user,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		UserDTO newUser = userService.create(user);

		return new ResponseEntity<UserDTO>(newUser, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> get(@RequestHeader(value = "locale", required = false) String locale)
			throws FixedAssetsServiceException {

		List<UserDTO> userFound = userService.retrieveAllUsers();

		return new ResponseEntity<List<UserDTO>>(userFound, HttpStatus.OK);

	}

	@GetMapping("/{personalId}")
	public ResponseEntity<UserDTO> getByName(@PathVariable(value = "personalId") String personalId,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		UserDTO userFound = userService.retrieveByPersonalId(personalId);

		return new ResponseEntity<UserDTO>(userFound, HttpStatus.OK);

	}

	@PutMapping
	public ResponseEntity<UserDTO> put(@RequestBody UserDTO user,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {

		UserDTO newUser = userService.update(user);
		return new ResponseEntity<UserDTO>(newUser, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") String id,
			@RequestHeader(value = "locale", required = false) String locale) throws FixedAssetsServiceException {
		userService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}

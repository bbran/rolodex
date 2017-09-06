package com.libertymutual.goforcode.rolodex.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such entity is present")  // 404
public class EntityNotFoundException extends Exception {

}

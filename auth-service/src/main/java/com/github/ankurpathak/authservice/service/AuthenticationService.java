package com.github.ankurpathak.authservice.service;

import com.github.ankurpathak.authservice.dto.AuthenticationRequest;
import com.github.ankurpathak.authservice.dto.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
}

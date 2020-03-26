package com.cio.gidservice.auth;

import com.cio.gidservice.models.User;

public interface IAuthenticator {

     Response sendAuthRequest(User user);
     Response getSMSCode();

}

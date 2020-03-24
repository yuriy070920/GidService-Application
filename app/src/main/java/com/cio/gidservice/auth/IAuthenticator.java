package com.cio.gidservice.auth;

public interface IAuthenticator {

     Response sendAuthRequest(User user);
     Response getSMSCode();

}

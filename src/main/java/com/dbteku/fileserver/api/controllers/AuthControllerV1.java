package com.dbteku.fileserver.api.controllers;

import static spark.Spark.get;
import static spark.Spark.post;

import com.dbteku.fileserver.api.services.IAuthServiceV1;
import com.dbteku.fileserver.models.NewUser;
import com.dbteku.fileserver.responses.HttpResponse;
import com.google.gson.Gson;

import spark.Request;
import spark.Response;

public class AuthControllerV1 implements IAuthControllerV1{

	private static final String AUTH_HEADER = "authentication";
	private IAuthServiceV1 service;
	private Gson gson;

	public AuthControllerV1(IAuthServiceV1 service) {
		this.service = service;
		gson = new Gson();
	}

	@Override
	public void start() {
		get("/v1/auth/loggedIn", (req, res)->{
			return isLoggedIn(req, res);
			//			HttpResponse response = new OkResponse();
			//			setJsonHeaders(res);
			//			String id = req.headers(AUTH_HEADER);
			//			if(isLoggedIn(id)) {
			//				response = new LoggedInResponse(sessions.get(id));
			//			}else {
			//				response = new UnAuthorizedResponse();
			//				res.status(401);
			//			}
			//			return gson.toJson(response);
		});

		//		post("/v1/auth/user", (req, res)->{
		//			HttpResponse response = new OkResponse();
		//			setJsonHeaders(res);
		//			String id = req.headers(AUTH_HEADER);
		//			if(isLoggedIn(id)) {
		//				NewUser user = gson.fromJson(req.body(), NewUser.class);
		//				if(user == null) {
		//					response = new InvalidJsonResponse();
		//					res.status(400);
		//				}else {
		//					SaltGenerator generator = new SaltGenerator();
		//					String salt = generator.generate();
		//					String saltedPassword = user.getPassword() + salt;
		//					Sha256Encryption encryption = new Sha256Encryption();
		//					String encrypedPassword = encryption.encrypt(saltedPassword);
		//					ProtectedUser protectedUser = new ProtectedUser(user.getUsername(), encrypedPassword, salt);
		//					if(clients.has(protectedUser.getUsername())) {
		//						response = new AlreadyExistsResponse();
		//						res.status(400);
		//					}else {
		//						clients.put(user.getUsername(), protectedUser);	
		//					}
		//				}
		//			}else {
		//				response = new UnAuthorizedResponse();
		//				res.status(401);
		//			}
		//			return gson.toJson(response);
		//		});

//		delete("/v1/auth/:user", (req, res)->{
			//			HttpResponse response = new OkResponse();
			//			setJsonHeaders(res);
			//			String id = req.headers(AUTH_HEADER);
			//			if(isLoggedIn(id)) {
			//				String username = req.params(":user");
			//				if(clients.has(username)) {
			//					ClientSession session = sessions.get(id);
			//					if(session.getUsername().equals(username)) {
			//						response = new CannotDeleteYourselfResponse();
			//						res.status(400);
			//					}else {
			//						clients.delete(username);	
			//					}
			//				}else {
			//					response = new NotFoundResponse();
			//					res.status(400);
			//				}
			//			}else {
			//				response = new UnAuthorizedResponse();
			//				res.status(401);
			//			}
			//			return gson.toJson(response);
//		});

		post("/v1/auth/login", (req, res)->{
			return gson.toJson(login(req, res));
					//			HttpResponse response = new OkResponse();
					//			setJsonHeaders(res);
					//			NewUser user = gson.fromJson(req.body(), NewUser.class);
					//			if(user == null) {
					//				response = new InvalidJsonResponse();
					//				res.status(400);
					//			}else {
					//				ProtectedUser protectedUser = clients.get(user.getUsername());
					//				if(protectedUser.isNull()) {
					//					response = new InvalidUsernameOrPassword();
					//				}else {
					//					String potentialSaltedPassword = user.getPassword() + protectedUser.getSaltCode();
					//					Sha256Encryption encryption = new Sha256Encryption();
					//					String potentialPassword = encryption.encrypt(potentialSaltedPassword);
					//					if(potentialPassword.equals(protectedUser.getHashedPassword())) {
					//						String sessionId = UUID.randomUUID().toString();
					//						while(sessions.has(sessionId)) {
					//							sessionId = UUID.randomUUID().toString();
					//						}
					//						long now = System.currentTimeMillis();
					//						long expire = now + (ONE_SECOND_IN_MILLIS * 60 * 30);
					//						ClientSession session = new ClientSession(sessionId, protectedUser.getUsername(), expire);
					//						sessions.put(session.getId(), session);
					//						response = new LoggedInResponse(session);
					//					}else {
					//						response = new InvalidUsernameOrPassword();
					//					}
					//				}
					//			}
					//
					//			return gson.toJson(response);
		});

		post("/v1/auth/logout", (req, res)->{
			return gson.toJson(logout(req, res));
		});
	}
	@Override
	public HttpResponse login(Request req, Response res) {
		return service.login(gson.fromJson(req.body(), NewUser.class));
	}
	@Override
	public HttpResponse logout(Request req, Response res) {
		return service.logout(req.headers(AUTH_HEADER));
	}
	@Override
	public HttpResponse isLoggedIn(Request req, Response res) {
		return service.isLoggedIn(req.headers(AUTH_HEADER));
	}


}

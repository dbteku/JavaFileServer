package com.dbteku.fileserver.api;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.UUID;

import com.dbteku.fileserver.directories.ClientDirectory;
import com.dbteku.fileserver.directories.ClientSessionDirectory;
import com.dbteku.fileserver.interfaces.IService;
import com.dbteku.fileserver.models.ClientSession;
import com.dbteku.fileserver.models.NewUser;
import com.dbteku.fileserver.models.ProtectedUser;
import com.dbteku.fileserver.responses.AlreadyExistsResponse;
import com.dbteku.fileserver.responses.CannotDeleteYourselfResponse;
import com.dbteku.fileserver.responses.DoesntExistResponse;
import com.dbteku.fileserver.responses.HttpResponse;
import com.dbteku.fileserver.responses.InvalidJsonResponse;
import com.dbteku.fileserver.responses.InvalidUsernameOrPassword;
import com.dbteku.fileserver.responses.LoggedInResponse;
import com.dbteku.fileserver.responses.OkResponse;
import com.dbteku.fileserver.responses.UnAuthorizedResponse;
import com.dbteku.fileserver.tools.SaltGenerator;
import com.dbteku.fileserver.tools.Sha256Encryption;
import com.google.gson.Gson;

import spark.Response;

public class ApiV1 implements IService{

	private static final String CONTENT_TYPE = "Content-Type";
	private static final String APPLICATION_JSON = "application/json";
	private static final String AUTH_HEADER = "authentication";
	private static final long ONE_SECOND_IN_MILLIS = 1000;
	private ClientSessionDirectory sessions;
	private ClientDirectory clients;
	private Gson gson;

	public ApiV1() {
		this.sessions = ClientSessionDirectory.getInstance();
		this.clients = ClientDirectory.getInstance();
		this.gson = new Gson();
	}

	@Override
	public void start() {

		options("/*", (request, response) -> {

			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}

			return "OK";
		});

		before((request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Request-Method", "GET, POST, DELETE, PUT, PATCH, OPTIONS");
			response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
			// Note: this may or may not be necessary in your particular application
			response.type("application/json");
		});

		get("/v1/auth/loggedIn", (req, res)->{
			HttpResponse response = new OkResponse();
			setJsonHeaders(res);
			String id = req.headers(AUTH_HEADER);
			System.out.println(sessions.has(id));
			if(isLoggedIn(id)) {
				response = new LoggedInResponse(sessions.get(id));
			}else {
				response = new UnAuthorizedResponse();
				res.status(401);
			}
			return gson.toJson(response);
		});

		post("/v1/auth/user", (req, res)->{
			HttpResponse response = new OkResponse();
			setJsonHeaders(res);
			String id = req.headers(AUTH_HEADER);
			if(isLoggedIn(id)) {
				NewUser user = gson.fromJson(req.body(), NewUser.class);
				if(user == null) {
					response = new InvalidJsonResponse();
					res.status(400);
				}else {
					SaltGenerator generator = new SaltGenerator();
					String salt = generator.generate();
					String saltedPassword = user.getPassword() + salt;
					Sha256Encryption encryption = new Sha256Encryption();
					String encrypedPassword = encryption.encrypt(saltedPassword);
					ProtectedUser protectedUser = new ProtectedUser(user.getUsername(), encrypedPassword, salt);
					if(clients.has(protectedUser.getUsername())) {
						response = new AlreadyExistsResponse();
						res.status(400);
					}else {
						clients.put(user.getUsername(), protectedUser);	
					}
				}
			}else {
				response = new UnAuthorizedResponse();
				res.status(401);
			}
			return gson.toJson(response);
		});

		delete("/v1/auth/user/:user", (req, res)->{
			HttpResponse response = new OkResponse();
			setJsonHeaders(res);
			String id = req.headers(AUTH_HEADER);
			if(isLoggedIn(id)) {
				String username = req.params(":user");
				if(clients.has(username)) {
					ClientSession session = sessions.get(id);
					if(session.getUsername().equals(username)) {
						response = new CannotDeleteYourselfResponse();
						res.status(400);
					}else {
						clients.delete(username);	
					}
				}else {
					response = new DoesntExistResponse();
					res.status(400);
				}
			}else {
				response = new UnAuthorizedResponse();
				res.status(401);
			}
			return gson.toJson(response);
		});

		post("/v1/auth/login", (req, res)->{
			HttpResponse response = new OkResponse();
			setJsonHeaders(res);
			NewUser user = gson.fromJson(req.body(), NewUser.class);
			if(user == null) {
				response = new InvalidJsonResponse();
				res.status(400);
			}else {
				ProtectedUser protectedUser = clients.get(user.getUsername());
				if(protectedUser.isNull()) {
					response = new InvalidUsernameOrPassword();
				}else {
					String potentialSaltedPassword = user.getPassword() + protectedUser.getSaltCode();
					Sha256Encryption encryption = new Sha256Encryption();
					String potentialPassword = encryption.encrypt(potentialSaltedPassword);
					if(potentialPassword.equals(protectedUser.getHashedPassword())) {
						String sessionId = UUID.randomUUID().toString();
						while(sessions.has(sessionId)) {
							sessionId = UUID.randomUUID().toString();
						}
						long now = System.currentTimeMillis();
						long expire = now + (ONE_SECOND_IN_MILLIS * 60 * 30);
						ClientSession session = new ClientSession(sessionId, protectedUser.getUsername(), expire);
						sessions.put(session.getId(), session);
						response = new LoggedInResponse(session);
					}else {
						response = new InvalidUsernameOrPassword();
					}
				}
			}

			return gson.toJson(response);
		});

		post("/v1/auth/logout", (req, res)->{
			HttpResponse response = new OkResponse();
			setJsonHeaders(res);
			String id = req.headers(AUTH_HEADER);
			if(isLoggedIn(id)) {
				sessions.delete(id);
			}else {
				response = new UnAuthorizedResponse();
				res.status(401);
			}
			return gson.toJson(response);
		});

		get("/v1/files", (req, res)->{
			return null;
		});

		get("/v1/files/:file", (req, res)->{
			return null;
		});

		put("/v1/files/:file", (req, res)->{
			return null;
		});

		delete("/v1/files/:file", (req, res)->{
			return null;
		});

	}

	private void renewSession(String id) {
		ClientSession session = sessions.get(id);
		if(session.isNull()) {
		}else {
			long now = System.currentTimeMillis();
			long expire = now + (ONE_SECOND_IN_MILLIS * 60 * 30);
			session.setExpireTime(expire);
		}
	}

	private boolean isLoggedIn(String id) {
		boolean loggedIn = false;

		if(sessions.has(id)) {
			ClientSession session = sessions.get(id);
			long now = System.currentTimeMillis();
			if(session.getExpireTime() - now > 0) {
				renewSession(id);
				loggedIn = true;
			}else {
				sessions.delete(id);
			}
		}

		return loggedIn;
	}

	private void setJsonHeaders(Response res) {
		res.header(CONTENT_TYPE, APPLICATION_JSON);
	}

	@Override
	public void shutdown() {

	}

	@Override
	public boolean isRunning() {
		return true;
	}

}

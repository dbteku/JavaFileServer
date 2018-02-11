package com.dbteku.fileserver.api;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

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
import com.dbteku.fileserver.responses.OkResponse;
import com.dbteku.fileserver.responses.UnAuthorizedResponse;
import com.dbteku.fileserver.tools.SaltGenerator;
import com.dbteku.fileserver.tools.Sha256Encryption;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.Response;

public class ApiV1 implements IService{

	private static final String CONTENT_TYPE = "Content-Type";
	private static final String APPLICATION_JSON = "application/json";
	private static final String AUTH_HEADER = "authenciation";
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
		
		post("/v1/auth/user", (req, res)->{
			HttpResponse response = new OkResponse();
			setJsonHeaders(res);
			String header = req.headers(AUTH_HEADER);
			JsonObject obj = gson.fromJson(header, JsonObject.class);
			if(obj.has("id")) {
				String id = obj.get("id").getAsString();
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
			}else {
				response = new UnAuthorizedResponse();
				res.status(401);
			}
			return gson.toJson(response);
		});
		
		delete("/v1/auth/user/:user", (req, res)->{
			HttpResponse response = new OkResponse();
			setJsonHeaders(res);
			String header = req.headers(AUTH_HEADER);
			JsonObject obj = gson.fromJson(header, JsonObject.class);
			if(obj.has("id")) {
				String id = obj.get("id").getAsString();
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
			}else {
				response = new UnAuthorizedResponse();
				res.status(401);
			}
			return gson.toJson(response);
		});
		
		post("/v1/auth/login", (req, res)->{
			return null;
		});
		
		post("/v1/auth/logout", (req, res)->{
			setJsonHeaders(res);
			return null;
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
	
	private boolean isLoggedIn(String id) {
		return sessions.has(id);
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

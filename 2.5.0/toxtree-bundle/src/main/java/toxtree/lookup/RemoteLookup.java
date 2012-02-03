package toxtree.lookup;

import java.io.InputStream;
import java.util.Hashtable;

import org.opentox.aa.IOpenToxUser;
import org.opentox.aa.OpenToxUser;
import org.opentox.aa.cli.InvalidCredentials;
import org.opentox.aa.opensso.AAServicesConfig;
import org.opentox.aa.opensso.OpenSSOToken;
import org.opentox.rest.HTTPClient;

public abstract class RemoteLookup<RESULT> {
	//protected static final String defaultLookup = "http://apps.ideaconsult.net:8080/ambit2/query/compound/search/all?search=%s&max=1&media=chemical%%2Fx-mdl-sdfile";;
	protected static String lookupService = null;
	protected String authService;
	protected String policyService;
	protected OpenSSOToken ssotoken;
	protected IOpenToxUser user;
	
	
	public RemoteLookup() throws Exception {
		super();
		user = new OpenToxUser();
		authService = AAServicesConfig.getSingleton().getOpenSSOService();
		policyService = AAServicesConfig.getSingleton().getPolicyService();

		


	}
	protected String init() {
		HTTPClient cli = null;
		try {
			cli = new HTTPClient("http://toxtree.sf.net/lookup.txt");
			cli.get();
			return cli.getText();
		} catch (Exception x) {
			try { cli.release();} catch (Exception xx) {}
			return null;
		}
	}
	public void login() throws Exception {
		login(AAServicesConfig.getSingleton().getTestUser(),AAServicesConfig.getSingleton().getTestUserPass());
	}
	public void login(String uname,String pass) throws Exception {
		user.setUserName(uname);
		user.setPassword(pass);		
		Hashtable<String, String> results = new Hashtable<String, String>();
		
		if (ssotoken==null) ssotoken = new OpenSSOToken(authService);
		if (ssotoken.getToken()==null) {
			ssotoken.login(user);
			if (ssotoken.getToken()==null) {
				throw new InvalidCredentials(authService);
			} //else
			//System.out.println(String.format("Logged as %s token %s",user.getUsername(),ssotoken.getToken()));
		} else {
			//System.out.println(String.format("Using provided token %s",ssotoken.getToken()));
			if (!ssotoken.isTokenValid()) {
				throw new InvalidCredentials("Invalid token submited to ");
			}
		}
//		ssotoken.getAttributes(new String[] {"uid"},results);
		
	}
	public void logout() throws Exception {

		if ((ssotoken!=null) && (ssotoken.getToken()!=null)) {
			//System.out.println("Invalidating the token ...");
			ssotoken.logout();
			//System.out.println("Logout completed. The token is no longer valid.");
		}
		
	}
	public abstract RESULT processStream(String uri,String input,InputStream in) throws Exception;
	
	public RESULT lookup(String input) throws Exception {
		
		if (lookupService==null)
		synchronized (this) {
			
			String uri = init();
			if ((uri!=null) && !"".equals(uri.trim())) lookupService = uri;
			//else lookupService = defaultLookup;
		}
		
		HTTPClient cli = null;
		InputStream in = null;
		try {
			login();
			cli = new HTTPClient(String.format(lookupService,input));
			cli.get();
			if (cli.getStatus()==200) {
				in = cli.getInputStream();
				return processStream(lookupService,input,in);
				
			}
			return null;
		} catch (Exception x) {
			return null;
		} finally {
			try { if (in != null) in.close();} catch (Exception x) {}
			try { if (cli != null) cli.release();} catch (Exception x) {}
			try { logout();} catch (Exception x) {}
		}
	}	
}

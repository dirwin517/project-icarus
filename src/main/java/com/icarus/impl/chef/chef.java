package com.icarus.impl.chef;
import static org.jclouds.compute.options.TemplateOptions.Builder.runScript;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.jclouds.ContextBuilder;
import org.jclouds.chef.ChefContext;
import org.jclouds.chef.config.ChefProperties;
import org.jclouds.chef.domain.BootstrapConfig;
import org.jclouds.chef.util.RunListBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunScriptOnNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.JsonBall;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.scriptbuilder.domain.Statement;
import org.jclouds.sshj.config.SshjSshClientModule;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;
import com.google.inject.Module;

import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import java.security.cert.X509Certificate;



import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.collect.Iterables.concat;
import static org.jclouds.compute.options.TemplateOptions.Builder.overrideLoginCredentials;
import static org.jclouds.compute.predicates.NodePredicates.inGroup;

import java.util.Map.Entry;
import org.jclouds.compute.domain.ExecResponse;
import org.jclouds.scriptbuilder.domain.OsFamily;
public class chef {

	public static void main(String args[]) throws Exception{
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] {
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}
					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}
				}
		};

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);


		String recipe = "apache2";
		//Get the credentials that will be used to authenticate to the Chef server

		String pemFile = "/Users/daniel.irwin/keys/daniel.irwin.pem";
		String validatorPem = "/Users/daniel.irwin/.chef/validator-devops.pem";

		String credential = Files.toString(new File(pemFile), Charsets.UTF_8);

		//Provide the validator information to let the nodes to auto-register themselves
		//in the Chef server during bootstrap

		String validatorCredential = Files.toString(new File(validatorPem), Charsets.UTF_8);

		//String args2[] = {"chef.devops.enernoc.net"};
		//InstallCert.main(args2);

		Properties chefConfig = new Properties();
		chefConfig.put(ChefProperties.CHEF_VALIDATOR_NAME, "chef-validator");
		chefConfig.put(ChefProperties.CHEF_VALIDATOR_CREDENTIAL, validatorCredential);

		//Create the connection to the Chef server
		ChefContext chefContext = ContextBuilder.newBuilder("chef")
				.endpoint("http://chef.devops.enernoc.net")
				.credentials("daniel_irwin", credential)
				.overrides(chefConfig)
				.buildView(ChefContext.class);

		//Check to see if the recipe you want exists
		List<String> runlist = new RunListBuilder().addRecipe(recipe).build();
		//Iterable< ? extends CookbookVersion> cookbookVersions = chefContext.getChefService().listCookbookVersions();
		//for(CookbookVersion cv : cookbookVersions){
		//	System.out.println(cv.getVersion());
		//}


		//Create the connection to the compute provider. Note that ssh will be used to bootstrap chef
		ComputeServiceContext computeContext = ContextBuilder.newBuilder("ec2")
				.endpoint("localhost")
				.credentials("l", "j")
				.modules(ImmutableSet.<Module> of(new SshjSshClientModule()))
				.buildView(ComputeServiceContext.class);

		//Group all nodes in both Chef and the compute provider by this group
		String group = "jclouds-chef-example";

		//Set the recipe to install and the configuration values to override
		JsonBall attributes = new JsonBall("{\"apache\": {\"listen_ports\": \"8080\"}}");

		//Update the chef service with the run list you wish to apply to all nodes in the group
		//and also provide the json configuration used to customize the desired values
		BootstrapConfig config = BootstrapConfig.builder().runList(runlist).attributes(attributes).build();
		chefContext.getChefService().updateBootstrapConfigForGroup(group, config);

		//Build the script that will bootstrap the node
		Statement bootstrap = chefContext.getChefService().createBootstrapScriptForGroup(group);

		//Run a node on the compute provider that bootstraps chef
		ComputeService computeSvc = computeContext.getComputeService();
		if(computeSvc != null){
			TemplateOptions template = runScript(bootstrap);
			if(template != null){
				//Set< ? extends NodeMetadata> nodes = computeSvc.createNodesInGroup(group, 1, template);

				//System.out.println(nodes.size());
				// Run the script in the nodes of the group
        LoginCredentials login = getLoginForCommandExecution();

				System.out.printf(">> installing [%s] on group %s as %s%n", recipe, group, login.identity);
				runScriptOnGroup(computeSvc, login, group, bootstrap);
			}
		}
		//Release resources
		chefContext.close();
		computeContext.close();

	}

	private static void runScriptOnGroup(final ComputeService compute,
			final LoginCredentials login, final String groupName, final Statement command)
					throws RunScriptOnNodesException {
		// when you run commands, you can pass options to decide whether
		// to run it as root, supply or own credentials vs from cache,
		// and wrap in an init script vs directly invoke
		System.out.println(command.render(OsFamily.UNIX));
		
		Map<? extends NodeMetadata, ExecResponse> execResponses =
				compute.runScriptOnNodesMatching(//
						inGroup(groupName), // predicate used to select nodes
						command, // what you actually intend to run
						overrideLoginCredentials(login)); // use the local user & ssh key

		for (Entry<? extends NodeMetadata, ExecResponse> response : execResponses.entrySet()) {
			System.out.printf(
					"<< node %s: %s%n",
					response.getKey().getId(),
					concat(response.getKey().getPrivateAddresses(), response.getKey()
							.getPublicAddresses())
					);
			System.out.printf("<<     %s%n", response.getValue());
		}
	}
	
  private static LoginCredentials getLoginForCommandExecution() {
    try {
        String user = System.getProperty("user.name");
        String privateKey =
                Files.toString(new File(System.getProperty("user.home") + "/keys/at-chef.pem"), UTF_8);
        return LoginCredentials.builder().user(user).privateKey(privateKey)
                .authenticateSudo(true).build();
    } catch (Exception e) {
        System.err.println("error reading ssh key " + e.getMessage());
        System.exit(1);
        return null;
    }
}
}

package com.mycat.server;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.SslSocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * web start
 * @author sw
 *
 */
public class WebServerStart {

	//private final static Logger log = Logger.getLogger(WebServerStart.class);

	private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

	@SuppressWarnings("unchecked")
	public static void startup(String args[]){
		try {
            Server server = new Server();
            System.err.println("mycat init ... ");

            int httpPort = 7066;
            int httpsPort = -1;
            String contextPath = "/";

            String keyStorePath = null;
            String keyStorePassword = null;

            String updateServer = null;

            boolean disableUpdateCenterSwitch = false;
            boolean skipInitSetup = false;

            for (int i = 0; i < args.length; ++i){
               String portStr;
               if (args[i].startsWith("--httpPort=")) {
                  portStr = args[i].substring("--httpPort=".length());
                  httpPort = Integer.parseInt(portStr);
               }

               if (args[i].startsWith("--httpsPort=")) {
                   portStr = args[i].substring("--httpsPort=".length());
                   httpsPort = Integer.parseInt(portStr);
               }

               if (args[i].startsWith("--httpsKeyStore=")) {
                   keyStorePath = args[i].substring("--httpsKeyStore=".length());
               }

               if (args[i].startsWith("--httpsKeyStorePassword=")) {
                 keyStorePassword = args[i].substring("--httpsKeyStorePassword=".length());
               }

               if (args[i].startsWith("--prefix=")) {
                  String prefix = args[i].substring("--prefix=".length());
                  if (prefix.startsWith("/"))
                     contextPath = prefix;
                  else {
                     contextPath = "/" + prefix;
                  }
               }
               if (args[i].startsWith("--updateServer=")) {
                  updateServer = args[i].substring("--updateServer=".length());
               }

               if (args[i].startsWith("--disableUpdateCenterSwitch")) {
                  disableUpdateCenterSwitch = true;
               }

               if (args[i].startsWith("--skipInitSetup")) {
                  skipInitSetup = true;
               }
           }

           List connectors = new ArrayList();
           if (httpPort != -1) {
               SelectChannelConnector httpConnector = new SelectChannelConnector();
               httpConnector.setPort(httpPort);
               connectors.add(httpConnector);
           }

           if (httpsPort != -1) {
             SslSocketConnector httpsConnector = new SslSocketConnector();
             httpsConnector.setPort(httpsPort);
             if (keyStorePath != null) {
               httpsConnector.setKeystore(keyStorePath);
             }
             if (keyStorePassword != null) {
               httpsConnector.setKeyPassword(keyStorePassword);
             }
             connectors.add(httpsConnector);
            }

            server.setConnectors((Connector[])connectors.toArray(new Connector[connectors.size()]));

            ProtectionDomain protectionDomain = Executable.class.getProtectionDomain();
            URL location = protectionDomain.getCodeSource().getLocation();
            String url = java.net.URLDecoder.decode(location.getPath() , "utf-8");
            System.out.println(url);

            File tempDir = new File(getHomeDir(), "war");
            tempDir.mkdirs();

            WebAppContext webapp = new WebAppContext();
            webapp.setContextPath("/mycat" );//
            webapp.setDescriptor(url+"/WEB-INF/web.xml");
            webapp.setResourceBase(url);
            webapp.setTempDirectory(tempDir);

            server.setHandler(webapp);

            System.err.println("mycat start success,port: "+httpsPort);
            server.start();
            server.join();

        } catch (Exception e) {
            System.exit(-1);
        }
	}

	@SuppressWarnings("unchecked")
	public static void startupDev(String args[]){
		try {
            Server server = new Server();
            System.err.println("mycat init ... ");

            int httpPort = 7066;
            int httpsPort = -1;
            String contextPath = "/";

            String keyStorePath = null;
            String keyStorePassword = null;

            String updateServer = null;

            boolean disableUpdateCenterSwitch = false;
            boolean skipInitSetup = false;

            for (int i = 0; i < args.length; ++i){
               String portStr;
               if (args[i].startsWith("--httpPort=")) {
                  portStr = args[i].substring("--httpPort=".length());
                  httpPort = Integer.parseInt(portStr);
               }

               if (args[i].startsWith("--httpsPort=")) {
                   portStr = args[i].substring("--httpsPort=".length());
                   httpsPort = Integer.parseInt(portStr);
               }

               if (args[i].startsWith("--httpsKeyStore=")) {
                   keyStorePath = args[i].substring("--httpsKeyStore=".length());
               }

               if (args[i].startsWith("--httpsKeyStorePassword=")) {
                 keyStorePassword = args[i].substring("--httpsKeyStorePassword=".length());
               }

               if (args[i].startsWith("--prefix=")) {
                  String prefix = args[i].substring("--prefix=".length());
                  if (prefix.startsWith("/"))
                     contextPath = prefix;
                  else {
                     contextPath = "/" + prefix;
                  }
               }
               if (args[i].startsWith("--updateServer=")) {
                  updateServer = args[i].substring("--updateServer=".length());
               }

               if (args[i].startsWith("--disableUpdateCenterSwitch")) {
                  disableUpdateCenterSwitch = true;
               }

               if (args[i].startsWith("--skipInitSetup")) {
                  skipInitSetup = true;
               }
           }

           List connectors = new ArrayList();
           if (httpPort != -1) {
               SelectChannelConnector httpConnector = new SelectChannelConnector();
               httpConnector.setPort(httpPort);
               connectors.add(httpConnector);
           }

           if (httpsPort != -1) {
             SslSocketConnector httpsConnector = new SslSocketConnector();
             httpsConnector.setPort(httpsPort);
             if (keyStorePath != null) {
               httpsConnector.setKeystore(keyStorePath);
             }
             if (keyStorePassword != null) {
               httpsConnector.setKeyPassword(keyStorePassword);
             }
             connectors.add(httpsConnector);
            }

            server.setConnectors((Connector[])connectors.toArray(new Connector[connectors.size()]));

            ProtectionDomain protectionDomain = Executable.class.getProtectionDomain();
            URL location = protectionDomain.getCodeSource().getLocation();
            String url = java.net.URLDecoder.decode(location.getPath() , "utf-8");
            System.out.println(url);

            File tempDir = new File(getHomeDir(), "war");
            tempDir.mkdirs();

            WebAppContext webapp = new WebAppContext();
            webapp.setContextPath("/mycat" );//
            webapp.setDescriptor(url+"../../src/main/webapp/WEB-INF/web.xml");
            webapp.setResourceBase(url+"../../src/main/webapp");
            webapp.setTempDirectory(tempDir);

            server.setHandler(webapp);

            System.err.println("mycat start success,port: "+httpsPort);
            server.start();
            server.join();

        } catch (Exception e) {
            System.exit(-1);
        }
	}
	private static File getHomeDir(){
	    String home = System.getProperty("MYCAT_HOME");
	    if(home != null) {
	        return new File(home.trim());
	    }

	    try{
	      String MYCATHomeEnv = System.getenv("MYCAT_HOME");
	      if (MYCATHomeEnv != null) {
	         return new File(MYCATHomeEnv.trim()).getAbsoluteFile();
	      }

	    }
	    catch (Throwable e) { }

	    return new File(new File(System.getProperty("user.home")), ".MYCAT");
	}

	public static void main(String[] args) {
		startupDev(args);
	}
}

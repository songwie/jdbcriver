package com.mycat.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Executable{
  private List<String> arguments;

  public static void main(String[] args) throws Exception{
      String javaVersion = System.getProperty("java.version");
      StringTokenizer tokens = new StringTokenizer(javaVersion, ".-_");

      int majorVersion = Integer.parseInt(tokens.nextToken());
      int minorVersion = Integer.parseInt(tokens.nextToken());

      if ((majorVersion < 2) && (minorVersion < 6)) {
	      System.err.println("mycat requires Java 7 or later.");
	      System.err.println("Your java version is " + javaVersion);
	      System.err.println("Java Home:  " + System.getProperty("java.home"));
	      System.exit(0);
	  }

      Executable executable = new Executable();
      executable.parseArguments(args);
      executable.launchJetty();
  }

  private void parseArguments(String[] args) throws IOException {
    this.arguments = Arrays.asList(args);

    for (String arg : this.arguments){
    	if (arg.startsWith("--version")) {
            System.exit(0);
        } else {
            if (arg.startsWith("--usage")) {
              //Êä³öÃüÁî²ÎÊý
              return;
            }
            if (arg.startsWith("--logfile=")) {
              String logFile = arg.substring("--logfile=".length());
              System.out.println("Logging information is send to file " + logFile);

              FileOutputStream fos = new FileOutputStream(new File(logFile));
              PrintStream ps = new PrintStream(fos);
              System.setOut(ps);
              System.setErr(ps);

              return;
            }
          }
    }
  }

  @SuppressWarnings("unchecked")
  private void launchJetty() throws Exception {
	  ProtectionDomain protectionDomain = Executable.class.getProtectionDomain();
      URL location = protectionDomain.getCodeSource().getLocation();
      String url = java.net.URLDecoder.decode(location.getPath() , "utf-8");
      System.out.println(url);

      URL []urls = {location};

      ClassLoader urlClassLoader = new URLClassLoader(urls);
      Thread.currentThread().setContextClassLoader(urlClassLoader);

      Class jettyUtil = urlClassLoader.loadClass("com.mycat.server.WebServerStart");
      Method mainMethod = jettyUtil.getMethod("startup", new Class[] { String[].class  });
      mainMethod.invoke(null, new Object[] { this.arguments.toArray(new String[this.arguments.size()]) });
  }



}
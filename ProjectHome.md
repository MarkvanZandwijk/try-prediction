<font size='+2'>

<h1>Try Prediction</h1>

<font size='+4'>
NOTE: This project is deprecated. The current version has moved to this location:<br>
<br>
<a href='https://github.com/GoogleCloudPlatform/prediction-try-java-python'>https://github.com/GoogleCloudPlatform/prediction-try-java-python</a>
</font>


This project provides a complete application illustrating use of the <a href='http://developers.google.com/prediction'>Google Prediction API</a> within the Google App Engine environment. Sample code is provided for both the Java and Python App Engine runtimes, along with resources for CSS, Javascript, images and config data files, all of which are shared across the two runtime environments.<br>
<br>
The application presents a simple interactive user experience: select a prediction model, enter a corresponding set of input text and submit your prediction request. For classification models, a graphical response is provided showing the confidence level for each category in the selected model. For regression models, a numerical result is presented.<br>
<br>
The set of models supported and the corresponding input fields are entirely dynamic and controlled by a runtime text file (rc/models.json). You can freely add, change or remove models without changing any source code.<br>
<br>
Web services in this domain typically provide access to a prediction model via a common set of shared security credentials. In this model, there is no need to force end users to perform the OAuth token granting sequence - authorization of end users is entirely up to the discretion of the application provider. This shared-server authorization model is one of the key elements being illustrated in this sample application.<br>
<br>
You can try a live instance of this application at <a href='http://try-prediction.appspot.com'>http://try-prediction.appspot.com</a>.<br>
<br>
<h2>Prerequisites</h2>

Before using this project, you should familiarize yourself with the <a href='https://developers.google.com/prediction/docs/developer-guide'>Google Prediction API Developer's Guide</a> and experiment with the <a href='https://developers.google.com/prediction/docs/hello_world'>Hello Prediction!</a> sample exercise. You should have at least one trained model of your own to use with the Try Prediction app.<br>
<br>
<h2>Dependencies for the Python version:</h2>

<ul><li><a href='http://www.python.org'>Python 2.5 or later</a>
</li><li><a href='http://code.google.com/appengine/'>Google App Engine</a>
</li><li><a href='http://code.google.com/p/google-api-python-client/'>Google Python API Client</a>
</li><li><a href='http://code.google.com/p/python-gflags/'>Command line flags modules for Python</a>
</li><li><a href='http://code.google.com/p/httplib2/'>HTTP Client Library for Python</a>
</li><li><a href='http://code.google.com/p/google-api-python-client/'>Google OAuth 2.0 Client Library for Python</a>
</li><li><a href='http://code.google.com/p/uri-templates/'>URI Templates for Python</a></li></ul>

<h2>Dependencies for the Java version:</h2>

<ul><li><a href='http://www.java.com'>Java 5 (or higher) standard (SE) and enterprise (EE)</a>
</li><li><a href='http://code.google.com/appengine/'>Google App Engine</a>
</li><li><a href='http://maven.apache.org/'>Maven</a>
</li><li><a href='http://code.google.com/p/maven-gae-plugin/'>Maven Plugin for App Engine</a></li></ul>

<h2>Getting Started</h2>

<ol><li>Download the latest tarfile available on the "Download" tab.<br>
</li><li>Unpack the runtime package into a new directory.<br>
</li><li>Customize the following files:<br>
<ul><li>In shared/rc/client_secrets.json, replace the placeholder strings with your actual client id and client secret from the <a href='http://code.google.com/apis/console'>Google APIs console</a>.<br>
</li><li>In shared/rc/models.json, enter information about the model(s) you would like to use , following the format shown for the two sample models.<br>
</li><li>Java only: edit gae-java/src/main/java/com/google/tryPredictionJava/web/IndexServlet.java to specify your redirect URI, which should be your app's base URI + /auth_return, e.g. <a href='http://your-app-name.appspot.com/auth_return'>http://your-app-name.appspot.com/auth_return</a>.<br>
</li><li>Java and Python: Add your redirect URI (as defined in previous step) to the list of valid redirect URIs in the "API Access" tab of the APIs Console. If you miss this step, you'll get a 'redirect_uri_mismatch' error during initial authorization of the shared server credentials.<br>
</li></ul></li><li>Build and deploy your app:<br>
<ul><li>For Python: modify the "application:" line in your app.yaml file to reflect your chosen app name and use the Google App Engine tools to deploy your app.<br>
</li><li>For Java: modify the contents of the "application" XML element in your gae-java/src/main/webapp/WEB-INF/appengine-web.xml file to reflect your chosen app name and use the Maven plugin for Google App Engine to deploy your app (you need to run "mvn gae:unpack" once and then you can subsequently deploy your app repeatedly with "mvn gae:deploy").<br>
</li></ul></li><li>The first time you access your app, it will step you through the login and OAuth 2.0 sequence, however, all access thereafter, by you or anyone else, will reuse your initially established security credentials. If you ever wish to change or re-establish the shared server credentials, simply visit your service's URI with the "/reset" suffix (note that the reset service can only be invoked by the application administrator).</li></ol>

<b>Try Prediction is brought to you by the Google Developer Relations team.</b>
</font>
# Onegini Template styling

With this tool you can develop the Thymeleaf templates for Onegini Connect without running the actual products. This makes it possible to style
a template without going through the flow to render a specific screen or trigger sending emails.

## Build the project

You need the following to build the project:
* [Java 11](https://adoptopenjdk.net/)
* [Apache Maven](https://maven.apache.org)
* Access to Onegini Artifactory

Build this project from its root directory:
```bash
mvn clean package
```

## Setup

### CIM Extension resources

This is the location where all custom templates, messages and static resources for Onegini CIM are maintained. It has the following directory structure:

* `email-templates`: custom templates that are used for emails
* `messages`: properties files that contain translations for the custom templates.
    * `messages-personal-branding.properties`: default messages
    * `messages-personal-branding_nl.properties`: translations of messages for locale `nl` (optional, depends on which languages your CIM extension supports)
* `static`: CSS, fonts, scripts and images that are referenced by the custom templates
* `templates`: custom templates that are used for web pages

The default location for the extension resources is `/opt/onegini-styling/extension-resources`. To set a different location use the environment variable 
`STYLING_EXTENSIONRESOURCESLOCATION`.

### CIM Core resources

When you start developing templates for Onegini CIM, most screens and emails will use the core resources. These are the default templates, messages and styling 
from the standard product. Over time you will replace the core resources with extension resources to create your custom look and feel.

Within the core resources the following directory structure is expected (all are optional if you only use extension resources):
* `email-templates`: default templates that are used for emails
* `messages`: properties files that contain the translations for the default templates
* `static`: CSS, fonts, scripts and images that are referenced by the default templates
* `templates`: default templates that are used for web pages

The default location for the core resources is `/opt/onegini-styling/core-resources`. To set a different location use the environment variable `STYLING_CORERESOURCESLOCATION`.


### Token Server styling

Within the Onegini Token Server the following directory structure is expected for both the default resources and the custom resources:
* `email-templates`: templates that are used for emails in the Token Server
* `messages`: properties files that contain the translations for the templates
    * `messages.properties`: default messages
    * `messages_nl.properties`: translations of messages for locale `nl` (optional, depends on which languages your Token Server supports)
* `templates`: templates that are used for web pages in the Token Server

*Note:* The Token Server error template should be called using `/error` as template name and requires a `_` as prefix for the yaml file. This is _only_ for 
the error template.

The default location for the Token Server default resources is `/opt/onegini-styling/tokenserver-default-resources`. To set a different location use the 
environment variable `STYLING_TOKENSERVERDEFAULTRESOURCESLOCATION`.

The default location for the Token Server custom resources is `/opt/onegini-styling/tokenserver-custom-resources`. To set a different location use the 
environment variable `STYLING_TOKENSERVERCUSTOMRESOURCESLOCATION`.

### YAML template configuration

The ModelMap that is used to render the template can be configured via YAML files. The standard location for these YAML files
is `./template-config`. The yaml files should be named similar to the templates relative to the directory that contains the templates 
(`email-templates` or `templates`). Replace any `/` in the path with `__`. 

E.g. the template `personal/pages/login.html` is configured in the file `personal__pages__login.yaml`.

Specify the directory with the template configurations via the environment variable `STYLING_TEMPLATECONFIGLOCATION`.

Example template configuration file:

```yaml
showLogin: true
showUnpLogin: true
showSignUp: true
showQRLogin: false
migrationLoginFlow: false
showEmailOnlyLogin: false
showInlineSignUp: false
loginUnrestrictedFormat: true
showRemindUsername: true
mobileLoginEnabled: false
mobileLoginEmail: somebody@example.com
mobileLoginUsernameLdap: somebody@example.com
csrfToken: abcdef12345
optionalMobileLogin: true
showIdps: true
isActionTokenLinkEnabled: false
loginForm:
  !!com.onegini.styling.mocked.model.LoginForm  
  email: 
  password:
  confirmPassword:
idps:
  - !!com.onegini.styling.mocked.model.Idp
    id: 1234567
    type: facebook
    enabled: true
    name: Facebook
  - !!com.onegini.styling.mocked.model.Idp
    id: 7654321
    type: google_oauth2
    enabled: true
    name: Google
```

### Language

The default locale is `en`. To change the default locale set the environment variable `STYLING_DEFAULTLOCALE`. During runtime you can change the locale via the
request parameter `language` e.g. `&language=fr`.

## Run the project

Run the project from the commandline.

```bash
mvn spring-boot:run
```

Then access the application via [http://localhost:8000](http://localhost:8000).

You can pass configuration via environment variables. For example:

```bash
mvn spring-boot:run -DSTYLING_DEFAULTLOCALE=nl_NL
``` 

### IntelliJ IDEA

Install the following plugins:
* Spring Boot 
* Lombok

The `StylingApplication` will be available as Spring Boot application in the run configurations.

### VS Code

Install the following plugins:
* [Spring Boot Dashboard](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-spring-boot-dashboard)
* [Lombok Annotations Support for VS Code](https://marketplace.visualstudio.com/items?itemName=GabrielBB.vscode-lombok)

The `standalone-styling` will be available under the Spring Boot Dashboard.

## Advanced configuration

### Gulp

This project contains a sample Gulp configuration to compile SCSS to CSS and bundle JavaScript files. To use Gulp for SCSS and JS (Babel) compilation:

1. Navigate to the `gulp` directory
2. Run `npm i`
2. Copy the `example.config.json` file and rename it to `config.json`
3. Change the configuration to your own needs
    - The variables in `src` being the path to the source of the files to compile
    - The variables in `target` being the path to the files where to be compiled to
    - `htmlLocation` being the path to the html files to watch for Browsersync
4. Run `gulp`

### Run with bash script
The bash script starts both the Gulp task and the standalone styling tool.

1. If you use Windows, use [git bash](https://gitforwindows.org/) to run the script.
2. Create a config file with the correct locations of your files (see example.cfg)
3. Start script by running:
  ```
  bash start-standalone-styling.sh <config file>
  ```
  Replace \<config file\> with the config file you have made for the project you want to open
4. Stop the script with `ctrl-c`
# Onegini Template styling

With this tool you can develop the Thymeleaf templates for Onegini Connect without running the actual products. This makes it possible to style
a template without going through the flow to render a specific screen or trigger sending emails.

## Prerequisites

You need the following to run the project:
* [Java 11](https://adoptopenjdk.net/)
* [Apache Maven](https://maven.apache.org)
* Access to Onegini Artifactory
* Python 3.*
* Node >= 12

## Setup

### CIM Extension resources

This is the location where all custom templates, messages and static resources for Onegini CIM are maintained. It has the following directory structure:

* `email-templates`: custom templates that are used for emails
* `js`: the scripts that are referenced by the custom templates which will be compiled
* `messages`: properties files that contain translations for the custom templates.
    * `messages-personal-branding.properties`: default messages
    * `messages-personal-branding_nl.properties`: translations of messages for locale `nl` (optional, depends on which languages your CIM extension supports)
* `scss`: SCSS files that are referenced by the custom templates which will be compiled
* `static`: assets that are referenced by the custom templates
    * `css`: custom css files
    * `fonts`: custom fonts
    * `img`: custom images
* `templates`: custom templates that are used for web pages
    * `personal`: customer facing templates
        * `components`: small bits of reusable code (like the csrf_token)
        * `fragments`: bigger bits of reusable code (like reusable forms)
        * `layouts`: layouts for the pages (uses Thymeleaf Layout Dialect)
        * `pages`: custom pages

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

For front-end development the Spring Boot server can be run together with the Gulp task runner to see changes immediately.
A python script has been created to load the configuration and start the project. It can also build a new JAR file.

On first use, run `npm i` to install node dependencies.

### Creating a config file

As a default, the script looks for a `config.json` file in the root of this project.
The configuration file can be used for different projects. 

See `example.config.json` for an example on how to setup the config file.
A value not specified in a project uses the value from `default`.

### Using the script

To run a project, the script can be started by running `python3 start-env.py`
The script accepts the following arguments:
-h: show help for script
-e <argument>, --environment <argument>: specify the environment (project) to load from config file. When omitted it will use `default`
-c <file location>, --configuration <file location>: specify alternative config file to load. When omitted it will use `config.json`
-b, --build: Build a new JAR file 

To build a project onegini (specified in config.json) we run `python3 start-env.py -e onegini`

Close the script using `crtl + c`

# VANotify Java client (fork of GOV.UK Notify Java client)

Use this client to send emails, text messages and letters using the [VANotify](https://notifications.va.gov) API.

## Useful links

- [Documentation](https://github.com/department-of-veterans-affairs/notifications-java-client/blob/main/DOCUMENTATION.md) - For developers interested in using the VANotify Java client to send emails and text messages
- [Artifact repository](https://github.com/department-of-veterans-affairs/notifications-java-client/packages/990487)
- [Changelog](https://github.com/department-of-veterans-affairs/notifications-java-client/blob/main/CHANGELOG.md)
- [Contributing to this client](https://github.com/department-of-veterans-affairs/notifications-java-client/blob/main/CONTRIBUTING.md)

## Development

Install [Maven](https://maven.apache.org) to work with this code base.  If you are not familiar with Maven, read through [Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).  Also read [Introduction to the Build Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html).

For a more verbose description of the development process, read [CONTRIBUTING.md](https://github.com/department-of-veterans-affairs/notifications-java-client/blob/main/CONTRIBUTING.md).

### Environment file

This repository contains unit and integration tests.  The latter make API calls and presume certain environment variables.  To set up your environment, copy .env.example to .env, and substitute templated values as appropriate.  The example file assumes API's Development environment and that you are using a Unix-like operating system.

Activate the environment in Bash by running `. .env`.

### Workflow

1. Make your desired changes.  This should include bumping the version number in the files pom.xml and application.properties, and it should include creating a new entry in CHANGELOG.md.
2. Run `mvn clean test` to run unit tests.  If any test fails, return to step 1.
3. When all unit tests pass, run `mvn clean install` to execute all phases of the lifecycle other than deploying to Github.  This includes running integration tests.  Note that your environment must be properly configured at this point or you will get errors.
4. The final step is to deploy the package to Github so other projects can depend on it.  If you have not already created your local .m2/settings.xml file and Github access token, read [Working with the Apache Maven registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry).  Then run `mvn deploy`, visit [here](https://github.com/department-of-veterans-affairs/notifications-java-client/packages/990487), and ensure the displayed version reflects the version you created in step 1.

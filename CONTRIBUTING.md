# Contributing

Pull requests are welcome.

## Setting Up

### Docker container

This app uses dependencies that are difficult to install locally. In order to make local development easy, we run app commands through a Docker container. Run the following to set this up:

```shell
make bootstrap-with-docker
```

### Environment file

If you're part of the VANotify team you can download environment file with settings required for running integration tests from our AWS Parameter Store. 

After logging in with AWS CLI, in the root directory of the repo, run:

```
aws ssm get-parameter --with-decryption --name /credentials/client-integration-tests | jq '.Parameter.Value' -r > .env
```

## Tests

### Unit tests

To run the unit tests:
```shell
make test
```
or using docker:
```shell
make test-with-docker
```

### Integration Tests

To run the integration tests:

```shell
make integration-test
```

or using docker:
```shell
make integration-test-with-docker
```

This step requires environment variables from the [.env](#Environment-file) file.

## Update version

Increment the version in the `src/main/resources/application.properties` and `pom.xml` files following semantic versioninig.

## Deploying

To deploy artifacts to maven repository in Github packages run the `deploy.sh` script.

```shell
./deploy.sh
```

This step requires the [.env](#Environment-file) file.

You will also need to configure maven access to github. In your settings.xml (`~/.m2/settings.xml` or `.m2/settgins.xml`) you should have:
```xml
<settings>
	<servers>
		<server>
			<id>github</id>
			<username>github-username</username>
			<password>github-personal-access-token</password>
		</server>
	</servers>
</settings>
```

## Creating a release

After updating version and deploying artifacts you can create a new release for the client.

Make sure that `CHANGELOG.md` is updated with description of changes.

Create a tag with the new version:
```shell
git tag 1.2.0
git push --tags
```

Next create a new release in Github UI from the new tag: [https://github.com/department-of-veterans-affairs/notifications-java-client/releases/new](https://github.com/department-of-veterans-affairs/notifications-java-client/releases/new) with `release title` set to the new version.
For release description use information from `CHANGELOG.md`.


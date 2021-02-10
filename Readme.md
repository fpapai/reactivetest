## Experiment using SpringBoot, Docker, Reactive.

##Testing different Java app dockerizations

### Palantir

https://plugins.gradle.org/search?term=com.palantir.docker

It requires local docker CLI.
It can add local or remote repository.

Todo, test authentication of pushed to Artifactory.

```gradle bootJar docker```

### Jib

https://github.com/GoogleContainerTools/jib

Made by Google.
Can work with and without local docker CLI.
It can add local or remote repository.

With local docker:

```gradle jibDockerBuild```

Todo, test authentication of pushed to Artifactory.

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

 * ```gradle jibDockerBuild```
 uses local docker cli, it stores image locally

 * ```gradle jib``` pushes image to remove repository. It does not need local docker installation. Uncomment the documented code in build.gradle. Username/passwords are required. Can be set in build file or provided in CLI.
 

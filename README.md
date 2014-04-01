com-ar-reportsrv (aka Report Server)
====================================

##dependency order:

1. reportsrv-client -> com-ar-shared
2. reportsrv-core -> com-ar-shared, prodcycle-client
3. reportsrv-ejb -> com-ar-shared, reportsrv-client, reportsrv-core
4. reportsrv-jasper -> reportsrv-core
5. reportsrv-web -> reportsrv-core

##build notes:

There is an issue with the gradle ear plugin that causes a war file:

    dependencies {
        ...
        deploy project(":reportsrv-web")
        ...
    }

to be included in the ear as a jar, which won't work for an ear.  So there is a
work around that will help.  See it [here](http://forums.gradle.org/gradle/topics/problem_with_the_ear_plugin)

The work around looks like:

    dependencies {
        ...
        deploy project(path: ":reportsrv-web", configuration: "archives")
        ...
    }

For the record I have no real clue why this works, I just noticed it happening and searched for a
solution and came across the work around on the gradle forums (link above).
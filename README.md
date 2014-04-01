gradle-ear
====================================

A sample project that shows one way to build those pesky ear artifacts that J2EE containers use.

##build notes:

There is an issue with the gradle ear plugin that causes a war file:

    dependencies {
        ...
        deploy project(":web")
        ...
    }

to be included in the ear as a jar, which won't work for an ear.  So there is a
work around that will help.  See it [here](http://forums.gradle.org/gradle/topics/problem_with_the_ear_plugin)

The work around looks like:

    dependencies {
        ...
        deploy project(path: ":web", configuration: "archives")
        ...
    }

# TestRail-JUnit
This  Library helps you to integrate your JUnit-based tests with TestRail in an easily manner.

## Installation
1 Since it's not pushed to the MVN central repository yet, there are two installation options:

* Download already compiled and packaged **TestRail-JUnit-1.0.jar** file [HERE][here] and proceed to the 2nd step
* Download source code [HERE][here] to your local machine, make a jar by running **`mvn package`** in the repo directory and proceed to the 2nd step

2 Put a **TestRail-JUnit-1.0.jar** file into your project directory (for instance – create root dir `~/libs/` and paste there)

3 Add this dependency to your `pom.xml` (**systemPath** value is for instance)
````
    <dependency>
      <groupId>com.github.rsheremeta</groupId>
      <artifactId>TestRail-JUnit</artifactId>
      <version>1.0</version>
      <scope>system</scope>
      <systemPath>${basedir}/libs/TestRail-JUnit-1.0.jar</systemPath>
    </dependency>
````

## Usage
1. See my article on Medium – [Link][link]

![Medium article](https://i.ibb.co/kBJnC55/2021-02-08-12-52-20.png)

[here]: https://github.com/RSheremeta/TestRail-JUnit/releases
[link]: https://rsheremeta.medium.com/integrate-your-junit-tests-with-testrail-bac4bfe31111

# RDF_GraphCreation
This project corresponds to the master’s research project. Hence, it will have its own grade (3 ECTS). The project spans across the Symbolic AI and Reasoning) (SAIR) and Data Engineer courses. We will dedicate some lab sessions to it but you will also have to spend some additional time to complete the different implementation steps. Considering the SAIR course, your project group needs to create some RDF data sets on people who have been vaccined for covid-19. You will also generate information about side effects witnessed for these persons. You will generate SPARQL queries to conduct some analysis over these data. You will use and create a set of ontologies associated to the information and knowledge stored in these data sets. During the Data Engineer’s course, you will submit the side effects information to the Apache Kafka commit log, run some “real-time” analysis with Spark’s structured streaming, and display some visualization.


# Groupe

We where a groupe of three student to do this project:  
[Mamadou CISSE](https://github.com/mciissee) ,[Eric TRAN](https://github.com/etran2907) , [Volkan ZULAL](https://github.com/volkanzulal)

# How to use this Project

You can open with IntelliJ IDE simply run the build to install the depandancies and you can run different main thanks to the integradted runner or IntelliJ.

# About the project

The project use the data from http://swat.cse.lehigh.edu/projects/lubm/. And we injected some data on it with JavaFaker. The data injected was about the personne who have been vaccineted. We follow the order give by our Proffesor thanks to percentage.

```
We will use a data set generated using the Lehigh University Benchmark (LUBM). 
You can find more information on LUBM at: http://swat.cse.lehigh.edu/projects/lubm/. 
This benchmark corresponds to a Javaprogram that enables to create data sets of varying sizes. 
With one university (LUBM1 - the one currentlyproposed on the e-learning website), the data set 
contains over 100.000 triples.It contains information aboutstudents, professors, courses, departments, etc.
In this session, you first have to familiarize yourself with the data set. Using Apache Jena, 
you willimplement some programs to extend the RDF graph with information about persons. 
Basically, we will addan identifier(integer), last and first names, gender (male or female),
zipcode, date of birth to each person.Make these information consistent, 
meaning that a student’s age must been between 20 and 30 years old,a professor’s age is between 30 and 70.
Moreover, a proportion of the persons in the data sets have beenvaccined for covid-19. 
You have to extend the data set with a vaccination date and vaccine name for thesepersons.
At the moment, the set of vaccines is as follows (but it can be increased later on):
Pfizer, Moderna,AstraZeneca, SpoutnikV and CanSinoBio.Your data set extension must be parametrable and adaptable.
Some of these paramaters corresponds tothe number of persons that have been vaccined,
the proportion of men and women (for instance 48% of menand 52% of women), the proportion of each vaccine used among the vaccinees.
Adaptable because we willbe asked to run your program extension on larger LUBM data sets.To extend the persons’ records,
you will use the Java Faker project (https://github.com/DiUS/javafaker).
The dataset concerns US citizens so zipcodes and states have a US context. 
```


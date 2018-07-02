# Transaction assessment

## Prerequisites

*Java 8*

## Run application

1. Navigate to root application folder
2. Choose the desired action and execute

## Available actions

### Add

##### Command
```
java -jar payclip-assessment-1.0.0.jar <user_id> add <transaction_json>
```

##### Example
```
java -jar payclip-assessment-1.0.0.jar 345 add "{\"amount\": 1.23, \"description\": \"Joes Tacos\", \"date\":\"2018-07-01\", \"user_id\": 123}"
```

### Show

##### Command
```
java -jar payclip-assessment-1.0.0.jar <user_id> <transaction_id>
```

##### Example
```
java -jar payclip-assessment-1.0.0.jar 123 0745884f-eb95-48bc-940a-e2750d96a960 
```

### List

##### Command
```
java -jar payclip-assessment-1.0.0.jar <user_id> list
```

##### Example
```
java -jar payclip-assessment-1.0.0.jar 123 list
```

### Sum

##### Command
```
java -jar payclip-assessment-1.0.0.jar <user_id> sum
```

##### Example
```
java -jar payclip-assessment-1.0.0.jar 123 sum
```

## Build application

1. Navigate to root application folder
2. Execute the following command
3. Jar file will be placed inside *../build/libs*
4. Move Jar to root application folder

##### Linux/OSX
```
./gradlew clean bootJar
```

##### Windows
```
gradlew.bat clean bootJar
```

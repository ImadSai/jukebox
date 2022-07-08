# Specifie the Image Used
FROM openjdk:12-jdk-alpine

# Define the working directory
WORKDIR /usr/app

# Copy the jar in app folder
COPY target/jukeboxSettings.jar /usr/app/juckboxSettings.jar

# We expose only the 8080 port
EXPOSE 8080

# Command to launch the app
CMD ["java", "-jar", "/usr/app/juckboxSettings.jar"]

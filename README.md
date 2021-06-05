# Readme

Student number : s3767707
Course name: Further Programming (2110)

# Packaging
The main class is Main.java

Packaging for classes:
 - main.controller
 - main.model
 - main.ui
Packaging for test:
 - test.model

## How to clone the project using intelliJIDEA and RUN the application
1- Download IntelliJ IDEA Ultimate Version (You had to apply for student license)

2- Open IntelliJ IDEA, select "File" from the top menu, select "New" and select "Project from Version Control"  

3- Copy your Github classroom repository and paste into URL, click on "Clone".
 Your project will be cloned and open in your IntelliJ IDEA window.
 
 However, you still need to add the SQLite jar file to your project so you can have access to your database. Follow next steps for adding the Jar file:
 
1- Download the SQLite JDBC jar file from week 7 Canvas module.

2- In your project under project root, make a new directory called lib and move the jar file into lib folder

3- Open IntelliJ IDEA, click on "File", open "Project Structure"

4- Under "Project Setting", select "Libraries"

5- Click + button, choose Java, and navigate to your project folder, then Lib folder, choose "sqlite-jdbc-3.34.0.jar", and click on "open"

6- Click on Apply and then OK to close the window

Now you are ready to Run the Application.

Simply right click on Main.java and choose Run.
Congratulations!

Login info:

Username: test

Password: test


## Prepare other content

Readme files are made for developers (including you), but also could be used for the final users.
So while you are writing your readme files please consider a few things:

1. What is about?  
Arub is a system to manage ‘Hotdesking’ methodology to allocate tables and seats to Arub employees.
Users can register and login to their account and make their bookings by entering date and choose time. Bookings are  
made by clicking on the seats where:  
    - green -> available  
    - orange -> locked  
    - red -> booked  
User can also edit time and seat of a booking and choose to cancel it within 48 hours of the booking date.
Admin have three main features:
    - Booking management  
        Admin can accept and reject bookings. Besides that, admin can choose to apply restriction by locking down certain seats for a certain amount of time 
    - Account management  
        Admin can update, remove and add both users and admins account
    - Generate report   
        Admin can generate account report and booking report. Booking report generated will be sorted by date and admin can specify what date of boookings should they export.  
2. What steps need to be taken?
    - The project main class will be main.java
    - Please make sure to include the Junit 5 Jar file before running the test packages
    - Please make sure to run all the unit tests first before executing the program as changes in database may result in changes of outcome of test 
    - Right click on the main.java and select "Run 'Main.main()'" to run the project  
3. Execution examples
    - You could provide examples of execution with code and screenshots
    

other things you could add:

- Table of content
- Test cases
- Know bugs
- Things that have not been working or complete



### References and tutorials for Readme (Markdown)
- **IntelliJ IDEA MarkDown guide**. jetbrains.com/help/idea/markdown.html
- **Choose an open source license**. Github. Available at: https://choosealicense.com/
- **Getting started with writing and formatting on Github**. Github. Available at: https://help.github.com/articles/getting-started-with-writing-and-formatting-on-github/
- **Markdown here cheatsheet**. Markdown Here Wiki. Available at: https://github.com/adam-p/markdown-here/wiki/Markdown-Here-Cheatsheet
- **Markdown quick reference**. Wordpress. Available at: https://en.support.wordpress.com/markdown-quick-reference/
- **readme-template**. Dan Bader. Github. Available at: https://github.com/dbader/readme-template
- Writing READMEs. **Udacity**. Available at: https://classroom.udacity.com/courses/ud777/

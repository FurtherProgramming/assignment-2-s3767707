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
 - main.test

## How to clone the project using intelliJIDEA and RUN the application (Updated)
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

6- Click + button again, choose maven, and download junit 5.4 library from url org.junit.jupiter:junit-jupiter:5.4.2  

7- Click on Apply and then OK to close the window

Now you are ready to Run the Application.

Simply right click on Main.java and choose Run.
Congratulations!

Database table attribute description:  

1- Employee Table
 - emp_id :  Arub employer id  
 - firstname :  Employer's first name  
 - lastname :  Employer's last name  
 - role :  Employer's account role  
 - username :  Employer's account username  
 - password :  Employer's account password  
 - secret_question :  Employer's account secret question to reset password  
 - answer :  Employer's answer for secret question  
 - status :  Employer account status (sctivated/deactivated)  
 Note: Activated account can access all functions while deactivated account can only view profile menu  
 
 2- Booking Table
  - id :  Booking id 
  - username :  Employer's username  
  - seat_id :  Booked seat no.  
  - booking_date :  Booking date  
  - booking_time :  Booking time (Time where seat is available for check-in)  
  - status :  Booking status (Accepted/Pending/Rejected)  
  Note: Accepted-> Approved by admin, Pending->Have not been reviewed by admin, Rejected->Cancelled by admin   
  - check_in :  Attribute to determine whether the seat is seated by that user during the booking session  
  
 3- Seat Table
  - id :  Seat no. 
  - status : Seat that will be affected by social distancing (0/1)  
  Note: 0->No, 1->yes
  - condition :  Seat condition (Normal,Restriction,Lockdown)  
  Note: Normal->Seat available, Restriction->Seat locked due to COVID social distance, Lockdown->Seat locked due to COVID lockdown  
  - start_date :  Date where COVID condition applies  
  - end_date :  Date where COVID condition ends  
 
 
 
User manual rules:  
(***Important: Only special and complex features are included here***)  
Login info:

1- Admin:  
 - Username: test  
   Password: test    
   
2- User:
 - Username: a  
   Password: a    
 - Username: q   
   Password: q    
 - Username: w   
   Password: w         
 - Username: e   
   Password: e    

3- Activated account can access all functions

4- Deactivated account can only access profile page  

5- Admin can choose to log in as admin or user. Log in as admin will only have access to admin functions. If admin wants to access normal user functions, admin would need to log out and log in as user again.

Register info :  

1- Employer id and username must be unique


User Make Booking:

1- User cannot book the same seat with the same date and time again when the booking status is pending and accepted.  

2- User cannot book the same seat with the same date and time again if the booking gets rejected given that the seat is available for booking.  

3- User cannot book the same seat as the previous booking regardless of booking status

4- If a user books a seat and the booking is either pending or accepted, others will not be able to book it at that particular date and time.
Note: The seat is opened again if that booking gets rejected.

5-  Additional feature where user cannot sit beside same Employees that have been sitten previously is implemented by restricting user from booking the seats beside the seat booked by previous employees that have checked in and seat beside each other.  
Note: Only applies to previous **checked in** bookings. Restricting bookings would be more reasonable and efficient than restricting sitting as users may find annoying if they booked a seat and found out they cannot sit on it later. It also removes the need of admin checking whether they sit beside each other in the previous booking before accepting their current booking.


User Update Booking:  

1- User can only update pending bookings

2- User can only change their seat when updating booking

3- The seat of **latest** booking will not be available even when user update the booking before it(user cannot book the same desk as the previous booking).  

4- The seat beside same Employees that have been sitten previously will not be available.  

User check in:  

1- The system follows the current date and time  

2- User can only check in when the booking date is the same as current date and the time is within the range of the session(0800-1359,1400-2359)  

3- User can only check in with accepted bookings  

Admin manage bookings:  

1- Admin can only see pending bookings   

Admin COVID seat management  

1- The system can only apply one COVID condition at a time. If a new COVID condition is issued, it substitutes the previous condition  

2- There are three COVID condition(Normal->all seats are available, Restriction->Even seat ids will be locked, Lockdown->All seats will be locked)  

3- Applying the condition will automatically manage all the seats without the need of admin to choose which seats to lock.  

4- Bookings that lies within the condition duration will be automatically cancelled.  

5- Reset button automatically set all the seats to normal (Cancelled bookings will not be restored)
Note: Normal condition does not have any duration  


Admin account management:  

1- Admin cannot update and delete his own account  

2- Admin cannot deactivate other admin account  

3- Removing an account will remove all its bookings from the database  


Admin report management:  

1- Admin can choose to export booking reports by date. Clicking the export button will export only what is currently shown in the table.

  

 



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
3. Lessons learned  
    - How to create classess by taking OOP into consideration  
    - Has a deeper insight and nderstanding of the MVC structure   
    - Learns and visualize the overall steps and requirements needed to complete a full application project.  
    - Learn how javafx elements are linked with java code in order to produce a user-interace based app.  
4. Difficulties faced  
    - Finds it hard to determine what methods to put in classes
    - When implementing booking function, discover lots of uncertainties in making design decision which makes the code implementing process complex.
      
        
   
    



### References and tutorials for Readme (Markdown)
- **IntelliJ IDEA MarkDown guide**. jetbrains.com/help/idea/markdown.html
- **Choose an open source license**. Github. Available at: https://choosealicense.com/
- **Getting started with writing and formatting on Github**. Github. Available at: https://help.github.com/articles/getting-started-with-writing-and-formatting-on-github/
- **Markdown here cheatsheet**. Markdown Here Wiki. Available at: https://github.com/adam-p/markdown-here/wiki/Markdown-Here-Cheatsheet
- **Markdown quick reference**. Wordpress. Available at: https://en.support.wordpress.com/markdown-quick-reference/
- **readme-template**. Dan Bader. Github. Available at: https://github.com/dbader/readme-template
- Writing READMEs. **Udacity**. Available at: https://classroom.udacity.com/courses/ud777/

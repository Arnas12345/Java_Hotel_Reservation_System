# HotelReservationSystem

**Firstly compile the HotelSimulation class and run the main.

1.To begin you have to run the HotelSimulation.java 
When the program is ran you are offered which hotel to choose: 
A)  5-star
B)  3-star
C)  4-star
2.Choose which hotel you would like to use a,b,c.(It is not case-sensitive) 

3.Then you are offered which interface you would like to use
  **password is required for the receiptionist and supervisor**
    LOGINS - receiptionist interface : USERNAME = reception  and PASSWORD = apples.
             supervisor interfeace :   USERNAME = supervisor and PASSWORD = pears.

1) Customer Interface (Customer can add reservations cancel and quit)
2) Receiptionist Interface (The receiptionist inteface can do everything a customer can do aswell as being able to check in and check out a reservation)
3) Supervisor (The supervisor can do everything the receiptionist can do but also Apply discounts, **Hotel Analysis**, Remove from System)
 Q)uit  


What each option does:
-->Avalible for Supervisor,Receptionist and Customer
A)dd Reservation --> step 1. add your date
		             What check in date (DD/MM/YYYY): eg. 24/11/2019
                     step 2. How many nights? eg. 4
		     step 3. How many Adults? eg. 3
		     step 4. How many Children? eg. 2
		     step 5. Select a room a,b or c
		             What rooms would you like: 
			     A) Classic Double Max Adults: 2 Max Children: 0       
			     B) Classic Twin Max Adults: 2 Max Children: 1
                             C) Classic Single Max Adults: 1 Max Children: 1 
			     *The system will not allow you past this point until you have filled all adults and children into a room*
		     step 6. Enter your booking name -->Firstname: James 
							Lastname: Gosling
							James Gosling
		     step 7. Enter your email --> Email Address: javajames@gmail.com
		     step 8. Would you like breakfast? (Y/N) : y or n
		     step 9. System will return the revervation
 
C)ancel Reservations --> step 1. Enter Reservation ID: eg.1
			 step 2. System will remove that reservation

-->Avalible for Supervisor,Receptionist

S)how Reservations --> shows all reservations currently in the system


I) Check-In --> step 1. Enter Reservation ID:
		step 2. Will check-in the that reservation 
  
O) Check-Out --> ^^same as above but check-out 

-->Avalible for Supervisor only
D) Apply Discount --> step 1. Enter Reservation ID: eg. 4
                      step 2. Enter Discount Percentage: eg. 0.2
		      Discount will be applied to that reservation

Y) Hotel Analysis --> Select the type of data analysis you want eg. 1,2,3,4
			1) Hotel Occupancy Analysis --> step 1. Input what date you would like to start analysis from (DD/MM/YYYY)
							step 2. Input what date you would like to finsih analysis from (DD/MM/YYYY)
							step 3. Then select the intervals you would like to use 
								A) Daily
								B) Weekly 
								C) Monthly
								D) Other

							step 4. Creates a unique csv file in a subfolder when analysis is complete this contains every person staying in the hotel

			2) Room Occupancy Analysis  --> step 1. Input what date you would like to start analysis from (DD/MM/YYYY)
							step 2. Input what date you would like to finsih analysis from (DD/MM/YYYY)
							step 3. Then select the intervals you would like to use 
								A) Daily
								B) Weekly 
								C) Monthly
								D) Other

							step 4. Creates a unique csv file in a subfolder when analysis is complete it shows occupents of a specific room form the to/from to the dates inputed 

			3) Reservation Billing  Analysis --> step 1. Input what date you would like to start analysis from (DD/MM/YYYY)
							     step 2. Input what date you would like to finsih analysis from (DD/MM/YYYY)
							     step 3. Then select the intervals you would like to use 
								A) Daily
								B) Weekly 
								C) Monthly
								D) Other

							     step 4. Creates a unique csv file in a subfolder when analysis is complete it shows the total amount made by the hotel in the date interval entered

			4) Total Money Recieved --> Prints out the amount of money the hotel has made since its first reservation.

							   
  
R) Remove From System --> step 1. Select what system you would like to remove reservations from
			       1) Current Files  Reservations
			       2) Audit Files Reservations 
				
				 **Current files can remove reservations from the system after one month of being on the system**
				   **Audit files can be removed from the system only after seven years of being there**

**This is a feature we added only for the customer this allows them to simulate a transaction**
P) Pay

		           



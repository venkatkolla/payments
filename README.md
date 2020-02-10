# payments
Luminor assignment
	Read Me 

Payment Application has  implemented the following business flows.

Payment Creation : 
	Payment creation user flow was implemented using with custom validations to meet the type specific requirements.
	Unit and Integration tests are provided as much as possible with in the time constraint.

PaymentsQuerying: 
	First half of the payment querying was implemented successfully.


Features to be implemented:
 	Payment Cancellation
	Notification 
	Country logging 

	
Improvements:
	Error handling needs to be implemented properly.
	Unit and integration test coverage.
	
  

To run the application:

Check out the code.

Build:

  Mvn clean install

Run:

	java -jar target/payments-0.0.1-SNAPSHOT.jar

Test:

curl -i -X POST -H "Content-Type: application/json" -d  ' {"paymentType":"TYPE1","currency":"EUR","amount":100,"debtorIban":"BE71 0961 2345 6769","creditorIban":"GB98 MIDL 0700 9312 3456 78","bic":"DABAIE2D","details":"details","cancellationFee":null,"paymentCancelled":false}' http://localhost:8080/luminor/api/payment

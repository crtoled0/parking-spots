[Back To Index](./index.md)

# ParkingSpotMan

## **Path: /parking-spots/ParkingSpotMan**  

## Description
Endpoint that manages car visits. Allowing to assign a parking spot on check-in and 
also calculate the value to pay on check-out. Finally after confirming payment. The system 
can release a parking spot and mark it as available.

## Scope: Public 

## Services

- [Reserve Available Spot (Checkin)](#Reserve-Available-Spot-(Checkin)).
- [Checkout Visit (Checkout)](#Checkout-Visit-(Checkout)).  
- [Confirm Payment](#Confirm-Payment).  

-----------

## Reserve Available Spot (Checkin)

Endpoint to retrieve and reserve last available parking spot for a given car type. 
Car types can be 'standard', '20kw' and '50kw'.

### **Method: GET**
### **Params:**  
- carType (mandatory): Car type. Can be one of these values: 'standard', '20kw' and '50kw'

### **Request Examples**  

```
GET http://localhost:8080/parking-spots/ParkingSpotMan?carType=50kw

```

### **Response Examples**

```
{
    "ok": true, 
    spot: {
        "identifier": "B006",
        "type": "50kw",
        "pricePolicy": "policy-50kw",
        "available": true
    }
}
```

```
{
    "msg": "No Parking Spot Available for 50kw",
    "ok": false
}
```
##### [Back To Top](#ParkingSpotMan)
-----------
## Checkout Visit (Checkout)

Endpoint to create single or multiple parking spots in the system.

### **Method: PUT**
### **Params:**  
- parkIdentifier (mandatory): Parking spot identifier previously obtained from Checkin. 

### **Request Example:**  
```
PUT http://localhost:8080/parking-spots/ParkingSpotMan?parkIdentifier=B006

```

### **Response Example**  

```
{
    "ok": true,
    "spot": "B006",
    "type": "50kw",
    "total": 16.0
}
```

##### [Back To Top](#ParkingSpotMan)
-----------

## Confirm Payment

After checkout this service needs to be called in order to confirm payment and system release parking spot.

### **Method: POST**
### **Params:**  
- parkIdentifier (mandatory): Parking spot identifier previously obtained from Checkin.

### **Request Example:**  
```
POST http://localhost:8080/parking-spots/ParkingSpotMan?parkIdentifier=B006

```

### **Response Example**  

```
{
    "ok": true,
    "spot": "B006",
    "type": "50kw"
}
```

##### [Back To Top](#ParkingSpotMan)
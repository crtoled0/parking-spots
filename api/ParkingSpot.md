[Back To Index](./index.md)

# ParkingSpot

## Description
Endpoint that works as CRUD maintainer of parking spots available in the system.  
Each parking spot is composed of following attributes:  
- identifier: Identifier name for a parking spot. 
- type: Type of cars than this spot will be able to host. Possible values are 'standard', '20kw' and '50kw'.  
- pricePolicy: Price Policy assigned to this Parking Spot.
- available: Boolean value that flags if a parking spot is available to be reserved or not.  
- checkedIn: Timestamp on when a parking spot is used. This field is useful to eventually calculate price charge for car's visit.

## Scope: Private 

## Services

- [Get Parking Spots](#get-parking-spots).
- [Create Parking Spots](#create-parking-spots).  
- [Edit Parking Spot](#edit-parking-spot).  
- [Delete Parking Spot](#delete-parking-spot).  

-----------

## Get Parking Spots

Endpoint to retrieve parking spots from the system.

### **Path: /ParkingSpot** 
### **Method: GET**
### **Headers:** 
- Authorization (mandatory): Must include it although Auth is not yet implemented. 
So any dummy value for know will grant access to the service  
### **Params:**  
- filter (optional): Add keyword to filter returned results. 

### **Request Examples**  

```
GET http://localhost:7005/ParkingSpot

GET http://localhost:7005/ParkingSpot?filter=B0

```

### **Response Example**

```
{
    "ok": true,
    "items": [
        {
            "identifier": "B001",
            "type": "20kw",
            "pricePolicy": "standard-policy",
            "available": false,
            "checkedIn": {
                "seconds": 1586133354,
                "nanos": 15000000
            }
        },
        {
            "identifier": "B002",
            "type": "20kw",
            "pricePolicy": "policy-20kw",
            "available": true
        },
        {
            "identifier": "B003",
            "type": "20kw",
            "pricePolicy": "policy-20kw",
            "available": true
        },
        {
            "identifier": "B004",
            "type": "20kw",
            "pricePolicy": "policy-20kw",
            "available": true
        },
        {
            "identifier": "B005",
            "type": "20kw",
            "pricePolicy": "policy-20kw",
            "available": true
        },
        {
            "identifier": "B006",
            "type": "50kw",
            "pricePolicy": "policy-50kw",
            "available": true
        },
        {
            "identifier": "B007",
            "type": "standard",
            "pricePolicy": "standard-policy",
            "available": false,
            "checkedIn": {
                "seconds": 1586102261,
                "nanos": 159000000
            }
        },
        {
            "identifier": "B008",
            "type": "standard",
            "pricePolicy": "standard-policy",
            "available": true
        }
    ]
}
```
##### [Back To Top](#ParkingSpot)
-----------
## Create Parking Spots

Endpoint to create single or multiple parking spots in the system.

### **Path: /ParkingSpot** 
### **Method: POST**
### **Headers:**   
- Authorization (mandatory): Must include it although Auth is not yet implemented. 
So any dummy value for know will grant access to the service  
### **Params:**  
N/A
### **Request Body:** 
JSON Array Structure of new parking spots  

### **Request Example:**  
```
POST http://localhost:7005/ParkingSpot
BODY:  [{   "identifier": "B005",
            "type": "20kw",
            "pricePolicy": "policy-20kw"
        },
        {   "identifier": "B006",
            "type": "50kw",
            "pricePolicy": "policy-50kw"
        },
        {   "identifier": "B007",
            "type": "standard",
            "pricePolicy": "standard-policy"
        },
        {   "identifier": "B008",
            "type": "standard",
            "pricePolicy": "standard-policy"
        }]

```

### **Response Example**  

```
{
    "ok": true
}
```

##### [Back To Top](#ParkingSpot)
-----------

## Edit Parking Spot

Endpoint to edit Parking Spot.

### **Path: /ParkingSpot/{id}** 
### **Method: PUT**  
### **Headers:** 
- Authorization (mandatory): Must include it although Auth is not yet implemented. 
So any dummy value for know will grant access to the service  
### **Path Variable:**  
- id: Indentifier of Parking Spot to update
### **Request Body:**  
JSON Structure of parking spot to modify

### **Request Example:**  
```
PUT http://localhost:7005/ParkingSpot/B007
BODY:  {
            "type": "standard",
            "pricePolicy": "standard-policy",
            "available": true,
            "checkedIn": null
        }

```

### **Response Example**  

```
{
    "ok": true
}
```

##### [Back To Top](#ParkingSpot)
-----------

## Delete Parking Spot

Endpoint to remove parking spot from the system.

### **Path: /ParkingSpot/{id}** 
### **Method: DELETE**
### **Headers:** 
- Authorization (mandatory): Must include it although Auth is not yet implemented. 
So any dummy value for know will grant access to the service  
### **Path Variable:**  
- id (mandatory): Identifier of parking spot to remove.

### **Request Example:**  
```
DELETE http://localhost:7005/ParkingSpot/B001

```

### **Response Example**  

```
{
    "ok": true
}
```

##### [Back To Top](#ParkingSpot)
-----------